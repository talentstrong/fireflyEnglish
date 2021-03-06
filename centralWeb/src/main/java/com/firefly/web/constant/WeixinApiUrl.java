package com.firefly.web.constant;

public enum WeixinApiUrl {
    ACCESS_TOKEN("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}"),
    USER_ACCESS_TOKEN("https://api.weixin.qq.com/sns/oauth2/access_token?appid={0}&secret={1}&code={2}&grant_type=authorization_code"),
    USER_INFO("https://api.weixin.qq.com/sns/userinfo?access_token={0}&openid={1}&lang=zh_CN"),;
    private String urlTemplate;

    WeixinApiUrl(String urlTemplate) {
        this.urlTemplate = urlTemplate;
    }

    public String getUrlTemplate() {
        return urlTemplate;
    }
}
