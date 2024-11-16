package com.yang.common.mybatis.sql;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yang.common.utils.YListUtils;

import java.util.List;

import static org.apache.ibatis.ognl.DynamicSubscript.FIRST;

/**
 * 自定义mapper
 * @param <T>
 */
public interface MyBaseMapper<T> extends BaseMapper<T> {
    List<T> findAll();

    default T selectOneOfList(Wrapper<T> wrapper) {
        List<T> ts = selectList(wrapper);
        return YListUtils.isNotEmptyList(ts) ? ts.get(FIRST) : null;
    }
}