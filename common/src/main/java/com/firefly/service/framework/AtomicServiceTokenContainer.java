package com.firefly.service.framework;

public class AtomicServiceTokenContainer {
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<String>();

    public static void setToken(String token) {
        threadLocal.set(token);
    }

    public static String getToken() {
        return threadLocal.get();
    }

    public static void clear() {
        threadLocal.remove();
    }
}
