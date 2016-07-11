package com.zeuschan.littlefreshweather.common.util;

import android.content.Context;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

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

    private static String bytesToHexString(byte[] bytes) {
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

    public static Date stringToDate(String pattern, String in) {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat)SimpleDateFormat.getDateInstance();
        simpleDateFormat.applyPattern(pattern);

        Date retDate = null;
        try {
            retDate = simpleDateFormat.parse(in);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return retDate;
    }

    public static String getFriendlyDateString(Date date) {
        if (date == null) {
            return "--月--日";
        }

        GregorianCalendar nowCalender = new GregorianCalendar();
        GregorianCalendar dstCalender = new GregorianCalendar();
        dstCalender.setTime(date);

        int now = nowCalender.get(GregorianCalendar.DAY_OF_YEAR);
        int dst = dstCalender.get(GregorianCalendar.DAY_OF_YEAR);

        if (dst - now == 0) {
            return "今天";
        } else if (dst - now == 1) {
            return "明天";
        } else if (dst - now == -1) {
            return "昨天";
        } else {
            SimpleDateFormat simpleDateFormat = (SimpleDateFormat)SimpleDateFormat.getDateInstance();
            simpleDateFormat.applyPattern("M月d日");
            return simpleDateFormat.format(date);
        }
    }
}
