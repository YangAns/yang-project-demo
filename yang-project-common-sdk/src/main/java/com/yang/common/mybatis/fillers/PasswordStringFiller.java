package com.yang.common.mybatis.fillers;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/9/26
 */
public class PasswordStringFiller implements ColumnFiller{
    @Override
    public String fieldName() {
        return "password";
    }

    @Override
    public Class<?> fieldType() {
        return String.class;
    }

    @Override
    public Object defaultValue() {
        return "123456";
    }
}
