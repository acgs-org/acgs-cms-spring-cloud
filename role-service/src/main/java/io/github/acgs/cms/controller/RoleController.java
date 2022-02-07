package io.github.acgs.cms.controller;

import io.github.acgs.cms.entity.Role;
import io.github.acgs.cms.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *     角色信息控制器
 * </p>
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/7
 * </p>
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    /** 导入角色信息服务类对象 */
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
        // 初始化数据库数据
        this.roleService.initDatabase();
    }

    /**
     * <p>
     *     获取所有角色方法
     * </p>
     * @return 角色信息集合类
     */
    @GetMapping
    public List<Role> getAll() {
        return roleService.selectAll();
    }

    /**
     * <p>
     *     通过角色名称获取角色信息
     * </p>
     * @param roleName 角色名称
     * @return 角色信息
     */
    @GetMapping("/{roleName}")
    public Role getRoleByName(@PathVariable("roleName") String roleName) {
        return roleService.selectRoleByName(roleName);
    }

    /**
     * <p>
     *     填加新角色信息
     * </p>
     * @param roleName 角色名称
     * @return 添加结果
     */
    @PostMapping("/{roleName}")
    public boolean add(@PathVariable("roleName") String roleName) {
        return roleService.addRole(roleName);
    }

    /**
     * <p>
     *     删除角色信息
     * </p>
     * @param roleName 角色名称
     * @return 删除结果
     */
    @DeleteMapping("/{roleName}")
    public boolean remove(@PathVariable("roleName") String roleName) {
        return roleService.removeRole(roleName);
    }
}
