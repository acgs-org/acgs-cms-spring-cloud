package io.github.acgs.cms.client;

import io.github.acgs.cms.token.Tokens;
import org.bson.types.ObjectId;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 *     身份验证服务 feign 客户端接口
 * </p>
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/5
 * </p>
 */
@FeignClient("authorization")
public interface AuthorizationClient {

    /**
     * <p>
     *     获取双令牌方法
     * </p>
     * @param id 用于加密的用户 id
     * @return 双令牌封装类
     */
    @GetMapping("/tokens/{id}")
    Tokens getTokens(@PathVariable("id") String id);

    /**
     * <p>
     *     获取 access_token 方法
     * </p>
     * @param id 用于加密的用户 id
     * @return access_token
     */
    @GetMapping("/token/access/{id}")
    String getAccessToken(@PathVariable("id") String id);

    /**
     * <p>
     *     验证 access_token 方法
     * </p>
     * @param auth access_token 令牌
     * @return 解密后的用户 id
     */
    @GetMapping("/access/{auth}")
    ObjectId verificationAccessToken(@PathVariable("auth") String auth);

    /**
     * <p>
     *     验证 refresh_token 方法
     * </p>
     * @param auth refresh_token 令牌
     * @return 解密后的用户 id
     */
    @GetMapping("/refresh/{auth}")
    ObjectId verificationRefreshToken(@PathVariable("auth") String auth);
}
