package com.yang.securitydemoservice.controller;

import com.yang.securitydemoservice.domain.entity.User;
import com.yang.securitydemoservice.service.DemoService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/9/19
 */
@RestController()
@RequestMapping("/demo")
public class DemoController {

    private final DemoService demoService;

    public DemoController(ObjectProvider<DemoService> demoService) {
        this.demoService = demoService.getIfAvailable();
    }


    @PreAuthorize("hasAuthority('/demo/d2')")
    @RequestMapping("/d2")
    public void hello1(){
        demoService.sayHello();
    }

    @RequestMapping("/d1")
    public User hello2(User user) {
        return user;
    }


    @RequestMapping("/d3")
    public User hello3(@RequestBody User user) {
        return user;
    }
}
