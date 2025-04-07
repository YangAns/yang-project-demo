package com.yang.demoservice.listener;

import com.yang.common.listener.EasyListener;
import com.yang.demoservice.controller.PassayController;
import org.springframework.context.ApplicationContext;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/11/27
 */
//@Component
public class CustomListener implements EasyListener {

    @Override
    public void onApplicationContext(ApplicationContext applicationContext) {
        PassayController bean = applicationContext.getBean(PassayController.class);
        System.out.println(bean);
    }
}
