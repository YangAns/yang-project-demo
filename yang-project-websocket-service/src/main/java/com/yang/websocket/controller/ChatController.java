package com.yang.websocket.controller;

import com.yang.websocket.domain.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

//@Controller
public class ChatController {

    // 处理用户加入事件
    @MessageMapping("/chat.join")
    @SendTo("/topic/chat.messages")
    public ChatMessage handleJoin(ChatMessage message, SimpMessageHeaderAccessor headerAccessor) {
        // 将用户名存入 WebSocket Session
        headerAccessor.getSessionAttributes().put("username", message.getSender());
        return ChatMessage.builder()
                .type(ChatMessage.MessageType.JOIN)
                .sender(message.getSender())
                .content(message.getSender() + " 加入了聊天室")
                .build();
    }

    // 处理普通消息
    @MessageMapping("/chat.send")
    @SendTo("/topic/chat.messages")
    public ChatMessage handleMessage(ChatMessage message) {
        return message;
    }
}