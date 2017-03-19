package com.firefly.web.framework.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BasePageModel extends ResponseModel {
    private String pageKeywords;
    private String pageDesc;
    private String pageTitle;
    private String searchKeyword;
    private String mobileAgent;
    private String canonical;
    private String pageCode;

    private long level1MenuId;

    private long level2MenuId;

    private long level3MenuId;

    private String tokenId;

    // 获取来访者地址
    private String referer;

    // 客户端浏览器唯一标识
    private String browserToken;

    // 新访客标识 "true":新访客 "false":老访客
    private String newUserFlag;

    private String platform;
}
