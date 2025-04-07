package com.yang.quartz.config;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2025/3/25
 */
@Configuration
public class JobConfig {

    @Autowired
    private Scheduler scheduler;

    @PostConstruct
    public void init() {
        System.out.println("初始化");
        System.out.println(scheduler);
    }


}
