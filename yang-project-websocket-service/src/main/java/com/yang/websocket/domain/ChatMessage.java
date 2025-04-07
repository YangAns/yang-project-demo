package com.yang.websocket.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatMessage {
    public enum MessageType { JOIN, CHAT, LEAVE }

    private MessageType type;
    private String sender;
    private String content;
    private String timestamp;
}