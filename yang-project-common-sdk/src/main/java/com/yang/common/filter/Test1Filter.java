package com.yang.common.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class Test1Filter implements Filter {

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) arg0;
        System.out.println("自定义过滤器filter1触发,拦截url:" + request.getRequestURI());
        arg2.doFilter(arg0, arg1);
    }

}