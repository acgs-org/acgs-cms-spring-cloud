package io.github.acgs.cms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 *     用户登录信息封装类
 * </p>
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/2
 * </p>
 */
@Data
@ApiModel("用户登录信息封装类")
public class LoginDTO {

    /** 登录账号 */
    @NotBlank(message = "{user.username.not-blank}")
    @ApiModelProperty("登录账号")
    private String username;

    /** 登录密码 */
    @NotBlank(message = "{user.password.not-blank}")
    @ApiModelProperty("登录密码")
    private String password;

}
