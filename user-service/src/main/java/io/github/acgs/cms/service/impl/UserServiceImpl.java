package io.github.acgs.cms.service.impl;

import io.github.acgs.cms.client.AuthorizationClient;
import io.github.acgs.cms.client.RoleServiceClient;
import io.github.acgs.cms.common.exception.UserException;
import io.github.acgs.cms.entity.User;
import io.github.acgs.cms.repository.UserRepository;
import io.github.acgs.cms.service.UserService;
import io.github.acgs.cms.token.Tokens;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
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

    /** 导入权限验证服务 feign 客户端接口 */
    private final AuthorizationClient authorizationClient;

    public UserServiceImpl(UserRepository userRepository, RoleServiceClient roleServiceClient, AuthorizationClient authorizationClient) {
        this.userRepository = userRepository;
        this.roleServiceClient = roleServiceClient;
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
        return Objects.nonNull(roleServiceClient.getRoleByName(roleName));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public Tokens getTokensByUsername(String username) {
        User user = getUserByUsername(username);
        return authorizationClient.getTokens(user.getId().toHexString());
    }

    @Override
    public boolean addUser(User user) {
        userRepository.save(user);
        return true;
    }
}
