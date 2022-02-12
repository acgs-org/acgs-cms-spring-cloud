package io.github.acgs.cms.entity;

import io.github.acgs.cms.constant.RolePermission;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "用户角色模型类", description = "用于封装用户信息")
public class Role {

    /** 文档 id */
    @ApiModelProperty(value = "文档 id")
    private ObjectId id;

    /** 角色名称 */
    @NotBlank(message = "{role.name.not-blank}")
    @ApiModelProperty(value = "角色名称")
    private String name;

    /** 所属权限 */
    @ApiModelProperty(value = "所属权限")
    private RolePermission permission;

}
