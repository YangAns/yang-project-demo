package com.yang.designpatternservice.templateMethod.service2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 信息校验错误项封装对象
 * </p>
 *
 * @author YangAns
 * @since 2024/1/30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorVo {
    private String id;
    private String name;
    private String errorCount;
    private String errorMessages;
    private List<String> errorFields;

    public ValidationErrorVo(String id, String name, Integer errorCount, List<String> errorMessages, List<String> errorFields) {
        this.id = id;
        this.name = name;
        this.errorCount = errorCount+"项";
        this.errorMessages = errorMessages.stream().distinct().collect(Collectors.joining("、"));
        this.errorFields = errorFields.stream().distinct().collect(Collectors.toList());
    }


    public ValidationErrorVo(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
