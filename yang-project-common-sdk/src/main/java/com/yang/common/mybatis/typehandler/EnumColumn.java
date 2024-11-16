package com.yang.common.mybatis.typehandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/6/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnumColumn {

    private String code;


    private String name;


    public EnumColumn(String code) {
        this.code = code;
    }
}
