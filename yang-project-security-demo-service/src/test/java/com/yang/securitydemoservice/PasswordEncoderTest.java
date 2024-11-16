package com.yang.securitydemoservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/11/4
 */
@SpringBootTest(classes = SecurityDemoApplication.class)
public class PasswordEncoderTest {

    @Test
    void test() {
        String password = "123456";

        BCryptPasswordEncoder by = new BCryptPasswordEncoder(4);
        String encode = by.encode(password);

        System.out.println("加密后的密码：" + encode);


        boolean matches = by.matches(password, encode);

        System.out.println(matches);


    }
}
