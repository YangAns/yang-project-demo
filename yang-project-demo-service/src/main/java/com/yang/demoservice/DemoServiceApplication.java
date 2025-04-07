package com.yang.demoservice;

import com.yang.demoservice.service.DemoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/11/16
 */
@SpringBootApplication
public class DemoServiceApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(DemoServiceApplication.class, args);
        DemoService demoService = run.getBean("demoService", DemoService.class);
        System.out.println(demoService);
    }
}
