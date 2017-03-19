package com.firefly.web.framework;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.google.common.base.Strings;
import org.springframework.util.Assert;

public class PageCookie {
    private Cookie[] cookies;
    private HttpServletResponse response;

    public String cookieDomain;
    public final static String USER_TOKEN_NAME = "ut";
    public final static String BROSWER_TOKEN_NAME = "bt";
    public final static String BROSWER_PLATFORM = "platform";
    public final static int EXPIRY_TWO_YEAR = 60 * 60 * 24 * 365 * 2;
    public final static int EXPIRY_ONE_DAY = 60 * 60 * 24;


    public PageCookie() {
        super();
    }

    public PageCookie(String cookieDomain, HttpServletRequest request, HttpServletResponse response) {
        this.cookieDomain = cookieDomain;
        this.cookies = request.getCookies();
        this.response = response;
    }

    public String getCookie(String name) {
        Assert.isTrue(!Strings.isNullOrEmpty(name));
        String value = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie != null && cookie.getName().equals(name)) {
                    try {
                        value = URLDecoder.decode(cookie.getValue(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
        return value;
    }

    public void setCookie(String name, String value) {
        setCookie(name, value, cookieDomain, -1);
    }

    public void setCookie(String name, String value, String domain) {
        setCookie(name, value, domain, -1);
    }

    public void setCookie(String name, String value, int expiry) {
        setCookie(name, value, cookieDomain, expiry);
    }

    public void setCookie(String name, String value, String domain, int expiry) {
        Cookie cookie = null;
        try {
            cookie = new Cookie(name, URLEncoder.encode(value, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        cookie.setDomain(domain);
        setExpiry(cookie, expiry);
        cookie.setPath("/");
        if (cookie != null)
            response.addCookie(cookie);
    }

    public void removeCookie(String name) {
        removeCookie(name, cookieDomain);
    }

    public void removeCookie(String name, String domain) {
        Cookie cookie = new Cookie(name, null);
        cookie.setDomain(domain);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    private void setExpiry(Cookie cookie, int expiry) {
        if (expiry > 0) {
            cookie.setMaxAge(expiry);
        } else {
            cookie.setMaxAge(-1);
        }
    }

    public String toString() {
        String result = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                result += cookie.getName() + "_" + cookie.getValue();
            }
        }
        return result;
    }
}