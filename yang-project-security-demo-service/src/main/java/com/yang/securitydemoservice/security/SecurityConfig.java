package com.yang.securitydemoservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.access.event.AuthorizationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/10/16
 */
@SuppressWarnings("all")
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/demo/d4").permitAll() // 无需登录
                .antMatchers("/demo/d2").permitAll()
                .anyRequest().permitAll()
                .and()
                .formLogin()
//                .loginProcessingUrl()
                .and()
                .csrf().disable()
                .httpBasic();
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


    /**
     * 授权失败事件
     * @param event
     */
    @EventListener(AuthorizationFailureEvent.class)
    public void handleAuthorizationFailureEvent(AuthorizationFailureEvent event) {
        System.out.println(event.getAuthentication().getPrincipal());
        System.out.println(event.getAccessDeniedException().getMessage());
    }


    /**
     * 身份认证成功事件
     * @param event
     */
    @EventListener(AuthenticationSuccessEvent.class)
    public void handlerAuthenticationSuccessEvent(AuthenticationSuccessEvent event) {
        Object principal = event.getAuthentication().getPrincipal();
        System.out.println(principal);
    }


}
