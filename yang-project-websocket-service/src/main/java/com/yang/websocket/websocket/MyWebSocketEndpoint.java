package com.yang.websocket.websocket;

import com.yang.websocket.config.CustomConfigurator;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@Component
@ServerEndpoint(value = "/ws",configurator = CustomConfigurator.class) // 定义 WebSocket 端点路径
public class MyWebSocketEndpoint {


    /**
     * 连接建立成功调用的方法
     * @param session websocket会话对象
     */
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("客户端连接: " + session.getId());
    }


    /**
     * 收到客户端消息后调用的方法
     * @param message 消息
     * @param session websocket会话对象
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("收到消息: " + message);
        // 向客户端发送响应
        session.getAsyncRemote().sendText("服务器回复: " + message);
    }


    /**
     * 连接关闭调用的方法
     * @param session websocket会话对象
     */
    @OnClose
    public void onClose(Session session) {
        System.out.println("连接关闭: " + session.getId());
    }

    /**
     * 发生错误时调用的方法
     * @param session websocket会话对象
     * @param error 错误信息
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }
}