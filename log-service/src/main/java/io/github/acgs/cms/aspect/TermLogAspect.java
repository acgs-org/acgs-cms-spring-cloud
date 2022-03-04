package io.github.acgs.cms.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/**
 * <p>
 * LogService 接口日志增强
 * </p>
 *
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/3/4
 * </p>
 */
@Slf4j
@Aspect
@Component
public class TermLogAspect {

    /**
     * <p>
     * "@Pointcut" 定义切点，此处的切点是注解的方式，也可以用包名的方式达到相同的效果
     * </p>
     */
    @Pointcut("execution(* io.github.acgs.cms.service.LogService.*(..))")
    public void termLogPointCut() {
    }

    @Around("termLogPointCut()")
    public Object around(@NotNull ProceedingJoinPoint joinPoint) throws Throwable {
        // 方法名称
        String methodName = ((MethodSignature) joinPoint.getSignature()).getMethod().getName();
        // 方法前日志
        switch (methodName) {
            case "saveLog" -> log.info("<== 日志信息导入中");
            case "checkLogs" -> log.info("==> 日志信息导出中");
            case "exportLogs" -> log.info("==> 日志文件生成中");
            default -> log.info(methodName + "方法正在执行");
        }
        // 执行方法
        Object result = joinPoint.proceed();
        // 方法后日志
        switch (methodName) {
            case "saveLog" -> log.info("<== 日志信息导入完毕");
            case "checkLogs" -> log.info("==> 日志信息导出完毕");
            case "exportLogs" -> log.info("==> 日志文件生成完毕");
            default -> log.info(methodName + "方法执行完毕");
        }
        return result;
    }
}
