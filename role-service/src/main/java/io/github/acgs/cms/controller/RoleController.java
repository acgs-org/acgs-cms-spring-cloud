package io.github.acgs.cms.controller;

import io.github.acgs.cms.entity.Role;
import io.github.acgs.cms.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(tags = {"角色信息接口"})
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
     *     获取所有角色信息方法
     * </p>
     * @return 角色信息集合类
     */
    @ApiOperation(value = "获取所有角色信息方法")
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
    @ApiOperation(value = "通过角色名称获取角色信息")
    @GetMapping("/{roleName}")
    public Role getRoleByName(@ApiParam(value = "角色名称") @PathVariable("roleName") String roleName) {
        return roleService.selectRoleByName(roleName);
    }

    /**
     * <p>
     *     填加新角色信息
     * </p>
     * @param roleName 角色名称
     * @return 添加结果
     */
    @ApiOperation(value = "填加新角色信息")
    @PostMapping("/{roleName}")
    public boolean add(@ApiParam(value = "角色名称") @PathVariable("roleName") String roleName) {
        return roleService.addRole(roleName);
    }

    /**
     * <p>
     *     删除角色信息
     * </p>
     * @param roleName 角色名称
     * @return 删除结果
     */
    @ApiOperation(value = "删除角色信息")
    @DeleteMapping("/{roleName}")
    public boolean remove(@ApiParam(value = "角色名称") @PathVariable("roleName") String roleName) {
        return roleService.removeRole(roleName);
    }
}
