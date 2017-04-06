package com.firefly.cache.memcache.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.firefly.cache.memcache.conf.MemcachePoolConfig;
import com.firefly.cache.memcache.exception.MemcacheException;
import com.firefly.cache.memcache.AbstractMemcacheProxy;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;

public class BaseMemcacheProxy extends AbstractMemcacheProxy {

    // private static final String FAILED_KEY_COUNTER = "error_key_counter";
    //
    // private static final Integer MAX_COUNTER = 1000;
    //
    // private static final String FAILED_KEY = "memcache_failed_";

    private static final Logger LOG = Logger.getLogger(BaseMemcacheProxy.class);

    public BaseMemcacheProxy(MemcachePoolConfig poolConfig) {
        super(poolConfig);
    }

    @Override
    public boolean put(String key, Object value) {
        return this.put(key, value, this.poolConfig.getDefaultExpiryMinutes());
    }

    @Override
    public boolean put(String key, Object value, int expirMins) {
        boolean result = false;
        if (value != null) {
            long expirMilliSecond;
            int tempExpirMins = expirMins;
            if (tempExpirMins <= 0) {
                tempExpirMins = 30;
            }
            expirMilliSecond = 1000L * 60 * tempExpirMins;

            String tempKey = key;
            tempKey = getGoodKey(tempKey);
            result = client.set(tempKey, value, new Date(expirMilliSecond));
            LOG.debug("put " + tempKey + ":" + value);
        } else {
            try {
                throw new MemcacheException("Memcache value is null error! The key is :" + key);
            } catch (Exception e) {
                LOG.error(e.getMessage());
            }
        }
        return result;
    }

    @Override
    public Object get(String key) {
        Object obj = client.get(getGoodKey(key));

        LOG.debug("get " + getGoodKey(key) + ":" + obj);
        return obj;
    }

    @Override
    public Map<String, Object> getMulti(String[] keys) {
        LOG.debug("getMulti " + Arrays.toString(keys));
        Map<String, Object> result = new HashMap<String, Object>();
        if (keys != null && keys.length > 0) {
            String[] goodKeys = new String[keys.length];
            Map<String, String> goodKeyMap = new HashMap<String, String>();
            for (int j = 0; j < keys.length; j++) {
                String goodKey = getGoodKey(keys[j]);
                goodKeyMap.put(goodKey, keys[j]);
                goodKeys[j] = goodKey;
            }

            Map<String, Object> mapFromMc = client.getMulti(goodKeys);
            if (!mapFromMc.isEmpty()) {

                for (Entry<String, Object> entry : mapFromMc.entrySet()) {
                    String oriKey = goodKeyMap.get(entry.getKey());
                    result.put(oriKey, entry.getValue());
                }
            }
        }

        return result;
    }

    @Override
    public void remove(String key) {
        this.client.delete(getGoodKey(key));
        LOG.debug("remove " + getGoodKey(key));
    }

    @Override
    public String getString(String key) {
        Object obj = get(key);

        return (String) obj;
    }

    @Override
    public void putString(String key, String value) {
        put(key, value);
    }

    @Override
    public void putString(String key, String value, int expirMins) {
        put(key, value, expirMins);
    }

    @Override
    public boolean add(String key, Object value) {
        return add(key, value, poolConfig.getDefaultExpiryMinutes());
    }

    @Override
    public boolean add(String key, Object value, int expirMins) {
        if (!checkNotNull(key, value)) {
            return false;
        }
        LOG.debug("add " + key);
        String tempKey = key;
        tempKey = getGoodKey(tempKey);

        int tempExpirMins = expirMins;
        tempExpirMins = tempExpirMins <= 0 ? poolConfig.getDefaultExpiryMinutes() : tempExpirMins;

        long expirMilliSeconds = (long) tempExpirMins * 60 * 1000L;
        return client.add(tempKey, value, new Date(expirMilliSeconds));
    }

    /**
     * 验证key和value非null
     */
    private boolean checkNotNull(String key, Object value) {
        if (key == null) {
            return false;
        } else if (value == null) {
            logger.warn("Saving null memcached value! The key is :" + key);
            return false;
        }
        return true;
    }

    @Override
    public long addOrInrc(String key) {
        Assert.notNull(key);

        long expirMilliSecond;
        int tempExpirMins = 60 * 24;
        expirMilliSecond = 1000L * 60 * tempExpirMins;

        String tempKey = key;
        tempKey = getGoodKey(tempKey);
        long result = client.addOrIncr(tempKey);
        LOG.debug("put " + tempKey + ":" + result);

        return result;
    }

    @Override
    public long inrc(String key) {
        Assert.notNull(key);

        long expirMilliSecond;
        int tempExpirMins = 60 * 24;
        expirMilliSecond = 1000L * 60 * tempExpirMins;

        String tempKey = key;
        tempKey = getGoodKey(tempKey);
        long result = client.incr(tempKey);
        LOG.debug("put " + tempKey + ":" + result);

        return result;
    }
}
