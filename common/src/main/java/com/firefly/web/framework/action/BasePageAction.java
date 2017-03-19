package com.firefly.web.framework.action;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.google.common.base.Strings;
import com.firefly.common.PropertyConfigurer;
import com.firefly.utils.LogUtil;
import com.firefly.web.framework.ActionContext;
import com.firefly.web.framework.PageCookie;
import com.firefly.web.framework.PageSession;
import com.firefly.web.framework.inputVo.RequestInputVo;
import com.firefly.web.framework.model.BasePageModel;
import com.firefly.web.framework.model.RedirectSupport;
import com.firefly.web.framework.model.ResetViewNameSupport;

import lombok.extern.log4j.Log4j;

@Log4j
public abstract class BasePageAction<T extends ActionContext, V extends RequestInputVo, R extends BasePageModel>
        extends AbstractAction<ModelAndView, T, V, R> {

    public String getPageTitle() {
        return "华融道理财平台";
    }

    public String getPageKeywords() {
        return "综合理财平台,互联网金融,网络投融资平台,网络理财,互联网理财,华融道,华融道理财，投资理财";
    }

    public String getPageDesc() {
        return "华融道，稳健型理财项目撮合平台。为投资者提供稳健型理财项目及理财顾问服务,为国内实体产业提供金融服务。上海互联网金融协会会员单位，理财产品包括活期理财、多期限定期投资产品。";
    }

    public String getSearchKeyword() {
        return "";
    }

    public String getMobileAgent() {
        return "";
    }

    public String getCanonical() {
        return "";
    }

    @Override
    protected final void afterBiz(HttpServletRequest request, HttpServletResponse response, V inputVo,
            PageCookie cookie, PageSession session, R result) {
        // 设置页面通用属性
        result.setPageTitle(getPageTitle());
        result.setPageKeywords(getPageKeywords());
        result.setPageDesc(getPageDesc());
        result.setMobileAgent(getMobileAgent());
        result.setCanonical(getCanonical());
        result.setSearchKeyword(getSearchKeyword());
        result.setPageCode(getPageCode());

        // 获取来访者地址
        String referer = request.getHeader("Referer");
        if (!Strings.isNullOrEmpty(referer)) {
            result.setReferer(referer);
        }

        // 客户端浏览器唯一标识
        // String browserToken =
        // cookie.getCookie(PageCookie.BROSWER_TOKEN_NAME);
        // if (!Strings.isNullOrEmpty(browserToken)) {
        // result.setBrowserToken(browserToken);
        // }

        // 新老访客判断
        String newUserFlag = (String) session.getAttribute(PageCookie.BROSWER_TOKEN_NAME);
        result.setNewUserFlag(Strings.isNullOrEmpty(newUserFlag) ? "true" : "false");

        // 来源平台
        String platform = cookie.getCookie(PageCookie.BROSWER_PLATFORM);
        result.setPlatform(platform);
    }

    @Override
    protected ModelAndView buildOutput(V inputVo, R result) {
        if (result instanceof RedirectSupport) {
            String backUrl = ((RedirectSupport) result).getReturnUrl();
            if (StringUtils.hasText(backUrl)) {
                return new ModelAndView(new RedirectView(backUrl));
            }
        } else if (result instanceof ResetViewNameSupport) {
            String viewName = ((ResetViewNameSupport) result).getViewName();
            if (!Strings.isNullOrEmpty(viewName)) {
                return new ModelAndView(viewName, "model", result);
            }
        }

        return new ModelAndView(getViewName(), "model", result);
    }

    @Override
    protected ModelAndView buildRedirectToLoginPageOutput(HttpServletRequest request, HttpServletResponse response,
            V inputVo) {
        System.out.println(request.getRequestURL());
        String currentUrl = request.getRequestURL().toString();
        String encodedCurrentUrl;
        try {
            currentUrl += this.resolveUrlParams(request.getParameterMap());
            encodedCurrentUrl = URLEncoder.encode(currentUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            encodedCurrentUrl = null;
            LogUtil.error(log, "UnsupportedEncodingException", e);
        }
        StringBuffer sb = new StringBuffer();
        sb.append(PropertyConfigurer.getContextProperty("SITE_PROTOCOL_PREFIX"));
        sb.append(getPlatformUrl());
        sb.append("/user/loginPage.html");
        if (!Strings.isNullOrEmpty(encodedCurrentUrl)) {
            sb.append("?returnUrl=");
            sb.append(encodedCurrentUrl);
        }
        System.out.println(sb.toString());
        return new ModelAndView(new RedirectView(sb.toString()));
    }

    private String resolveUrlParams(Map<String, String[]> params) {
        String paramString = "";
        if (params.isEmpty()) {
            return paramString;
        }

        for (String key : params.keySet()) {
            String[] values = params.get(key);
            for (int i = 0; i < values.length; i++) {
                if (!key.equals("returnUrl")) {
                    String value = values[i];
                    paramString += key + "=" + value + "&";
                }
            }
        }

        if (!Strings.isNullOrEmpty(paramString)) {
            paramString = "?" + paramString;
            if (paramString.lastIndexOf("&") == (paramString.length() - 1)) {
                paramString = paramString.substring(0, paramString.length() - 1);
            }
        }
        return paramString;
    }

    protected abstract String getPlatformUrl();

    protected abstract String getViewName();

    protected abstract String getPageCode();

}