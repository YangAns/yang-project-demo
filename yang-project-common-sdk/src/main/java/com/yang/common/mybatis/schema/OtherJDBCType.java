package com.yang.common.mybatis.schema;

public class OtherJDBCType implements DBJDBCType {
    @Override
    public String varcharType(int length) {
        return "varchar2(" + (length * 2) + ")";
    }

    @Override
    public String intType() {
        return "number(10)";
    }

    @Override
    public String longType() {
        return "number(19)";
    }

    @Override
    public String booleanType() {
        return "number(1)";
    }

    @Override
    public String dateType() {
        return "date";
    }

    @Override
    public String textType() {
        return "text";
    }
}
