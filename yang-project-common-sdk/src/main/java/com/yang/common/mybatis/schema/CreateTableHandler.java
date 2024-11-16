/*
 * @copyright zysoft product 5.0
 */

package com.yang.common.mybatis.schema;

import com.baomidou.mybatisplus.annotation.TableId;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class CreateTableHandler {

    private final JDBCSupport jdbcSupport;
    private final SchemaAnalyserProperties frameworkProperties;


    public CreateTableHandler(JDBCSupport jdbcSupport, SchemaAnalyserProperties frameworkProperties) {
        this.frameworkProperties = frameworkProperties;
        this.jdbcSupport = jdbcSupport;
    }

    public void createTable(SchemaDefinition schemaDefinition) {
        if (!frameworkProperties.isEnableAutoAlterTable()) {
            return;
        }
        List<String> columns = schemaDefinition.getColumns();

        Map<String, ColumnName> columnContainer = schemaDefinition.getColumnContainer();
        Map<String, Field> propContainer = schemaDefinition.getPropContainer();

        List<ColumnInfo> columnInfos = new ArrayList<>();
        TableId tableId = schemaDefinition.getTableId();
        if (null != tableId) {
            //主键
            ColumnInfo columnInfo = new ColumnInfo();
            columnInfo.setColumnName(tableId.value());
            columnInfo.setIsPrimary(true);
            columnInfo.setType(jdbcSupport.decideJDBCPrimaryType());
            columnInfos.add(columnInfo);
        }

        for (String column : columns) {
            ColumnName columnName = columnContainer.get(column);
            Field field = propContainer.get(column);
            String jdbcType = jdbcSupport.decideJDBCType(column, field, columnName);
            ColumnInfo columnInfo = new ColumnInfo();
            columnInfo.setType(jdbcType);
            columnInfo.setIsPrimary(false);
            columnInfo.setColumnName(column);
            columnInfos.add(columnInfo);
        }
        jdbcSupport.createTable(schemaDefinition.getTableName(), columnInfos);
    }
}
