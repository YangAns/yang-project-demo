package com.yang.demoservice.controller;

import com.yang.demoservice.service.UserService;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/11/16
 */
//@Component
public class PassayController {


//    @Autowired
//    private List<UserService> userServices;


    public PassayController(List<UserService> userServices){
        System.out.println(userServices);
    }



//    @Bean
//    public UserConfig userConfig(Map<String, UserService> userServiceMap) {
//        return new UserConfig(userServiceMap);
//    }









}
