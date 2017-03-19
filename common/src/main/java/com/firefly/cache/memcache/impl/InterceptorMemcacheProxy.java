package com.firefly.cache.memcache.impl;

import java.io.Serializable;
import java.util.Map;

import com.firefly.cache.CacheProxy;
import com.firefly.cache.memcache.MemcacheInterceptor;

public class InterceptorMemcacheProxy implements CacheProxy, Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = 7789653659765335552L;
    
    private MemcacheInterceptor headInterceptor;
    
	private String poolName;

	public InterceptorMemcacheProxy(String poolName,
			MemcacheInterceptor headInterceptorT) {
		this.poolName = poolName;
		this.headInterceptor = headInterceptorT;
	}

	@Override
	public Object get(String key) {
		return headInterceptor.handleGet(poolName, key);
	}

	@Override
	public Map<String, Object> getMulti(String[] keys) {
		return headInterceptor.handleGetMulti(poolName, keys);
	}

	@Override
	public boolean put(String key, Object value) {
		return headInterceptor.handlePut(poolName, key, value);

	}

	@Override
	public boolean put(String key, Object value, int expirMins) {
		return headInterceptor.handlePut(poolName, key, value, expirMins);

	}

	@Override
	public void remove(String key) {
		headInterceptor.handleRemove(poolName, key);

	}

	@Override
	public String getString(String key) {

		return (String) headInterceptor.handleGet(poolName, key);
	}

	@Override
	public void putString(String key, String value) {
		headInterceptor.handlePut(poolName, key, value);

	}

	@Override
	public void putString(String key, String value, int expirMins) {
		headInterceptor.handlePut(poolName, key, value, expirMins);

	}

	@Override
	public boolean add(String key, Object value) {
		return headInterceptor.handleAdd(poolName, key, value);
	}

	@Override
	public boolean add(String key, Object value, int expirMins) {
		return headInterceptor.handleAdd(poolName, key, value, expirMins);
	}

    @Override
    public long addOrInrc(String key) {
        return headInterceptor.addOrInrc(poolName, key);
    }

    @Override
    public long inrc(String key) {
        return headInterceptor.inrc(poolName, key);
    }
}
