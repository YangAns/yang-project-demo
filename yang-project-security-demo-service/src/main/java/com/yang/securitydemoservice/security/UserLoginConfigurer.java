package com.yang.securitydemoservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 用户登录配置器
 * </p>
 *
 * @author YangAns
 * @since 2024/10/26
 */
@Component
public class UserLoginConfigurer <H extends HttpSecurityBuilder<H>> extends AbstractHttpConfigurer<UserLoginConfigurer<H>, H> {

    private CustomAuthenticationFailureHandler CustomAuthenticationFailureHandler;

    private CustomAuthenticationSuccessHandler CustomAuthenticationSuccessHandler;

    @Autowired
    public void setCustomAuthenticationFailureHandler(
            CustomAuthenticationFailureHandler customAuthenticationFailureHandler) {
        this.CustomAuthenticationFailureHandler = customAuthenticationFailureHandler;
    }

    @Autowired
    public void setCustomAuthenticationSuccessHandler(
            CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.CustomAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    public UserLoginConfigurer() {

    }

    @Override
    public void init(H http) throws Exception {
        super.init(http);
    }

    @Override
    public void configure(H http) throws Exception {
        CustomUsernamePasswordAuthenticationFilter authFilter = new CustomUsernamePasswordAuthenticationFilter();
        authFilter.setRequiresAuthenticationRequestMatcher(createLoginProcessingUrlMatcher());
        authFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        authFilter.setAuthenticationSuccessHandler(CustomAuthenticationSuccessHandler);
        authFilter.setAuthenticationFailureHandler(CustomAuthenticationFailureHandler);
        http.addFilterAfter(authFilter, LogoutFilter.class);
    }


    private RequestMatcher createLoginProcessingUrlMatcher(){
        return new AntPathRequestMatcher("/user/login","POST");
    }
}