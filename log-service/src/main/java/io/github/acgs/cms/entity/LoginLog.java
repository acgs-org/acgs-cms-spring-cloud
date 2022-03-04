package io.github.acgs.cms.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * <p>
 *     登录日志模型类
 * </p>
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/3/4
 * </p>
 */
@Data
@ApiModel("登录日志模型类")
public class LoginLog {

    /** 文档 id */
    @ApiModelProperty("文档 id")
    private ObjectId id;

    /** 登录账号 */
    @ApiModelProperty("登录账号")
    private String username;

    /** 用户昵称 */
    @ApiModelProperty("用户昵称")
    private String nick;

    /** 登录时间 */
    @ApiModelProperty("登录时间")
    private LocalDateTime loginTime;

}
