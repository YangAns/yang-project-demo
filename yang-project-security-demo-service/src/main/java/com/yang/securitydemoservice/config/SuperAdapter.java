package com.yang.securitydemoservice.config;

import com.yang.securitydemoservice.mapper.UserMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/10/19
 */
public abstract class SuperAdapter implements ApplicationContextAware, InitializingBean, DoAfter {
    public ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        UserMapper bean = applicationContext.getBean(UserMapper.class);
        System.out.println(bean);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        doAfter();
    }


    @Override
    public void doAfter() {
        doAfterPropertiesSet();
    }

    public abstract void doAfterPropertiesSet();

}
