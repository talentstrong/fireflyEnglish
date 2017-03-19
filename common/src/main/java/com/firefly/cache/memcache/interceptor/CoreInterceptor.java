package com.firefly.cache.memcache.interceptor;

import java.io.Serializable;
import java.util.Map;

import com.firefly.cache.CacheProxy;
import com.firefly.cache.memcache.MemcacheAdmin;
import com.firefly.cache.memcache.MemcacheInterceptor;

/**
 * 这是核心的拦截器, 直接调用Memcache核心客户端代理。该拦截器是拦截器链的末端。
 * 
 * @author jiangchen
 * @date 2011-03-16
 * 
 */
public class CoreInterceptor implements MemcacheInterceptor, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2229503146468170627L;

    /**
     * 从memcache获取对象
     */
    @Override
    public Object handleGet(String poolName, String key) {
        CacheProxy baseMcProxy = MemcacheAdmin.getBaseProxy(poolName);

        return baseMcProxy.get(key);
    }

    /**
     * 从memcache获取多个对象
     */
    @Override
    public Map<String, Object> handleGetMulti(String poolName, String[] keys) {
        CacheProxy baseMcProxy = MemcacheAdmin.getBaseProxy(poolName);
        return baseMcProxy.getMulti(keys);
    }

    /**
     * 保存对象到memcache
     */
    @Override
    public boolean handlePut(String poolName, String key, Object value) {
        CacheProxy baseMcProxy = MemcacheAdmin.getBaseProxy(poolName);
        return baseMcProxy.put(key, value);

    }

    /**
     * 保存对象到memcache
     */
    @Override
    public boolean handlePut(String poolName, String key, Object value, int expirMins) {
        CacheProxy baseMcProxy = MemcacheAdmin.getBaseProxy(poolName);
        return baseMcProxy.put(key, value, expirMins);

    }

    /**
     * 从memcache移除对象
     */
    @Override
    public void handleRemove(String poolName, String key) {
        CacheProxy baseMcProxy = MemcacheAdmin.getBaseProxy(poolName);
        baseMcProxy.remove(key);

    }

    /**
     * 向memcache新增对象
     */
    @Override
    public boolean handleAdd(String poolName, String key, Object value) {
        CacheProxy baseMcProxy = MemcacheAdmin.getBaseProxy(poolName);
        return baseMcProxy.add(key, value);
    }

    /**
     * 向memcache添加对象
     */
    @Override
    public boolean handleAdd(String poolName, String key, Object value, int expirMins) {
        CacheProxy baseMcProxy = MemcacheAdmin.getBaseProxy(poolName);
        return baseMcProxy.add(key, value, expirMins);
    }

    /**
     * 获取下一个Interceptor处理器，<code>CoreInterceptor</code>处于拦截器链的末端， 下一个拦截器为空。
     */
    @Override
    public MemcacheInterceptor getNextHandler() {
        return null;
    }

    /**
     * 设置下一个拦截器，<code>CoreInterceptor</code>处于拦截器链的末端，不接受下一个拦截器的设置。
     */
    @Override
    public void setNextHandler(MemcacheInterceptor nextHandler) {
        // 方法为空,不接受下一个拦截器的设置
    }

    @Override
    public long addOrInrc(String poolName, String key) {
        CacheProxy baseMcProxy = MemcacheAdmin.getBaseProxy(poolName);
        return baseMcProxy.addOrInrc(key);
    }

    @Override
    public long inrc(String poolName, String key) {
        CacheProxy baseMcProxy = MemcacheAdmin.getBaseProxy(poolName);
        return baseMcProxy.inrc(key);
    }
}
