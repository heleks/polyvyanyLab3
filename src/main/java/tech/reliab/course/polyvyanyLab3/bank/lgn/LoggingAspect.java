package tech.reliab.course.polyvyanyLab3.bank.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Around("execution(* tech.reliab.course.polyvyanyLab3.bank.service.impl.*.*(..))")
    public Object loggingAroundMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        log.info("Method call: {}", methodName);
        try {
            Object result = joinPoint.proceed();
            log.info("Method {} exec successfully", methodName);
            return result;
        } catch (Exception e) {
            log.error("Error in method {}: {}", methodName, e.getMessage());
            throw e;
        }
    }
}
