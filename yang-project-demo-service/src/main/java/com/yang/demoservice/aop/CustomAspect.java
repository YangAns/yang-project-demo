package com.yang.demoservice.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

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
public class CustomAspect {


    /**
     * 前置通知
     */
    @Before("execution(* com.yang.demoservice.controller.DemoController.beforeDemo(..))")
    public void before(JoinPoint joinPoint) {
        log.info("CustomAspect#before--方法执行前" + joinPoint.getSignature());
    }


    //-------------------------------------同一个切点-----------------------------------------

    /**
     * 前置通知
     */
    @Before("@annotation(com.yang.demoservice.aop.Log)")
    public void beforeAroundDemo(JoinPoint joinPoint) {
        log.info("4.CustomAspect#before--aroundDemo方法执行前--" + joinPoint.getSignature());
    }


    /**
     * 环绕通知 绑定参数
     */
    @Around(value = "execution(* com.yang.demoservice.controller.DemoController.aroundDemo(..))&&args(name,age)", argNames = "joinPoint,name,age")
    public Object aroundAroundDemoArgs(ProceedingJoinPoint joinPoint, String name, Integer age) throws Throwable {
        log.info("2.CustomAspect#aroundAroundDemoArgs--环绕通知--aroundDemo方法执行前");
        log.info("2.CustomAspect#aroundAroundDemoArgs--方法参数,name:{},参数2,age:{}", name, age);
        Object proceed = joinPoint.proceed();
        log.info("9.CustomAspect#aroundAroundDemoArgs--环绕通知--aroundDemo方法执行后");
        return proceed;
    }

    /**
     * 环绕通知 绑定参数
     */
    @Around("@annotation(rel)")
    public Object aroundAroundDemoLog(ProceedingJoinPoint joinPoint, Log rel) throws Throwable {
        log.info("3.CustomAspect#aroundAroundDemoLog--环绕通知--aroundDemo方法执行前");
        log.info("3.CustomAspect#aroundAroundDemoLog--注解参数,log.value:{}", rel.value());
        Object proceed = joinPoint.proceed();
        log.info("8.CustomAspect#aroundAroundDemoLog--注解参数,环绕通知--aroundDemo方法执行后");
        return proceed;
    }


    /**
     * 后置通知
     */
    @After("@annotation(com.yang.demoservice.aop.Log))")
    public void afterAroundDemo(JoinPoint joinPoint) {
        log.info("7.CustomAspect#after--aroundDemo方法执行后--" + joinPoint.getSignature());
    }


    /**
     * 方法正常执行完成   返回通知
     */
    @AfterReturning(value = "@annotation(com.yang.demoservice.aop.Log)", returning = "result")
    public void afterReturningAroundDemo(JoinPoint joinPoint, String result) {
        log.info("6.CustomAspect#afterReturning--aroundDemo方法正常执行完成--" + joinPoint.getSignature());
        log.info("6.CustomAspect#afterReturning--aroundDemo方法执行返回值:" + result);
    }

    /**
     * 方法执行异常  异常通知
     */
    @AfterThrowing(value = "@annotation(com.yang.demoservice.aop.Log)", throwing = "ex")
    public void afterThrowingAroundDemo(JoinPoint joinPoint, Exception ex) {
        log.info("--CustomAspect#afterThrowing--aroundDemo方法执行异常--" + joinPoint.getSignature());
        log.info("--CustomAspect#afterThrowing--aroundDemo异常信息--" + ex);
    }

    //------------------------------------------------------------------------------


    /**
     * 后置通知
     */
    @After("execution(* com.yang.demoservice.controller.DemoController.afterDemo(..))")
    public void after(JoinPoint joinPoint) {
        log.info("CustomAspect#after--方法执行后--" + joinPoint.getSignature());
    }


    /**
     * 方法正常执行完成   返回通知
     */
    @AfterReturning(value = "execution(* com.yang.demoservice.controller.DemoController.afterReturningDemo(..))", returning = "result")
    public void afterReturning(JoinPoint joinPoint, String result) {
        log.info("CustomAspect#afterReturning--方法正常执行完成--" + joinPoint.getSignature());
        log.info("CustomAspect#afterReturning--返回值" + result);
    }

    /**
     * 方法执行异常  异常通知
     */
    @AfterThrowing(value = "execution(* com.yang.demoservice.controller.DemoController.afterThrowingDemo(..))", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Exception ex) throws Throwable {
        log.info("CustomAspect#afterThrowing--方法执行异常" + joinPoint.getSignature());
        log.info("CustomAspect#afterThrowing--异常信息" + ex);
    }
}
