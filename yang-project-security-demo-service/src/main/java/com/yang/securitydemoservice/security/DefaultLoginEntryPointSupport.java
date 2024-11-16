package com.yang.securitydemoservice.security;

import com.alibaba.fastjson.JSON;
import com.yang.common.webresult.JsonResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/11/8
 */
@Component
public class DefaultLoginEntryPointSupport implements AuthenticationEntryPoint, AccessDeniedHandler {

    /**
     * 认证失败时调用
     * @param request that resulted in an <code>AuthenticationException</code>
     * @param response so that the user agent can begin authentication
     * @param authException that caused the invocation
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        handleNoLogin(request, response);
    }

    /**
     * 权限不足时调用
     * @param request that resulted in an <code>AccessDeniedException</code>
     * @param response so that the user agent can be advised of the failure
     * @param accessDeniedException that caused the invocation
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        handleNoLogin(request, response);
    }

    private void handleNoLogin(HttpServletRequest request, HttpServletResponse response) {
        // 如果是APP登录（有的话），响应JSON
        JsonResult jsonResult = JsonResult.fail_302("您尚未登录系统");
        response.setContentType("application/json;charset=UTF-8");
        try {
            response.getWriter().write(JSON.toJSONString(jsonResult));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
