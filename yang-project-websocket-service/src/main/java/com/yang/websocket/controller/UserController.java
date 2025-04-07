package com.yang.websocket.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2025/3/6
 */
@RestController
public class UserController {

    @RequestMapping("/login")
    public Map<String,Object> login(String username, String password, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        if("123".equals(password)){
            map.put("code",200);
            map.put("msg","登录成功");
            System.out.println(session.getId());
            session.setAttribute("user",username);
            Object user = session.getAttribute("user");
            System.out.println("user = " + user);
        }else{
            map.put("code",500);
            map.put("msg","登录失败");
        }
        return map;
    }


    @RequestMapping("/getUserName")
    public String getUsername(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String user = (String) session.getAttribute("user");
        System.out.println("user = " + user);
        System.out.println(session.getId());
        return user;
    }
}
