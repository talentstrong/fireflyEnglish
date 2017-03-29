package com.firefly.utils;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import lombok.extern.log4j.Log4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.util.StringUtils;

@Log4j
public class HttpUtil {

//    public static Map<String, String> doRestPost(String uri, Map<String, String> reqMap) throws Exception {
//        RestTemplate restTemplate = new RestTemplate(new ArrayList<HttpMessageConverter<?>>() {{
//            add(new FastJsonHttpMessageConverter());
//            add(new StringHttpMessageConverter());
//        }});
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Accept-Charset", "UTF-8");
//        headers.set("contentType", "application/json");
//        trustAllHttpsCertificates();
//        HttpsURLConnection.setDefaultHostnameVerifier(hv);
//
//        HttpEntity entity = new HttpEntity(reqMap, headers);
//
//        log.info("请求信息：\r\n" + JSON.toJSON(reqMap).toString().replace(",", ",\r\n"));
//        log.info("\r\n发送请求至：" + uri + "\r\n");
//
//        //请求到即信端
//        ResponseEntity response = restTemplate.exchange(uri, HttpMethod.GET, entity, Map.class);
//
//        //响应报文
//        log.info("响应报文：\r\n" + response.getBody().toString().replace(",", ",\r\n"));
//        Map responseMap = (Map) response.getBody();
//        return responseMap;
//    }
//
//    static HostnameVerifier hv = new HostnameVerifier() {
//        public boolean verify(String urlHostName, SSLSession session) {
//            System.out.println("Warning: URL Host: " + urlHostName + " vs. "
//                    + session.getPeerHost());
//            return true;
//        }
//    };
//
//    private static void trustAllHttpsCertificates() throws Exception {
//        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
//        javax.net.ssl.TrustManager tm = new miTM();
//        trustAllCerts[0] = tm;
//        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext
//                .getInstance("SSL");
//        sc.init(null, trustAllCerts, null);
//        HttpsURLConnection.setDefaultSSLSocketFactory(sc
//                .getSocketFactory());
//    }
//
//    static class miTM implements javax.net.ssl.TrustManager,
//            javax.net.ssl.X509TrustManager {
//        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//            return null;
//        }
//
//        public boolean isServerTrusted(
//                java.security.cert.X509Certificate[] certs) {
//            return true;
//        }
//
//        public boolean isClientTrusted(
//                java.security.cert.X509Certificate[] certs) {
//            return true;
//        }
//
//        public void checkServerTrusted(
//                java.security.cert.X509Certificate[] certs, String authType)
//                throws java.security.cert.CertificateException {
//            return;
//        }
//
//        public void checkClientTrusted(
//                java.security.cert.X509Certificate[] certs, String authType)
//                throws java.security.cert.CertificateException {
//            return;
//        }
//    }


    public static Map<String, String> post(String url, Map<String, String> parameters) {
        log.debug("post Map, url:" + url);

        PostMethod postMethod = null;
        String responseString = "";
        try {
            int responseStatCode = 0;
            HttpClient httpclient = new HttpClient();
            // 设置 Http 连接超时为5秒
            httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(20 * 1000);
            httpclient.getHttpConnectionManager().getParams().setSoTimeout(10 * 1000);

            postMethod = new PostMethod(url);
            httpclient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");

            NameValuePair[] nameValuePairs = new NameValuePair[parameters.size()];
            Set<Map.Entry<String, String>> entrys = parameters.entrySet();
            int i = 0;
            for (Map.Entry<String, String> entry : entrys) {
                NameValuePair name = new NameValuePair((String) entry.getKey(), entry.getValue());
                nameValuePairs[i] = name;
                i++;
            }

            postMethod.setRequestBody(nameValuePairs);
            responseStatCode = httpclient.executeMethod(postMethod);
            log.info("statcode:" + responseStatCode);
            responseString = postMethod.getResponseBodyAsString();

            log.info("response:" + responseString);
        } catch (IOException  e) {
            log.error(e);
        }finally {
            postMethod.releaseConnection();
        }

        // 判断是否BOM头
        if (!StringUtils.isEmpty(responseString) && responseString.length() > 0) {
            byte[] head = responseString.getBytes();
            if (head[0] == -17 && head[1] == -69 && head[2] == -65) {
                responseString = responseString.substring(1);
            }
        }

        JSONObject jsonObject = (JSONObject) JSON.parse(responseString);
        Iterator<Map.Entry<String, Object>> it = jsonObject.entrySet().iterator();
        Map<String, String> result = Maps.newHashMap();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            if (entry.getValue() != null) {
                result.put(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        return result;
    }
}
