package io.github.acgs.cms.common.exception;

/**
 * <p>
 *     用户相关异常类
 * </p>
 *
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/5
 * </p>
 */
public class UserException extends BaseException {

    public UserException(Integer code, String message) {
        super(code, message);
    }
}
