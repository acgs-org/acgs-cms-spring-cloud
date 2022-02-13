package io.github.acgs.cms.entity.role;

import lombok.Data;
import org.bson.types.ObjectId;

/**
 * <p>
 *     用户角色模型类
 * </p>
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/2
 * </p>
 */
@Data
public class Role {

    /** 文档 id */
    private ObjectId id;

    /** 角色名称 */
    private String name;

    /** 权限列表 */
    private RolePermission permission;

}
