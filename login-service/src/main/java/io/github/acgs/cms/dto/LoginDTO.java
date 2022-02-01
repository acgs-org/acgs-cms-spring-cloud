package io.github.acgs.cms.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 *     用户登录信息封装类
 * </p>
 *
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/2
 * </p>
 */
@Data
public class LoginDTO {

    /** 登录账号 */
    @NotBlank(message = "{user.username.not-blank}")
    private String username;

    /** 登录密码 */
    @NotBlank(message = "{user.password.not-blank}")
    private String password;

}
