package com.yang.demoservice.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * <p>
 * Aspect切面类
 * </p>
 *
 * @author YangAns
 * @since 2024/12/6
 */
@Slf4j
@Aspect
//@Component
public class Custom3Aspect {


    /**
     * 环绕通知 绑定参数
     */
    @Around("execution(* com.yang.demoservice.service.UserService.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("2.CustomAspect#around--环绕通知--方法执行前");
        Object proceed = joinPoint.proceed();
        log.info("6.CustomAspect#around--环绕通知--方法执行后");
        return proceed;
    }

    @Around("@annotation(com.yang.demoservice.aop.Log)")
    public Object around1(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("2.CustomAspect#around--环绕通知--方法执行前");
        Object proceed = joinPoint.proceed();
        log.info("6.CustomAspect#around--环绕通知--方法执行后");
        return proceed;
    }


}
