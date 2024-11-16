package com.yang.securitydemoservice.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/10/22
 */
@Component
public class CustomUserDetailsPasswordService implements UserDetailsPasswordService {
    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return user;
    }
}
