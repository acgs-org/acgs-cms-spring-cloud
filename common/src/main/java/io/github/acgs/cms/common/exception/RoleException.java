package io.github.acgs.cms.common.exception;

import lombok.Builder;

/**
 * <p>
 *     角色相关异常类
 * </p>
 *
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/7
 * </p>
 */
public class RoleException extends BaseException {

    public RoleException(Integer code, String message) {
        super(code, message);
    }
}
