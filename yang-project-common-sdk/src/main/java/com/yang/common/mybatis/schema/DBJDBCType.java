package com.yang.common.mybatis.schema;

public interface DBJDBCType {

    static DBJDBCType matchJDBCType(String databaseType) {
        boolean isMySql = databaseType.toLowerCase().contains("mysql");
        return isMySql ? new MySqlJDBCType() : new OtherJDBCType();
    }

    String varcharType(int length);

    String intType();

    String longType();

    String booleanType();

    String dateType();

    String textType();
}