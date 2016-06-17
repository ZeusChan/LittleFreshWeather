package com.zeuschan.littlefreshweather.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by chenxiong on 2016/6/17.
 */
public class StringUtil {
    public static String bytesToMd5String(byte[] bytes) {
        String resultString;
        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(bytes);
            resultString = bytesToHexString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            resultString = String.valueOf(bytes.hashCode());
        }

        return  resultString;
    }

    private static  String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
