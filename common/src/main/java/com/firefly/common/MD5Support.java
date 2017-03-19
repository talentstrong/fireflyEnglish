package com.firefly.common;

import java.security.MessageDigest;

import org.apache.log4j.Logger;

public class MD5Support {

	private static final Logger LOG = Logger.getLogger(MD5Support.class);

	private static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public final static String MD5(String s) {

		try {
			// 本来我要使用getBytes("UTF-8");但因为我也要在lua中使用md5,而lua中搞定utf8很麻烦，所以
			// 我在此并未使用utf-8,如果所有部署的机器都是同样编码（本系统是gbk）的话，不会出问题，但如果出现不同编码的机器
			// 明显会出问题

			// 同样的问题也出现在base64surpport
			byte[] strTemp = s.getBytes("UTF-8");
			// byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				// 右移四位并截掉高四位
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				// 直接截掉高四位
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			LOG.warn(e.getMessage());
			return null;
		}
	}

	public final static String MD5(String s, String encoding) {

		try {
			// 本来我要使用getBytes("UTF-8");但因为我也要在lua中使用md5,而lua中搞定utf8很麻烦，所以
			// 我在此并未使用utf-8,如果所有部署的机器都是同样编码（本系统是gbk）的话，不会出问题，但如果出现不同编码的机器
			// 明显会出问题

			// 同样的问题也出现在base64surpport
			byte[] strTemp;
			if (encoding != null) {
				strTemp = s.getBytes(encoding);
			} else {
				strTemp = s.getBytes();
			}
			// byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				// 右移四位并截掉高四位
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				// 直接截掉高四位
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			LOG.warn(e.getMessage());
			return null;
		}
	}

}
