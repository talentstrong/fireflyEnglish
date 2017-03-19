package com.firefly.cache.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.firefly.cache.redis.conf.HrdRedisPoolConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisProxy {

    /** 缓存生存时间 */
    private static final int DEFAULT_EXPIRE = 3600 * 24;

    private static JedisPool jedisPool = null;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxActive(HrdRedisPoolConfig.REDIS_POOL_MAXACTIVE);
        config.setMaxIdle(HrdRedisPoolConfig.REDIS_POOL_MAXIDLE);
        config.setMaxWait(HrdRedisPoolConfig.REDIS_POOL_MAXWAIT);
        config.setTestOnBorrow(HrdRedisPoolConfig.REDIS_POOL_TESTONBORROW);
        config.setTestOnReturn(HrdRedisPoolConfig.REDIS_POOL_TESTONRETURN);
        // redis如果设置了密码：
        jedisPool = new JedisPool(config, HrdRedisPoolConfig.REDIS_HOST, HrdRedisPoolConfig.REDIS_PORT, 10000,
                HrdRedisPoolConfig.REDIS_PASS);
    }

    public static JedisPool getPool() {
        return jedisPool;
    }

    /**
     * 从jedis连接池中获取获取jedis对象
     */
    public static Jedis getJedis() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
        } catch (Exception e) {
            if (jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            throw new RuntimeException("[getConnection]" + " error:" + e.getMessage());
        }
        return jedis;
    }

    /**
     * 回收jedis
     */
    public static void returnJedis(Jedis jedis) {
        if (jedis != null)
            jedisPool.returnResource(jedis);
    }

    /**
     * 设置过期时间
     */
    public static void expire(String key, int seconds) {
        if (seconds <= 0) {
            return;
        }
        Jedis jedis = getJedis();
        jedis.expire(key, seconds);
        returnJedis(jedis);
    }

    /**
     * 设置默认过期时间
     */
    public static void expire(String key) {
        expire(key, DEFAULT_EXPIRE);
    }

    /**
     * 设置默认过期时间
     */
    public static void expire(String key, long seconds) {
        expire(key, seconds);
    }

    public static void set(String key, String value) {
        if (StringUtils.isEmpty(key))
            return;
        Jedis jedis = getJedis();
        jedis.set(key, value);
        returnJedis(jedis);
    }

    public static void set(String key, Object value) throws IOException {
        if (StringUtils.isEmpty(key))
            return;
        Jedis jedis = getJedis();
        jedis.set(key.getBytes(), serialize(value));
        returnJedis(jedis);
    }

    public static void set(String key, int value) {
        if (StringUtils.isEmpty(key))
            return;
        set(key, String.valueOf(value));
    }

    public static void set(String key, long value) {
        if (StringUtils.isEmpty(key))
            return;
        set(key, String.valueOf(value));
    }

    public static void set(String key, float value) {
        if (StringUtils.isEmpty(key))
            return;
        set(key, String.valueOf(value));
    }

    public static void set(String key, double value) {
        if (StringUtils.isEmpty(key))
            return;
        set(key, String.valueOf(value));
    }

    public static Float getFloat(String key) {
        if (StringUtils.isEmpty(key))
            return null;
        return Float.valueOf(getStr(key));
    }

    public static Double getDouble(String key) {
        if (StringUtils.isEmpty(key))
            return null;
        return Double.valueOf(getStr(key));
    }

    public static Long getLong(String key) {
        if (StringUtils.isEmpty(key))
            return null;
        return Long.valueOf(getStr(key));
    }

    public static Integer getInt(String key) {
        if (StringUtils.isEmpty(key))
            return null;
        return Integer.valueOf(getStr(key));
    }

    public static String getStr(String key) {
        if (StringUtils.isEmpty(key))
            return null;
        Jedis jedis = getJedis();
        String value = jedis.get(key);
        returnJedis(jedis);
        return value;
    }

    public static Object getObj(String key) {
        if (StringUtils.isEmpty(key))
            return null;
        Jedis jedis = getJedis();
        byte[] bits = jedis.get(key.getBytes());
        Object obj = unserialize(bits);
        returnJedis(jedis);
        return obj;
    }

    /**
     * 序列化
     *
     * @param object
     * @return
     */
    public static byte[] serialize(Object obj) throws IOException {
        Assert.notNull(obj);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);

        byte[] result = bos.toByteArray();
        oos.flush();
        oos.close();
        bos.close();
        return result;
    }

    /**
     * 反序列化
     *
     * @param bytes
     * @return
     */
    public static Object unserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
            // 反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 增加1
     * 
     * @param key
     */
    public static Long incr(String key) {
        Jedis jedis = getJedis();
        Long result = jedis.incr(key);
        returnJedis(jedis);
        return result;
    }

    /**
     * 增加某个值
     * 
     * @param key
     */
    public static void incrBy(String key, long step) {
        if (StringUtils.isEmpty(key))
            return;
        Jedis jedis = getJedis();
        jedis.incrBy(key, step);
        returnJedis(jedis);
    }

    public static void main(String[] args) {
        System.out.println(RedisProxy.getStr("fsdfsdgsdg"));
    }
}
