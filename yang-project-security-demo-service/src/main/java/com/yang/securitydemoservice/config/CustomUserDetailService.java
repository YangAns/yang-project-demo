package com.yang.securitydemoservice.config;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yang.securitydemoservice.domain.entity.MyUserDetail;
import com.yang.securitydemoservice.domain.entity.User;
import com.yang.securitydemoservice.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/10/21
 */
@Component
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getAccount, username));
        if(user == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        return new MyUserDetail(user, Collections.emptyList());
    }
}
