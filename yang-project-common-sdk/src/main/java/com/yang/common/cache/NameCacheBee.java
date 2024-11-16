package com.yang.common.cache;

import java.util.List;
import java.util.function.Function;

/**
 * 名称缓存
 */
@SuppressWarnings("unused")
public interface NameCacheBee {

    String prefix();

    Function<String, String> nameFunction();

    default void flush(String businessId, String name) {
        NameCache.flush(businessId, name, prefix(), nameFunction());
    }

    default String getName(String key) {
        return NameCache.getName(key, prefix(), nameFunction());
    }

    default  void remove( String key){
      NameCache.remove(prefix(), key);
    }

    default   void remove(List<String> keys){
        NameCache.remove(prefix(), keys);
    }
}