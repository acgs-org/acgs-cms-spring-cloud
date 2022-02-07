package io.github.acgs.cms.common.exception;

import lombok.Getter;

/**
 * <p>
 *     通用自定义异常父类
 * </p>
 *
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/7
 * </p>
 */
@Getter
public class BaseException extends RuntimeException {

    /** 自定义异常码 */
    private final Integer code;

    public BaseException(Integer code) {
        this.code = code;
    }
}
