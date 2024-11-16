package com.yang.designpatternservice.templateMethod.service2;

import cn.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/10/23
 */
public abstract class AbstractValidationService<T, E extends ValidationErrorVo> implements ValidationService<T, E> {
    private Validator validator;
    public ValidatorManager<T> validatorManager;

    @Autowired
    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public void setValidatorManager(ValidatorManager<T> validatorManager) {
        this.validatorManager = validatorManager;
    }

    @Override
    public List<E> validateData(List<T> dataList, Object param) {
        Class<?> aClass = determineValidationGroup(param);
        List<E> validationErrorVos = new ArrayList<>();
        for (T t : dataList) {
            AtomicInteger errorCount = new AtomicInteger(0);
            List<String> errorMessages = new ArrayList<>();
            List<String> errorFields = new ArrayList<>();
            Set<ConstraintViolation<T>> validates = validator.validate(t, aClass);
            if (CollUtil.isNotEmpty(validates)) {
                int size = validates.size();
                errorCount.addAndGet(size);
                for (ConstraintViolation<T> violation : validates) {
                    errorMessages.add(violation.getMessage());
                    errorFields.add(violation.getPropertyPath().toString());
                }
            }
            doOtherValidation(t, param, errorMessages, errorFields, errorCount);
            if (errorCount.get() > 0) {
                validationErrorVos.add(doResult(errorCount.get(), t, errorMessages, errorFields, errorCount));
            }
        }
        return validationErrorVos;
    }

    public abstract E doResult(int param, T t, List<String> errorMessages, List<String> errorFields, AtomicInteger errorCount);

    public void doOtherValidation(T t, Object param, List<String> errorMessages, List<String> errorFields, AtomicInteger errorCount) {
        //
    }

    public abstract Class<?> determineValidationGroup(Object param);
}
