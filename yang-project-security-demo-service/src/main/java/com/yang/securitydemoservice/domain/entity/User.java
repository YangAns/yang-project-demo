package com.yang.securitydemoservice.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.yang.common.mybatis.schema.ColumnName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ColumnName("主键")
    @TableId("id")
    private String id;

    @ColumnName("姓名")
    @TableField("name")
    private String name;


    @ColumnName("账号")
    @TableField("account")
    private String account;


    @ColumnName("手机号")
    @TableField("mobile")
    private String mobile;


    @ColumnName("密码")
    @TableField(value = "password")
    private String password;

    @ColumnName("生日")
    @TableField("birth")
    private Long birth;

    @ColumnName("爱好")
    @TableField("hobbies")
    private String hobbies;

    @ColumnName("创建时间")
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private Long createDate;

    @ColumnName("创建时间")
    @TableField(value = "update_date", fill = FieldFill.INSERT_UPDATE)
    private Long updateDate;

    @ColumnName("删除标记")
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    @TableLogic
    private Boolean delFlag;
}
