package com.yang.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * <p>
 *   第一种方式
 * </p>
 *
 * @author YangAns
 * @since 2025/3/5
 */
@Configuration
public class WebsocketConfiguration {


    /**
     * 用于 扫描并注册所有带有 @ServerEndpoint 注解的类
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
