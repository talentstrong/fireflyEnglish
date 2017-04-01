package com.firefly.web.action.weixin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import com.firefly.dto.CustomerDto;
import com.firefly.web.constant.SessionKey;
import com.firefly.web.framework.ActionContext;
import com.firefly.web.framework.PageCookie;
import com.firefly.web.framework.PageSession;
import com.firefly.web.framework.action.SimpleJsonAction;
import com.firefly.web.framework.inputVo.JsonRequestInputVo;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Log4j
// 生成获取用户accessToken时用到的state
public class WeixinAuthStateAction extends SimpleJsonAction<JsonRequestInputVo, WeixinAuthStateModel> {
    private static final String PAGE_URL_PARTTEN = "/weixin/authState";

    @Override
    protected ActionContext buildActionContext(JsonRequestInputVo inputVo) {
        return null;
    }

    @Override
    protected WeixinAuthStateModel doBiz(JsonRequestInputVo inputVo, PageCookie cookie, PageSession session, CustomerDto customer) throws Exception {
        UUID uuid = UUID.randomUUID();
        session.setAttribute(SessionKey.WEIXIN_AUTH_STATUS, uuid);
        WeixinAuthStateModel result = new WeixinAuthStateModel();
        result.setState(uuid.toString());
        return result;
    }

    @ResponseBody
    @RequestMapping(PAGE_URL_PARTTEN)
    public Object handleRequest(HttpServletRequest request, HttpServletResponse response, JsonRequestInputVo inputVo)
            throws Exception {
        return super.handleRequest(request, response, inputVo);
    }
}
