package com.yang.securitydemoservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/10/19
 */
@Component
public class ChildConfig extends SuperAdapter {
    public ApplicationContext applicationContext;


    @Autowired
    public void set(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    @Override
    public void doAfterPropertiesSet() {
        System.out.println("doAfterPropertiesSet");
    }
}
