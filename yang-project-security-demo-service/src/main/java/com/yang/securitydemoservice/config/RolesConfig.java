package com.yang.securitydemoservice.config;

import cn.hutool.core.collection.ListUtil;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/12/3
 */
@Component
public class RolesConfig {

    public List<String> getRoles() {
        return ListUtil.of("ROLE_USER", "ROLE_ADMIN");
    }


    public List<String> getAuthority() {
        return ListUtil.of("a1");
    }

}
