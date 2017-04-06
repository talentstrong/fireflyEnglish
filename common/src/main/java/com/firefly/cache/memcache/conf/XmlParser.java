package com.firefly.cache.memcache.conf;

import com.firefly.cache.memcache.exception.MemcacheInitException;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;

public class XmlParser {

	public static Integer parseIntAttr(Element ele, String attrName)
			throws MemcacheInitException {
		String value = ele.attributeValue(attrName);
		checkRequiedAttr(attrName, value);

		return Integer.valueOf(trimToNull(value));
	}

	public static Long parseLongAttr(Element ele, String attrName)
			throws MemcacheInitException {
		String value = ele.attributeValue(attrName);
		checkRequiedAttr(attrName, value);

		return Long.valueOf(trimToNull(value));
	}

	public static boolean parseBooleanAttr(Element ele, String attrName)
			throws MemcacheInitException {
		String value = ele.attributeValue(attrName);
		checkRequiedAttr(attrName, value);

		return Boolean.valueOf(trimToNull(value));
	}

	public static String parseAttr(Element ele, String attrName)
			throws MemcacheInitException {
		String value = ele.attributeValue(attrName);
		checkRequiedAttr(attrName, value);

		return trimToNull(value);
	}

	private static void checkRequiedAttr(String attrName, String value)
			throws MemcacheInitException {
		if (StringUtils.isEmpty(value)) {
			throw new MemcacheInitException(
					"memcache configure file error: attribute " + attrName
							+ " is required!");
		}
	}

	private static String trimToNull(String str) {

		return StringUtils.trimToNull(str);
	}

}
