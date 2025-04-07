package com.yang.security.sdk.security;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/12/4
 */
public interface SpecialPathAuthenticationStrategy {

    boolean support(HttpServletRequest request);

    void authorise();

}
