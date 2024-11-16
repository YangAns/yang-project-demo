package com.yang.common.mybatis.schema;

public interface TableCondition {

    static TableCondition defaultInstance() {
        return new TableCondition() {
        };
    }

    default boolean isNecessary(Class<?> aClass) {
        return isNecessary();
    }

    default boolean isNecessary() {
        return true;
    }
}