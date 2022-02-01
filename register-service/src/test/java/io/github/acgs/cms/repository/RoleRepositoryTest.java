package io.github.acgs.cms.repository;

import io.github.acgs.cms.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>
 *     角色信息仓储层测试类
 * </p>
 *
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/2
 * </p>
 */
@SpringBootTest
class RoleRepositoryTest {

    /** 导入角色信息仓储层对象 */
    @Autowired
    private RoleRepository roleRepository;

    @Test
    void findRoleByName() {
        // 测试业务方法
        Role role = roleRepository.findRoleByName("张麻子");

        // 校验测试结果
        assertNull(role);
    }
}