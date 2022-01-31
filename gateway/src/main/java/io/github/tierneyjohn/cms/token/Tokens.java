package io.github.tierneyjohn.cms.token;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 *     双令牌模型类
 * </p>
 *
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/1/30
 * </p>
 */
@Data
public class Tokens {

    /** 访问令牌 */
    @NotBlank
    private String accessToken;

    /** 刷新令牌 */
    @NotBlank
    private String refreshToken;

    public Tokens() {}

    public Tokens(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
