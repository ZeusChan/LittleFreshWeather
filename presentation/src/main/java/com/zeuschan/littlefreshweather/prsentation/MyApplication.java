package com.zeuschan.littlefreshweather.prsentation;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.zeuschan.littlefreshweather.common.util.LogUtil;

/**
 * Created by chenxiong on 2016/6/3.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initLeakCheck();
        initLogUtil();
    }

    private void initLogUtil() {
        if (BuildConfig.DEBUG) {
            LogUtil.mLogOn = true;
            LogUtil.mLogLevel = LogUtil.VERBOSE;
        } else {
            LogUtil.mLogOn = false;
        }
    }

    private void initLeakCheck() {
        if (BuildConfig.DEBUG) {
            //LeakCanary.install(this);
        }
    }
}
