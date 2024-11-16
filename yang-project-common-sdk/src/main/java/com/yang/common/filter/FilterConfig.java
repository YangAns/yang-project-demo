package com.yang.common.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<CustomFilter> loggingFilter(){
        FilterRegistrationBean<CustomFilter> registrationBean 
          = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CustomFilter());
        registrationBean.addUrlPatterns("/demo/*"); // 设置过滤URL
        registrationBean.setOrder(1); // 设置过滤器顺序
        return registrationBean;
    }
}