package com.yang.security.sdk.security;
import org.springframework.security.core.Authentication;

public class AuthenticationHolder {

    private static final ThreadLocal<Authentication> authenticationShare = new ThreadLocal<>();

    public static void put(Authentication authentication) {
        authenticationShare.set(authentication);
    }

    public static Authentication getRemove() {
        Authentication authentication = authenticationShare.get();
        authenticationShare.remove();
        return authentication;
    }

    public static Authentication get() {
        return authenticationShare.get();
    }

    public static void remove() {
        authenticationShare.remove();
    }
}
