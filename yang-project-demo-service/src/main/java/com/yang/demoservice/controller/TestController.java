package com.yang.demoservice.controller;

import cn.hutool.core.util.StrUtil;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/12/7
 */
//@RestController
@RequestMapping("/test")
public class TestController {


    @RequestMapping("/t1")
    public String test(String name) {
        if (StrUtil.isNotBlank(name)) {
            return "test" + name;
        } else {
            return "test";
        }
    }
//    ResponseEntity


}
