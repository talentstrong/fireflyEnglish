package com.firefly.cache.memcache.interceptor;

import com.firefly.common.CacheInThread;
import org.apache.log4j.Logger;


/**
 * 这个拦截器用来拦截在同一个线程中，对同一个key的多次memcache读取。 同一个key首次访问memcache的时候,
 * 其值会被缓存在本地线程中，以后的访问都从本地线程读取，更新的时候也会更新本地线程。
 * @author jiangchen
 *
 */
public class ThreadLocalCacheInterceptor extends EmptyInterceptor{
	
	/**
     * 
     */
    private static final long serialVersionUID = -3461745891331756220L;
    
    private static final Logger log = Logger.getLogger(ThreadLocalCacheInterceptor.class);
	
	public ThreadLocalCacheInterceptor() {}
	
	@Override
	public Object handleGet(String poolName,String key) {
		String cacheThreadKey = cacheInRequestKey(poolName,key);
		
		if (CacheInThread.getResult(cacheThreadKey) != null){
			log.debug("hit key=" + key + " from  CacheInRequest with pool " + poolName);
			return CacheInThread.getResult(cacheThreadKey);	
		}
		
		log.debug("miss key=" + key + " from  CacheInRequest with pool " + poolName);
		Object result = this.nextHandler.handleGet(poolName,key);
		if(result != null) {
			CacheInThread.setResult(cacheThreadKey, result);	
		}
		
		
		return result;
	}

	@Override
	public boolean handlePut(String poolName,String key, Object value) {
		boolean result = this.nextHandler.handlePut(poolName,key, value);
		
		if(result && value != null) {
			CacheInThread.setResult(cacheInRequestKey(poolName,key), value);	
		}
		
		return result;
		
	}
	
	@Override
	public boolean handlePut(String poolName,String key, Object value, int expirMins) {		
		boolean result =  getNextHandler().handlePut(poolName,key, value, expirMins);
		
		if(result && value != null) {
			CacheInThread.setResult(cacheInRequestKey(poolName,key), value);	
		}
		
		return result;
		
	}
	
	@Override
	public void handleRemove(String poolName,String key) {
		CacheInThread.removeResult(cacheInRequestKey(poolName,key));
		getNextHandler().handleRemove(poolName,key);
		
	}


	private String cacheInRequestKey(String poolName,String memKey) {
		return poolName + "_" + memKey;
	}
}
