package com.zeuschan.littlefreshweather.common.util;

import android.util.Log;

/**
 * Created by chenxiong on 2016/7/15.
 */
public class LogUtil {
    /**
     * Priority constant for the println method; use Log.v.
     */
    public static final int VERBOSE = 2;

    /**
     * Priority constant for the println method; use Log.d.
     */
    public static final int DEBUG = 3;

    /**
     * Priority constant for the println method; use Log.i.
     */
    public static final int INFO = 4;

    /**
     * Priority constant for the println method; use Log.w.
     */
    public static final int WARN = 5;

    /**
     * Priority constant for the println method; use Log.e.
     */
    public static final int ERROR = 6;

    /**
     * Priority constant for the println method.
     */
    public static final int ASSERT = 7;

    public static boolean mLogOn = true;
    public static int mLogLevel = VERBOSE;

    public static void v(String tag, String msg) {
        if (mLogOn && mLogLevel <= VERBOSE) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (mLogOn && mLogLevel <= DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (mLogOn && mLogLevel <= INFO) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (mLogOn && mLogLevel <= WARN) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (mLogOn && mLogLevel <= ERROR) {
            Log.e(tag, msg);
        }
    }
}
