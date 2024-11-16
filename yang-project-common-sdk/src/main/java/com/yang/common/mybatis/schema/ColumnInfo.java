package com.yang.common.mybatis.schema;

import lombok.Data;

@Data
public class ColumnInfo {

    private Boolean isPrimary;

    private String columnName;

    private String type;

    public String toCreateColumn() {

        if (isPrimary) {
            return columnName + " " + type + " " + "primary key";
        } else {
            return columnName + " " + type;
        }
    }
}