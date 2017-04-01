package com.firefly.web.action.weixin;

import java.net.URLEncoder;

import com.firefly.web.framework.model.BaseJsonModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class WeixinAccessTokenModel extends BaseJsonModel {
    private String accessToken;
}
