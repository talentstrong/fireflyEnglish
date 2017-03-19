package com.firefly.service.framework;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

public class AtomicServiceLogUtil {
    private final static String INTERNAL_SERVICE_DONE = "%s:internal:%s";

    public static void info(String msg) {
        String token = AtomicServiceTokenContainer.getToken();
        Assert.isTrue(!StringUtils.isEmpty(token), "token is not exist");
        SoaServiceAspect.getLogger().info(String.format(INTERNAL_SERVICE_DONE, token, msg));
    }

    public static void info(String msg, Object data) {
        String token = AtomicServiceTokenContainer.getToken();
        Assert.isTrue(!StringUtils.isEmpty(token), "token is not exist");
        SoaServiceAspect.getLogger().info(String.format(INTERNAL_SERVICE_DONE, token, msg + ":" + data.toString()));
    }
}
