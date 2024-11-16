package com.yang.securitydemoservice.config;

import com.yang.common.cache.HashRedisTemplate;
import com.yang.securitydemoservice.domain.entity.Office;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/9/20
 */
@Configuration
public class RedisConfig {

    @Autowired
    public RedisConfig(DoAfter doAfter) {
        System.out.println("init config");
        doAfter.doAfter();
    }


    @Bean
    public HashRedisTemplate<Office> customColumnRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new HashRedisTemplate<>("sys_custom_office", Office.class, redisConnectionFactory);
    }
}
