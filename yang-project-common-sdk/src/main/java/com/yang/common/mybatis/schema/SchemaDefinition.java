
package com.yang.common.mybatis.schema;

import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.Data;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class SchemaDefinition {

    private String tableName;

    private String tableComment;
    // 表格信息
    private TableName tableNameAnnotation;
    // 主键信息
    private TableId tableId;

    private List<String> columns = new ArrayList<>();
    // 字段属性定义
    private Map<String, Field> propContainer = new HashMap<>();
    // 字段描述信息
    private Map<String, ColumnName> columnContainer = new HashMap<>();

    public void joinSchemaDefinition(SchemaDefinition schemaDefinition) {
        List<String> targetColumns = schemaDefinition.getColumns();
        Map<String, ColumnName> targetColumnContainer = schemaDefinition.getColumnContainer();
        for (String targetColumn : targetColumns) {
            if (!columns.contains(targetColumn)) {
                columns.add(targetColumn);
                ColumnName columnName = targetColumnContainer.get(targetColumn);
                if (null != columnName) {
                    columnContainer.put(targetColumn, columnName);
                }
            }
        }


    }

    public SchemaDefinition(Class<?> aClass) {
        this.tableNameAnnotation = aClass.getAnnotation(TableName.class);
        Assert.notNull(tableNameAnnotation, "表名注解不能为空");
        // 表名
        this.tableName = tableNameAnnotation.value().toLowerCase();
        // 收集字段定义信息
        Field[] fields = ReflectUtil.getFields(aClass);
        for (Field field : fields) {
            field.setAccessible(true);
            //跳过静态属性
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            // 主键字段
            TableId tableId = field.getAnnotation(TableId.class);
            if (null != tableId) {
                this.tableId = tableId;
                continue;
            }
            String column = field.getName();
            // 普通字段
            TableField tableField = field.getAnnotation(TableField.class);
            if (null == tableField) {
                //默认驼峰转下划线
                column = StringUtils.camelToUnderline(column);
            }else{
                if (!tableField.exist()) {
                    continue;
                }
                column = tableField.value().toLowerCase();
            }
            // 字段集合
            columns.add(column);
            // 字段反射属性
            propContainer.put(column, field);
            // 建表描述
            ColumnName columnName = field.getAnnotation(ColumnName.class);
            if (null != columnName) {
                columnContainer.put(column, columnName);
            }
        }
    }
}
