
package com.yang.common.logging;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.json.JSONObject;
import com.yang.common.globalconfig.FrameworkProperties;
import com.yang.common.utils.YDateUtils;
import com.yang.common.utils.YRequestUtils;
import com.yang.common.utils.YStrUtils;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RequestLogAspect {

    private final FrameworkProperties frameworkProperties;

    @Around(value = PointCut.CONTROLLER_POINT_CUT)
    public Object aroundController(ProceedingJoinPoint jp) throws Throwable {
        if(!frameworkProperties.isEnableRequestLog()){
            return jp.proceed();
        }

//        Object aThis = jp.getThis();
//        Object target = jp.getTarget();
//        Class<? extends ProceedingJoinPoint> aClass = jp.getClass();
//        Class<?> aClass1 = jp.getThis().getClass();
//        Class<?> aClass2 = jp.getTarget().getClass();
//        MethodSignature signature = (MethodSignature) jp.getSignature();
//        String name = signature.getName();
//        Class returnType = signature.getReturnType();
//        String className = aClass2.getName();
//        Method method = signature.getMethod();
//        String name1 = method.getName();
//        Class<?> returnType1 = method.getReturnType();



        //获取request对象
        HttpServletRequest request = YRequestUtils.getRequest();
        if (request == null) {
            return jp.proceed();
        }
        log.info("****访****问****开****始***************************************************");
        // 记录处理业务前的日志信息
        LogParam lp = new LogParam(jp, request);
        log.info("[请求路径]==>{}", lp.getUrl());
        log.info("[请求方式]==>{}", lp.getReqMethod());
        log.info("[请求参数]==>{}", lp.getParams());
        log.info("[请求时间]==>{}", lp.getStartDate());
        // ****************************************
        long startTime = System.currentTimeMillis();
        Object result = jp.proceed(); // 处理业务 *
        long endTime = System.currentTimeMillis();
        // ****************************************
        log.info("[处理结果]==>{}", result);
        log.info("[访问耗时]==>{}", endTime-startTime + "毫秒");
        log.info("****访****问****结****束***************************************************");
        return result;
    }
    @Data
    private static class LogParam {
        private String id;
        private String params;
        private Date startTime;
        private String startDate;
        private String url;
        private String reqMethod;
        private String userId;
        private boolean hasLogin = true;

        public LogParam(ProceedingJoinPoint jp, HttpServletRequest request) {
            this.startTime = new Date();
            this.startDate = YDateUtils.formart(startTime, "yyyy-MM-dd HH:mm:ss");
            this.url = request.getRequestURL().toString();
            this.reqMethod = request.getMethod();
            this.params = doResolveArgStr(jp);
        }

        public String doResolveArgStr(ProceedingJoinPoint jp) {
            Object[] args = jp.getArgs();
            if (args.length == 0) {
                return "";
            }
            Map<String, Object> baseValue = new HashMap<>();
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                if (YStrUtils.isNull(arg)) {
                    continue;
                }
                Class<?> aClass = arg.getClass();
                // 正宇开头的
                boolean isRequest = arg instanceof ServletRequest;
                if (!isRequest) {
                    Map<String, Object> properties = BeanUtil.beanToMap(arg);
                    properties.forEach((k, v) -> {
                        if (YStrUtils.isNotNull(v)) {
                            baseValue.put(k, v);
                        }
                    });
                } else if (ClassUtil.isBasicType(aClass)) {
                    // 基本类型
                    baseValue.put("arg[" + i + "]", arg);
                }
            }
            return new JSONObject(baseValue, true).toString();
        }
    }
}