package com.yang.securitydemoservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/12/4
 */
@RestController
@RequestMapping("/test")
public class TestController {


    @RequestMapping("/t1")
    public void test1() {
        System.out.println("test1");

    }


}



