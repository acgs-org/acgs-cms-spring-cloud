package io.github.acgs.cms.common.aspect;

import io.github.acgs.cms.common.annotation.FeignLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * <p>
 *     {@link FeignLog} 注解实现类
 * </p>
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/13
 * </p>
 */
@Slf4j
@Aspect
@Component
public class FeignLogAspect {

    /**
     * <p>
     *     "@Pointcut" 定义切点，此处的切点是注解的方式，也可以用包名的方式达到相同的效果
     * </p>
     */
    @Pointcut("@annotation(io.github.acgs.cms.common.annotation.FeignLog)")
    public void logPointCut() {}

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 织入切面
        // 方法名称
        String methodName = ((MethodSignature) joinPoint.getSignature()).getMethod().getName();
        // 方法执行开始时间
        long beginTime = System.currentTimeMillis();
        log.info(methodName + " 方法调用中...");
        // 执行方法
        Object result = joinPoint.proceed();
        // 方法执行结束时间
        long time = System.currentTimeMillis() - beginTime;
        log.info(methodName + " 方法调用完成，用时 " + time + " 毫秒");
        // 织出切面
        return result;
    }
}
