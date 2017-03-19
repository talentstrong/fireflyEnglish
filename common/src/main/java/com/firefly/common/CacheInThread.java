package com.firefly.common;

import java.util.HashMap;
import java.util.Map;

/**
 * memcached线程变量
 * 
 * @author jiangchen
 */
public final class CacheInThread {

	/** memcached对象的内存缓存 key => value */
	private static final ThreadLocal<Map<Object, Object>> threadCache = new ThreadLocal<Map<Object, Object>>();

	/** memcached key的版本记录 key => version */
	private static final ThreadLocal<Map<String, Long>> threadVersion = new ThreadLocal<Map<String, Long>>() {

		/**
		 * ThreadLocal变量的初始化方法
		 */
		@Override
		protected Map<String, Long> initialValue() {
			return new HashMap<String, Long>();
		}
	};

	public static Object getResult(Object key) {
		if (threadCache.get() == null) {
			return null;
		}

		return threadCache.get().get(key);
	}

	public static Object setResult(Object key, Object value) {
		Map<Object, Object> cacheMap = threadCache.get();
		if (cacheMap == null) {
			cacheMap = new HashMap<Object, Object>();
			threadCache.set(cacheMap);
		}
		return cacheMap.put(key, value);
	}

	public static void removeResult(Object key) {
		Map<Object, Object> cacheMap = threadCache.get();
		if (cacheMap != null) {
			cacheMap.remove(key);
		}
	}

	/**
	 * 保存key的版本号
	 * 
	 * @param key
	 * @param versionId
	 */
	public static void setVersion(String key, Long versionId) {
		threadVersion.get().put(key, versionId);
	}

	/**
	 * 获取key的版本号
	 * 
	 * @param key
	 * @return 匹配不到则返回null
	 */
	public static Long getVersion(String key) {
		if (threadVersion.get() == null) {
			return null;
		}

		return threadVersion.get().get(key);
	}

	/**
	 * 删除key的版本号纪录
	 * 
	 * @param key
	 */
	public static void removeVersion(String key) {
		if (threadVersion.get() != null) {
			threadVersion.get().remove(key);
		}
	}

	/**
	 * 清理 memcached 线程变量
	 */
	public static void reset() {
		if (threadCache.get() != null) {
			threadCache.get().clear();
		}
		if (threadVersion.get() != null) {
			threadVersion.get().clear();
		}
	}
}
