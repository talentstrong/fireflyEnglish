package com.firefly.web.framework.exceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import com.firefly.dto.CustomerDto;
import com.firefly.web.framework.PageSession;
import com.firefly.web.framework.model.BaseSessionKeys;
import lombok.extern.log4j.Log4j;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

@Log4j
public class CustomSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        if (!(ex instanceof BindException)) {
            ex.printStackTrace();
        }
        // Expose ModelAndView for chosen error view.
        String viewName = determineViewName(ex, request);
        if (null == viewName) {
            return null;
        }
        if (!isRequestAcceptJsonOrXml(request)) {
            // Apply HTTP status code for error views, if specified.
            // Only apply it if we're processing a top-level request.
            Integer statusCode = determineStatusCode(request, viewName);
            if (statusCode != null) {
                applyStatusCodeIfPossible(request, response, statusCode);
            }
            return getModelAndView(viewName, ex, request);
        } else {
            // JSON/xml格式返回
            try {
                PrintWriter writer = response.getWriter();
                writer.write(ex.getMessage());
                writer.flush();
            } catch (IOException e) {
                log.error("unknown ioException", e);
            }
            return null;
        }
    }

    private boolean isRequestAcceptJsonOrXml(HttpServletRequest request) {
        if (request.getHeader("accept").indexOf("application/json") > -1) {
            return true;
        } else if (request.getHeader("X-Requested-With") != null
                && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1) {
            return true;
        } else {
            return false;
        }
    }

    private static String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            t.printStackTrace(pw);
            return sw.toString();
        } finally {
            pw.close();
        }
    }

    protected CustomerDto getCustomer(PageSession session) {
        Object user = session.getAttribute(BaseSessionKeys.CUSTOMER_INFO_SESSION_KEY);
        if (null != user) {
            return (CustomerDto) user;
        }
        return null;
    }
}
