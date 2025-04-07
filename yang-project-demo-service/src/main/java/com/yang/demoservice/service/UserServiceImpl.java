package com.yang.demoservice.service;

import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/11/26
 */
@Service
public class UserServiceImpl implements UserService{


    @Override
//    @Log(value = "添加用户")
    public void addUser(String name) {
        System.out.println("添加用户:" + name);
    }
}
