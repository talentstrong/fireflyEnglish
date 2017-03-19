package com.firefly.cache.memcache;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.danga.MemCached.MemCachedClient;
import com.firefly.cache.CacheProxy;
import com.firefly.cache.memcache.conf.MemcachePoolConfig;
import com.firefly.common.MD5Support;
import com.firefly.version.CurrentVersionUtil;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;

/**
 * memcache 客户端抽象代理, 具体实现继承该类
 *
 * @author jiangchen
 * @date 2011-03-16
 */
public abstract class AbstractMemcacheProxy implements CacheProxy {
    protected static final Logger logger = Logger
            .getLogger(AbstractMemcacheProxy.class);

    protected MemCachedClient client;
    protected MemcachePoolConfig poolConfig;

    public AbstractMemcacheProxy(MemcachePoolConfig poolConfig) {
        this.client = new MemCachedClient(poolConfig.getPoolName());
        client.set("_test", 1);
        Integer testVal = (Integer) client.get("_test");
        Assert.isTrue(testVal != null, "memcached server init error:" + poolConfig.getPoolName());
        this.poolConfig = poolConfig;
    }

    /**
     * 对传入的key进行二次处理
     *
     * @param oldkey
     * @return
     */
    protected String getGoodKey(String oldkey) {
        if (oldkey == null) {
            return null;
        }
        int bylength = 0;
        String tempOldkey = oldkey;

        StringBuffer sb = new StringBuffer(tempOldkey);
        String encodekey = "";
        String currentVersion = null;
        try {
            currentVersion = CurrentVersionUtil.getCurrentVersion();
            // 从session pool中获取yhd版本
            // currentVersion =
            // CurrentVersionUtil.getYHDCurrentVersionFromMemcache();
            // 当前pool不支持版本号时，则将当前版本号赋值为0
            if (poolConfig != null && !poolConfig.isVersionEnable()) {
                currentVersion = "0";
            }
            if (!"".equals(currentVersion)) {
                tempOldkey = sb.append("_").append(currentVersion).toString();
            }
            encodekey = URLEncoder.encode(tempOldkey, "UTF-8");
            // oldkey 变为 oldkey+"_"+codeversion
        } catch (Exception e1) {
            try {
                encodekey = URLEncoder.encode(tempOldkey, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                encodekey = tempOldkey;
                logger.error("old key can't covent to utf-8 || "
                        + e.getMessage());
            }
            logger.warn(e1.getMessage());
        }

        try {
            bylength = encodekey.length();

            if (bylength > 240 || tempOldkey.indexOf(' ') > -1) {
                // 经测试memcached最大支持250位key
                logger.debug("this key is too long or have space :"
                        + tempOldkey + "||  MD5 to 32 bit length!!");
                String headkey = null;
                if (encodekey.length() >= 48) {
                    headkey = encodekey.substring(0, 48);
                } else {
                    headkey = encodekey;
                }
                return headkey + "_" + MD5Support.MD5(tempOldkey) + "_"
                        + currentVersion;
            } else {
                return tempOldkey;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return tempOldkey;

        }
    }
}
