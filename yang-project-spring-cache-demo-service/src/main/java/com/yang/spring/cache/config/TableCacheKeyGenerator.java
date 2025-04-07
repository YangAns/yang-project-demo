package com.yang.spring.cache.config;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 自定义缓存Key生成器
 */
@Component
public class TableCacheKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        // 获取方法上的 @Cacheable 注解
        Cacheable cacheable = method.getAnnotation(Cacheable.class);
        if (cacheable == null) return "";

        // 获取实体类上的表名（优先取 @TableCache.value()，其次取 @Table.name()）
//        Class<?> entityClass = method.getReturnType(); // 假设返回类型是实体类
//        TableCache tableCache = entityClass.getAnnotation(TableCache.class);
//        String tableName = tableCache.value();
//
//        if (tableName.isEmpty()) {
//            Table table = entityClass.getAnnotation(Table.class);
//            tableName = table.name();
//        }

        // 组合缓存名称和ID作为Key（例如：users::1）
//        return tableName + "::" + params[0];
        return "custom"+"::"+params[0];
    }
}