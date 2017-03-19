package com.firefly.cache.memcache.interceptor;

import java.io.Serializable;
import java.util.Map;

import com.firefly.cache.memcache.MemcacheInterceptor;

/**
 * 默认实现的空拦截器，可以作为其他普通拦截器的父类。
 * 
 * 拦截器链以<code>EmptyInterceptor</code>的一个实例为链首, 以<code>CoreInterceptor</code>
 * 的一个实例为链尾。
 * 
 * <p/>
 * 执行顺序是: <code>EmptyInterceptor</code>-->用户自定义的拦截器链 -->
 * <code>CoreInterceptor</code>.
 * 
 * @author jiangchen
 * @date 2011-03-16
 * 
 */
public class EmptyInterceptor implements MemcacheInterceptor, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5505505268728055301L;
    
    protected MemcacheInterceptor nextHandler;

    public EmptyInterceptor() {
    }

    @Override
    public Object handleGet(String poolName, String key) {
        return getNextHandler().handleGet(poolName, key);
    }

    @Override
    public Map<String, Object> handleGetMulti(String poolName, String[] keys) {
        return getNextHandler().handleGetMulti(poolName, keys);
    }

    @Override
    public boolean handlePut(String poolName, String key, Object value) {
        return getNextHandler().handlePut(poolName, key, value);
    }

    @Override
    public boolean handlePut(String poolName, String key, Object value, int expirMins) {
        return getNextHandler().handlePut(poolName, key, value, expirMins);
    }

    @Override
    public void handleRemove(String poolName, String key) {
        getNextHandler().handleRemove(poolName, key);
    }

    @Override
    public boolean handleAdd(String poolName, String key, Object value) {
        return getNextHandler().handleAdd(poolName, key, value);
    }

    @Override
    public boolean handleAdd(String poolName, String key, Object value, int expirMins) {
        return getNextHandler().handleAdd(poolName, key, value, expirMins);
    }

    @Override
    public MemcacheInterceptor getNextHandler() {
        return nextHandler;
    }

    @Override
    public void setNextHandler(MemcacheInterceptor nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public long addOrInrc(String poolName, String key) {
        return getNextHandler().addOrInrc(poolName, key);
    }

    @Override
    public long inrc(String poolName, String key) {
        return getNextHandler().inrc(poolName, key);
    }
}
