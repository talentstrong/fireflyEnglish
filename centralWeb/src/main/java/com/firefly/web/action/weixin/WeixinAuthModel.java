package com.firefly.web.action.weixin;

import com.firefly.web.framework.model.BaseJsonModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class WeixinAuthModel extends BaseJsonModel {
    // 认证结果:0失败,1成功
    private int authState;

    private String country;

    private String province;

    private String city;

    private String nickName;

    private String sex;

    private String headimgUrl;
}
