package com.zeuschan.littlefreshweather.prsentation.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zeuschan.littlefreshweather.prsentation.service.WeatherUpdateService;

/**
 * Created by chenxiong on 2016/6/22.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, WeatherUpdateService.class);
        context.startService(i);
    }
}
