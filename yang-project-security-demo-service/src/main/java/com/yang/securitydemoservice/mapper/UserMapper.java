package com.yang.securitydemoservice.mapper;

import com.yang.common.mybatis.sql.MyBaseMapper;
import com.yang.securitydemoservice.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author YangAns
 * @since 2024-09-26
 */
@Mapper
public interface UserMapper extends MyBaseMapper<User> {

}
