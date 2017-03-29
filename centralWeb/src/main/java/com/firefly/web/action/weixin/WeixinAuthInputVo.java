package com.firefly.web.action.weixin;

import com.firefly.web.framework.inputVo.JsonRequestInputVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class WeixinAuthInputVo extends JsonRequestInputVo {
    private String state;
    private String code;
}
