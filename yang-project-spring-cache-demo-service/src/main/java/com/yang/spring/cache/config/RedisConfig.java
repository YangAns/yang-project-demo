package com.yang.spring.cache.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Configuration
public class RedisConfig {

    /**
     * 配置RedisCacheManager
     * @param factory Redis连接工厂
     * @return RedisCacheManager
     */
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory factory) {
        // 配置序列化方式
        // 如果不指定默认的序列化方式，则默认使用JdkSerializationRedisSerializer
        RedisSerializationContext.SerializationPair<Object> valueSerializationPair =
            RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer());

        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
            .serializeValuesWith(valueSerializationPair)
            .entryTtl(Duration.ofHours(1));  // 全局过期时间

        return RedisCacheManager.builder(factory)
            .cacheDefaults(config)
            .build();
    }


    // 配置RedisCacheConfiguration，推荐这种配置
//    @Bean
//    public RedisCacheConfiguration redisCacheConfiguration() {
//        return RedisCacheConfiguration.defaultCacheConfig()
//            .entryTtl(Duration.ofHours(1))
//            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
//    }
}