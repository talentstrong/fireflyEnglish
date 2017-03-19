package com.firefly.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

public class GuidUtil {

    /**
     * The flag.
     */
    private static final int PAD_BELOW = 0x10;

    /**
     * The flag.
     */
    private static final int TWO_BYTES = 0xFF;

    public static String getGuid(String ip) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        SecureRandom rand = new SecureRandom();
        long time = System.currentTimeMillis();

        StringBuffer sbValueBeforeMD5 = new StringBuffer(128);
        sbValueBeforeMD5.append(ip);
        sbValueBeforeMD5.append(":");
        sbValueBeforeMD5.append(Long.toString(time));
        sbValueBeforeMD5.append(":");
        sbValueBeforeMD5.append(Long.toString(rand.nextLong()));

        md5.update(sbValueBeforeMD5.toString().getBytes());

        byte[] array = md5.digest();
        StringBuffer sb = new StringBuffer(32);
        for (int j = 0; j < array.length; ++j) {
            int b = array[j] & TWO_BYTES;
            if (b < PAD_BELOW) {
                sb.append('0');
            }

            sb.append(Integer.toHexString(b));
        }

        String valueAfterMD5 = sb.toString() + UUID.randomUUID().toString();
        return valueAfterMD5.toUpperCase();
    }
}
