package com.yang.designpatternservice.templateMethod.service2;

import com.yang.common.utils.YListUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("unused")
public class ValidatorManager<T> {
    private final List<ValidatorStrategy<T>> strategies;

    public ValidatorManager(List<ValidatorStrategy<T>> strategies) {
        this.strategies = strategies;
    }

    public void validate(T vo, Object context, List<String> errorMessages, List<String> errorFields, AtomicInteger errorCount) {
        if(YListUtils.isEmptyList(strategies)) {
            return;
        }
        for (ValidatorStrategy<T> strategy : strategies) {
            if (strategy.support(vo, context)) {
                strategy.validate(vo, errorMessages, errorFields, errorCount);
            }
        }
    }
}