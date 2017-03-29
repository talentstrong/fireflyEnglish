package com.firefly.web.action.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.firefly.dto.CustomerDto;
import com.firefly.web.framework.ActionContext;
import com.firefly.web.framework.PageCookie;
import com.firefly.web.framework.PageSession;
import com.firefly.web.framework.action.BaseJsonAction;
import com.firefly.web.framework.inputVo.JsonRequestInputVo;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Log4j
public class SessionUserAction extends BaseJsonAction<ActionContext, JsonRequestInputVo, SessionUserModel> {
    private static final String PAGE_URL_PARTTEN = "/user/sessionUser";


    @Override
    protected ActionContext buildActionContext(JsonRequestInputVo inputVo) {
        return null;
    }

    @Override
    protected SessionUserModel doBiz(JsonRequestInputVo inputVo, PageCookie cookie, PageSession session, CustomerDto customer) throws Exception {
        SessionUserModel result = new SessionUserModel();
        if (customer != null) {
            result.setId(customer.getId());
            result.setNickName(customer.getNickName());
        } else {
            result.setId(0);
            result.setNickName(null);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(PAGE_URL_PARTTEN)
    public Object handleRequest(HttpServletRequest request, HttpServletResponse response, JsonRequestInputVo inputVo)
            throws Exception {
        return super.handleRequest(request, response, inputVo);
    }
}
