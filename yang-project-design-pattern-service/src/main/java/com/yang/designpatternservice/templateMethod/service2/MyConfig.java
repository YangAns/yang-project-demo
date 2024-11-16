package com.yang.designpatternservice.templateMethod.service2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/10/23
 */
@Configuration
public class MyConfig {
    @Bean
    public ValidatorManager<User> validatorManager(List<ValidatorStrategy<User>> strategies) {
        return new ValidatorManager<>(strategies);
    }
}
