package com.yang.demoservice.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;

/**
 * <p>
 *   Aspect切面类
 * </p>
 *
 * @author YangAns
 * @since 2024/12/6
 */
@Order
@Slf4j
@Aspect
//@Component
public class Custom2Aspect {
    @Pointcut("@annotation(com.yang.demoservice.aop.Log)")
    public void pointCut() {

    }

    /**
     * 前置通知
     */
    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        log.info("1.Custom2Aspect#before--aroundDemo方法执行前--"+joinPoint.getSignature());
    }

}
