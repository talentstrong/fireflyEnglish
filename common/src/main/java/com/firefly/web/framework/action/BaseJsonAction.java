package com.firefly.web.framework.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Strings;
import com.firefly.web.framework.ActionContext;
import com.firefly.web.framework.PageCookie;
import com.firefly.web.framework.PageSession;
import com.firefly.web.framework.inputVo.JsonRequestInputVo;
import com.firefly.web.framework.model.BaseJsonModel;
import com.firefly.web.framework.model.ResultBoolSupport;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.http.converter.json.MappingJacksonValue;

/**
 * 同时支持json和jsonp请求<br>
 * jsonp请求必须将jsonp属性设置为“jsonpCallback”，如下<br>
 * $.ajax({ type: "get",<br>
 * url: "http://www.hrd800.com/a/aaa.html",<br>
 * dataType: "jsonp",<br>
 * jsonp: "jsonpCallback",<br>
 * jsonpCallback:"jsonpCallback",<br>
 * success: function(json){ alert('json:' + json); },<br>
 * error: function(){ alert('fail'); }<br>
 * });
 */
public abstract class BaseJsonAction<T extends ActionContext, V extends JsonRequestInputVo, R extends BaseJsonModel>
        extends AbstractAction<Object, T, V, R> {
    @Override
    protected void afterBiz(HttpServletRequest request, HttpServletResponse response, V inputVo, PageCookie cookie,
            PageSession session, R result) {
        result.setJsonp(isJsonpRequest(inputVo));
    }

    @Override
    protected Object buildOutput(V inputVo, R result) {
        if (result.isJsonp()) {
            MappingJacksonValue val = new MappingJacksonValue(result);
            val.setJsonpFunction(inputVo.getCallbackparam());
            return val;
        } else if (result instanceof ResultBoolSupport) {
            return ((ResultBoolSupport) result).getBoolResult();
        }
        return result;
    }

    @Override
    protected Object buildRedirectToLoginPageOutput(HttpServletRequest request, HttpServletResponse response, V inputVo) {
        String resultStr = "mustLogin";

        if (!isJsonpRequest(inputVo)) {
            return resultStr;
        } else {
            return new JSONPObject(inputVo.getCallbackparam(), resultStr);
        }
    }

    private boolean isJsonpRequest(JsonRequestInputVo inputVo) {
        return !Strings.isNullOrEmpty(inputVo.getCallbackparam());
    }
}
