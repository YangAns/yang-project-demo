package com.yang.common.mybatis.fillers;


@SuppressWarnings("unused")
public interface ColumnFiller {

    // 字段名称
    String fieldName();

    // 字段类型
    Class<?> fieldType();

    // 默认值
    Object defaultValue();

    // 更新时是否需要填充
    default boolean updateNeed() {
        return false;
    }
}
