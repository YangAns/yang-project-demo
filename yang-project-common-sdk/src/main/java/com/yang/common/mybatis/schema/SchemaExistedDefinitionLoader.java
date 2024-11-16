package com.yang.common.mybatis.schema;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.yang.common.utils.YListUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder类
 */
@RequiredArgsConstructor
@SuppressWarnings("ALL")
public class SchemaExistedDefinitionLoader {

    private final DataSourceProperties dataSourceProperties;

    private final DataSource dataSource;

    private IDbQuery dbQuery;

    private DataSourceConfig dataSourceConfig;

    private Connection connection;

    private GlobalConfig globalConfig;

    private StrategyConfig strategyConfig;

    private ConfigBuilder configBuilder;

    @SneakyThrows
    public Map<String, TableInfo> findExistedTableInfo() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig.Builder(
                dataSourceProperties.getUrl(),
                dataSourceProperties.getUsername(),
                dataSourceProperties.getPassword()
        ).build();
        this.connection = dataSourceConfig.getConn();
        this.dataSourceConfig = dataSourceConfig;
        this.dbQuery = dataSourceConfig.getDbQuery();
        // 初始化策略和全局配置
        StrategyConfig strategyConfig = new StrategyConfig.Builder().build();
        GlobalConfig globalConfig = new GlobalConfig.Builder().build();
        PackageConfig packageConfig = new PackageConfig.Builder().build();
        TemplateConfig templateConfig = new TemplateConfig.Builder().build();
        InjectionConfig injectionConfig = new InjectionConfig.Builder().build();
        configBuilder = new ConfigBuilder(packageConfig, dataSourceConfig, strategyConfig, templateConfig, globalConfig, injectionConfig);
        List<TableInfo> tablesInfo = getTablesInfo();
        return YListUtils.groupModel(tablesInfo, TableInfo::getName);
    }



    private List<TableInfo> getTablesInfo() {
        //所有的表信息
        List<TableInfo> tableList = new ArrayList<>();
        //不存在的表名
        PreparedStatement preparedStatement = null;
        try {
            String tablesSql = dbQuery.tablesSql();
            preparedStatement = connection.prepareStatement(tablesSql);
            ResultSet results = preparedStatement.executeQuery();
            TableInfo tableInfo;
            while (results.next()) {
                String tableName = results.getString(dbQuery.tableName());
                if (StringUtils.isNotEmpty(tableName)) {
                    String tableComment = results.getString(dbQuery.tableComment());
                    if ("VIEW".equalsIgnoreCase(tableComment)) {
                        // 跳过视图
                        continue;
                    }
                    tableInfo = new TableInfo(configBuilder, tableName);
                    tableInfo.setComment(tableComment);
                    tableList.add(tableInfo);
                } else {
                    System.err.println("当前数据库为空！！！");
                }
            }
            tableList.forEach(ti -> convertTableFieldsOld(ti));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tableList;
    }

    private TableInfo convertTableFieldsOld(TableInfo tableInfo) {
        boolean haveId = false;
        try {
            String tableFieldsSql = dbQuery.tableFieldsSql();
            tableFieldsSql = String.format(tableFieldsSql, tableInfo.getName());
            PreparedStatement preparedStatement = connection.prepareStatement(tableFieldsSql);
            ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {
                TableField field = new TableField(configBuilder,results.getString(dbQuery.fieldName().toLowerCase()));
                field.setType(results.getString(dbQuery.fieldType()));
                field.setPropertyName(results.getString(dbQuery.fieldName().toLowerCase()),DbColumnType.CHARACTER);
                field.setComment(results.getString(dbQuery.fieldComment()));
                tableInfo.addField(field);
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception：" + e.getMessage());
        }
        return tableInfo;
    }


    public String formatComment(String comment) {
        return StringUtils.isBlank(comment) ? StringPool.EMPTY : comment.replaceAll("\r\n", "\t");
    }
}
