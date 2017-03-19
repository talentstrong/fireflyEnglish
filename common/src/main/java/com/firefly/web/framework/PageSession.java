package com.firefly.web.framework;

import java.io.Serializable;

import com.google.common.base.Joiner;
import com.firefly.cache.CacheProxy;

public class PageSession implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6950012966562802601L;

    private String sessionToken;

    private CacheProxy proxy;

    private final static String SESSION_PREFIX = "SESSION";

    public PageSession() {
        super();
    }

    public PageSession(CacheProxy proxy, String sessionToken) {
        this.proxy = proxy;
        this.sessionToken = sessionToken;
    }

    public Object getAttribute(String key) {
        return proxy.get(getSessionKey(key));
    }

    public void setAttribute(String key, Object obj) {
        proxy.put(getSessionKey(key), obj, 120);
    }

    public void removeAttribute(String key) {
        proxy.remove(getSessionKey(key));
    }

    public String getSessionKey(String key) {
        return Joiner.on("_").join(SESSION_PREFIX, key, sessionToken);
    }

    public Object getAttributeByRawKey(String key) {
        return proxy.get(key);
    }
}
