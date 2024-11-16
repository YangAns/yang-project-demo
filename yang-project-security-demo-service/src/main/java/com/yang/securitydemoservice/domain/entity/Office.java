package com.yang.securitydemoservice.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Accessors(chain = true)
@TableName("sys_office_enhanced")
public class Office implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private String id;

    @TableField("parent_id")
    private String parentId;

    @TableField("name")
    private String name;

    @TableField("code")
    private String code;

    @TableField("postcode")
    private String postcode;

    @TableField("contact_user")
    private String contactUser;

    @TableField("contact_mobile")
    private String contactMobile;

    @TableField("fax")
    private String fax;

    @TableField("email")
    private String email;

    @TableField("responsible_person")
    private String responsiblePerson;

    @TableField("sort")
    private Integer sort;

    @TableField("remark")
    private String remark;

    @TableField("office_type")
    private String officeType;

    @TableField("institutional_adjustment")
    private String institutionalAdjustment;

    @TableField(value = "area_id", fill = FieldFill.INSERT)
    private String areaId;

    @TableField("headcount_num")
    private String headcountNum;

    @TableField("leader")
    private String leader;

    @TableField("address")
    private String address;

    @TableField("abbreviation")
    private String abbreviation;

    @TableField(value = "create_by", fill = FieldFill.INSERT)
    @JsonIgnore
    private String createBy;

    @TableField(value = "create_date", fill = FieldFill.INSERT)
    @JsonIgnore
    private Long createDate;

    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    @JsonIgnore
    private String updateBy;

    @TableField(value = "update_date", fill = FieldFill.INSERT_UPDATE)
    @JsonIgnore
    private Long updateDate;

    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    @TableLogic
    @JsonIgnore
    private Integer delFlag;

    @TableField("play_roles_tree_id")
    private String playRolesTreeId;

    @TableField("party_building_tree_id")
    private String partyBuildingTreeId;

    @TableField("report_unit")
    private String reportUnit;

    @TableField("test")
    private String test;

    private boolean test1;
}
