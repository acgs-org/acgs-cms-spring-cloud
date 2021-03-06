package io.github.acgs.cms.service.impl;

import io.github.acgs.cms.common.exception.RoleException;
import io.github.acgs.cms.constant.RolePermission;
import io.github.acgs.cms.entity.Role;
import io.github.acgs.cms.repository.RoleRepository;
import io.github.acgs.cms.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    /** 导入角色信息仓储层对象 */
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        this.initDatabase();
    }

    @Override
    public void initDatabase() {
        log.info("初始化角色数据信息");
        if (Objects.isNull(roleRepository.findRoleByName("root"))) {
            Role root = new Role();
            root.setName("root");
            root.setPermission(RolePermission.ROOT);
            roleRepository.save(root);
            log.info("载入 root 角色信息");
        }
        if (Objects.isNull(roleRepository.findRoleByName("admin"))) {
            Role admin = new Role();
            admin.setName("admin");
            admin.setPermission(RolePermission.ALL);
            roleRepository.save(admin);
            log.info("载入 admin 角色信息");
        }
        log.info("角色信息初始化成功");
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
            throw new RoleException(60103, "不可创建该角色");
        }
        if (Objects.nonNull(roleRepository.findRoleByName(roleName))) {
            // 该角色名称已存在
            throw new RoleException(60102, "角色已存在");
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
            throw new RoleException(60104, "不可删除该角色");
        }
        if (Objects.isNull(roleRepository.findRoleByName(roleName))) {
            // 该角色名称不存在
            throw new RoleException(60101, "角色不存在");
        }
        // 删除响应角色信息
        roleRepository.deleteRoleByName(roleName);
        // 删除子角色信息
        roleRepository.deleteRoleByName(roleName + WRITE);
        roleRepository.deleteRoleByName(roleName + UPDATE);
        roleRepository.deleteRoleByName(roleName + READ);
        return true;
    }

    @Override
    public List<Role> checkRoles(List<String> roles) {
        List<Role> list = new ArrayList<>();
        roles.forEach(roleName -> {
            Role result = roleRepository.findRoleByName(roleName);
            if (Objects.nonNull(result)) {
                list.add(result);
            }
        });
        return list;
    }
}
