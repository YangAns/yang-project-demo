package com.yang.common.mybatis.fillers;

public class CreateDateLongFiller implements ColumnFiller {
    @Override
    public String fieldName() {
        return "createDate";
    }

    @Override
    public Class<?> fieldType() {
        return Long.class;
    }

    @Override
    public Object defaultValue() {
        return System.currentTimeMillis();
    }
}
