package com.yang.securitydemoservice.controller;

import com.yang.common.annotions.AutowiredFalse;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/10/23
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {


    @AutowiredFalse
    private UserDetailsService userDetailsService;


    @RequestMapping("/login")
    public String login() {
        userDetailsService.loadUserByUsername("zs");
        return "login";
    }
}
