package com.yang.securitydemoservice.security;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <p>
 * 自定义认证提供者
 * </p>
 *
 * @author YangAns
 * @since 2024/10/22
 */
//@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserDetailsPasswordService userDetailsPasswordService;


    @Autowired
    public void setPasswordEncoder(ObjectProvider<PasswordEncoder> passwordEncoder) {
        passwordEncoder.ifAvailable(passwordEncoder1 -> this.passwordEncoder = passwordEncoder1);
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //获取用户
        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
        if (userDetails == null) {
            throw new RuntimeException("用户不存在");
        }
        //校验用户
        //自定义校验逻辑

        //校验密码
        Object credentials = authentication.getCredentials();
        if (credentials == null) {
            throw new RuntimeException("密码不能为空");
        }
        //前端传过来的密码
        String presentedPassword = authentication.getCredentials().toString();

        //从数据库中查到的密码
        String dbPassword = userDetails.getPassword();

        String encode = passwordEncoder.encode(presentedPassword);
        System.out.println("encode:" + encode);

        boolean matches = passwordEncoder.matches(presentedPassword, dbPassword);
        if (!matches) {
            throw new RuntimeException("密码匹配失败");
        }

        boolean upgradeEncoding = this.userDetailsPasswordService != null
                && this.passwordEncoder.upgradeEncoding(dbPassword);

        if (upgradeEncoding) {
            String newPassword = this.passwordEncoder.encode(presentedPassword);
            System.out.println("newPassword:" + newPassword);
            userDetails = this.userDetailsPasswordService.updatePassword(userDetails, newPassword);
        }
//        return new CustomUsernamePasswordToken(userDetails, authentication.getCredentials(), userDetails.getAuthorities());
        return new UsernamePasswordAuthenticationToken(userDetails, authentication.getCredentials(), userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
//        return CustomUsernamePasswordToken.class.isAssignableFrom(authentication);
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
