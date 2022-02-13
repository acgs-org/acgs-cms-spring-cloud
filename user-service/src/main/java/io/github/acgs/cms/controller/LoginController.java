package io.github.acgs.cms.controller;

import io.github.acgs.cms.common.exception.UserException;
import io.github.acgs.cms.common.exception.ValidatedException;
import io.github.acgs.cms.dto.LoginDTO;
import io.github.acgs.cms.service.UserService;
import io.github.acgs.cms.token.Tokens;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *     用户登录服务控制器
 * </p>
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/2
 * </p>
 */
@Api(tags = {"用户登录服务接口"})
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class LoginController {

    /** 导入用户信息服务对象 */
    private final UserService userService;

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
    @ApiOperation(value = "用户登录接口")
    public Tokens login(@RequestBody @Validated LoginDTO validator, @NotNull BindingResult result) {
        // 校验入参信息
        if (result.getErrorCount() != 0) {
            throw new ValidatedException(result.getAllErrors());
        }

        // 验证登录账号和密码
        if (userService.checkUsernameAndPassword(validator.getUsername(), validator.getPassword())) {
            // 验证成功, 发送 Tokens 令牌
            return userService.getTokensByUsername(validator.getUsername());
        } else {
            // 验证失败
            throw new UserException(60204, "用户名或密码错误");
        }
    }
}
