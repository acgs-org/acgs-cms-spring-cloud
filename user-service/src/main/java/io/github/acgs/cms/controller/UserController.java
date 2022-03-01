package io.github.acgs.cms.controller;

import io.github.acgs.cms.entity.role.Role;
import io.github.acgs.cms.entity.token.Tokens;
import io.github.acgs.cms.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *     用户操作相关接口
 * </p>
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/27
 * </p>
 */
@Api(tags = {"用户操作相关接口"})
@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    /** 导入用户信息服务对象 */
    private final UserService userService;

    /**
     * <p>
     *     用户权限获取接口
     * </p>
     * @param request 访问请求
     * @return 用户角色组
     */
    @GetMapping("/roles")
    @ApiOperation(value = "用户权限获取接口")
    public List<Role> getUserRoles(HttpServletRequest request) {
        return userService.getUserRoles(request);
    }

    /**
     * <p>
     *     刷新 Tokens 令牌方法
     * </p>
     * @param request 访问请求
     * @return 刷新后的 Tokens 令牌
     */
    @GetMapping("/refresh")
    @ApiOperation(value = "刷新令牌接口")
    public Tokens refreshToken(HttpServletRequest request) {
        return userService.refreshToken(request);
    }
}
