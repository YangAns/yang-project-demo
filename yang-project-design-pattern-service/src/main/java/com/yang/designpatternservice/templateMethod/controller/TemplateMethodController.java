package com.yang.designpatternservice.templateMethod.controller;

import com.yang.designpatternservice.templateMethod.service2.User;
import com.yang.designpatternservice.templateMethod.service2.UserServiceImpl;
import com.yang.designpatternservice.templateMethod.service2.ValidationErrorVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/10/23
 */
@RestController
@RequestMapping("/templateMethod")
public class TemplateMethodController {

    @Autowired
    UserServiceImpl userService;


    @RequestMapping("/t1")
    public List<ValidationErrorVo> test() {
        return userService.validateData(createUserList(), "1");
    }


    private List<User> createUserList() {
        List<User> users = new ArrayList<>();
        users.add(new User(null,"zs","123321",10086L,null));
        users.add(new User("123","","123321",10086L,null));
        users.add(new User("124","","",null,null));
        return users;
    }





}
