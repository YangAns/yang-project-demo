package com.yang.demoservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2025/3/8
 */
@Configuration
public class CustomConfiguration {


    @Autowired
    private RestTemplateBuilder restTemplateBuilder;


    @Bean
    public RestTemplate restTemplate(){
       return restTemplateBuilder.build();
    }


}
