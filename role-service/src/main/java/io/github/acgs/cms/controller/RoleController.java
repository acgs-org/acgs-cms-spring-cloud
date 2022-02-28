package io.github.acgs.cms.controller;

import io.github.acgs.cms.entity.Role;
import io.github.acgs.cms.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class RoleController {

    /** 导入角色信息服务类对象 */
    private final RoleService roleService;

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

    /**
     * <p>
     *     验证角色组信息
     * </p>
     * @param roles 待验证角色组名称
     * @return 验证通过角色组信息
     */
    @ApiOperation(value = "验证角色组信息")
    @PostMapping("/check")
    public List<Role> checkRoles(@ApiParam(value = "待验证角色组名称") @RequestBody List<String> roles) {
        return roleService.checkRoles(roles);
    }
}
