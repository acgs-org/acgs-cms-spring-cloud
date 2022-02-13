package io.github.acgs.cms.controller;

import io.github.acgs.cms.common.exception.RoleException;
import io.github.acgs.cms.common.exception.UserException;
import io.github.acgs.cms.common.exception.ValidatedException;
import io.github.acgs.cms.entity.User;
import io.github.acgs.cms.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/5
 * </p>
 */
@Api(tags = {"用户注册接口"})
@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class RegisterController {

    /** 导入用户信息服务对象 */
    private final UserService userService;

    /**
     * <p>
     *     用户注册接口
     * </p>
     * @param validator 用户注册信息校验器
     * @param result 参数校验结果
     * @return 注册成功返回注册信息
     * @throws ValidatedException 参数校验失败异常
     * @throws UserException 用户信息相关异常
     */
    @PostMapping("/register")
    @ApiOperation(value = "用户注册接口")
    public Boolean register(@RequestBody @Validated User validator, @NotNull BindingResult result) {
        log.info("register接口被调用");
        // 校验入参信息
        if (result.getErrorCount() != 0) {
            // 参数校验异常
            log.warn("register 接口调用失败");
            throw new ValidatedException(result.getAllErrors());
        }
        // 验证登录账户是否存在
        if (userService.checkUsername(validator.getUsername())) {
            // 已获取到指定用户 注册失败
            log.warn("register 接口调用失败");
            throw new UserException(60202, "账号已存在");
        }

        // 验证角色信息是否正确
        String roleName = validator.getRoles().get(0);

        if (Objects.isNull(roleName)) {
            // 没有获取到任何角色信息，注册失败
            log.warn("register 接口调用失败");
            throw new UserException(60203, "没有所属角色");
        }

        if (!userService.checkRoleName(roleName)) {
            // 角色信息异常 注册失败
            log.warn("register 接口调用失败");
            throw new RoleException(60101, "角色不存在");
        }

        // 符合预期值 保存用户信息
        validator.setCreateDateTime(LocalDateTime.now());

        log.info("信息校验成功，正在保存用户信息...");
        return userService.addUser(validator);
    }
}
