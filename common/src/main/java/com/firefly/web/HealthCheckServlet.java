package com.firefly.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import com.firefly.common.PropertyConfigurer;
import com.firefly.version.CurrentVersionUtil;

@WebServlet(urlPatterns = "/healthCheck", asyncSupported = false)
public class HealthCheckServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("{\"success\":\"ok\",\"env\":\"" + PropertyConfigurer.getEnv() + "\",\"version\":\""
                + CurrentVersionUtil.getCurrentVersion() + "\"}");
        out.flush();
    }
}
