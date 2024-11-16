
package com.yang.common.mybatis.schema;
public class MySqlJDBCType implements DBJDBCType {
    @Override
    public String varcharType(int length) {
        return "varchar(" + length + ")";
    }

    @Override
    public String intType() {
        return "int";
    }

    @Override
    public String longType() {
        return "bigint";
    }

    @Override
    public String booleanType() {
        return "tinyint";
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
