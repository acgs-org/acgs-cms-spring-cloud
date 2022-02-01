package io.github.acgs.cms.filter;

import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import io.github.acgs.cms.token.DoubleJWT;
import lombok.extern.slf4j.Slf4j;
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

    /** JWT 令牌校验器 */
    private final DoubleJWT doubleJWT;

    public AuthorizeFilter(DoubleJWT doubleJWT) {
        this.doubleJWT = doubleJWT;
    }

    @Override
    public Mono<Void> filter(@NotNull ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取请求
        ServerHttpRequest request = exchange.getRequest();
        // 获取请求路径
        String path = request.getPath().toString();

        // 登录请求访问路径
        String LOGIN_PATH = "/login";
        // 注册请求访问路径
        String REGISTER_PATH = "/register";

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

        try {
            // 验证 Authorization 请求头中的数据
            String objectId = doubleJWT.decodeAccessToken(auth.get(0)).get("identity").asString();
            // 获取到用户 id, 修改请求头信息
            request.getHeaders().remove(HttpHeaders.AUTHORIZATION);
            request.getHeaders().add(HttpHeaders.AUTHORIZATION, objectId);
            // 打印响应日志信息
            log.info("放行请求: [" + path + "]");
            // 放行请求
            return chain.filter(exchange);
        } catch (InvalidClaimException | TokenExpiredException e) {
            log.warn("Token 验证失败: " + e.getMessage());
            // 拒绝访问
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            // 拦截请求
            return exchange.getResponse().setComplete();
        }
    }

}
