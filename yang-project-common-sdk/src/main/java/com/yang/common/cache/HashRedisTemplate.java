
package com.yang.common.cache;

import com.yang.common.utils.YRequestUtils;
import com.yang.common.utils.YStrUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.function.Predicate;

import static com.yang.common.utils.YListUtils.list2list;

@SuppressWarnings("unchecked,unused")
@Slf4j
public class HashRedisTemplate<T> extends RedisTemplate<String, T> {

    private final String hashKeyPrefix;

    public HashRedisTemplate(String hashKeyPrefix, Class<T> cacheClass, RedisConnectionFactory redisConnectionFactory) {
        this.hashKeyPrefix = hashKeyPrefix;
        this.setConnectionFactory(redisConnectionFactory);
        this.setKeySerializer(new StringRedisSerializer());
        this.setValueSerializer(new Jackson2JsonRedisSerializer<>(cacheClass));
        this.setHashKeySerializer(new StringRedisSerializer());
        this.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(cacheClass));
    }


    public T getModel(String key) {
        Object o = this.opsForHash().get(toHashKey(), key);
        return null != o ? (T) o : null;
    }

    public List<T> getFilter(Predicate<T> chooseCondition) {
        List<T> all = getAll();
        List<T> result = new ArrayList<>();
        for (T t : all) {
            if (chooseCondition.test(t)) {
                result.add(t);
            }
        }
        return result;
    }

    public List<T> getPart(List<String> keys) {
        if (null == keys || keys.isEmpty()) {
            return new ArrayList<>();
        }
        Set<Object> singleton = new HashSet<>(keys);
        List<Object> values = this.opsForHash().multiGet(toHashKey(), singleton);
        return list2list(values, value -> (T) value);
    }

    public List<T> getAll() {
        List<Object> values = this.opsForHash().values(toHashKey());
        return list2list(values, value -> (T) value);
    }


    public void putModel(String key, T value) {
        if (YStrUtils.isNotNull(value)) {
            this.opsForHash().put(toHashKey(), key, value);
        }
    }

    public void putModels(Map<String, T> values) {
        if (null != values && !values.isEmpty()) {
            this.opsForHash().putAll(toHashKey(), values);
        }
    }

    public void clearModels(List<String> keys) {
        HttpServletRequest request = YRequestUtils.getRequest();
        if (null != request) {
            log.info("{}接口删除键{}=============================================================", YRequestUtils.getApiFromRequest(request), keys);
        }
        if (null == keys || keys.size() == 0) {
            return;
        }
        Object[] keyArr = new Object[keys.size()];
        for (int i = 0; i < keyArr.length; i++) {
            keyArr[i] = keys.get(i);
        }
        this.opsForHash().delete(toHashKey(), keyArr);
    }

    public void clearModel(String key) {
        if (YStrUtils.isNotNull(key)) {
            this.opsForHash().delete(toHashKey(), key);
        }
    }

    public void clearAll() {
        this.delete(toHashKey());
    }

    public boolean exist() {
        String hk = toHashKey();
        Boolean exists = this.hasKey(hk);
        return null != exists && exists;
    }


    public String toHashKey() {
//        return ZYRedisUtils.wrapperKey(hashKeyPrefix);
        return hashKeyPrefix;
    }
}
