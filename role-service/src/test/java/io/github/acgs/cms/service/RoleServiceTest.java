package io.github.acgs.cms.service;

import io.github.acgs.cms.constant.RolePermission;
import io.github.acgs.cms.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static io.github.acgs.cms.constant.RoleNameSuffix.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>
 *     角色服务测试类
 * </p>
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/7
 * </p>
 */
@SpringBootTest
class RoleServiceTest {

    @Autowired
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        roleService.initDatabase();
    }

    /**
     * <p>
     *     测试方法: {@link RoleService#selectAll()}
     * </p>
     * <p>
     *     测试环境: 预期环境
     * </p>
     * <p>
     *     预期结果: 查询到相应的角色信息集合
     * </p>
     * <p>
     *     异常信息: 无
     * </p>
     */
    @Test
    void selectAll() {
        // 测试业务方法
        List<Role> roles = roleService.selectAll();
        // 验证测试结果
        assertNotNull(roles);
        assertTrue(roles.size() >= 2);
        assertEquals("root", roles.get(0).getName());
        assertEquals("admin", roles.get(1).getName());
    }

    /**
     * <p>
     *     测试方法: {@link RoleService#selectRoleByName(String)}
     * </p>
     * <p>
     *     测试环境: 预期环境
     * </p>
     * <p>
     *     预期结果: 无法查询到指定名称角色信息
     * </p>
     * <p>
     *     异常信息: 无
     * </p>
     */
    @Test
    void selectRoleByName() {
        // 测试业务方法
        Role role = roleService.selectRoleByName("张麻子");
        // 校验测试结果
        assertNull(role);
    }

    /**
     * <p>
     *     测试方法:
     *     {@link RoleService#addRole(String)},
     *     {@link RoleService#removeRole(String)}
     * </p>
     * <p>
     *     测试环境: 预期环境
     * </p>
     * <p>
     *     预期结果: 能够正确进行角色信息的 "添加"、"删除" 操作
     * </p>
     * <p>
     *     异常信息: 无
     * </p>
     */
    @Test
    void updateRole() {
        // 创建测试数据
        String roleName = "test-role";
        assertNull(roleService.selectRoleByName(roleName));

        // 测试业务方法
        boolean result = roleService.addRole(roleName);

        // 验证测试结果
        Role role = roleService.selectRoleByName(roleName);
        Role writeRole = roleService.selectRoleByName(roleName + WRITE);
        Role updateRole = roleService.selectRoleByName(roleName + UPDATE);
        Role readRole = roleService.selectRoleByName(roleName + READ);

        assertTrue(result);

        assertNotNull(role);
        assertEquals(RolePermission.ALL, role.getPermission());

        assertNotNull(writeRole);
        assertEquals(RolePermission.WRITE, writeRole.getPermission());

        assertNotNull(updateRole);
        assertEquals(RolePermission.UPDATE, updateRole.getPermission());

        assertNotNull(readRole);
        assertEquals(RolePermission.READ, readRole.getPermission());

        // 测试业务方法
        result = roleService.removeRole(roleName);

        // 验证测试结果
        role = roleService.selectRoleByName(roleName);
        writeRole = roleService.selectRoleByName(roleName + WRITE);
        updateRole = roleService.selectRoleByName(roleName + UPDATE);
        readRole = roleService.selectRoleByName(roleName + READ);

        assertTrue(result);

        assertNull(role);
        assertNull(writeRole);
        assertNull(updateRole);
        assertNull(readRole);
    }
}