package com.firefly.cache.memcache.conf;

import java.util.Arrays;

/**
 * Memcache pool的配置参数
 * 
 * @author jiangchen
 * @date 2011-03-16
 */
public class MemcachePoolConfig {

    private String poolName; // pool名称
    private int initConn; // 初始连接数
    private int minConn; // 最小连接数
    private int maxConn; // 最大连接数
    private long maintSleep; // 单位:ms,后台线程管理SocketIO池的检查间隔时间,如果设置为0，则表明不需要后台线程维护SocketIO线程池，默认需要管理
    private boolean nagle = false; // 禁用nagle算法
    private boolean failover = true; // 启用故障转移
    private int socketTo; // 传输超时设置,单位ms
    private int socketConnTo; // 连接超时设置,单位ms
    private int defaultExpiryMinutes; // 默认超时,单位分钟
    private boolean compressEnable = true; // 是对数据否进行压缩
    private boolean versionEnable = true; // 是对缓存的key加上版本号
    private int compressThreshold = 4096; // 当数据超过该字节数值时，进行压缩
    private String[] servers; // 该pool所包含的memcached server,server格式为host:port, 如
                              // 127.0.0.1:11211

    MemcachePoolConfig() {
    }

    public boolean isCompressEnable() {
        return compressEnable;
    }

    public void setCompressEnable(boolean compressEnable) {
        this.compressEnable = compressEnable;
    }

    public boolean isVersionEnable() {
        return versionEnable;
    }

    public void setVersionEnable(boolean versionEnable) {
        this.versionEnable = versionEnable;
    }

    public int getCompressThreshold() {
        return compressThreshold;
    }

    public void setCompressThreshold(int compressThreshold) {
        this.compressThreshold = compressThreshold;
    }

    public int getDefaultExpiryMinutes() {
        return defaultExpiryMinutes;
    }

    public void setDefaultExpiryMinutes(int defaultExpiryMinutes) {
        this.defaultExpiryMinutes = defaultExpiryMinutes;
    }

    public boolean isFailover() {
        return failover;
    }

    public void setFailover(boolean failover) {
        this.failover = failover;
    }

    public int getSocketTo() {
        return socketTo;
    }

    public void setSocketTo(int socketTo) {
        this.socketTo = socketTo;
    }

    public int getSocketConnTo() {
        return socketConnTo;
    }

    public void setSocketConnTo(int socketConnTo) {
        this.socketConnTo = socketConnTo;
    }

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public int getInitConn() {
        return initConn;
    }

    public void setInitConn(int initConn) {
        this.initConn = initConn;
    }

    public int getMinConn() {
        return minConn;
    }

    public void setMinConn(int minConn) {
        this.minConn = minConn;
    }

    public int getMaxConn() {
        return maxConn;
    }

    public void setMaxConn(int maxConn) {
        this.maxConn = maxConn;
    }

    public long getMaintSleep() {
        return maintSleep;
    }

    public void setMaintSleep(long maintSleep) {
        this.maintSleep = maintSleep;
    }

    public boolean isNagle() {
        return nagle;
    }

    public void setNagle(boolean nagle) {
        this.nagle = nagle;
    }

    public String[] getServers() {
        return servers;
    }

    public void setServers(String[] servers) {
        if (null != servers) {
            this.servers = Arrays.copyOf(servers, servers.length);
        } else {
            this.servers = null;
        }
    }
}
