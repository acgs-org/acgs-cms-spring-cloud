package io.github.acgs.cms.role;

import org.bson.types.ObjectId;

import java.util.List;

/**
 * <p>
 *     用户角色模型类
 * </p>
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/2
 * </p>
 */
public class Role {

    /** 文档 id */
    private ObjectId id;

    /** 角色名称 */
    private String name;

    /** 权限列表 */
    private RolePermission permission;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RolePermission getPermission() {
        return permission;
    }

    public void setPermission(RolePermission permission) {
        this.permission = permission;
    }

}
