package com.yang.securitydemoservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/10/24
 */
@RestController
@RequestMapping("/public")
public class PublicController {

    @RequestMapping("/test")
    public String test() {
        return "hello";
    }
}
