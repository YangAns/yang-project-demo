package com.yang.designpatternservice.templateMethod.service2;

import lombok.Data;

import java.util.List;

/**
 * <p>
 *  错误信息对象
 * </p>
 *
 * @author xiejiyang
 * @since 2024/7/17
 */
@Data
public class RetireValidationErrorVo extends ValidationErrorVo {

    private String officeName;

    public RetireValidationErrorVo(String id, String name, Integer errorCount, List<String> errorMessages, List<String> errorFields) {
        super(id, name, errorCount, errorMessages, errorFields);
        this.officeName = "广东省";
    }

    public RetireValidationErrorVo(String id, String name) {
        super(id, name);
        this.officeName = "广东省";
    }
}
