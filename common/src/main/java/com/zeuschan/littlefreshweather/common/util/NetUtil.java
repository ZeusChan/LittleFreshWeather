package com.zeuschan.littlefreshweather.common.util;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by chenxiong on 2016/6/17.
 */
public class NetUtil {
    public static boolean isNetworkAvailable(Context context) {
        if (null == context)
            throw new IllegalArgumentException("isNetworkAvailable argument cannot be null");

        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null)
                return networkInfo.isAvailable();
        }

        return false;
    }
}
