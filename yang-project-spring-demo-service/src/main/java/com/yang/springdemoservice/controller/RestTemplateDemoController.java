package com.yang.springdemoservice.controller;

import com.yang.springdemoservice.domain.params.DemoParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2025/3/8
 */
@RestController
@RequestMapping("/rest")
public class RestTemplateDemoController {



    @PostMapping
    public String restTemplateDemo(@RequestBody DemoParams demoParams) {
        return demoParams.toString();
    }




}
