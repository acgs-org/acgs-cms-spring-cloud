package io.github.acgs.cms.entity.token;

import lombok.Data;

/**
 * <p>
 *     双令牌模型类
 * </p>
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/1/30
 * </p>
 */
@Data
public class Tokens {

    /** 访问令牌 */
    private String accessToken;

    /** 刷新令牌 */
    private String refreshToken;
}
