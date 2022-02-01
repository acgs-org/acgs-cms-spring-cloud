package io.github.tierneyjohn.cms.exception;

import lombok.Getter;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * <p>
 *     参数校验异常信息封装
 * </p>
 *
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/2
 * </p>
 */
@Getter
public class ValidatedException extends RuntimeException {

    private final List<ObjectError> errors;

    public ValidatedException(List<ObjectError> errors) {
        this.errors = errors;
    }
}
