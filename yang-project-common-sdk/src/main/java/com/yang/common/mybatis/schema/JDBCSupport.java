
package com.yang.common.mybatis.schema;

import com.yang.common.utils.YListUtils;
import com.yang.common.utils.YStrUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.List;

@Slf4j
public class JDBCSupport implements InitializingBean {

    private final static String SQL_ADD = "alter table %s add %s %s %s;";

    private final static String SQL_MODIFY = "alter table %s modify %s %s;";
    private final static String SQL_CREATE = "create table %s (%s);";

    private final DataSource dataSource;

    private String databaseProductName;

    public JDBCSupport(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void createTable(String tableName, List<ColumnInfo> columnInfos) {
        List<String> createColumns = YListUtils.list2list(columnInfos, ColumnInfo::toCreateColumn);
        String columnSqlItems = YStrUtils.join(createColumns);
        String createSql = String.format(SQL_CREATE, tableName, columnSqlItems);
        executeSql(createSql);
    }

    public void addColumn(String tableName, String columnName, String type, Object defaultValue, String comment) {
        // 默认值
        String defaultValueSegment = "";
        if (YStrUtils.isNotNull(defaultValue)) {
            if (defaultValue instanceof String) {
                defaultValueSegment = "default '" + defaultValue + "'";
            } else {
                if (!"-999".equals(String.valueOf(defaultValue))) {
                    defaultValueSegment = "default " + defaultValue;
                }
            }
        }
        String sql = String.format(SQL_ADD, tableName, columnName, type, defaultValueSegment);
        executeSql(sql);
    }


    public void modifyColumn(String tableName, String columnName, String type) {
        String sql = String.format(SQL_MODIFY, tableName, columnName, type);
        executeSql(sql);
    }

    public String decideJDBCPrimaryType() {
        DBJDBCType dbjdbcType = DBJDBCType.matchJDBCType(databaseProductName);
        // 主键用60位字符
        return dbjdbcType.varcharType(60);
    }

    public String decideJDBCType(String columnName, Field field, ColumnName definition) {
        DBJDBCType dbjdbcType = DBJDBCType.matchJDBCType(databaseProductName);
//        if (null != definition) {
//            return chooseByColumnDefinition(definition, dbjdbcType);
//        } else {
//            return chooseByField(columnName, field, dbjdbcType);
//        }
        return chooseByField(columnName, field, dbjdbcType);
    }

    @Override
    public void afterPropertiesSet() {
        try (Connection connection = dataSource.getConnection()) {
            this.databaseProductName = connection.getMetaData().getDatabaseProductName();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String chooseByField(String columnName, Field field, DBJDBCType dbjdbcType) {
        if (null == field) {
            return dbjdbcType.varcharType(200);
        }
        String name = field.getName();
        Class<?> fieldType = field.getType();
        if (String.class.isAssignableFrom(fieldType)) {
            // 基于经验的一些合理猜测判断
            if (columnName.endsWith("_id")) {
                return dbjdbcType.varcharType(60);
            } else if (columnName.endsWith("_ids")) {
                return dbjdbcType.varcharType(500);
            } else if (name.equals("content")) {
                return dbjdbcType.varcharType(500);
            } else if (name.equals("createBy")) {
                return dbjdbcType.varcharType(60);
            } else if (name.equals("updateBy")) {
                return dbjdbcType.varcharType(60);
            } else if (name.equals("areaId")) {
                return dbjdbcType.varcharType(60);
            } else {
                return dbjdbcType.varcharType(255);
            }
        }
        if (Integer.class.isAssignableFrom(fieldType)) {
            // 基于经验的一些合理猜测判断
            if (columnName.startsWith("is_") || columnName.startsWith("has_")) {
                return dbjdbcType.booleanType();
            } else {
                return dbjdbcType.intType();
            }
        }
        if (Long.class.isAssignableFrom(fieldType)) {
            return dbjdbcType.longType();
        }
        if (Date.class.isAssignableFrom(fieldType)) {
            return dbjdbcType.dateType();
        }
        if (Boolean.class.isAssignableFrom(fieldType)) {
            return dbjdbcType.booleanType();
        }
        return dbjdbcType.varcharType(255);
    }

    private String chooseByColumnDefinition(ColumnName definition, DBJDBCType dbjdbcType) {
        if (definition.varcharColumn()) {
            return dbjdbcType.varcharType(definition.varcharLength());
        } else if (definition.booleanColumn()) {
            return dbjdbcType.booleanType();
        } else if (definition.intColumn()) {
            return dbjdbcType.intType();
        } else if (definition.longColumn()) {
            return dbjdbcType.longType();
        } else if (definition.dateColumn()) {
            return dbjdbcType.dateType();
        } else if (definition.textColumn()) {
            return dbjdbcType.textType();
        } else {
            return dbjdbcType.varcharType(definition.varcharLength());
        }
    }

    private void executeSql(String sql) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.execute();
        } catch (Exception e) {
            log.warn("sql[{}]执行异常:{}", sql, e.getMessage());
        }
    }
}
