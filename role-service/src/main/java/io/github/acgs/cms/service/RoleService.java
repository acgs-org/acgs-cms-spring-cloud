package io.github.acgs.cms.service;

import io.github.acgs.cms.entity.Role;

import java.util.List;

/**
 * <p>
 *     角色服务类接口
 * </p>
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/7
 * </p>
 */
public interface RoleService {

    /**
     * 初始化数据库数据方法
     */
    void initDatabase();

    /**
     * <p>
     *     获取所有角色信息
     * </p>
     * @return 角色信息集合类
     */
    List<Role> selectAll();

    /**
     * <p>
     *     通过角色名称获取角色信息
     * </p>
     * @param roleName 角色名称
     * @return 角色信息
     */
    Role selectRoleByName(String roleName);

    /**
     * <p>
     *     添加新角色方法
     * </p>
     * @param roleName 角色名称
     * @return 添加结果
     */
    boolean addRole(String roleName);

    /**
     * <p>
     *     删除角色方法
     * </p>
     * @param roleName 角色名称
     * @return 删除结果
     */
    boolean removeRole(String roleName);
}
