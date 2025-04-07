package com.yang.spring.cache.service;

import com.yang.spring.cache.domain.Account;
import com.yang.spring.cache.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2025/4/6
 */
@Service
public class AccountService {

    @Autowired
    private AccountMapper accountMapper;


    // 缓存查询结果：如果缓存存在，直接返回，不执行方法体
    @Cacheable(cacheNames = "account",key = "#id")
    public Account getUserById(Long id) {
        System.out.println("查询数据库 -> User ID: " + id);
        return accountMapper.selectById(id);
    }

    // 更新用户并清除缓存
    @CacheEvict(value = "users", key = "#id")
    public void updateUser(Long id, String newName) {
        Account account = accountMapper.selectById(id);
        account.setName(newName);
        accountMapper.updateById(account);
    }

    // 删除用户并清除缓存
    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(Long id) {
        accountMapper.deleteById(id);
    }
}
