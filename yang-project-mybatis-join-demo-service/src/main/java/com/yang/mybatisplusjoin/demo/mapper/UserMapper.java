package com.yang.mybatisplusjoin.demo.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.yang.mybatisplusjoin.demo.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends MPJBaseMapper<User> {

}