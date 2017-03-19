package com.firefly.web.action.weixin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.Map;

import com.firefly.dto.CustomerDto;
import com.firefly.utils.HttpClient;
import com.firefly.web.constant.WeixinApiUrl;
import com.firefly.web.framework.ActionContext;
import com.firefly.web.framework.PageCookie;
import com.firefly.web.framework.PageSession;
import com.firefly.web.framework.action.BaseJsonAction;
import com.firefly.web.framework.inputVo.JsonRequestInputVo;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WeixinAccessTokenAction extends BaseJsonAction<ActionContext, JsonRequestInputVo, WeixinAccessTokenModel> implements InitializingBean {
    private static final String PAGE_URL_PARTTEN = "/weixin/accessToken.htm";

    private String accessTokenApi;

    @Value("${weixin_appId}")
    private String appId;

    @Value("${weixin_appSecret}")
    private String appSecret;

    @Override
    protected ActionContext buildActionContext(JsonRequestInputVo inputVo) {
        return null;
    }

    @Override
    protected WeixinAccessTokenModel doBiz(JsonRequestInputVo inputVo, PageCookie cookie, PageSession session, CustomerDto customer) throws Exception {
        Map<String, String> param = Maps.newHashMap();
        param.put("appid", appId);
        param.put("secret", appSecret);
        Map<String, String> data = HttpClient.doRestPost(accessTokenApi, Maps.newHashMap());

        System.out.println(data);
        WeixinAccessTokenModel result = new WeixinAccessTokenModel();
        result.setAccessToken(data.get("access_token"));
        return result;
    }

    @ResponseBody
    @RequestMapping(PAGE_URL_PARTTEN)
    public Object handleRequest(HttpServletRequest request, HttpServletResponse response, JsonRequestInputVo inputVo)
            throws Exception {
        return super.handleRequest(request, response, inputVo);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        accessTokenApi = MessageFormat.format(WeixinApiUrl.ACCESS_TOKEN.getUrlTemplate(), appId, appSecret);
    }

    public static void main(String[] args) {
        System.out.println(MessageFormat.format(WeixinApiUrl.ACCESS_TOKEN.getUrlTemplate(), "appId", "appSecret"));
    }
}
