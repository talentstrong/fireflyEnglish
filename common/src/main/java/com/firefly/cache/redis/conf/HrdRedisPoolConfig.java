package com.firefly.cache.redis.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.firefly.utils.LogUtil;

import lombok.extern.log4j.Log4j;

@Log4j
public class HrdRedisPoolConfig {

    public static String REDIS_HOST;
    public static Integer REDIS_PORT;
    public static String REDIS_PASS;
    public static Integer REDIS_POOL_MAXIDLE;
    public static Integer REDIS_POOL_MAXACTIVE;
    public static Long REDIS_POOL_MAXWAIT;
    public static Boolean REDIS_POOL_TESTONBORROW;
    public static Boolean REDIS_POOL_TESTONRETURN;

    static {
        init();
    }

    private static void init() {
        String redisConfPath = System.getProperty("confPath") + "/common/redis.properties";
        try {
            File confFile = new File(redisConfPath);
            if (confFile.exists()) {
                InputStream in = new FileInputStream(redisConfPath);
                Properties prop = new Properties();
                prop.load(in);
                REDIS_HOST = prop.getProperty("redisHost");
                REDIS_PORT = Integer.valueOf(prop.getProperty("redisPort"));
                REDIS_PASS = prop.getProperty("redisPass");
                REDIS_POOL_MAXIDLE = Integer.valueOf(prop.getProperty("redisPoolMaxIdle"));
                REDIS_POOL_MAXACTIVE = Integer.valueOf(prop.getProperty("redisPoolMaxActive"));
                REDIS_POOL_MAXWAIT = Long.valueOf(prop.getProperty("redisPoolMaxWait"));
                REDIS_POOL_TESTONBORROW = Boolean.valueOf(prop.getProperty("redisPoolTestOnBorrow"));
                REDIS_POOL_TESTONRETURN = Boolean.valueOf(prop.getProperty("redisPoolTestOnReturn"));
            }
        } catch (FileNotFoundException e) {
            LogUtil.error(log, "error on load Redis conf, cant find " + redisConfPath, e);
        } catch (IOException e) {
            LogUtil.error(log, "error on load Redis conf, cant load inputStream", e);
        }

    }

}
