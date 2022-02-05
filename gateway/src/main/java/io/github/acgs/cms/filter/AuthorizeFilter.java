package io.github.acgs.cms.filter;

import io.github.acgs.cms.client.AuthorizationClient;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 *     身份验证过滤器
 * </p>
 *
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/1/30
 * </p>
 */
@Order(-1)
@Slf4j
@Component
public class AuthorizeFilter implements GlobalFilter {

    /** 导入身份验证服务 feign 接口客户端 */
    private final AuthorizationClient authorizationClient;

    public AuthorizeFilter(AuthorizationClient authorizationClient) {
        this.authorizationClient = authorizationClient;
    }

    @Override
    public Mono<Void> filter(@NotNull ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取请求
        ServerHttpRequest request = exchange.getRequest();
        // 获取请求路径
        String path = request.getPath().toString();

        // 登录请求访问路径
        String LOGIN_PATH = "/user/login";
        // 注册请求访问路径
        String REGISTER_PATH = "/user/register";

        if (Objects.equals(LOGIN_PATH, path) || Objects.equals(REGISTER_PATH, path)) {
            // 当前请求为 登录 或 注册 请求
            // 无须验证身份，直接放行
            return chain.filter(exchange);
        }

        // 获取请求头 Authorization 中的信息
        List<String> auth = request.getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (Objects.isNull(auth) || Objects.equals(0, auth.size())) {
            // Authorization 请求头中数据不存在, 拒绝访问
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            log.warn("Authorization 不存在, 拒绝访问!");
            // 拦截请求
            return exchange.getResponse().setComplete();
        }

        // 验证 Authorization 中的信息
        ObjectId id = authorizationClient.verificationAccessToken(auth.get(0));

        if (Objects.nonNull(id)) {
            // 获取到 Authorization 中的用户 id
            // 修改请求头信息
            request.getHeaders().remove(HttpHeaders.AUTHORIZATION);
            request.getHeaders().add(HttpHeaders.AUTHORIZATION, id.toString());
            // 打印响应日志信息
            log.info("放行请求: [" + path + "]");
            // 放行请求
            return chain.filter(exchange);
        } else {
            // 令牌失效，打印日志信息
            log.info("Token 令牌失效");
            // 拒绝访问
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            // 拦截请求
            return exchange.getResponse().setComplete();
        }
    }

}
