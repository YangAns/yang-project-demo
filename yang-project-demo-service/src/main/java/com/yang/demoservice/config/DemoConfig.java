package com.yang.demoservice.config;

import com.yang.demoservice.pojo.Demo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/12/3
 */
@Configuration
public class DemoConfig {


    @Bean
    @Lazy
    public Demo demo() {
        System.out.println("Demo bean 被创建");
        return new Demo("zhangsan", 18);
    }

}
