package com.firefly.common.logicTemplate;

import com.firefly.cache.CacheProxy;
import com.firefly.cache.EmptyData;

public abstract class GetValueWithCache<T> {

    private CacheProxy cache;

    private String cacheKey;

    private int cacheTime;

    public GetValueWithCache(CacheProxy cache, String key, int cacheTime) {
        this.cache = cache;
        this.cacheKey = key;
        this.cacheTime = cacheTime;
    }

    public T get() {
        Object obj = cache.get(cacheKey);
        if (obj != null) {
            if (obj instanceof EmptyData) {
                return null;
            } else {
                return (T) obj;
            }
        } else {
            T result = doBiz();
            if (result != null) {
                cache.put(cacheKey, result, cacheTime);
                return result;
            } else {
                cache.put(cacheKey, EmptyData.getInstance(), CacheProxy.EXPIRE_MINS_MIN);
                return null;
            }
        }
    }

    public abstract T doBiz();
}
