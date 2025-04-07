package com.yang.websocket.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yang.websocket.config.CustomConfigurator;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2025/3/5
 */
@Slf4j
@ServerEndpoint(value = "/chat", configurator = CustomConfigurator.class)
@Component
public class ChatEndpoint {


    //保存所有连接的session 即在线的用户
    private static final Map<String, Session> onlineSessionMap = new ConcurrentHashMap<>();

    //保存当前用户
    private HttpSession httpSession;

    private final ObjectMapper objectMapper = new ObjectMapper();


    /**
     * 建立连接后被调用
     *
     * @param session websocket会话
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        config.getUserProperties().forEach((k, v) -> {
            log.info("key:{},value:{}", k, v);
        });
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        // 保存到当前对象中
        this.httpSession = httpSession;
        //1.将session保存
        onlineSessionMap.put((String) httpSession.getAttribute("user"), session);
        //2.广播消息 将登录的所有用户推送给所有用户
        Map<String, Object> map = new HashMap<>();
        map.put("isSystem", true);
        map.put("fromUser", null);
        map.put("message", onlineSessionMap.keySet());
        try {
            String message = objectMapper.writeValueAsString(map);
            fanOutAllUser(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    //定义广播用户的方法
    public void fanOutAllUser(String message) {
        for (Map.Entry<String, Session> entry : onlineSessionMap.entrySet()) {
            Session session = entry.getValue();
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                log.error("websocket error: {}", e.getMessage());
            }

        }

    }


    /**
     * 接收客户端消息后被调用
     *
     * @param message 消息
     */
    @OnMessage
    public void onMessage(String message) {
        //将消息推送给指定用户
        try {

            var msg = objectMapper.readValue(message, Map.class);
            //1.获取消息接收放的用户名
            String toName = (String) msg.get("toName");
            String mess = (String) msg.get("message");
            //获取消息接收方对应的session 对象
            Session toSession = onlineSessionMap.get(toName);
            if (toSession != null) {
                //2.将消息推送给指定用户
                var user = httpSession.getAttribute("user");
                Map<String, Object> map = new HashMap<>();
                    map.put("isSystem", false);
                map.put("fromUser", user);
                    map.put("message", mess);
                try {
                    String toMsg = objectMapper.writeValueAsString(map);
                    toSession.getBasicRemote().sendText(toMsg);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            log.error("websocket error: {}", e.getMessage());
        }

    }


    /**
     * 关闭连接时被调用
     *
     * @param session websocket会话
     */
    @OnClose
    public void OnClose(Session session) {
        //1.从在先用户列表剔除
        String user = (String) httpSession.getAttribute("user");
        onlineSessionMap.remove(user);
        //2.广播消息 将登录的所有用户推送给所有用户
        Map<String, Object> map = new HashMap<>();
        map.put("isSystem", true);
        map.put("fromUser", null);
        map.put("message", onlineSessionMap.keySet());
        try {
            String message = objectMapper.writeValueAsString(map);
            fanOutAllUser(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 错误
     *
     * @param session websocket会话
     * @param error   错误信息
     */
    @OnError
    public void OnError(Session session, Throwable error) {
        log.error("websocket error: {}", error.getMessage());
    }

}
