package com.yang.securitydemoservice.cache;

import com.yang.common.cache.NameCacheBee;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/9/19
 */
@RequiredArgsConstructor
public enum UserNameCache implements NameCacheBee {
    ;

    @Override
    public String prefix() {
        return null;
    }

    @Override
    public Function<String, String> nameFunction() {
        return null;
    }
}
