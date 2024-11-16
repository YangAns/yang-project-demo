
package com.yang.common.mybatis.schema;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yang.common.utils.YListUtils;

import javax.annotation.PostConstruct;
import java.util.*;

public class SchemaDefinitionLoader {

    private final SchemaAnalyserProperties frameworkProperties;

    private final List<SchemaDefinition> schemaDefinitions = new ArrayList<>();

    public List<SchemaDefinition> getProjectSchemaDefinition() {
        return schemaDefinitions;
    }

    public SchemaDefinitionLoader(SchemaAnalyserProperties frameworkProperties) {
        this.frameworkProperties = frameworkProperties;
    }


    @PostConstruct
    public void init() {
//        Set<Class<?>> classes1 = frameworkProperties.scanClasses();
        String[] scanTableNamePackage = frameworkProperties.getTableNamePackage();
        Set<Class<?>> allClasses = new HashSet<>();
        for (String local : scanTableNamePackage) {
            Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(local, TableName.class);
            if (null != classes && !classes.isEmpty()) {
                allClasses.addAll(classes);
            }
        }
        List<SchemaDefinition> definitions = new ArrayList<>();
        Map<String, TableCondition> tableConditionCache = new HashMap<>();
        for (Class<?> aClass : allClasses) {
            TableExplain tableExplain = aClass.getAnnotation(TableExplain.class);
            if (isNecessary(tableConditionCache, tableExplain, aClass)) {
                SchemaDefinition schemaDefinition = new SchemaDefinition(aClass);
                if (null != tableExplain) {
                    // 表的注释
                    schemaDefinition.setTableComment(tableExplain.value());
                }
                definitions.add(schemaDefinition);
            }
        }
        Map<String, List<SchemaDefinition>> schemaContainer = YListUtils.groupList(definitions, SchemaDefinition::getTableName);
        schemaContainer.forEach((schemaName, schemas) -> {
            if (schemas.size() == 1) {
                schemaDefinitions.add(schemas.get(0));
            } else if (schemas.size() > 1) {
                SchemaDefinition schemaDefinition = schemas.get(0);  // 默认第一个
                // 合并集合
                for (int i = 1; i < schemas.size(); i++) {
                    schemaDefinition.joinSchemaDefinition(schemas.get(i));
                }
                schemaDefinitions.add(schemaDefinition);
            }
        });
    }

    private boolean isNecessary(Map<String, TableCondition> tableConditionCache, TableExplain tableExplain, Class<?> aClass) {
        if (null == tableExplain) {
            return true;
        }

        if (tableExplain.exclude()) {
            return false;
        }

        Class<? extends TableCondition> condition = tableExplain.condition();
        String name = condition.getName();
        TableCondition tableCondition = tableConditionCache.get(name);
        if (null == tableCondition) {
            tableCondition = ReflectUtil.newInstance(condition);
            tableConditionCache.put(name, tableCondition);
        }
        return tableCondition.isNecessary(aClass);

    }
}
