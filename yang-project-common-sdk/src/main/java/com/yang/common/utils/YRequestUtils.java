
package com.yang.common.utils;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class YRequestUtils {

    public static String getParam(String paramName) {
        HttpServletRequest request = getRequest();
        return null != request ? request.getParameter(paramName) : null;
    }



    public static String getApiFromRequest() {
        HttpServletRequest request = getRequest();
        if (null == request) {
            return "";
        }
        return getApiFromRequest(request);
    }


    public static String getApiFromRequest(HttpServletRequest request) {
        if (null == request) {
            return "";
        }
        return request.getRequestURI().replaceAll(request.getContextPath(), "");
    }


    public static String getHostFromRequest() {
        HttpServletRequest request = getRequest();
        if (null == request) {
            return null;
        }
        String requestURI = request.getRequestURI();
        StringBuffer requestURL = request.getRequestURL();
        return requestURL.toString().replaceAll(requestURI, "") + request.getContextPath();
    }

    public static String getIpAddr(HttpServletRequest request) {
        if (null == request) {
            return null;
        }

        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String getIpAddr() {
        return getIpAddr(getRequest());
    }

    public static Map<String, String> getRequestParams() {
        return getRequestParams(getRequest());
    }

    public static Map<String, String> getRequestParams(HttpServletRequest request) {
        if (null == request) {
            return new HashMap<>();
        }
        Map<String, String> map = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            map.put(paramName, request.getParameter(paramName));
        }
        return map;
    }

    public static RequestAttributes getRequestAttributes() {
        return RequestContextHolder.getRequestAttributes();
    }

    public static void shareRequest(RequestAttributes request) {
        try {
            RequestContextHolder.setRequestAttributes(request, true);
        } catch (Exception e) {
            System.out.println("线程共享失败");
        }
    }

    public static void removeRequest() {
        try {
            RequestContextHolder.resetRequestAttributes();
        } catch (Exception e) {
            System.out.println("线程共享失败");
        }
    }

    public static HttpServletRequest getRequest() {
        try {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (null == requestAttributes) {
                return null;
            }
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        } catch (Exception e) {
            return null;
        }
    }
}
