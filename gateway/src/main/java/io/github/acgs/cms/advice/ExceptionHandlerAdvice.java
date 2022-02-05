package io.github.acgs.cms.advice;

import io.github.acgs.cms.common.exception.UserException;
import io.github.acgs.cms.common.exception.ValidatedException;
import io.github.acgs.cms.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 *     通过 advice 统一异常信息返回结构
 * </p>
 *
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/2
 * </p>
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ResponseBody
    @ResponseStatus
    @ExceptionHandler(Exception.class)
    public ResponseVO handlerException(Exception e) {
        if (e instanceof ValidatedException) {
            // 参数校验异常，获取所有异常信息
            List<String> errors = new ArrayList<>();
            ((ValidatedException) e).getErrors().forEach(error -> {
                // 打印异常日志
                log.warn(error.getDefaultMessage(), error);
                // 异常信息封装
                errors.add(error.getDefaultMessage());
            });
            // 返回自定义封装结果
            return ResponseVO.builder().code(60000).errors(errors).build();
        } else if (e instanceof UserException) {
            // 用户相关异常，获取异常状态码
            Integer code = ((UserException) e).getCode();
            // 异常日志打印
            log.warn("用户信息异常码: => [" + code + "]");
            // 返回自定义封装结果
            return ResponseVO.builder().code(code).build();
        } else {
            // 未知异常，打印异常日记
            log.error(e.getMessage(), e);
            // 返回自定义封装结果
            return ResponseVO.builder().code(99999).errors(Collections.singletonList(e.getMessage())).build();
        }
    }
}
