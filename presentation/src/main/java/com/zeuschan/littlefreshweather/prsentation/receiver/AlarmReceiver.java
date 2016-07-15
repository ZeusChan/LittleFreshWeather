package com.zeuschan.littlefreshweather.prsentation.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Process;

import com.zeuschan.littlefreshweather.common.util.LogUtil;
import com.zeuschan.littlefreshweather.prsentation.service.WeatherUpdateService;

/**
 * Created by chenxiong on 2016/6/22.
 */
public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = AlarmReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.e(TAG, "onReceive");
        LogUtil.e(TAG, "pid=" + Process.myPid());
        LogUtil.e(TAG, "uid=" + Process.myUid());

        Intent i = new Intent(context, WeatherUpdateService.class);
        context.startService(i);
    }
}
