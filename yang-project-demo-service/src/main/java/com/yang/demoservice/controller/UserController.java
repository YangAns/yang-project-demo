package com.yang.demoservice.controller;

import com.yang.demoservice.pojo.Demo;
import com.yang.demoservice.service.DemoService;
import com.yang.demoservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/12/7
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Lazy
    @Autowired
    private UserService userService;

    private Demo demo;

    @Autowired
    private DemoService demoService;


//    public UserController(@Lazy Demo demo){
//        this.demo = demo;
//        demo.getDemoList();
//        System.out.println("UserController 构造方法");
//    }




    @RequestMapping("/add")
    public String useAdd(String name){
        userService.addUser(name);
        return "success";
    }



}
