package com.yang.securitydemoservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yang.securitydemoservice.domain.entity.User;
import com.yang.securitydemoservice.mapper.UserMapper;
import com.yang.securitydemoservice.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author YangAns
 * @since 2024-09-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
