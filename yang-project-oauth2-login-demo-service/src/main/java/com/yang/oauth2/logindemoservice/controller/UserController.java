package com.yang.oauth2.logindemoservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/11/15
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private TokenStore tokenStore;



    @RequestMapping("/test")
    public String test() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof Jwt){
            Jwt jwt = (Jwt) principal;
            jwt.getClaims().forEach((k, v) -> System.out.println(k + " : " + v));
        }
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MzE3MTYwMTksInVzZXJfbmFtZSI6InpzIiwianRpIjoiYjk4NDgxNGUtYzJhMS00ZmUxLWEyZTMtMjVkMzlhNWU3YzljIiwiY2xpZW50X2lkIjoiWWFuZ0FucyIsInNjb3BlIjpbImFsbCJdfQ.4CQlXyyyWpxySsAdXhusscOlh71BQRM5apgnsotHbNU");
        System.out.println(oAuth2AccessToken);
//        oAuth2AccessToken.isExpired()


        return "user test";
    }


}
