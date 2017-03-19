package com.firefly.web.framework.action;

import com.firefly.web.framework.ActionContext;
import com.firefly.web.framework.inputVo.JsonRequestInputVo;
import com.firefly.web.framework.model.BaseJsonModel;

public abstract class SimpleJsonAction<V extends JsonRequestInputVo, R extends BaseJsonModel>
        extends BaseJsonAction<ActionContext, V, R> {

    @Override
    protected ActionContext buildActionContext(V inputVo) {
        return null;
    }

}
