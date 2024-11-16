package com.yang.security.sdk.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class MyAuthenticationManager implements AuthenticationManager {

    private final AuthenticationManager authenticationManager;

    public MyAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication authenticate;
        try {
            AuthenticationHolder.put(authentication);
            authenticate = authenticationManager.authenticate(authentication);
        } finally {
            AuthenticationHolder.remove();
        }
        return authenticate;
    }
}