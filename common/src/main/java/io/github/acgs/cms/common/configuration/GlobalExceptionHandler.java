package io.github.acgs.cms.common.configuration;

import io.github.acgs.cms.common.exception.BaseException;
import io.github.acgs.cms.common.exception.ValidatedException;
import io.github.acgs.cms.common.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

/**
 * <p>
 *     全局自定义异常返回结果
 * </p>
 *
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/12
 * </p>
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BaseException.class)
    public ResponseVO<?> roleExceptionHandler(@NotNull BaseException error) {
        log.warn(error.getClass().getSimpleName() + " [" + error.getCode() + "] " + error.getMessage());
        return ResponseVO
                .builder()
                .code(error.getCode())
                .message(error.getMessage())
                .result(null)
                .success(false)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ValidatedException.class)
    public ResponseVO<?> ValidatedException(@NotNull ValidatedException error) {
        error.getErrors().forEach(e -> log.warn("参数校验异常: " + e.getDefaultMessage()));
        return ResponseVO
                .builder()
                .code(10000)
                .message("参数校验异常")
                .result(null)
                .success(false)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
