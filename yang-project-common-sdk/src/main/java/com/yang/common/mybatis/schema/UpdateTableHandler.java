
package com.yang.common.mybatis.schema;

import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.yang.common.utils.YListUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class UpdateTableHandler {

    private final JDBCSupport jdbcSupport;

    private final SchemaAnalyserProperties frameworkProperties;


    public UpdateTableHandler(JDBCSupport jdbcSupport, SchemaAnalyserProperties frameworkProperties) {
        this.frameworkProperties = frameworkProperties;
        this.jdbcSupport = jdbcSupport;
    }


    public void updateTable(SchemaDefinition schemaDefinition, TableInfo tableInfo) {
        if (!frameworkProperties.isEnableAutoAlterTableAddColumn() && !frameworkProperties.isEnableAutoAlterTableModifyColumn()) {
            return;
        }

        List<String> columns = schemaDefinition.getColumns();
        List<TableField> commonFields = tableInfo.getFields();

        Map<String, TableField> existsColumnContainer = YListUtils.groupModel(commonFields, TableField::getName);
        // 列的定义
        Map<String, ColumnName> columnContainer = schemaDefinition.getColumnContainer();
        // 列的字段描述
        Map<String, Field> propContainer = schemaDefinition.getPropContainer();

        String tableName = tableInfo.getName();
        for (String column : columns) {
            // 列的定义注解
            ColumnName columnName = columnContainer.get(column);
            // 列属性的反射类型
            Field field = propContainer.get(column);
            // 决定jdbc现有的类型
            String jdbcType = jdbcSupport.decideJDBCType(column, field, columnName);
            if (!existsColumnContainer.containsKey(column)) {
                // 添加字段
                if (frameworkProperties.isEnableAutoAlterTableAddColumn()) {
                    Object defaultValue = null;
                    String comment = "";
                    if (null != columnName && null != field) {
                        boolean isVarchar = String.class.isAssignableFrom(field.getType());
                        defaultValue = isVarchar ? columnName.varcharDefaultValue() : columnName.intDefaultValue();
                        comment = columnName.value();
                    }
                    jdbcSupport.addColumn(tableName, column, jdbcType, defaultValue, comment);
                }
            } else {
                // 更新字段
                TableField existsTableField = existsColumnContainer.get(column);
                if (compareAndNecessaryModify(column, columnName, field, existsTableField)) {
                    if (frameworkProperties.isEnableAutoAlterTableModifyColumn()) {
                        jdbcSupport.modifyColumn(tableName, column, jdbcType);
                    }
                }
            }
        }

    }

    // 比对下新旧数据库，看字段是否需要modify
    private boolean compareAndNecessaryModify(String column, ColumnName columnName, Field field, TableField existsTableField) {
        // 主要是字段类型跟长度
//        String type = existsTableField.getType();
//        return !type.equals(jdbcType);
//        return !type.equals(jdbcType);
//        return !type.equals(jdbcType);
        String jdbcType = jdbcSupport.decideJDBCType(column, field, columnName);
        boolean isVarchar = String.class.isAssignableFrom(field.getType());
        if (isVarchar) {
            return !field.getType().getSimpleName().equals(existsTableField.getColumnType().getType());
        }
        TableField.MetaInfo metaInfo = existsTableField.getMetaInfo();
        String dbJdbcType = metaInfo.getTypeName().toLowerCase();
        return !jdbcType.equals(dbJdbcType);
    }
}
