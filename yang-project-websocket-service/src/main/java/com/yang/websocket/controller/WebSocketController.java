package com.yang.websocket.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

//@Controller
public class WebSocketController {

    // 处理发送到 /app/chat 的消息
    @MessageMapping("/chat")
    @SendTo("/topic/messages") // 将结果广播到 /topic/messages
    public String handleMessage(String message) {
        return "服务器回复: " + message;
    }
}