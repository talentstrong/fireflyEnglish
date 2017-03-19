package com.firefly.web.constant;

public enum WeixinApiUrl {
    ACCESS_TOKEN("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}"),;
    private String urlTemplate;

    WeixinApiUrl(String urlTemplate) {
        this.urlTemplate = urlTemplate;
    }

    public String getUrlTemplate() {
        return urlTemplate;
    }
}
