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

    @RequestMapping("/d1")
    public User hello2(User user) {
        return user;
    }

    @PreAuthorize("hasRole(@rolesConfig.getAuthority())")
    @RequestMapping("/d2")
    public String hello1(String userAuthority){
        System.out.println(userAuthority);
        return "需要ADMIN角色,否则访问不了";
    }

    @RequestMapping("/d3")
    public User hello3(@RequestBody User user) {
        return user;
    }
    @RequestMapping("/d4")
    public String hello4() {
        return "我是白名单接口，不需要登录认证";
    }
}
