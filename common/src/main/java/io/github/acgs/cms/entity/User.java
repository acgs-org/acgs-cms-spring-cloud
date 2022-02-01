package io.github.acgs.cms.entity;

import lombok.Data;
import org.bson.types.ObjectId;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * <p>
 *     用户信息模型类
 * </p>
 *
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/1
 * </p>
 */
@Data
public class User {

    /** 文档 id */
    private ObjectId id;

    /** 登录账号 */
    @NotBlank(message = "{user.username.not-blank}")
    private String username;

    /** 登录密码 */
    @NotBlank(message = "{user.password.not-blank}")
    private String password;

    /** 所属角色 */
    @NotBlank(message = "{user.role.not-blank}")
    private String role;

    /** 用户昵称 */
    @NotBlank(message = "{user.nick.not-blank}")
    private String nick;

    /** 用户电话 */
    private String phone;

    /** 用户邮箱 */
    @Email(message = "{user.email.bad}")
    private String email;

    /** 用户头像 */
    private String img;

    /** 创建时间 */
    private LocalDateTime createDateTime;

}