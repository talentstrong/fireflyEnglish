package com.firefly.common;

import java.util.Properties;

public class PropertyPlaceholderUtil {
    private static Properties allprops = new Properties();

    public static String getProps(String key) {
        return allprops.getProperty(key);
    }

    public static Properties getProps() {
        return allprops;
    }

    public static void setProps(Properties props) {
        for(Object key : props.keySet()){
            allprops.put(key, props.getProperty(key.toString()));
        }

    }
}
