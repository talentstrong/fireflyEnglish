package com.firefly.web.framework.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

import com.firefly.cache.CacheProxy;
import com.firefly.dto.CustomerDto;
import com.firefly.utils.GuidUtil;
import com.firefly.utils.IpUtil;
import com.firefly.web.framework.ActionContext;
import com.firefly.web.framework.ActionContextContainer;
import com.firefly.web.framework.PageCookie;
import com.firefly.web.framework.PageSession;
import com.firefly.web.framework.inputVo.RequestInputVo;
import com.firefly.web.framework.model.BaseSessionKeys;
import com.firefly.web.framework.model.ResponseModel;
import com.google.common.base.Strings;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Log4j
public abstract class AbstractAction<O extends Object, T extends ActionContext, V extends RequestInputVo, R extends ResponseModel> {
    @Autowired
    @Qualifier(value = "sessionCache")
    private CacheProxy sessionCache;
    @Autowired
    @Qualifier(value = "dataCache")
    protected CacheProxy dataCache;

    @Value("${cookieDomain}")
    protected String cookieDomain;

    @Autowired(required = false)

    public O handleRequest(HttpServletRequest request, HttpServletResponse response, V inputVo) throws Exception {
        Date startTime = new Date();
        long startMillsecond = startTime.getTime();

        // 清空上下文
        ActionContextContainer.destory();
        // 必须要有inputVo和model
        Assert.isTrue(inputVo != null);
        // 构建上下文
        ActionContextContainer.setContext(buildActionContext(inputVo));
        // 构建cookie
        PageCookie pageCookie = new PageCookie(cookieDomain, request, response);

        // 读取userToken(session标识)
        String ut = pageCookie.getCookie(PageCookie.USER_TOKEN_NAME);
        String ip = IpUtil.getIpAddress(request);
        if (Strings.isNullOrEmpty(ut)) {
            ut = GuidUtil.getGuid(ip);
            pageCookie.setCookie(PageCookie.USER_TOKEN_NAME, ut);
        }

        // 构建session
        PageSession session = new PageSession(sessionCache, ut);

        // 读取broswerToken(浏览器标识，也可以作为设备标识)，保存2年
        String bt = pageCookie.getCookie(PageCookie.BROSWER_TOKEN_NAME);
        
        if (Strings.isNullOrEmpty(bt)) {
            bt = UUID.randomUUID().toString();
            pageCookie.setCookie(PageCookie.BROSWER_TOKEN_NAME, bt, PageCookie.EXPIRY_TWO_YEAR);
        } else {
            // 2015.11.06 zhaowenhao
            session.setAttribute(PageCookie.BROSWER_TOKEN_NAME, bt);
        }

        String route = request.getParameter("route");
        if (!StringUtils.isEmpty(route)) {
            // 如果route来源信息存在，在cookie中保存一天
            pageCookie.setCookie("route", route, PageCookie.EXPIRY_ONE_DAY);
        }

        // 设置请求属性
        inputVo.setRequestip(ip);
        inputVo.setUseragent(request.getHeader("user-agent"));

        boolean needRedirectToLoginPage = false;
        CustomerDto customer = getCustomer(session);
        if (mustLogin()) {
            if (customer == null) {
                needRedirectToLoginPage = true;
            }
        }
        O o;
        if (!needRedirectToLoginPage) {
            R result = doBiz(inputVo, pageCookie, session, customer);

            afterBiz(request, response, inputVo, pageCookie, session, result);

            o = buildOutput(inputVo, result);
        } else {
            o = buildRedirectToLoginPageOutput(request, response, inputVo);
        }
        Date endTime = new Date();
        return o;
    }

    protected abstract O buildRedirectToLoginPageOutput(HttpServletRequest request, HttpServletResponse response,
            V inputVo);

    protected abstract void afterBiz(HttpServletRequest request, HttpServletResponse response, V inputVo,
            PageCookie cookie, PageSession session, R result) throws Exception;

    protected abstract T buildActionContext(V inputVo);

    protected abstract R doBiz(V inputVo, PageCookie cookie, PageSession session, CustomerDto customer) throws Exception;

    protected abstract O buildOutput(V inputVo, R result);

    protected boolean mustLogin() {
        return false;
    }

    protected CustomerDto getCustomer(PageSession session) {

        Object user = session.getAttribute(BaseSessionKeys.CUSTOMER_INFO_SESSION_KEY);
        if (null != user) {
            session.setAttribute(BaseSessionKeys.CUSTOMER_INFO_SESSION_KEY, user);
            return (CustomerDto) user;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public T getActionContext() {
        return (T) ActionContextContainer.getActionContext();
    }
}
