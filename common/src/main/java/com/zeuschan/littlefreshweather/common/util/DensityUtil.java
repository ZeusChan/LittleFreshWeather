package com.zeuschan.littlefreshweather.common.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by chenxiong on 2016/6/30.
 */
public class DensityUtil {
    public static int getScreenWidth(Context context) {
        return context.getApplicationContext().getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context) {
        return context.getApplicationContext().getResources().getDisplayMetrics().heightPixels;
    }

    public static int dp2px(Context context, float dpValue) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return (int)(dpValue * dm.density + 0.5f);
    }

    public static int px2dp(Context context, float pxValue) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return (int)(pxValue / dm.density + 0.5f);
    }
}
