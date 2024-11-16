package com.yang.common.mybatis.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.yang.common.mybatis.fillers.ColumnFiller;
import com.yang.common.mybatis.fillers.ColumnFillerCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自动填充器
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        doSetDefaultValue(metaObject, true);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        doSetDefaultValue(metaObject, false);
    }

    public void doSetDefaultValue(MetaObject metaObject, boolean insertFill) {
        List<ColumnFiller> columnFillers = ColumnFillerCache.getFillers();
        for (ColumnFiller property : columnFillers) {
            try {
                setValue(property, metaObject, insertFill);
            } catch (Exception e) {
                log.error("Failed to set default value for field {}: {}", property.fieldName(), e.getMessage(), e);
            }
        }
    }


    private void setValue(ColumnFiller property, MetaObject metaObject, boolean insertFill) {
        TableInfo tableInfo = findTableInfo(metaObject);
        if (tableInfo == null) {
            log.warn("TableInfo not found for metaObject: {}", metaObject);
            return;
        }
        if ((insertFill && tableInfo.isWithInsertFill()) || (!insertFill && tableInfo.isWithUpdateFill())) {
            Class<?> fieldType = property.fieldType();
            String fieldName = property.fieldName();
            Object defaultValue = property.defaultValue();
            tableInfo.getFieldList().stream()
                    .filter(field  -> field.getProperty().equals(fieldName) && fieldType.equals(field.getPropertyType()))
                    .filter(field ->  (insertFill && field.isWithInsertFill()) || (!insertFill && field.isWithUpdateFill()))
                    .findFirst()
                    .ifPresent(j -> strictFillStrategy(metaObject, fieldName, () -> defaultValue));
        }
    }
}