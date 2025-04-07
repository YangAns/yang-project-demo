package com.yang.mybatisplusjoin.demo.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.yang.mybatisplusjoin.demo.domain.Address;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressMapper extends MPJBaseMapper<Address> {

}