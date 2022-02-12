package io.github.acgs.cms.controller;

import io.github.acgs.cms.client.AuthorizationClient;
import io.github.acgs.cms.common.exception.UserException;
import io.github.acgs.cms.common.exception.ValidatedException;
import io.github.acgs.cms.dto.LoginDTO;
import io.github.acgs.cms.entity.User;
import io.github.acgs.cms.repository.UserRepository;
import io.github.acgs.cms.token.Tokens;
import org.jetbrains.annotations.NotNull;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * <p>
 *     用户登录服务控制器
 * </p>
 *
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/2
 * </p>
 */
@RestController
@RequestMapping("/user")
public class LoginController {

    /** 导入用户信息仓储层对象 */
    private final UserRepository userRepository;

    /** 导入身份验证服务 feign 接口客户端 */
    private final AuthorizationClient authorizationClient;

    public LoginController(UserRepository userRepository, AuthorizationClient authorizationClient) {
        this.userRepository = userRepository;
        this.authorizationClient = authorizationClient;
    }

    /**
     * <p>
     *     用户登录接口
     * </p>
     * @param validator 用户登录信息校验器
     * @param result 校验结果
     * @return 登录令牌 Tokens
     * @throws ValidatedException 参数校验失败异常
     * @throws UserException 用户信息相关异常
     */
    @PostMapping("/login")
    public Tokens login(@RequestBody @Validated LoginDTO validator, @NotNull BindingResult result) {
        // 校验入参信息
        if (result.getErrorCount() != 0) {
            throw new ValidatedException(result.getAllErrors());
        }
        // 通过登录账号获取指定用户
        User user = userRepository.findUserByUsername(validator.getUsername());
        if (Objects.isNull(user)) {
            // 没有获取到指定用户 账号不存在
            throw new UserException(60201, "账号不存在");
        }
        // 验证密码信息
        if (!Objects.equals(validator.getPassword(), user.getPassword())) {
            // 验证失败 密码不正确
            throw new UserException(60204, "密码错误");
        }
        // 验证成功，发送 token 数据
        return authorizationClient.getTokens(user.getId().toString());
    }
}
