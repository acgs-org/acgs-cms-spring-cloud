package io.github.acgs.cms.common.exception;

/**
 * <p>
 *     令牌相关异常类
 * </p>
 *
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/27
 * </p>
 */
public class TokenException extends BaseException {

    public TokenException(Integer code, String message) {
        super(code, message);
    }
}
