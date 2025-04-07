package com.yang.demoservice.controller;

import cn.hutool.core.util.StrUtil;
import com.yang.demoservice.aop.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/12/6
 */
@Slf4j
@RestController
@RequestMapping("/demo")
public class DemoController {

//    private Demo demo;
//
//    public DemoController(Demo demo) {
//        this.demo = demo;
//        System.out.println(demo);
//    }


    @RequestMapping("/d1")
    public String beforeDemo(String name) {
        if (StrUtil.isNotBlank(name)) {
            return "beforeDemo" + name;
        } else {
            return "beforeDemo";
        }
    }


    @Log("环绕通知")
    @RequestMapping("/d2")
    public String aroundDemo(String name, Integer age) {
        if (StrUtil.isNotBlank(name) && age != null) {
            String result = name + ":" + age;
            log.info("5.aroundDemo:" + result + "执行成功");
            return "aroundDemo" + result;
        } else {
            log.info("5.aroundDemo:" + "执行失败");
            throw new RuntimeException("aroundDemo" + "执行失败");
        }
    }


    @RequestMapping("/d3")
    public String afterDemo(String name) {
        if (StrUtil.isNotBlank(name)) {
            return "afterDemo" + name;
        } else {
            return "afterDemo";
        }
    }


    @RequestMapping("/d4")
    public String afterReturningDemo(String name) {
        if (StrUtil.isNotBlank(name)) {
            return "afterReturnDemo" + name;
        } else {
            return "afterReturnDemo";
        }
    }


    @RequestMapping("/d5")
    public String afterThrowingDemo(String name) {
        if (StrUtil.isNotBlank(name)) {
            throw new RuntimeException("afterThrowingDemo" + name);
        } else {
            return "afterThrowingDemo";
        }
    }

}
