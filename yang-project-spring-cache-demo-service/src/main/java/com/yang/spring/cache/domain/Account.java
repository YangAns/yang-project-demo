package com.yang.spring.cache.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2025/4/6
 */
@Data
@TableName("account")
public class Account {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private BigDecimal balance;
}
