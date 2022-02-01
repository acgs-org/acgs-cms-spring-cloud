package io.github.acgs.cms.controller;

import io.github.acgs.cms.entity.Role;
import io.github.acgs.cms.entity.User;
import io.github.acgs.cms.exception.UserException;
import io.github.acgs.cms.exception.ValidatedException;
import io.github.acgs.cms.repository.RoleRepository;
import io.github.acgs.cms.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
 * file created on 2022/2/2
 * </p>
 */
@RestController
public class RegisterController {

    /** 导入用户信息仓储对象 */
    private final UserRepository userRepository;

    /** 导入角色信息仓储对象 */
    private final RoleRepository roleRepository;

    public RegisterController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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
    public User register(@RequestBody @Validated User validator, @NotNull BindingResult result) {
        // 校验入参信息
        if (result.getErrorCount() != 0) {
            throw new ValidatedException(result.getAllErrors());
        }
        // 通过登录账号获取指定用户
        User user = userRepository.findUserByUsername(validator.getUsername());
        if (Objects.nonNull(user)) {
            // 已获取到指定用户 注册失败
            throw new UserException(60011);
        }
        // 获取角色信息
        Role role = roleRepository.findRoleByName(validator.getRole());
        if (Objects.isNull(role)) {
            // 没有获取到角色信息 注册失败
            throw new UserException(60012);
        }
        // 符合预期值 保存用户信息
        validator.setCreateDateTime(LocalDateTime.now());
        return userRepository.save(validator);
    }
}
