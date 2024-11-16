package com.yang.common.mybatis.schema;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.builder.Entity;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.jdbc.DatabaseMetaDataWrapper;
import com.baomidou.mybatisplus.generator.type.ITypeConvertHandler;
import com.baomidou.mybatisplus.generator.type.TypeRegistry;
import com.yang.common.utils.YListUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder类
 */
@RequiredArgsConstructor
//@SuppressWarnings("ALL")
public class SchemaExistedDefinitionLoader2 {

    private final DataSourceProperties dataSourceProperties;

    private final DataSource dataSource;

    private DataSourceConfig dataSourceConfig;

    private GlobalConfig globalConfig;

    private StrategyConfig strategyConfig;

    private ConfigBuilder configBuilder;

    private DatabaseMetaDataWrapper databaseMetaDataWrapper;

    private TypeRegistry typeRegistry;


    @SneakyThrows
    public Map<String, TableInfo> findExistedTableInfo() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig.Builder(
                dataSourceProperties.getUrl(),
                dataSourceProperties.getUsername(),
                dataSourceProperties.getPassword()
        ).build();
        this.dataSourceConfig = dataSourceConfig;

        // 初始化策略和全局配置
        strategyConfig = new StrategyConfig
                .Builder()
                .enableSkipView()
                .build();

        globalConfig = new GlobalConfig
                .Builder()
                .build();

//        PackageConfig packageConfig = new PackageConfig.Builder().build();
//        InjectionConfig injectionConfig = new InjectionConfig.Builder().build();
        ConfigBuilder configBuilder = new ConfigBuilder(null, dataSourceConfig, strategyConfig, null, globalConfig, null);
        this.configBuilder = configBuilder;
        typeRegistry = new TypeRegistry(configBuilder.getGlobalConfig());
        this.databaseMetaDataWrapper = new DatabaseMetaDataWrapper(dataSourceConfig.getConn(), dataSourceConfig.getSchemaName());
        List<TableInfo> tablesInfo = getTablesInfo();
        return YListUtils.groupModel(tablesInfo, TableInfo::getName);
    }


    public @NotNull List<TableInfo> getTablesInfo() {
        try {
            //所有的表信息
            List<TableInfo> tableList = new ArrayList<>();
            //获取所有表信息
            List<DatabaseMetaDataWrapper.Table> tables = this.getTables();
            tables.forEach(table -> {
                String tableName = table.getName();
                if (StringUtils.isNotBlank(tableName)) {
                    TableInfo tableInfo = new TableInfo(this.configBuilder, tableName);
                    tableList.add(tableInfo);
                }
            });
            tableList.forEach(this::convertTableFields);
            return tableList;
        } finally {
            // 数据库操作完成,释放连接对象
            databaseMetaDataWrapper.closeConnection();
        }
    }


    protected void convertTableFields(@NotNull TableInfo tableInfo) {
        String tableName = tableInfo.getName();
        Map<String, DatabaseMetaDataWrapper.Column> columnsInfoMap = getColumnsInfo(tableName);
        Entity entity = strategyConfig.entity();
        columnsInfoMap.forEach((k, columnInfo) -> {
            String columnName = columnInfo.getName();
            TableField field = new TableField(this.configBuilder, columnName);
            // 处理ID
            if (columnInfo.isPrimaryKey()) {
                field.primaryKey(columnInfo.isAutoIncrement());
                tableInfo.setHavePrimaryKey(true);
            }
            field.setColumnName(columnName).setComment(columnInfo.getRemarks());
            String propertyName = entity.getNameConvert().propertyNameConvert(field);
            // 设置字段的元数据信息
            TableField.MetaInfo metaInfo = new TableField.MetaInfo(columnInfo, tableInfo);
            IColumnType columnType;
            ITypeConvertHandler typeConvertHandler = dataSourceConfig.getTypeConvertHandler();
            if (typeConvertHandler != null) {
                columnType = typeConvertHandler.convert(globalConfig, typeRegistry, metaInfo);
            } else {
                columnType = typeRegistry.getColumnType(metaInfo);
            }
            field.setPropertyName(propertyName, columnType);
            field.setMetaInfo(metaInfo);
            tableInfo.addField(field);
        });
    }


    /**
     * 获取所有表信息
     * @return
     * SELECT
     *   TABLE_SCHEMA AS TABLE_CAT,
     *           NULL AS TABLE_SCHEM,
     *           TABLE_NAME,
     *           CASE
     *
     *   WHEN TABLE_TYPE = 'BASE TABLE' THEN
     *           CASE
     *
     *   WHEN TABLE_SCHEMA = 'mysql'
     *   OR TABLE_SCHEMA = 'performance_schema' THEN
     *   'SYSTEM TABLE' ELSE 'TABLE'
     *   END
     *   WHEN TABLE_TYPE = 'TEMPORARY' THEN
     *   'LOCAL_TEMPORARY' ELSE TABLE_TYPE
     *   END AS TABLE_TYPE,
     *           TABLE_COMMENT AS REMARKS,
     *   NULL AS TYPE_CAT,
     *           NULL AS TYPE_SCHEM,
     *   NULL AS TYPE_NAME,
     *           NULL AS SELF_REFERENCING_COL_NAME,
     *   NULL AS REF_GENERATION
     *           FROM
     *   INFORMATION_SCHEMA.TABLES
     *           WHERE
     *  TABLE_SCHEMA = 'mybatis_study'
     *           HAVING
     *           TABLE_TYPE IN ( 'TABLE', 'VIEW', NULL, NULL, NULL )
     *           ORDER BY
     *           TABLE_TYPE,
     *                   TABLE_SCHEMA,
     *                   TABLE_NAME
     */
    protected List<DatabaseMetaDataWrapper.Table> getTables() {
        return databaseMetaDataWrapper.getTables(null, new String[]{"TABLE"});
    }


    /**
     * 获取表字段信息
     *
     * @param tableName 表名
     * @return
     *  SELECT
     * 		TABLE_SCHEMA AS TABLE_CAT,
     * 		NULL AS TABLE_SCHEM,
     * 		TABLE_NAME,
     * 		COLUMN_NAME,
     * 		SEQ_IN_INDEX AS KEY_SEQ,
     * 		'PRIMARY' AS PK_NAME
     * 	FROM
     * 		INFORMATION_SCHEMA.STATISTICS
     * 	WHERE
     * 		TABLE_SCHEMA = 'mybatis_study'
     * 		AND TABLE_NAME = 't_blog'
     * 		AND INDEX_NAME = 'PRIMARY'
     * 	ORDER BY
     * 		TABLE_SCHEMA,
     * 		TABLE_NAME,
     * 		INDEX_NAME,
     * 	SEQ_IN_INDEX
     */
    protected Map<String, DatabaseMetaDataWrapper.Column> getColumnsInfo(String tableName) {
        return databaseMetaDataWrapper.getColumnsInfo(tableName, true);
    }
}
