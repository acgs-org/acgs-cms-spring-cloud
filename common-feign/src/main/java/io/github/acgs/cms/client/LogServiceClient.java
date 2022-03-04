package io.github.acgs.cms.client;

import io.github.acgs.cms.entity.log.LoginLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * <p>
 *     日志服务 feign 客户端接口
 * </p>
 *
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/3/4
 * </p>
 */
@FeignClient(name = "log-service", path = "/log")
public interface LogServiceClient {

    /**
     * <p>
     *     登录日志保存接口
     * </p>
     * @param log 登录日志
     */
    @PostMapping("/login")
    void saveLoginLog(@RequestBody LoginLog log);

    /**
     * <p>
     *     登录日志信息获取接口
     * </p>
     * @return 登录日志集合
     */
    @GetMapping("/login")
    List<LoginLog> getLoginLogs();
}
