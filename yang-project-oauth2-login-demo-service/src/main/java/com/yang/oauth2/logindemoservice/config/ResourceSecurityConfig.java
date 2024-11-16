package com.yang.oauth2.logindemoservice.config;

import com.yang.common.annotions.AutowiredFalse;
import com.yang.security.sdk.security.CustomAuthenticationProvider;
import com.yang.security.sdk.security.MyAuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 资源服务器配置
 * </p>
 *
 * @author YangAns
 * @since 2024/10/16
 */
@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class ResourceSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @AutowiredFalse
    private List<CustomAuthenticationProvider> providers = Collections.emptyList();

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .requestMatcher(new AntPathRequestMatcher("/**"))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 禁用session
                .and()
                .formLogin().disable() // 禁用默认的表单登录
                .logout().disable()   // 禁用默认的注销
                .requestCache().disable()  // 禁用默认的请求缓存
                .addFilterBefore(new Oauth2AnonymousAuthenticationFilter(), AnonymousAuthenticationFilter.class)
                .oauth2ResourceServer()
                .jwt();
        providers.forEach(http::authenticationProvider);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager authenticationManager = super.authenticationManagerBean();
        return new MyAuthenticationManager(authenticationManager);
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public JwtDecoder jwtDecoder() {
        // 配置密钥和 HMAC 签名验证
        String secretKey = "wT26nqFlylE1Q/TJcyrtieiT676BnHfmxRobEVuCIf4=";  // 对称密钥
        SecretKey key = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(key).build();  // 使用 HMAC 密钥来创建解码器
    }

}
