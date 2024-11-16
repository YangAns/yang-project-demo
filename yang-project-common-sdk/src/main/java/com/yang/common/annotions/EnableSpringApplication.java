package com.yang.common.annotions;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootApplication(scanBasePackages = "com.yang")
@MapperScan(value = "com.yang", annotationClass = Mapper.class)
public @interface EnableSpringApplication {

    @AliasFor(annotation = SpringBootApplication.class)
    Class<?>[] exclude() default {};


    @AliasFor(annotation = SpringBootApplication.class)
    String[] excludeName() default {};
}
