package com.yang.securitydemoservice.mapper;


import com.yang.common.mybatis.sql.MyBaseMapper;
import com.yang.securitydemoservice.domain.entity.Office;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/9/25
 */
@Mapper
public interface OfficeMapper extends MyBaseMapper<Office> {

}
