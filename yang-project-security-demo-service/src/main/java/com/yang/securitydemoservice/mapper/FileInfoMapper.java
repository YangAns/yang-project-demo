package com.yang.securitydemoservice.mapper;

import com.yang.common.mybatis.sql.MyBaseMapper;
import com.yang.securitydemoservice.domain.entity.FileInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileInfoMapper extends MyBaseMapper<FileInfo> {

}