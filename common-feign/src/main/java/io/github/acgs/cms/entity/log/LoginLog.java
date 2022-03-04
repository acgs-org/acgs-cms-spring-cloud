package io.github.acgs.cms.entity.log;

import lombok.Data;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * <p>
 *     登录相关日志模型类
 * </p>
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/3/4
 * </p>
 */
@Data
public class LoginLog {

    /** 文档 id */
    private ObjectId id;

    /** 登录账号 */
    private String username;

    /** 用户昵称 */
    private String nick;

    /** 登录时间 */
    private LocalDateTime loginTime;

}
