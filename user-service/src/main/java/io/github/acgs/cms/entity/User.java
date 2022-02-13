package io.github.acgs.cms.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.bson.types.ObjectId;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *     用户信息模型类
 * </p>
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/1
 * </p>
 */
@Data
@ApiModel("用户信息模型类")
public class User {

    /** 文档 id */
    @ApiModelProperty("文档 id")
    private ObjectId id;

    /** 登录账号 */
    @NotBlank(message = "{user.username.not-blank}")
    @ApiModelProperty("登录账号")
    private String username;

    /** 登录密码 */
    @NotBlank(message = "{user.password.not-blank}")
    @ApiModelProperty("登录密码")
    private String password;

    /** 所属角色 */
    @NotBlank(message = "{user.role.not-blank}")
    @ApiModelProperty("所属角色")
    private List<String> roles;

    /** 用户昵称 */
    @NotBlank(message = "{user.nick.not-blank}")
    @ApiModelProperty("用户昵称")
    private String nick;

    /** 用户电话 */
    @ApiModelProperty("用户电话")
    private String phone;

    /** 用户邮箱 */
    @Email(message = "{user.email.bad}")
    @ApiModelProperty("用户邮箱")
    private String email;

    /** 用户头像 */
    @ApiModelProperty("用户头像")
    private String img;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private LocalDateTime createDateTime;

}
