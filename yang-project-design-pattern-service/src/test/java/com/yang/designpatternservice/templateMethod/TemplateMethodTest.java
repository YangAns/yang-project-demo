package com.yang.designpatternservice.templateMethod;

import com.yang.common.annotions.AutowiredFalse;
import com.yang.designpatternservice.DesignApplication;
import com.yang.designpatternservice.templateMethod.service2.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/10/23
 */
@SpringBootTest(classes = DesignApplication.class)
public class TemplateMethodTest {

    @AutowiredFalse
    private UserServiceImpl userService;

    @AutowiredFalse
    ValidationService<User, RetireValidationErrorVo> validationService;


    @Test
    void test() {
        List<RetireValidationErrorVo> validationErrorVos = validationService.validateData(createUserList(), "");
        for (ValidationErrorVo validationErrorVo : validationErrorVos) {
            System.out.println(validationErrorVo);
        }
    }


    private List<User> createUserList() {
        List<User> users = new ArrayList<>();
        users.add(new User(null,"zs","123321",10086L,null));
        users.add(new User("123","","123321",10086L,null));
        users.add(new User("124","","",null,null));
        return users;
    }
}
