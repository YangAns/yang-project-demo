package com.yang.designpatternservice.templateMethod.service2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.groups.Default;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/10/23
 */
@Service
public class UserServiceImpl extends AbstractValidationService<User, ValidationErrorVo> {


    @Override
    public ValidationErrorVo doResult(int param, User user, List<String> errorMessages, List<String> errorFields, AtomicInteger errorCount) {
       return new RetireValidationErrorVo(user.getId(),user.getName(),errorCount.get(),errorMessages,errorFields);
    }

    @Override
    public void doOtherValidation(User user, Object param, List<String> errorMessages, List<String> errorFields, AtomicInteger errorCount) {
        super.doOtherValidation(user, param, errorMessages, errorFields, errorCount);
    }

    @Override
    public Class<?> determineValidationGroup(Object param) {
        String paramStr = (String) param;
        switch (paramStr) {
            case "1":
                return RETIRE_TX.class;
            case "2":
                return RETIRE_LX.class;
            default:
                return Default.class;
        }
    }

    @Autowired
    @Override
    public void setValidatorManager(ValidatorManager<User> validatorManager) {
        super.setValidatorManager(validatorManager);
    }
}
