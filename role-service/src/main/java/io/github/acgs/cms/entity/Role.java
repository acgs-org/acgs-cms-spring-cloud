package io.github.acgs.cms.entity;

import io.github.acgs.cms.constant.RolePermission;
import lombok.Data;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotBlank;

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
    @NotBlank(message = "{role.name.not-blank}")
    private String name;

    /** 权限列表 */
    private RolePermission permission;

}
