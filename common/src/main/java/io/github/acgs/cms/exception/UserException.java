package io.github.acgs.cms.exception;

import lombok.Getter;

/**
 * <p>
 *     用户相关异常类
 * </p>
 *
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/2
 * </p>
 */
@Getter
public class UserException extends RuntimeException {

    /** 异常状态码 */
    private final Integer code;

    public UserException(Integer code) {
        this.code = code;
    }
}
