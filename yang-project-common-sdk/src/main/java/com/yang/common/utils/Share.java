package com.yang.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class Share {

    public static <T> void set(String key, T value) {
        HttpServletRequest request = YRequestUtils.getRequest();
        if (null != request && null != key && null != value) {
            request.setAttribute(key, value);
        }
    }

    public static <T> T get(String key, Class<T> aClass) {
        HttpServletRequest request = YRequestUtils.getRequest();
        if (null == request) {
            return null;
        }
        if (null == key) {
            return null;
        }
        Object attribute = request.getAttribute(key);
        return null != attribute ? (T) attribute : null;
    }

    public static <T> List<T> getList(String key, Class<T> aClass) {
        HttpServletRequest request = YRequestUtils.getRequest();
        if (null == request) {
            return null;
        }
        if (null == key) {
            return null;
        }
        Object attribute = request.getAttribute(key);
        return null != attribute ? (List<T>) attribute : new ArrayList<>();
    }

    public static <T> Map<String, T> getMap(String key, Class<T> aClass) {
        HttpServletRequest request = YRequestUtils.getRequest();
        if (null == request) {
            return null;
        }
        if (null == key) {
            return null;
        }
        Object attribute = request.getAttribute(key);
        return null != attribute ? (Map<String, T>) attribute : new HashMap<>();
    }

    public static void remove(String key) {
        HttpServletRequest request = YRequestUtils.getRequest();
        if (null != request) {
            request.removeAttribute(key);
        }
    }
}