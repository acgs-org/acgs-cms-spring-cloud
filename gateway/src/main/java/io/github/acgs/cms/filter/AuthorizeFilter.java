package io.github.acgs.cms.filter;

import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import io.github.acgs.cms.token.DoubleJWT;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * <p>
 *     Token 验证全局过滤器
 * </p>
 *
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/1/30
 * </p>
 */
@Slf4j
@Order(-1)
@Component
public class AuthorizeFilter implements GlobalFilter {

    /** 导入双令牌构造器 */
    private final DoubleJWT jwt;

    public AuthorizeFilter(@NotNull DoubleJWT jwt) {
        this.jwt = jwt;
        log.info("生成测试令牌: " + jwt.generateAccessToken(new ObjectId().toHexString()));
    }


    @Override
    public Mono<Void> filter(@NotNull ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取请求
        ServerHttpRequest request = exchange.getRequest();
        // 获取请求路径
        String path = request.getPath().toString();
        HttpMethod method = request.getMethod();
        // 打印请求日志
        log.info("接收访问请求: [" + path + "], 请求方式: [" + method + "]");

        // 登录请求访问路径
        String LOGIN_PATH = "/user/login";
        // 注册请求访问路径
        String REGISTER_PATH = "/user/register";
        // 刷新令牌请求
        String REFRESH_PATH = "/user/refresh";

        if (Objects.equals(LOGIN_PATH, path) || Objects.equals(REGISTER_PATH, path)) {
            // 当前请求为 登录 或 注册 请求
            // 无须验证身份，直接放行
            return chain.filter(exchange).then(Mono.fromRunnable(() -> log.info("返回 [" + path + "]" + "响应结果")));
        }

        // 获取请求头 Authorization 中的信息
        String auth = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (Objects.isNull(auth)) {
            // Authorization 请求头中数据不存在, 拒绝访问
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            log.warn("Authorization 不存在, 拒绝访问!");
            // 拦截请求
            return exchange.getResponse().setComplete();
        }

        if (Objects.equals(REFRESH_PATH, path)) {
            // 当前请求为刷新令牌请求
            // 获取 Authorization 中的用户 id
            ObjectId id = jwt.decodeRefreshToken(auth).get("identity").as(ObjectId.class);
            log.info("Token 令牌刷新: " + id.toHexString());
            return chain.filter(exchange);
        }

        // 验证 Authorization 中的信息
        try {
            // 获取 Authorization 中的用户 id
            ObjectId id = jwt.decodeAccessToken(auth).get("identity").as(ObjectId.class);
            log.info("Authorization: " + id.toHexString());
            // 放行请求
            return chain.filter(exchange)
                    .then(Mono.defer(() -> {
                        // response 过滤器 刷新 accessToken 信息
                        HttpHeaders headers = exchange.getResponse().getHeaders();
                        String accessToken = jwt.generateAccessToken(id.toHexString());
                        headers.add(HttpHeaders.AUTHORIZATION, accessToken);
                        return chain.filter(exchange);
                    }))
                    .then(Mono.fromRunnable(() -> log.info("返回 [" + path + "]" + "响应结果")));
        } catch (TokenExpiredException e) {
            log.warn("Token 令牌已过期");
            // 拦截请求 拒绝访问
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        } catch (InvalidClaimException e) {
            log.warn("Token 令牌已损坏");
            // 拦截请求
            // 拒绝访问
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        } catch (JWTDecodeException e) {
            log.error("Token 令牌无效");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }
}
