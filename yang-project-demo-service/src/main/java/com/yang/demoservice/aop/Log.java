package com.yang.demoservice.aop;

import java.lang.annotation.*;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/12/7
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Log {

    String value() default "";

}
