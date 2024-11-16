package com.yang.security.sdk.security;

import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/10/21
 */
@Component
public class CustomWebSecurity implements WebSecurityCustomizer {
    @Override
    public void customize(WebSecurity web) {
        // 排除静态资源和 Swagger 相关路径
        web.ignoring()
                .antMatchers("/css/**",
                        "/js/**",
                        "/images/**",
                        "/swagger-ui/**",
                        "/v2/api-docs",
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/auth/**"
                );
    }
}
