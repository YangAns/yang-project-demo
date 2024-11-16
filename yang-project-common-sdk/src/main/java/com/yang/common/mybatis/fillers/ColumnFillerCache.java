
package com.yang.common.mybatis.fillers;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ColumnFillerCache {
    private final static List<ColumnFiller> FILLER_CACHE = new CopyOnWriteArrayList<>();

    public static List<ColumnFiller> getFillers() {
        return FILLER_CACHE;
    }

    public static void register(ColumnFiller filler) {
        FILLER_CACHE.add(filler);
    }


    // 注册时传入固定的默认值
    public static void register(String fieldName, Object defaultValue, Class<?> valueClass) {
        FILLER_CACHE.add(newFiller(fieldName, () -> defaultValue, valueClass, false));
    }

    // 注册时传入固定的默认值和是否需要更新
    public static void register(String fieldName, Object defaultValue, Class<?> valueClass, boolean needUpdate) {
        FILLER_CACHE.add(newFiller(fieldName, () -> defaultValue, valueClass, needUpdate));
    }

    // 注册时允许传入动态计算默认值的 Supplier
    public static void register(String fieldName, Supplier<Object> defaultValueSupplier, Class<?> valueClass, boolean needUpdate) {
        FILLER_CACHE.add(newFiller(fieldName, defaultValueSupplier, valueClass, needUpdate));
    }

    private static ColumnFiller newFiller(String fieldName, Supplier<Object> defaultValueSupplier, Class<?> fieldType, boolean needUpdate) {
        return new ColumnFiller() {
            @Override
            public String fieldName() {
                return fieldName;
            }

            @Override
            public Class<?> fieldType() {
                return fieldType;
            }

            @Override
            public Object defaultValue() {
                return defaultValueSupplier.get();
            }

            @Override
            public boolean updateNeed() {
                return needUpdate;
            }
        };
    }
}
