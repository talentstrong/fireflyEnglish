package com.firefly.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j;

import com.google.common.collect.Lists;

@Log4j
public class JsCssServlet extends HttpServlet {
    private static final long serialVersionUID = -6367141190306872077L;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = req.getRequestURI();
        if (uri.contains(".css")) {
            resp.setHeader("Content-Type", "text/css");
        } else {
            resp.setHeader("Content-Type", "application/javascript");
        }
        String[] pathParts = uri.split("/");
        String baseFolder = "";
        String jsCssFile = "";
        for (int i = 0; i < pathParts.length; i++) {
            if (i != pathParts.length - 1) {
                baseFolder += pathParts[i] + "/";
            } else {
                jsCssFile = pathParts[i];
            }
        }
        log.info(baseFolder + ":" + jsCssFile);

        String mergePropertiesPath = baseFolder + "merge.properties";
        InputStream is;
        try {
            is = getServletContext().getResourceAsStream(mergePropertiesPath);
        } catch (Exception e) {
            is = null;
        }
        // 存在merge.properties，根据配置拼接js，css
        boolean needMergeFile = true;
        List<String> fileNames = null;
        if (is != null) {
            Properties prop = new Properties();
            prop.load(is);
            String mergeConfig = (String) prop.get(jsCssFile);
            if (mergeConfig == null) {
                needMergeFile = false;
            } else {
                fileNames = Lists.newArrayList(mergeConfig.split(","));
            }
        } else {
            needMergeFile = false;
        }

        // 直接读取js，css文件
        if (!needMergeFile) {
            InputStream cssJsFileIs = getServletContext().getResourceAsStream(uri);
            BufferedReader br = new BufferedReader(new InputStreamReader(cssJsFileIs, "utf-8"));
            String s;
            while ((s = br.readLine()) != null) {
                s += "\r";
                resp.getOutputStream().write(s.getBytes("utf-8"));
            }
            br.close();
            cssJsFileIs.close();
        } else {
            // 何必文件并输出
            for (String fileName : fileNames) {
                InputStream cssJsFileIs = getServletContext().getResourceAsStream(baseFolder + fileName);
                BufferedReader br = new BufferedReader(new InputStreamReader(cssJsFileIs, "utf-8"));
                String s;
                while ((s = br.readLine()) != null) {
                    s += "\r";
                    resp.getOutputStream().write(s.getBytes("utf-8"));
                }
                br.close();
                cssJsFileIs.close();
            }
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}
