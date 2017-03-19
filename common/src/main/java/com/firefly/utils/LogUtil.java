package com.firefly.utils;

import org.apache.log4j.Logger;

public class LogUtil {

    public static void info(Logger log, String msg) {
        if (log.isInfoEnabled()) {
            log.info(msg);
        }
    }

    public static void info(Logger log, String msg, Object... data) {
        if (log.isInfoEnabled()) {
            StringBuffer sb = new StringBuffer(msg);
            sb.append(":");
            appendData(sb, data);
            log.info(sb.toString());
        }
    }

    public static void warn(Logger log, String msg, Object... data) {
        StringBuffer sb = new StringBuffer(msg);
        sb.append(":");
        appendData(sb, data);
        log.warn(sb.toString());
    }

    public static void debug(Logger log, String msg) {
        if (log.isDebugEnabled()) {
            log.debug(msg);
        }
    }

    public static void debug(Logger log, String msg, Object... data) {
        if (log.isDebugEnabled()) {
            StringBuffer sb = new StringBuffer(msg);
            sb.append(":");
            appendData(sb, data);
            log.debug(sb.toString());
        }
    }

    public static void error(Logger log, String msg, Throwable t, Object... data) {

        StringBuffer sb = new StringBuffer(msg);
        sb.append(":");
        appendData(sb, data);
        log.error(sb.toString(), t);
    }

    public static void error(Logger log, String msg, Throwable t) {
        log.error(msg, t);
    }

    public static void error(Logger log, String msg) {
        log.error(msg);
    }

    private static void appendData(StringBuffer sb, Object... datas) {
        for (int i = 0; i < datas.length; i++) {
            sb.append(datas[i].toString());
            if (i != datas.length - 1) {
                sb.append("_");
            }
        }
    }
}
