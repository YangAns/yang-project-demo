package com.yang.securitydemoservice.cache;

import com.yang.securitydemoservice.domain.entity.Office;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/9/21
 */

@Component
public class OfficeCacheManager implements ApplicationRunner {

    @Autowired
    private RedisTemplate<String, Office> redisTemplate;


    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
