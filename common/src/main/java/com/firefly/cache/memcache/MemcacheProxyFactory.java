package com.firefly.cache.memcache;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.firefly.cache.CacheProxy;
import com.firefly.cache.memcache.exception.MemcacheException;
import com.firefly.cache.memcache.interceptor.EmptyInterceptor;
import com.firefly.cache.memcache.exception.MemcacheInitException;
import com.firefly.cache.memcache.interceptor.CoreInterceptor;
import org.apache.log4j.Logger;

import com.firefly.cache.memcache.impl.InterceptorMemcacheProxy;

/**
 * 該類是memcache的工廠類，主要功能如下:</br>
 * <ul>
 * <li>初始化memcache</li>
 * <li>獲取基于插件扩展的memcache客戶端</li>
 * </ul>
 * 
 * @author jiangchen
 * @version 1.0 2011-03-17
 * 
 */
public class MemcacheProxyFactory {

	protected static final Logger logger = Logger
			.getLogger(MemcacheProxyFactory.class);

	private static MemcacheProxyFactory singletonFactory = null;
	private static final Object initLock = new Object();

	protected Map<String, CacheProxy> proxyPool = new HashMap<String, CacheProxy>();
	protected MemcacheInterceptor headInterceptor;
	protected boolean interceptorInitFlag = false;
	protected String configureFilePath;

	/**
	 * 初始化工厂类
	 * 
	 * @param configurePath
	 *            : 传入的配置文件路径,支持两种类型的文件路径:
	 * 
	 *            <pre>
	 *        1. file:filePath 通过文件路径查找
	 *        2. classpath: path  通过classpath查找
	 *        现在支持xml文件配置
	 * </pre>
	 * 
	 * @return
	 * @throws MemcacheInitException
	 */
	public static MemcacheProxyFactory configure(String configurePath)
			throws MemcacheInitException {
		synchronized (initLock) {
			if (singletonFactory == null) {
				singletonFactory = new MemcacheProxyFactory(configurePath);
			}
		}

		return singletonFactory;
	}

	public synchronized static void destroy() {
		if (singletonFactory != null) {
			singletonFactory.close();
			singletonFactory = null;
		}
	}

	/**
	 * 根据pool名称获取对该pool操作的客户端
	 * 
	 * @param poolName
	 * @return
	 */
	public static CacheProxy getClient(String poolName) {

		return singletonFactory.findClient(poolName);
	}

	MemcacheProxyFactory(String configureFilePath) throws MemcacheInitException {
		init(configureFilePath);
	}

	/**
	 * 根据pool name获取memcache客户端代理
	 * 
	 * @param poolName
	 * @return
	 */
	CacheProxy findClient(String poolName) {
		synchronized (proxyPool) {
			if (proxyPool.containsKey(poolName)) {
				return proxyPool.get(poolName);
			}
			if (!MemcacheAdmin.getInstance().containPool(poolName)) {
				logger.warn("can't find memcache pool:" + poolName
						+ " make sure it is configured in file "
						+ configureFilePath);
				return null;
			}

			CacheProxy newInstance = new InterceptorMemcacheProxy(poolName,
					headInterceptor);

			proxyPool.put(poolName, newInstance);
		}
		return proxyPool.get(poolName);

	}

	/**
	 * 加载配置文件<code>CONFIGURE_FILE_XML</code>,初始化memcache
	 * 
	 * @throws MemcacheInitException
	 */
	synchronized void init(String filePath) throws MemcacheInitException {
		// String memcacheConfPath = System.getProperty("central.config.path")+
		// CONFIGURE_FILE_XML;
		configureFilePath = filePath.trim();
		logger.info("Load memcache configure from path: " + configureFilePath);

		InputStream fin;
		try {
			if (configureFilePath.startsWith("file:")) {
				String[] fileStr = configureFilePath.split("file:");
				fin = new FileInputStream(fileStr[1]);
			} else if (configureFilePath.startsWith("classpath:")) {
				String[] classpathStr = configureFilePath.split("classpath:");
				fin = MemcacheProxyFactory.class.getClassLoader()
						.getResourceAsStream(classpathStr[1]);
			} else {
				fin = MemcacheProxyFactory.class.getClassLoader()
						.getResourceAsStream(configureFilePath);
			}

			MemcacheAdmin.init(fin);

			headInterceptor = new EmptyInterceptor();
			headInterceptor.setNextHandler(new CoreInterceptor());
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			throw new MemcacheInitException(
					"MemcacheProxyFactory#init() error:", e);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new MemcacheInitException(
					"MemcacheProxyFactory#init() error:", e);
		}
	}

	/**
	 * 加载插件, 只能初始化一次。 如果<code>headInterceptor</code>已经初始化，则拒绝再一次初始化
	 * 
	 * @param interceptorClasses
	 */
	public synchronized void setInterceptors(List<String> interceptorClasses) {
		if (interceptorInitFlag) {
			logger.warn("headInterceptor has been aready initialized!");
			return;
		}

		MemcacheInterceptor coreInterceptor = headInterceptor.getNextHandler();
		MemcacheInterceptor nextHandler = headInterceptor;
		if (interceptorClasses != null) {
			for (String interceptorClass : interceptorClasses) {
				MemcacheInterceptor intercetor;
				try {
					intercetor = (MemcacheInterceptor) Class.forName(
							interceptorClass).newInstance();
					nextHandler.setNextHandler(intercetor);
					nextHandler = intercetor;
				} catch (Exception e) {

					throw new MemcacheException(
							"interceptor initialize fail: interceptorClass="
									+ interceptorClass, e);
				}

			}
		}

		nextHandler.setNextHandler(coreInterceptor);
		interceptorInitFlag = true;
	}

	public void close() {
		proxyPool.clear();
		headInterceptor = null;
		interceptorInitFlag = false;
		configureFilePath = null;
		MemcacheAdmin.getInstance().close();
	}

}
