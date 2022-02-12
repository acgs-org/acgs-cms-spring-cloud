package io.github.acgs.cms.configuration;

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
@ConfigurationProperties("acgs.cms.token")
public class TokenProperties {

    /** 令牌密匙 */
    private String tokenSecret = "x88Wf09911241bdseh218db1eBYudb28271VT*dsadA2";

    /** 令牌访问过期时间 */
    private Long tokenAccessExpire = 3600L;

    /** 令牌刷新过期时间 */
    private Long tokenRefreshExpire = 2592000L;

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

    public Long getTokenAccessExpire() {
        return tokenAccessExpire;
    }

    public void setTokenAccessExpire(Long tokenAccessExpire) {
        this.tokenAccessExpire = tokenAccessExpire;
    }

    public Long getTokenRefreshExpire() {
        return tokenRefreshExpire;
    }

    public void setTokenRefreshExpire(Long tokenRefreshExpire) {
        this.tokenRefreshExpire = tokenRefreshExpire;
    }
}
