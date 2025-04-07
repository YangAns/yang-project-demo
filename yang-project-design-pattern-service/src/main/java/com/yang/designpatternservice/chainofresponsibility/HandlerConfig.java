package com.yang.designpatternservice.chainofresponsibility;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/11/21
 */
@Configuration
public class HandlerConfig {

    @Bean
    public HandlerChain handlerChain() {
        List<Handler> handlers = new ArrayList<>();
        handlers.add(new UserHandler());
        handlers.add(new AdminHandler());
        return new HandlerChain(handlers);
    }

}
