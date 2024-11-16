
package com.yang.common.mybatis.schema;

import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;
import java.util.Map;

@Slf4j
@Data
public class SchemaAnalyserExecutor implements ApplicationListener<ContextRefreshedEvent> {
    private final CreateTableHandler createTableHandler;
    private final UpdateTableHandler updateTableHandler;
    private final SchemaAnalyserProperties frameworkProperties;
    private final SchemaDefinitionLoader schemaDefinitionLoader;
//    private final SchemaExistedDefinitionLoader schemaExistedDefinitionLoader;
    private final SchemaExistedDefinitionLoader2 schemaExistedDefinitionLoader2;

    public SchemaAnalyserExecutor(
            CreateTableHandler createTableHandler,
            UpdateTableHandler updateTableHandler,
            SchemaAnalyserProperties frameworkProperties,
            SchemaDefinitionLoader schemaDefinitionLoader,
            SchemaExistedDefinitionLoader2 schemaExistedDefinitionLoader) {
        this.createTableHandler = createTableHandler;
        this.updateTableHandler = updateTableHandler;
        this.frameworkProperties = frameworkProperties;
        this.schemaDefinitionLoader = schemaDefinitionLoader;
//        this.schemaExistedDefinitionLoader = schemaExistedDefinitionLoader;
        this.schemaExistedDefinitionLoader2 = schemaExistedDefinitionLoader;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        doBusiness();
    }

    public void doBusiness() {
//        TableInfoHelper.getTableInfos().forEach(tableInfo -> {
//            System.out.println(tableInfo);
//        });
        List<SchemaDefinition> projectSchemaDefinition = schemaDefinitionLoader.getProjectSchemaDefinition();
        Map<String, TableInfo> tableContainer = schemaExistedDefinitionLoader2.findExistedTableInfo();
        // 对比已存在的表和字段，更新字段或新建表
        for (SchemaDefinition schemaDefinition : projectSchemaDefinition) {
            // 看表里存不存在该表的定义信息
            TableInfo tableInfo = tableContainer.get(schemaDefinition.getTableName());
            if (null != tableInfo) {
                try {
                    updateTableHandler.updateTable(schemaDefinition, tableInfo);
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }
            } else {
                try {
                    createTableHandler.createTable(schemaDefinition);
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }
            }
        }
    }
}
