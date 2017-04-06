package com.firefly.web.action.campApply.createApply;

import com.firefly.web.framework.inputVo.JsonRequestInputVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class CreateCampApplyInputVo extends JsonRequestInputVo {
    private String name;
    private String mobile;
    private String weixinAccount;
    private String childAge;
    private String canSpeakEnglish;
}
