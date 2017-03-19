package com.firefly.cache.memcache;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.danga.MemCached.SockIOPool;
import com.firefly.cache.memcache.conf.MemcacheConfig;
import com.firefly.cache.memcache.conf.MemcachePoolConfig;
import com.firefly.cache.memcache.exception.MemcacheInitException;
import com.firefly.cache.memcache.impl.BaseMemcacheProxy;

/**
 * Memcache 客户端管理器，负责客户端的配置连接初始化，缓存客户端，以及销毁连接
 * 
 * @author jiangchen
 * @date 2011-03-16
 * 
 */
public class MemcacheAdmin {

	protected static final Logger logger = Logger
			.getLogger(MemcacheAdmin.class);

	private MemcacheConfig config;
	private Map<String, BaseMemcacheProxy> mcMap;
	private static MemcacheAdmin instance;

	static MemcacheAdmin getInstance() {
		return instance;
	}

	public static BaseMemcacheProxy getBaseProxy(String poolName) {
		return instance.getBaseCient(poolName);
	}

	public synchronized static void init(InputStream in)
			throws MemcacheInitException {
		MemcacheConfig newConf = MemcacheConfig.parseXmlConfig(in);
		init(newConf);
	}

	synchronized static void init(MemcacheConfig newConf) {
		synchronized (MemcacheAdmin.class) {
			if (instance != null) {
				return;
			}
			instance = new MemcacheAdmin(newConf);
		}
	}

	MemcacheAdmin(MemcacheConfig newConf) {
		this.config = newConf;
		mcMap = new HashMap<String, BaseMemcacheProxy>();
		init();
	}

	private void init() {
		logger.info("---->>start to init memcache pools:");
		for (MemcachePoolConfig poolConf : config.getPoolConfigs()) {
			logger.info("    initializing pool " + poolConf.getPoolName()
					+ "...");
			SockIOPool pool = SockIOPool.getInstance(poolConf.getPoolName());
			pool.setServers(poolConf.getServers());

			pool.setInitConn(poolConf.getInitConn());
			pool.setMinConn(poolConf.getMinConn());
			pool.setMaxConn(poolConf.getMaxConn());
			pool.setMaintSleep(poolConf.getMaintSleep());
			pool.setNagle(poolConf.isNagle());
			pool.setFailover(true);
			pool.setSocketTO(poolConf.getSocketTo());
			pool.setSocketConnectTO(poolConf.getSocketConnTo());
			pool.setHashingAlg(SockIOPool.CONSISTENT_HASH);
			pool.initialize();

			BaseMemcacheProxy mc = new BaseMemcacheProxy(poolConf);

			mcMap.put(poolConf.getPoolName(), mc);
			logger.info("    Finished init pool " + poolConf.getPoolName()
					+ "!");
		}
		logger.info("End to init memcache pools<<----");

	}

	public void close() {
		logger.info("Memcache connections is going to close....");
		for (MemcachePoolConfig poolConf : config.getPoolConfigs()) {
			SockIOPool pool = SockIOPool.getInstance(poolConf.getPoolName());
			if (pool != null) {
				logger.info("    memcache pool: " + poolConf.getPoolName()
						+ " is going to close...");
				pool.shutDown();
				logger.info("    memcache pool: " + poolConf.getPoolName()
						+ " has been closed successfully!");

			}
		}
		logger.info("All memcache connections has been colsed successfully!");
	}

	public void colsePool(String poolName) {
		SockIOPool pool = SockIOPool.getInstance(poolName);
		if (pool != null) {
			logger.info("memcache pool: " + poolName + " is going to close...");
			pool.shutDown();
			logger.info("memcache pool: " + poolName
					+ " has been closed successfully!");
		}
	}

	protected boolean containPool(String poolName) {
		return instance.mcMap.containsKey(poolName);
	}

	protected MemcacheConfig getConfig() {
		return this.config;
	}

	protected BaseMemcacheProxy getBaseCient(String poolName) {
		return mcMap.get(poolName);
	}
}
