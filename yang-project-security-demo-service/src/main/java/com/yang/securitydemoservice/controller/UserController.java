package com.yang.securitydemoservice.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author YangAns
 * @since 2024-09-26
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @PostMapping("/add")
    public void addUser() {
        System.out.println("添加用户成功!");
    }

}

