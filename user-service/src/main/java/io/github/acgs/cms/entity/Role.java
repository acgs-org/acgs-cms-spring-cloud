package io.github.acgs.cms.entity;

import lombok.Data;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * <p>
 *     用户角色模型类
 * </p>
 *
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
    private List<Character> permission;

    /** 子角色信息 */
    private List<Role> children;

}
