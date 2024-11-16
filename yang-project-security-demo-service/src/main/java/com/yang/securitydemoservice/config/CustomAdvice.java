package com.yang.securitydemoservice.config;

import com.yang.securitydemoservice.domain.entity.User;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/10/14
 */
@ControllerAdvice
public class CustomAdvice implements RequestBodyAdvice {
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        System.out.println("进入了CustomAdvice");
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        System.out.println("执行了beforeBodyRead");
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        System.out.println("执行了afterBodyRead");
        if (body instanceof User) {
            User user = (User) body;
            user.setBirth(System.currentTimeMillis());
        }
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        System.out.println("执行了handleEmptyBody");
        if (body instanceof User) {
            User user = (User) body;
            user.setBirth(System.currentTimeMillis());
        }
        return body;
    }
}
