package com.yang.common.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<CustomFilter> loggingFilter() {
        FilterRegistrationBean<CustomFilter> registrationBean
                = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CustomFilter());
        registrationBean.addUrlPatterns("/demo/*"); // 设置过滤URL
        registrationBean.setOrder(Integer.MIN_VALUE + 1); // 设置过滤器顺序
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<Test1Filter> RegistTest1(){
        FilterRegistrationBean<Test1Filter> bean = new FilterRegistrationBean<Test1Filter>();
        bean.setFilter(new Test1Filter());//注册自定义过滤器
        bean.setName("flilter1");//过滤器名称
        bean.addUrlPatterns("/*");//过滤所有路径
        bean.setOrder(1);//优先级，最顶级
        return bean;
    }
    @Bean
    public FilterRegistrationBean<Test2Filter> RegistTest2(){
        FilterRegistrationBean<Test2Filter> bean = new FilterRegistrationBean<Test2Filter>();
        bean.setFilter(new Test2Filter());//注册自定义过滤器
        bean.setName("flilter2");//过滤器名称
        bean.addUrlPatterns("/demo/*");//过滤所有路径
        bean.setOrder(6);//优先级，越低越优先
        return bean;
    }

}