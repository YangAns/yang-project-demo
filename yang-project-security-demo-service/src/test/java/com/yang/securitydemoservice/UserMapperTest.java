package com.yang.securitydemoservice;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yang.securitydemoservice.domain.entity.User;
import com.yang.securitydemoservice.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/9/26
 */
@SpringBootTest(classes = SecurityDemoApplication.class)
public class UserMapperTest {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    ApplicationContext applicationContext;


    @Test
    void testAdd() {
        applicationContext.getBean(UserMapper.class);
        User user = new User();
        user.setName("test6");
        userMapper.insert(user);
    }


    @Test
    void testUpdate() {
        User user = new User();
        user.setId("1839235897288065026");
        user.setName("test3");
        userMapper.updateById(user);
    }

    @Test
    void testSelect() {
//        LambdaQueryWrapper<User> query = Wrappers.lambdaQuery();
//        query.eq(User::getName,"zs");
//        query.le(User::getBirth,new Date());
//        userMapper.selectList(query);
//        List<User> all = userMapper.findAll();
//        for (User user : all) {
//            System.out.println(user);
//        }


        LambdaQueryWrapper<User> query = Wrappers.lambdaQuery();
        query.isNull(User::getBirth);
        User user1 = userMapper.selectOneOfList(query);
        System.out.println(user1);

    }


    @Test
    void testInsertBatch() {
        // 创建10个用户
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setName("test" + i);
            users.add(user);
        }
        userMapper.insert(users);
    }



}
