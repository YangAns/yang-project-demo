package com.yang.securitydemoservice.security;

import com.yang.common.annotions.AutowiredFalse;
import com.yang.security.sdk.security.MyAuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/10/16
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @AutowiredFalse
    private UserLoginConfigurer<HttpSecurity> userLoginConfigurer;

    @Autowired
    private DefaultLoginEntryPointSupport defaultLoginEntryPointSupport;

    @Override
    public void init(WebSecurity web) throws Exception {
        super.init(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //访问请求的授权规则配置
        http
                .authorizeRequests() // 开始配置 URL 授权
                .antMatchers("/public/**").permitAll() // 允许所有人访问 /public/** 路径
//                .antMatchers("/demo/**").hasRole("ADMIN") // 仅允许具有 ADMIN 角色的用户访问 /admin/** 路径
                .antMatchers("/user/**").authenticated() // 访问 /user/** 路径时需要认证
                .anyRequest().authenticated() // 其他所有请求都需要认证
                .and()
                .csrf().disable() // 禁用 CSRF
                .formLogin().successHandler(new CustomAuthenticationSuccessHandler()).failureHandler(new CustomAuthenticationFailureHandler()) ;// 启用表单登录
     /*       http
                    .authorizeRequests()
                    .anyRequest().authenticated() //所有请求都需要认证
                    .and()
                    .formLogin().disable() //禁用表单登录
                    .apply(userLoginConfigurer).and()
                    .csrf().disable()
                    .logout().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//禁用session
                    .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(defaultLoginEntryPointSupport)
                    .accessDeniedHandler(defaultLoginEntryPointSupport)
                    .and()
                    .httpBasic().disable();*/
//                    .oauth2ResourceServer().accessDeniedHandler(defaultLoginEntryPointSupport)
//                    .authenticationEntryPoint(defaultLoginEntryPointSupport);
    }



//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider()
//    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager authenticationManager = super.authenticationManagerBean();
        return new MyAuthenticationManager(authenticationManager);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
