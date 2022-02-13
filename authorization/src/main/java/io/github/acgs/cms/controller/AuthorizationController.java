package io.github.acgs.cms.controller;

import io.github.acgs.cms.token.DoubleJWT;
import io.github.acgs.cms.token.Tokens;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *     身份验证控制器
 * </p>
 *
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/5
 * </p>
 */
@Api(tags = {"身份验证接口"})
@Slf4j
@RestController
@RequestMapping("/authorization")
@RequiredArgsConstructor
public class AuthorizationController {

    /** 导入双令牌校验器 */
    private final DoubleJWT jwt;

    /**
     * <p>
     *     获取双令牌方法
     * </p>
     *
     * @param id 用于加密的用户 id
     * @return 双令牌封装类
     */
    @GetMapping("/tokens/{id}")
    @ApiOperation(value = "获取双令牌方法")
    public Tokens getTokens(@PathVariable("id") String id) {
        log.info("接收数据 /tokens/" + id);
        Tokens tokens = jwt.generateTokens(id);
        log.info("创建 Tokens: " + tokens);
        return tokens;
    }

    /**
     * <p>
     *     获取 access_token 方法
     * </p>
     * @param id 用于加密的用户 id
     * @return access_token
     */
    @GetMapping("/token/access/{id}")
    @ApiOperation(value = "获取 access_token 方法")
    public String getAccessToken(@PathVariable("id") String id) {
        return jwt.generateAccessToken(id);
    }

    /**
     * <p>
     *     验证 access_token 方法
     * </p>
     *
     * @param auth access_token 令牌
     * @return 解密后的用户 id
     */
    @GetMapping("/access/{auth}")
    @ApiOperation(value = "验证 access_token 方法")
    public ObjectId verificationAccessToken(@PathVariable("auth") String auth) {
        return jwt.decodeAccessToken(auth).get("identity").as(ObjectId.class);
    }

    /**
     * <p>
     *     验证 refresh_token 方法
     * </p>
     * @param auth refresh_token 令牌
     * @return 解密后的用户 id
     */
    @GetMapping("/refresh/{auth}")
    @ApiOperation(value = "验证 refresh_token 方法")
    public ObjectId verificationRefreshToken(@PathVariable("auth") String auth) {
        return jwt.decodeRefreshToken(auth).get("identity").as(ObjectId.class);
    }
}
