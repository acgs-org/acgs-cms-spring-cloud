package io.github.acgs.cms.controller;

import io.github.acgs.cms.client.RoleServiceClient;
import io.github.acgs.cms.common.exception.UserException;
import io.github.acgs.cms.common.exception.ValidatedException;
import io.github.acgs.cms.role.Role;
import io.github.acgs.cms.entity.User;
import io.github.acgs.cms.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 *     用户注册服务控制器
 * </p>
 *
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/5
 * </p>
 */
@RestController
@RequestMapping("/user")
public class RegisterController {

    /** 导入用户信息仓储对象 */
    private final UserRepository userRepository;

    /** 导入角色信息服务 feign 接口客户端 */
    private final RoleServiceClient roleServiceClient;


    public RegisterController(UserRepository userRepository, RoleServiceClient roleServiceClient) {
        this.userRepository = userRepository;
        this.roleServiceClient = roleServiceClient;
    }

    /**
     * <p>
     *     用户注册接口
     * </p>
     *
     * @param validator 用户注册信息校验器
     * @param result 参数校验结果
     * @return 注册成功返回注册信息
     * @throws ValidatedException 参数校验失败异常
     * @throws UserException 用户信息相关异常
     */
    @PostMapping("/register")
    public Boolean register(@RequestBody @Validated User validator, @NotNull BindingResult result) {
        // 校验入参信息
        if (result.getErrorCount() != 0) {
            System.out.println(result.getAllErrors());
            return false;
        }
        // 通过登录账号获取指定用户
        User user = userRepository.findUserByUsername(validator.getUsername());
        if (Objects.nonNull(user)) {
            // 已获取到指定用户 注册失败
            throw new UserException(60011);
        }
        // 获取角色信息
        String roleName = validator.getRoles().get(0);
        if (Objects.isNull(roleName)) {
            // 没有获取到任何角色信息，注册失败
            throw new UserException(60012);
        }
        Role role = roleServiceClient.getRoleByName(roleName);
        if (Objects.isNull(role)) {
            // 角色信息异常 注册失败
            throw new UserException(60012);
        }
        // 符合预期值 保存用户信息
        validator.setCreateDateTime(LocalDateTime.now());
        userRepository.save(validator);
        return true;
    }
}
