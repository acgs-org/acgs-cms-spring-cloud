package io.github.acgs.cms.service;

import io.github.acgs.cms.common.exception.UserException;
import io.github.acgs.cms.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>
 *     用户信息服务测试类
 * </p>
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/13
 * </p>
 */
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    /**
     * <p>
     *     测试方法: {@link UserService#checkUsername(String)}
     * </p>
     * <p>
     *     测试环境: 预期环境
     * </p>
     * <p>
     *     预期结果: 验证成功
     * </p>
     * <p>
     *     异常信息: 无
     * </p>
     */
    @Test
    void checkUsernameTestCase001() {
        // 测试业务方法
        boolean result = userService.checkUsername("root");
        // 验证测试结果
        assertTrue(result);
    }

    /**
     * <p>
     *     测试方法: {@link UserService#checkUsername(String)}
     * </p>
     * <p>
     *     测试环境: 预期环境
     * </p>
     * <p>
     *     预期结果: 验证失败
     * </p>
     * <p>
     *     异常信息: 无
     * </p>
     */
    @Test
    void checkUsernameTestCase002() {
        // 测试业务方法
        boolean result = userService.checkUsername("张麻子");
        // 验证测试结果
        assertFalse(result);
    }

    /**
     * <p>
     *     测试方法: {@link UserService#checkUsernameAndPassword(String, String)}
     * </p>
     * <p>
     *     测试环境: 预期环境
     * </p>
     * <p>
     *     预期结果: 验证成功
     * </p>
     * <p>
     *     异常信息: 无
     * </p>
     */
    @Test
    void checkUsernameAndPasswordTestCase001() {
        // 测试业务方法
        boolean result = userService.checkUsernameAndPassword("root", "root");
        // 验证测试结果
        assertTrue(result);
    }

    /**
     * <p>
     *     测试方法: {@link UserService#checkUsernameAndPassword(String, String)}
     * </p>
     * <p>
     *     测试环境: 账号不存在
     * </p>
     * <p>
     *     预期结果: 验证失败
     * </p>
     * <p>
     *     异常信息:
     *     @throws UserException 账号不存在
     * </p>
     */
    @Test
    void checkUsernameAndPasswordTestCase002() {
        // 测试业务方法
        String message = assertThrows(UserException.class,
                () -> userService.checkUsernameAndPassword("张麻子", "123456"))
                .getMessage();

        // 验证测试结果
        assertEquals("账号不存在", message);
    }

    /**
     * <p>
     *     测试方法: {@link UserService#checkUsernameAndPassword(String, String)}
     * </p>
     * <p>
     *     测试环境: 账号正确但密码不对
     * </p>
     * <p>
     *     预期结果: 验证失败
     * </p>
     * <p>
     *     异常信息: 无
     * </p>
     */
    @Test
    void checkUsernameAndPasswordTestCase003() {
        // 测试业务方法
        boolean result = userService.checkUsernameAndPassword("admin", "123456");
        // 验证测试结果
        assertFalse(result);
    }

    /**
     * <p>
     *     测试方法: {@link UserService#getUserByUsername(String)}
     * </p>
     * <p>
     *     测试环境: 预期环境
     * </p>
     * <p>
     *     预期结果: 验证成功
     * </p>
     * <p>
     *     异常信息: 无
     * </p>
     */
    @Test
    void getUserByUsername() {
        // 测试业务方法
        User user = userService.getUserByUsername("root");
        // 验证测试结果
        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals("root", user.getUsername());
        assertEquals("root", user.getPassword());
    }
}