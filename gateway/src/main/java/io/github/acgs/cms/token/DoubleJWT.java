package io.github.acgs.cms.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.acgs.cms.util.DateUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *     双令牌验证类
 * </p>
 *
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/1/30
 * </p>
 */
public class DoubleJWT {

    /** 加密算法 */
    private final Algorithm algorithm;

    /** 访问过期时间 */
    private final Long accessExpire;

    /** 刷新过期时间 */
    private final Long refreshExpire;

    /** 令牌构建器 */
    private JWTCreator.Builder builder;

    /** 访问令牌验证器 */
    private JWTVerifier accessVerifier;

    /** 刷新令牌验证器 */
    private JWTVerifier refreshVerifier;

    /**
     * <p>
     *     双令牌加密模式
     * </p>
     * <p>
     *     需要传入加密算法
     * </p>
     *
     * @param algorithm 加密算法
     * @param accessExpire access_token 过期时间
     * @param refreshExpire refresh_token 过期时间
     */
    public DoubleJWT(Algorithm algorithm, Long accessExpire, Long refreshExpire) {
        this.algorithm = algorithm;
        this.accessExpire = accessExpire;
        this.refreshExpire = refreshExpire;
        this.initBuilderAndVerifier();
    }

    /**
     * <p>
     *     双令牌加密模式
     * </p>
     * <p>
     *     无须传入加密算法 采用默认的 HMAC256 算法
     * </p>
     *
     * @param secret 加密密匙
     * @param accessExpire access_token 过期时间
     * @param refreshExpire refresh_token 过期时间
     */
    public DoubleJWT(String secret, Long accessExpire, Long refreshExpire) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.accessExpire = accessExpire;
        this.refreshExpire = refreshExpire;
        this.initBuilderAndVerifier();
    }

    /**
     * 初始化令牌构建器和验证器
     */
    private void initBuilderAndVerifier() {
        this.accessVerifier = JWT.require(this.algorithm).acceptExpiresAt(this.accessExpire).build();
        this.refreshVerifier = JWT.require(this.algorithm).acceptExpiresAt(this.refreshExpire).build();
        this.builder = JWT.create();
    }

    /**
     * <p>
     *     生成令牌方法
     * </p>
     *
     * @param tokenType 令牌类型
     * @param identity 所属身份
     * @param scope 使用范围
     * @param expire 过期时间
     * @return 令牌信息
     */
    public String generateToken(String tokenType, String identity, String scope, Long expire) {
        // 获取过期时间
        Date expireDate = DateUtil.getDurationDate(expire);
        // 返回令牌信息
        return this.builder
                .withClaim("type", tokenType)
                .withClaim("identity", identity)
                .withClaim("scope", scope)
                .withExpiresAt(expireDate)
                .sign(algorithm);
    }

    /**
     * <p>
     *     生成令牌方法
     * </p>
     *
     * @param tokenType 令牌类型
     * @param identity 所属身份
     * @param scope 使用范围
     * @param expire 过期时间
     * @return 令牌信息
     */
    public String generateToken(String tokenType, Long identity, String scope, Long expire) {
        // 获取过期时间
        Date expireDate = DateUtil.getDurationDate(expire);
        // 返回令牌信息
        return this.builder
                .withClaim("type", tokenType)
                .withClaim("identity", identity)
                .withClaim("scope", scope)
                .withExpiresAt(expireDate)
                .sign(algorithm);
    }

    /**
     * <p>
     *     生成访问令牌方法
     * </p>
     * @param identity 所属身份
     * @return 令牌信息
     */
    public String generateAccessToken(String identity) {
        return this.generateToken(TokenConstant.ACCESS_TYPE, identity, TokenConstant.SCOPE, this.accessExpire);
    }

    /**
     * <p>
     *     生成访问令牌方法
     * </p>
     * @param identity 所属身份
     * @return 令牌信息
     */
    public String generateAccessToken(Long identity) {
        return this.generateToken(TokenConstant.ACCESS_TYPE, identity, TokenConstant.SCOPE, this.accessExpire);
    }

    /**
     * <p>
     *     生成刷新令牌方法
     * </p>
     * @param identity 所属身份
     * @return 令牌信息
     */
    public String generateRefreshToken(String identity) {
        return this.generateToken(TokenConstant.REFRESH_TYPE, identity, TokenConstant.SCOPE, this.accessExpire);
    }

    /**
     * <p>
     *     生成刷新令牌方法
     * </p>
     * @param identity 所属身份
     * @return 令牌信息
     */
    public String generateRefreshToken(Long identity) {
        return this.generateToken(TokenConstant.REFRESH_TYPE, identity, TokenConstant.SCOPE, this.accessExpire);
    }

    /**
     * <p>
     *     生成双令牌方法
     * </p>
     * @param identity 所属身份
     * @return 双令牌封装对象
     */
    public Tokens generateTokens(String identity) {
        String accessToken = this.generateAccessToken(identity);
        String refreshToken = this.generateRefreshToken(identity);
        return new Tokens(accessToken, refreshToken);
    }

    /**
     * <p>
     *     生成双令牌方法
     * </p>
     * @param identity 所属身份
     * @return 双令牌封装对象
     */
    public Tokens generateTokens(Long identity) {
        String accessToken = this.generateAccessToken(identity);
        String refreshToken = this.generateRefreshToken(identity);
        return new Tokens(accessToken, refreshToken);
    }

    /**
     * <p>
     *     解码访问令牌
     * </p>
     * @param token 令牌信息
     * @return 解码后信息映射表
     * @throws TokenExpiredException 令牌已过期
     * @throws InvalidClaimException 令牌类型无效
     * @throws InvalidClaimException 令牌范围无效
     */
    public Map<String, Claim> decodeAccessToken(String token) {
        DecodedJWT jwt = this.accessVerifier.verify(token);
        this.checkTokenExpired(jwt.getExpiresAt());
        this.checkTokenType(jwt.getClaim("type").asString(), TokenConstant.ACCESS_TYPE);
        this.checkTokenScope(jwt.getClaim("scope").asString());
        return jwt.getClaims();
    }

    /**
     * <p>
     *     解码刷新令牌
     * </p>
     * @param token 令牌信息
     * @return 解码后信息映射表
     * @throws TokenExpiredException 令牌已过期
     * @throws InvalidClaimException 令牌类型无效
     * @throws InvalidClaimException 令牌范围无效
     */
    public Map<String, Claim> decodeRefreshToken(String token) {
        DecodedJWT jwt = this.accessVerifier.verify(token);
        this.checkTokenExpired(jwt.getExpiresAt());
        this.checkTokenType(jwt.getClaim("type").asString(), TokenConstant.REFRESH_TYPE);
        this.checkTokenScope(jwt.getClaim("scope").asString());
        return jwt.getClaims();
    }

    /**
     * 验证令牌是否过期
     *
     * @param expiresAt 过期时间
     * @throws TokenExpiredException 令牌已过期
     */
    public void checkTokenExpired(@NotNull Date expiresAt) {
        if (expiresAt.getTime() < System.currentTimeMillis()) {
            // 当前令牌已过期
            throw new TokenExpiredException("token is expired");
        }
    }

    /**
     * 验证令牌类型是否有效
     *
     * @param type 令牌类型
     * @param validType 待校验类型
     * @throws InvalidClaimException 令牌类型无效
     */
    public void checkTokenType(String type, String validType) {
        if (Objects.isNull(type) || !Objects.equals(type, validType)) {
            // 令牌类型不存在 或者令牌类型不匹配
            // 令牌无效
            throw new InvalidClaimException("token type is invalid");
        }
    }

    /**
     * 验证令牌范围是否有效
     *
     * @param scope 令牌范围
     * @throws InvalidClaimException 令牌范围无效
     */
    public void checkTokenScope(String scope) {
        if (Objects.isNull(scope) || !Objects.equals(scope, TokenConstant.SCOPE)) {
            // 令牌范围不存在 或者令牌范围不匹配
            // 令牌无效
            throw new InvalidClaimException("token scope is invalid");
        }
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public Long getAccessExpire() {
        return accessExpire;
    }

    public Long getRefreshExpire() {
        return refreshExpire;
    }

    public JWTCreator.Builder getBuilder() {
        return builder;
    }

    public JWTVerifier getAccessVerifier() {
        return accessVerifier;
    }

    public JWTVerifier getRefreshVerifier() {
        return refreshVerifier;
    }
}
