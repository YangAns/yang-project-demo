package com.yang.oauth2.logindemoservice.config;

import com.yang.security.sdk.security.CustomAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/11/14
 */
//@Component
public class LoginAuthenticationProvider implements CustomAuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
