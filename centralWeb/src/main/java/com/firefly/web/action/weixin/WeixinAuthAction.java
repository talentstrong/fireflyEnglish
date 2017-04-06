package com.firefly.web.action.weixin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.Map;

import com.firefly.dto.CustomerDto;
import com.firefly.web.framework.PageCookie;
import com.firefly.web.framework.PageSession;
import com.firefly.web.framework.action.SimpleJsonAction;
import com.firefly.utils.HttpUtil;
import com.firefly.web.constant.SessionKey;
import com.firefly.web.constant.WeixinApiUrl;
import com.google.common.collect.Maps;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Log4j
// 获取用户的auth信息,并完成用户创建
public class WeixinAuthAction extends SimpleJsonAction<WeixinAuthInputVo, WeixinAuthModel> {
    private static final String PAGE_URL_PARTTEN = "/weixin/auth";

    @Value("${weixin_appId}")
    private String appId;

    @Value("${weixin_appSecret}")
    private String appSecret;

    @Override
    protected WeixinAuthModel doBiz(WeixinAuthInputVo inputVo, PageCookie cookie, PageSession session, CustomerDto customer) throws Exception {
        WeixinAuthModel result = new WeixinAuthModel();

        Object sessionDataObj = session.getAttribute(SessionKey.WEIXIN_AUTH_STATUS);
        if (sessionDataObj == null) {
            result.setAuthState(0);
        }
        if (!String.valueOf(sessionDataObj).equals(inputVo.getState())) {
            result.setAuthState(0);
        }

        String userAccessTokenApi = MessageFormat.format(WeixinApiUrl.USER_ACCESS_TOKEN.getUrlTemplate(),
                appId, appSecret, inputVo.getCode());
        System.out.println(userAccessTokenApi);
        Map<String, String> userAuth = HttpUtil.post(userAccessTokenApi, Maps.newHashMap());
        String accessToken = userAuth.get("access_token");
        String refreshToken = userAuth.get("refresh_token");
        String openid = userAuth.get("openid");

        String userInfoApi = MessageFormat.format(WeixinApiUrl.USER_INFO.getUrlTemplate(), accessToken, openid);
        Map<String, String> userInfo = HttpUtil.post(userInfoApi, Maps.newHashMap());

        result.setNickName(userInfo.get("nickname"));
        result.setCity(userInfo.get("city"));
        result.setCountry(userInfo.get("country"));
        result.setProvince(userInfo.get("province"));
        result.setHeadimgUrl(userInfo.get("headimgurl"));
        if ("1".equals(userInfo.get("sex"))) {
            result.setSex("男");
        } else if ("2".equals(userInfo.get("sex"))) {
            result.setSex("女");
        } else {
            result.setSex("");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(PAGE_URL_PARTTEN)
    public Object handleRequest(HttpServletRequest request, HttpServletResponse response, WeixinAuthInputVo inputVo)
            throws Exception {
        return super.handleRequest(request, response, inputVo);
    }
}
