package com.firefly.web.action.user;

import com.firefly.web.framework.model.BaseJsonModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class SessionUserModel extends BaseJsonModel {
    private long id;
    private String nickName;
}
