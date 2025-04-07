package com.yang.websocket.config;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * <p>
 * 
 * </p>
 *
 * @author YangAns
 * @since 2025/3/5
 */
public class CustomConfigurator extends ServerEndpointConfig.Configurator {

     @Override
     public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
//         response.getHeaders().put("Access-Control-Allow-Origin", Collections.singletonList("*"));
         // 获取 HttpSession
         HttpSession httpSession = (HttpSession) request.getHttpSession();
         if(httpSession!=null){
             //保存到配置中
             sec.getUserProperties().put(HttpSession.class.getName(), httpSession);
         }

     }
}
