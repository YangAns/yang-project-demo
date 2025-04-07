package com.yang.demoservice.service;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/12/3
 */
@Component
@ToString
public class DemoService {

    @Value("${server.port}")
    private String port;


    @Value("#{demo.getDemoString()}")
    private String name;


    @Value("${demo.localAge}")
    private Integer age;

    @Value("#{demo.getDemoList()}")
    private List<String> demoList;

    public void test() {
        System.out.println("demo service");
    }

}
