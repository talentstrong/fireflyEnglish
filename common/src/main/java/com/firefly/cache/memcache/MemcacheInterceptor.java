package com.firefly.cache.memcache;

import java.util.Map;

import com.firefly.cache.memcache.interceptor.ThreadLocalCacheInterceptor;

/**
 * <pre>
 * Memcache拦截器，主要用来对memcache的操作进行拦截，做额外的工作。
 * 比如本地缓存拦截器:<code>ThreadLocalCacheInterceptor</code>,用来做memcache的线程级缓存。
 * 该拦截器实现采用责任链模式进行设计，实现参照{@link ThreadLocalCacheInterceptor}}:
 * 
 * spring配置:
 * <bean id="memcacheFactory" class="com.firefly.cache.memcache.MemcacheProxyFactory" factory-method="configure" destroy-method="close">
 *       <constructor-arg value="file:${central.config.path}memcache.xml"/>
 *       <property name="interceptors">
 *            <list>
 *                <value>com.firefly.cache.memcache.interceptor.ThreadLocalCacheInterceptor</value>
 *                <value>com.yihaodian.central.util.MemcacheMonitorIntercetor</value>
 *            </list>
 *        </property>
 *    </bean>
 * </pre>
 * 
 * @author jiangchen
 * @version 1.0 2011-03-17
 * 
 */
public interface MemcacheInterceptor {

	boolean handlePut(String poolName, String key, Object value);

	boolean handlePut(String poolName, String key, Object value, int expirMins);

	Object handleGet(String poolName, String key);

	Map<String, Object> handleGetMulti(String poolName, String[] keys);

	void handleRemove(String poolName, String key);

	boolean handleAdd(String poolName, String key, Object value);

	boolean handleAdd(String poolName, String key, Object value, int expirMins);

	MemcacheInterceptor getNextHandler();

	void setNextHandler(MemcacheInterceptor nextHandler);

    long addOrInrc(String poolName, String key);

    long inrc(String poolName, String key);
}
