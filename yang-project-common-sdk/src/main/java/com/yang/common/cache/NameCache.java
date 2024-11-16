
package com.yang.common.cache;

import com.yang.common.utils.Share;
import com.yang.common.utils.YListUtils;
import com.yang.common.utils.YStrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;


/**
 * 文件描述: 本类缓存只缓存相关恒定的数据。如用户姓名，机构名称，地区名称等。经常变动的数据切误使用本工具类
 * 因为不支持刷新缓存。只能手工去redis删key或者等缓存失效(1天)。
 */
@Component
public class NameCache {

    public final static String CONSTANT_NAME_CACHE = "constant_name_cache_";

    private static StringRedisTemplate redisTemplate;

    public static void remove(String prefix, String key) {
        if (YStrUtils.isNotNull(key)) {
            remove(prefix, Collections.singletonList(key));
        }
    }

    public static void remove(String prefix, List<String> keys) {
        if (YListUtils.isEmptyList(keys)) {
            return;
        }
        String hashKey = toHashKey(prefix);

        Object[] keyObjs = new Object[]{keys.size()};
        for (int i = 0; i < keyObjs.length; i++) {
            keyObjs[i] = keys.get(i);
        }
        redisTemplate.opsForHash().delete(hashKey, keyObjs);
    }

    public static String getName(String key, String prefix, Function<String, String> support) {
        if (YStrUtils.isAnyNull(key, support)) {
            return "";
        }
        String hashKey = toHashKey(prefix);

        String shareKey = hashKey + key;
        String shareName = Share.get(shareKey, String.class);
        if (null != shareName) {
            return shareName;
        }
        Object value = redisTemplate.opsForHash().get(hashKey, key);
        if (YStrUtils.isNotNull(value)) {
            Share.set(shareKey, value);
            return String.valueOf(value);
        }
        String name = support.apply(key);
        if (YStrUtils.isNotNull(name)) {
            Share.set(shareKey, name);
            redisTemplate.opsForHash().put(hashKey, key, name);
            return name;
        }
        Share.set(shareKey, "");
        return "";
    }

    public static void flush(String key, String name, String prefix, Function<String, String> support) {
        String hashKey = toHashKey(prefix);
        if (YStrUtils.isNotNull(name)) {
            redisTemplate.opsForHash().put(hashKey, key, name);
        } else {
            String findName = support.apply(key);
            if (YStrUtils.isNotNull(findName)) {
                redisTemplate.opsForHash().put(hashKey, key, findName);
            }
        }
    }

    @Autowired
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        NameCache.redisTemplate = redisTemplate;
    }

    private static String toHashKey(String prefix) {
//        return ZYRedisUtils.wrapperKey(CONSTANT_NAME_CACHE + prefix).toLowerCase();
        return (CONSTANT_NAME_CACHE + prefix).toLowerCase();
    }

}
