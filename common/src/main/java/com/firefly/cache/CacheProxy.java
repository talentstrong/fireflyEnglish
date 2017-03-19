package com.firefly.cache;

import java.util.Map;

/**
 * 该类对memcache的pool进行基本存取操作
 * 
 * @author jiangchen
 * @version 1.1 2012-04-05
 */
public interface CacheProxy {

    public static final int EXPIRE_MINS_MIN = 1;
    public static final int EXPIRE_MINS_MIDDLE = 6;
    public static final int EXPIRE_MINS_MAX = 60;
    public static final int EXPIRE_DAYS_MIN = 60 * 24;

    /**
     * 存入对象, 缓存时间取默认值
     * 
     * @param key
     * @param value
     */
    boolean put(String key, Object value);

    /**
     * 存入对象
     * 
     * @param key
     * @param value
     * @param expirMins
     *            过期时间
     */
    boolean put(String key, Object value, int expirMins);

    /**
     * 获取对象
     * 
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 获取多个键值
     * 
     * @param keys
     * @return
     */
    Map<String, Object> getMulti(String[] keys);

    /**
     * 从缓存中移除对象
     * 
     * @param key
     */
    void remove(String key);

    /**
     * 存入字符串
     * 
     * @param key
     * @param value
     */
    void putString(String key, String value);

    /**
     * 存入字符串
     * 
     * @param key
     * @param value
     * @param expirMins
     */
    void putString(String key, String value, int expirMins);

    /**
     * 获取字符串
     * 
     * @param key
     * @return
     */
    String getString(String key);

    /**
     * 新增缓存， 过期时间取默认值
     * 
     * @param key
     * @param value
     * @return 操作結果
     */
    boolean add(String key, Object value);

    /**
     * 新增緩存
     * 
     * @param key
     * @param value
     * @param expirMins
     *            过期时间，单位minutes
     * @return 操作结果
     */
    boolean add(String key, Object value, int expirMins);

    long addOrInrc(String key);

    long inrc(String key);
}
