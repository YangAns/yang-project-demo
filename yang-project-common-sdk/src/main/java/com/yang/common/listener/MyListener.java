package com.yang.common.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/11/27
 */

@Component
@RequiredArgsConstructor
public class MyListener {
    private final List<EasyListener> easyListeners;

    @EventListener(ContextRefreshedEvent.class)
    public void onListener(ContextRefreshedEvent event){
        for (EasyListener easyListener : easyListeners) {
            easyListener.onApplicationContext((ApplicationContext) event.getSource());
        }
    }
}
