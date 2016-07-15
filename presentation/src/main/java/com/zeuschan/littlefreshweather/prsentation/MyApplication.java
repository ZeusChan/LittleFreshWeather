package com.zeuschan.littlefreshweather.prsentation;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.zeuschan.littlefreshweather.common.util.LogUtil;

/**
 * Created by chenxiong on 2016/6/3.
 */
public class MyApplication extends Application {

    private void initLeakCheck() {
        if (BuildConfig.DEBUG) {
            //LeakCanary.install(this);
        }
    }

    private void initLogUtil() {
        if (BuildConfig.DEBUG) {
            LogUtil.mLogOn = true;
            LogUtil.mLogLevel = LogUtil.VERBOSE;
        } else {
            LogUtil.mLogOn = false;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initLeakCheck();
        initLogUtil();
    }
}
