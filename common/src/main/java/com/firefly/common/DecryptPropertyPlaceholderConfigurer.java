package com.firefly.common;

import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class DecryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    protected String resolvePlaceholder(String placeholder, Properties props) {

        PropertyPlaceholderUtil.setProps(props);
        String s = props.getProperty(placeholder);

        if (placeholder != null && s != null) {
//            if (placeholder.startsWith("jdbc")) {
//                if(placeholder.endsWith(".username")){
//                    s = Encrypter.decrypt(s);
//                }
//
//                if(placeholder.endsWith(".password")){
//                    s = Encrypter.decrypt(s);
//                }
//            }
        }
        return s;
    }
}
