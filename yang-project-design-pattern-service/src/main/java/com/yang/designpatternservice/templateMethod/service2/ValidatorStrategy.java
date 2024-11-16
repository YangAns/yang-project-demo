package com.yang.designpatternservice.templateMethod.service2;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("unused")
public interface ValidatorStrategy<T> {

    /**
     * 判断当前策略是否支持校验该对象
     *
     * @param vo      要校验的对象
     * @param context 上下文或类型信息
     * @return 是否支持校验
     */
    boolean support(T vo, Object context);



    /**
     * 执行校验逻辑
     *
     * @param vo            要校验的对象
     * @param errorMessages 收集的错误信息
     * @param errorFields   收集的错误字段信息
     * @param errorCount    错误计数器
     */
    void validate(T vo, List<String> errorMessages, List<String> errorFields, AtomicInteger errorCount);



    /**
     * 校验并添加错误信息
     * @param condition 校验条件 为true时添加错误信息!
     * @param message 错误信息
     * @param errorCount 错误数量
     * @param errorMessages 错误信息列表
     * @param errorFields 错误字段列表
     * @param fields 错误字段
     */
    default void checkAndAddErrorMessage(boolean condition, String message, AtomicInteger errorCount, List<String> errorMessages, List<String> errorFields, String... fields) {
        // 校验条件为true，则添加错误信息
        if (condition) {
            errorCount.incrementAndGet();
            errorMessages.add(message);
            errorFields.addAll(Arrays.asList(fields));
        }
    }




}