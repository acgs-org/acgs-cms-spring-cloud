package io.github.acgs.cms.service.impl;

import io.github.acgs.cms.client.AuthorizationClient;
import io.github.acgs.cms.client.LogServiceClient;
import io.github.acgs.cms.client.RoleServiceClient;
import io.github.acgs.cms.common.exception.UserException;
import io.github.acgs.cms.entity.User;
import io.github.acgs.cms.entity.log.LoginLog;
import io.github.acgs.cms.entity.role.Role;
import io.github.acgs.cms.entity.token.DecodeTokens;
import io.github.acgs.cms.entity.token.Tokens;
import io.github.acgs.cms.repository.UserRepository;
import io.github.acgs.cms.service.UserService;
import io.github.acgs.cms.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *     用户信息服务实现类
 * </p>
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/13
 * </p>
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    /** 导入用户信息仓储对象 */
    private final UserRepository userRepository;

    /** 导入角色信息服务 feign 客户端接口 */
    private final RoleServiceClient roleServiceClient;

    /** 导入日志服务 feign 客户端接口 */
    private final LogServiceClient logServiceClient;

    /** 导入权限验证服务 feign 客户端接口 */
    private final AuthorizationClient authorizationClient;

    public UserServiceImpl(UserRepository userRepository,
                           RoleServiceClient roleServiceClient,
                           LogServiceClient logServiceClient,
                           AuthorizationClient authorizationClient) {
        this.userRepository = userRepository;
        this.roleServiceClient = roleServiceClient;
        this.logServiceClient = logServiceClient;
        this.authorizationClient = authorizationClient;
        this.initDatabase();
    }

    @Override
    public void initDatabase() {
        log.info("初始化用户数据");
        if (Objects.isNull(userRepository.findUserByUsername("root"))) {
            User root = new User();
            root.setUsername("root");
            root.setPassword("root");
            root.setRoles(Collections.singletonList("root"));
            root.setNick("超级管理员");
            root.setCreateDateTime(LocalDateTime.now());
            userRepository.save(root);
            log.info("载入 root 用户信息");
        }
        if (Objects.isNull(userRepository.findUserByUsername("admin"))) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setRoles(Collections.singletonList("admin"));
            admin.setNick("管理员");
            admin.setCreateDateTime(LocalDateTime.now());
            userRepository.save(admin);
            log.info("载入 admin 用户信息");
        }
        log.info("用户数据初始化成功");
    }

    @Override
    public boolean checkUsername(String username) {
        return Objects.nonNull(getUserByUsername(username));
    }

    @Override
    public boolean checkUsernameAndPassword(String username, String password) {
        User user = userRepository.findUserByUsername(username);
        if (Objects.isNull(user)) {
            // 账号不存在
            throw new UserException(60201, "账号不存在");
        }
        return Objects.equals(password, user.getPassword());
    }

    @Override
    public boolean checkRoleName(String roleName) {
        ResponseVO<Role> res = roleServiceClient.getRoleByName(roleName);
        if (res.isSuccess()) {
            return Objects.nonNull(res.getResult());
        } else {
            return false;
        }
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public Tokens getTokensByUsername(String username) {
        User user = getUserByUsername(username);

        ResponseVO<Tokens> res = authorizationClient.getTokens(user.getId().toHexString());

        LoginLog loginLog = new LoginLog();
        BeanUtils.copyProperties(user, loginLog);
        loginLog.setLoginTime(LocalDateTime.now());

        if (res.isSuccess()) {
            logServiceClient.saveLoginLog(loginLog);
            return res.getResult();
        } else {
            return null;
        }
    }

    @Override
    public boolean addUser(User user) {
        userRepository.save(user);
        return true;
    }

    @Override
    public List<Role> getUserRoles(@NotNull HttpServletRequest request) {
        // 获取请求头信息
        String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
        // 解析请求头信息
        ResponseVO<DecodeTokens> tokens = authorizationClient.verificationAccessToken(auth);
        if (tokens.isSuccess()) {
            // 获取用户 id
           ObjectId id = new ObjectId(tokens.getResult().getAccessToken());
           // 通过 id 获取用户信息
           User user = userRepository.findUserById(id);

           // 验证获取的 user 信息
            if (Objects.isNull(user)) {
                throw new UserException(60201, "账号不存在");
            }
           // 获取用户信息保存的角色组信息并进行校验
           List<String> rolesNames = user.getRoles();
           ResponseVO<List<Role>> roles = roleServiceClient.checkRoles(rolesNames);
           if (roles.isSuccess()) {
               return roles.getResult();
           } else {
               return null;
           }
        } else {
            return null;
        }
    }

    @Override
    public Tokens refreshToken(@NotNull HttpServletRequest request) {
        // 获取请求头信息
        String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
        // 解析请求头信息
        ResponseVO<DecodeTokens> id = authorizationClient.verificationRefreshToken(auth);
        if (id.isSuccess()) {
            // 创建新的 Tokens
            ResponseVO<Tokens> tokens =  authorizationClient.getTokens(id.getResult().getRefreshToken());
            if (tokens.isSuccess()) {
                return tokens.getResult();
            }
        }
        // 服务请求异常
        return null;
    }
}
