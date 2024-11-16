package com.yang.common.mybatis.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/6/10
 */
@MappedJdbcTypes({JdbcType.VARCHAR})
@Component
public class MyTypeHandler extends BaseTypeHandler<EnumColumn> {


    /**
     * 写入数据库逻辑
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, EnumColumn parameter, JdbcType jdbcType) throws SQLException {
        System.out.println("MyTypeHandler.setNonNullParameter");
        ps.setString(i, parameter.getCode());
    }

    /**
     * Gets the nullable result.
     * 通过列名从结果集中获取数据。
     * @param rs         the rs
     * @param columnName Colunm name, when configuration <code>useColumnLabel</code> is <code>false</code>
     * @return the nullable result
     * @throws SQLException the SQL exception
     */
    @Override
    public EnumColumn getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String code = rs.getString(columnName);
        if(code == null){
            return null;
        }
        return createNationEnum(code);
    }

    /**
     * 通过列索引从结果集中获取数据。
     * @param rs
     * @param columnIndex
     * @return
     * @throws SQLException
     */
    @Override
    public EnumColumn getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String code = rs.getString(columnIndex);
        if(code == null){
            return null;
        }
        return createNationEnum(code);
    }

    /**
     * 通过列索引从CallableStatement中获取数据。
     * @param cs
     * @param columnIndex
     * @return
     * @throws SQLException
     */
    @Override
    public EnumColumn getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String code = cs.getString(columnIndex);
        if(code == null){
            return null;
        }
        return createNationEnum(code);
    }

    public EnumColumn createNationEnum(String code){
//        NationEnum nationEnum = NationEnum.getNationEnumByCode(code);
//        assert nationEnum != null;
//        String code1 = nationEnum.getCode();
//        String name = nationEnum.getName();
//        return new EnumColumn(code1, name);
        return null;
    }
}
