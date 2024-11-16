package com.yang.common.mybatis.schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TableExplain {

    // 表的注释
    String value();

    boolean exclude() default false;

    Class<? extends TableCondition> condition();
}