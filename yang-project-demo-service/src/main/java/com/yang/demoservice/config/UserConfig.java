package com.yang.demoservice.config;

import com.yang.demoservice.service.UserService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/11/26
 */
public class UserConfig {

    private List<UserService> userServices;

    private Map<String, UserService> userServiceMap;

    public UserConfig(List<UserService> userServices) {
        this.userServices = userServices;
    }

    public UserConfig(Map<String, UserService> userServiceMap) {
        this.userServiceMap = userServiceMap;
    }


}
