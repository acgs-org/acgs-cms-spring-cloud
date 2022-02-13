package io.github.acgs.cms.configuration;

import io.github.acgs.cms.token.DoubleJWT;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 * <p>
 *     Token Bean 自动注入配置
 * </p>
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/1/31
 * </p>
 */
@Configuration
@EnableConfigurationProperties(TokenProperties.class)
public class TokenBeanConfiguration {

    /** 导入令牌相关配置文件 */
    private final TokenProperties tokenProperties;

    public TokenBeanConfiguration(TokenProperties tokenProperties) {
        this.tokenProperties = tokenProperties;
    }

    @Bean
    public DoubleJWT doubleJWT() {
        String secret = tokenProperties.getTokenSecret();
        Long accessExpire = tokenProperties.getTokenAccessExpire();
        Long refreshExpire = tokenProperties.getTokenRefreshExpire();
        if (Objects.isNull(accessExpire)) {
            // 1 小时
            accessExpire = 60 * 60L;
        }
        if (Objects.isNull(refreshExpire)) {
            // 1 个月
            refreshExpire = 60 * 60 * 24 * 30L;
        }
        return new DoubleJWT(secret, accessExpire, refreshExpire);
    }
}
