package com.zeuschan.littlefreshweather.prsentation;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by chenxiong on 2016/6/3.
 */
public class MyApplication extends Application {

    private void initLeakCheck() {
        if (BuildConfig.DEBUG) {
            LeakCanary.install(this);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initLeakCheck();
    }
}
