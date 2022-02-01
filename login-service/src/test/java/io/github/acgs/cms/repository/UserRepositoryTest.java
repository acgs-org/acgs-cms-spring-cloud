package io.github.acgs.cms.repository;

import io.github.acgs.cms.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>
 *     用户信息仓储层测试类
 * </p>
 *
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/2
 * </p>
 */
@SpringBootTest
class UserRepositoryTest {

    /** 导入用户信息仓储对象 */
    @Autowired
    private UserRepository userRepository;

    @Test
    void findUserByUsername() {
        // 测试业务方法
        User user = userRepository.findUserByUsername("张麻子");

        // 校验测试结果
        assertNull(user);
    }
}