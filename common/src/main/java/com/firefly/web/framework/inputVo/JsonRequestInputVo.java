package com.firefly.web.framework.inputVo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class JsonRequestInputVo extends RequestInputVo {
    private String callbackparam;
}
