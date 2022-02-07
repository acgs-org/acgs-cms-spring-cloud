package io.github.acgs.cms.role;

/**
 * <p>
 *     角色权限枚举类
 * </p>
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/7
 * </p>
 */
public enum RolePermission {
    /**
     * Root 权限，对于项目的所有功能均可访问，项目唯一
     */
    ROOT,
    /**
     * 包含自身所属分区的所有权限，但无法访问 Root 分区的部分功能
     */
    ALL,
    /**
     * 包含自身所属分区的写入修改权限
     */
    WRITE,
    /**
     * 包含自身所属分区的数据修改权限，但无法进行新增和删除操作
     */
    UPDATE,
    /**
     * 包含自身所属分区的数据访问权限，无法进行任何修改操作
     */
    READ
}
