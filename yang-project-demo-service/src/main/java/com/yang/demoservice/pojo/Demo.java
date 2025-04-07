package com.yang.demoservice.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/12/3
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Demo {

    private String name;

    private Integer age;


    public List<String> getDemoList() {
        return Arrays.asList("1", "2");
    }


    public String getDemoString() {
        return "demo";
    }

}
