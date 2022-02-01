package io.github.acgs.cms.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 *     令牌相关配置文件
 * </p>
 *
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/1/31
 * </p>
 */
@Getter
@Setter
@ConfigurationProperties("acgs.cms.token")
public class TokenProperties {

    /** 令牌密匙 */
    private String tokenSecret = "";

    /** 令牌访问过期时间 */
    private Long tokenAccessExpire = 3600L;

    /** 令牌刷新过期时间 */
    private Long tokenRefreshExpire = 2592000L;
}
