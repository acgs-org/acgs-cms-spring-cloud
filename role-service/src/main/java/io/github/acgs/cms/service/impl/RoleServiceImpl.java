package io.github.acgs.cms.service.impl;

import io.github.acgs.cms.common.exception.RoleException;
import io.github.acgs.cms.constant.RolePermission;
import io.github.acgs.cms.entity.Role;
import io.github.acgs.cms.repository.RoleRepository;
import io.github.acgs.cms.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static io.github.acgs.cms.constant.RoleNameSuffix.*;

/**
 * <p>
 *     角色服务实现类
 * </p>
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/7
 * </p>
 */
@Service
public class RoleServiceImpl implements RoleService {

    /** 导入角色信息仓储层对象 */
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void initDatabase() {
        if (Objects.isNull(roleRepository.findRoleByName("root"))) {
            Role root = new Role();
            root.setName("root");
            root.setPermission(RolePermission.ROOT);
            roleRepository.save(root);
        }
        if (Objects.isNull(roleRepository.findRoleByName("admin"))) {
            Role admin = new Role();
            admin.setName("admin");
            admin.setPermission(RolePermission.ALL);
            roleRepository.save(admin);
        }
    }

    @Override
    public List<Role> selectAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role selectRoleByName(String roleName) {
        return roleRepository.findRoleByName(roleName);
    }

    @Override
    public boolean addRole(String roleName) {
        if (Objects.equals("root", roleName) || Objects.equals("admin", roleName)) {
            // root 角色 与 admin 角色 不允许被创建
            throw new RoleException(60103);
        }
        if (Objects.nonNull(roleRepository.findRoleByName(roleName))) {
            // 该角色名称已存在
            throw new RoleException(60102);
        }
        // 创建基本角色对象
        Role role = new Role();
        role.setName(roleName);
        role.setPermission(RolePermission.ALL);
        roleRepository.save(role);
        // 创建相应子角色
        // 写入权限
        Role writeRole = new Role();
        writeRole.setName(roleName + WRITE);
        writeRole.setPermission(RolePermission.WRITE);
        roleRepository.save(writeRole);
        // 修改权限
        Role updateRole = new Role();
        updateRole.setName(roleName + UPDATE);
        updateRole.setPermission(RolePermission.UPDATE);
        roleRepository.save(updateRole);
        // 读权限
        Role readRole = new Role();
        readRole.setName(roleName + READ);
        readRole.setPermission(RolePermission.READ);
        roleRepository.save(readRole);
        return true;
    }

    @Override
    public boolean removeRole(String roleName) {
        if (Objects.equals("root", roleName) || Objects.equals("admin", roleName)) {
            // root 角色 与 admin 角色 不允许被删除
            throw new RoleException(60104);
        }
        if (Objects.isNull(roleRepository.findRoleByName(roleName))) {
            // 该角色名称不存在
            throw new RoleException(60101);
        }
        // 删除响应角色信息
        roleRepository.deleteRoleByName(roleName);
        // 删除子角色信息
        roleRepository.deleteRoleByName(roleName + WRITE);
        roleRepository.deleteRoleByName(roleName + UPDATE);
        roleRepository.deleteRoleByName(roleName + READ);
        return true;
    }
}
