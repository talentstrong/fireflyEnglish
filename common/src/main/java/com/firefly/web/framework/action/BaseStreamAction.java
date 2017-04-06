package com.firefly.web.framework.action;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.firefly.web.framework.ActionContext;
import com.firefly.web.framework.PageCookie;
import com.firefly.web.framework.PageSession;
import com.firefly.web.framework.inputVo.RequestInputVo;
import com.firefly.web.framework.model.BaseStreamModel;
import org.apache.commons.lang.IllegalClassException;
import org.springframework.web.servlet.ModelAndView;

public abstract class BaseStreamAction<StreamDataType, V extends RequestInputVo, R extends BaseStreamModel<StreamDataType>>
        extends AbstractAction<ModelAndView, ActionContext, V, R> {

    @Override
    protected final void afterBiz(HttpServletRequest request, HttpServletResponse response, V inputVo,
            PageCookie cookie, PageSession session, R result) throws IOException {
        // 设置相应类型,告诉浏览器输出的内容类型
        response.setContentType(result.getContentType());
        // 设置响应头信息
        Map<String, String> headers = result.getHeaders();
        Set<Entry<String, String>> entrySet = headers.entrySet();
        for (Entry<String, String> entry : entrySet) {
            response.setHeader(entry.getKey(), entry.getKey());

        }
        response.setDateHeader("Expire", 0);

        StreamDataType dataStream = result.getDataStream();
        if (dataStream instanceof BufferedImage) {
            ImageIO.write((BufferedImage) dataStream, "JPEG", response.getOutputStream());
        } else if (dataStream instanceof byte[]) {
            response.getOutputStream().write((byte[]) dataStream);
        } else {
            throw new IllegalClassException("cant handle type:" + dataStream.getClass());
        }
    }

    @Override
    protected ModelAndView buildOutput(V inputVo, R result) {
        return null;
    }

    @Override
    protected ActionContext buildActionContext(RequestInputVo inputVo) {
        return null;
    }

    protected final boolean mustLogin() {
        return false;
    }

    @Override
    protected ModelAndView buildRedirectToLoginPageOutput(HttpServletRequest request, HttpServletResponse response,
            RequestInputVo inputVo) {
        return null;
    }
}
