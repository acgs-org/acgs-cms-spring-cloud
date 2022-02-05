package io.github.acgs.cms.filter;

import io.github.acgs.cms.client.AuthorizationClient;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 *     返回结果过滤器
 * </p>
 *
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/2
 * </p>
 */
@Order(-1)
@Slf4j
@Component
public class ResultFilter implements GlobalFilter {

    /** 导入身份验证服务 feign 接口客户端 */
    private final AuthorizationClient authorizationClient;

    public ResultFilter(AuthorizationClient authorizationClient) {
        this.authorizationClient = authorizationClient;
    }

    @Override
    public Mono<Void> filter(@NotNull ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取响应
        ServerHttpResponse response = exchange.getResponse();
        // 获取 Authorization 请求头数据
        List<String> headers = response.getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (Objects.nonNull(headers) && !Objects.equals(0, headers.size())) {
            // 存在相应的请求头数据 生成新的 access_token
            String accessToken = authorizationClient.getAccessToken(headers.get(0));
            // 修改请求头信息
            response.getHeaders().remove(HttpHeaders.AUTHORIZATION);
            response.getHeaders().add(HttpHeaders.AUTHORIZATION, accessToken);
            log.info("返回结果: [" + response.getRawStatusCode() + "]");
        }
        return chain.filter(exchange);
    }
}
