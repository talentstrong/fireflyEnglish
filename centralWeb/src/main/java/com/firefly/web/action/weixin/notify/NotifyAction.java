package com.firefly.web.action.weixin.notify;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import com.alibaba.dubbo.common.json.JSON;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Log4j
public class NotifyAction {

    @Value("${weixin_apiToken}")
    private String apiToken;

    @ResponseBody
    @RequestMapping(value = "/weixin/notifyMe", produces="text/html;charset=UTF-8")
    public String baseVerify(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // 用于验证微信公众平台中,基本配置,服务器配置的设置

        String timeStamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        String signature = request.getParameter("signature");


        String jsonParams = JSON.json(request.getParameterMap());
        System.out.println(jsonParams);

        if (signature.toUpperCase().equals(sha1Sign(apiToken, timeStamp, nonce))) {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-type", "text");
            return echostr;
        } else {
            return "";
        }
    }

    private static String sha1Sign(String token, String timestamp, String nonce) {
        //将传入参数变成一个String数组然后进行字典排序
        String[] arr = new String[]{token, timestamp, nonce};
        Arrays.sort(arr);
        //创建一个对象储存排序后三个String的结合体
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }


        //启动sha1加密法的工具
        MessageDigest md = null;
        String tmpStr = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            //md.digest()方法必须作用于字节数组
            byte[] digest = md.digest(content.toString().getBytes());
            //将字节数组弄成字符串
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            log.error("加密时出错:" + token + "_" + timestamp + "_" + nonce, e);
        }
        return tmpStr;
    }

    /**
     * 将字节加工然后转化成字符串
     *
     * @param digest
     * @return
     */
    private static String byteToStr(byte[] digest) {
        String strDigest = "";
        for (int i = 0; i < digest.length; i++) {
            //将取得字符的二进制码转化为16进制码的的码数字符串
            strDigest += byteToHexStr(digest[i]);
        }
        return strDigest;
    }

    /**
     * 把每个字节加工成一个16位的字符串
     *
     * @param b
     * @return
     */
    public static String byteToHexStr(byte b) {
        //转位数参照表
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};


        //位操作把2进制转化为16进制
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(b >>> 4) & 0X0F];//XXXX&1111那么得到的还是XXXX
        tempArr[1] = Digit[b & 0X0F];//XXXX&1111那么得到的还是XXXX

        //得到进制码的字符串
        String s = new String(tempArr);
        return s;
    }
}
