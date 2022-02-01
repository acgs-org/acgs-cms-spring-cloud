package io.github.tierneyjohn.cms.token;

import com.auth0.jwt.interfaces.Claim;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>
 *     双令牌验证测试类
 * </p>
 *
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/1
 * </p>
 */
@SpringBootTest
class DoubleJWTTest {

    /** 双令牌生成器 */
    @Autowired
    private DoubleJWT jwt;

    /**
     * 双令牌验证测试方法
     */
    @Test
    void test() {
        // 创建模拟数据
        Long id = 12556L;
        // 生成 tokens 令牌
        Tokens tokens = jwt.generateTokens(id);

        // 测试令牌信息
        assertNotNull(tokens);
        assertNotNull(tokens.getAccessToken());
        assertNotNull(tokens.getRefreshToken());

        // 解码 access_token
        Map<String, Claim> access = jwt.decodeAccessToken(tokens.getAccessToken());
        // 解码 refresh_token
        Map<String, Claim> refresh = jwt.decodeRefreshToken(tokens.getRefreshToken());

        // 获得解码数据
        Long accessId = access.get("identity").asLong();
        Long refreshId = refresh.get("identity").asLong();

        // 校验检验结果
        assertEquals(accessId, refreshId);
    }



}