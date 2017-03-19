package com.firefly.web.framework.action;

import com.firefly.web.framework.ActionContext;
import com.firefly.web.framework.inputVo.RequestInputVo;
import com.firefly.web.framework.model.BasePageModel;

public abstract class SimplePageAction<V extends RequestInputVo, R extends BasePageModel> extends
        BasePageAction<ActionContext, V, R> {

    @Override
    protected ActionContext buildActionContext(V inputVo) {
        return null;
    }
}
