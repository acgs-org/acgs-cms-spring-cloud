package io.github.acgs.cms.controller;

import io.github.acgs.cms.entity.LoginLog;
import io.github.acgs.cms.service.LoginLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *     用户相关日志服务控制器
 * </p>
 *
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/3/4
 * </p>
 */
@Api(tags = {"用户登录服务接口"})
@Slf4j
@RestController
@RequestMapping("/log")
@RequiredArgsConstructor
public class UserLogController {

    private final LoginLogService loginLogService;

    /**
     * <p>
     *     登录日志保存接口
     * </p>
     * @param log 登录日志
     */
    @PostMapping("/login")
    @ApiOperation(value = "登录日志保存接口")
    public void saveLoginLog(@RequestBody LoginLog log) {
        loginLogService.saveLog(log);
    }

    /**
     * <p>
     *     登录日志信息获取接口
     * </p>
     * @return 登录日志集合
     */
    @GetMapping("/login")
    @ApiOperation(value = "登录日志信息获取接口")
    public List<LoginLog> getLoginLogs() {
        return loginLogService.checkLogs();
    }

}
