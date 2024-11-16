package com.yang.common.mybatis.schema;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@SuppressWarnings("unused")
public @interface ColumnName {

    // 字段注释
    String value();

    // 自定义的一些判断条件
    String customDesc() default "";

    // 默认是varchar字段
    boolean varcharColumn() default false;

    // 默认varchar字段的长度为60
    int varcharLength() default 60;

    // 是否是date字段
    boolean dateColumn() default false;

    // 是否是布尔字段
    boolean booleanColumn() default false;

    // 是否是long字段
    boolean longColumn() default false;

    // 是否是int字段
    boolean intColumn() default false;

    // 是否是text字段
    boolean textColumn() default false;

    // int类型数据默认值
    int intDefaultValue() default -999;

    // varchar类型数据默认值
    String varcharDefaultValue() default "";
}
