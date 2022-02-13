package io.github.acgs.cms.common.annotation;

import java.lang.annotation.*;

/**
 * <p>
 *     feign 客户端日志增强注解
 * </p>
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/13
 * </p>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FeignLog {

    String value() default  "";
}
