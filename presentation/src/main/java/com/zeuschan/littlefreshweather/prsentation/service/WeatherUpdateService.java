package com.zeuschan.littlefreshweather.prsentation.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.zeuschan.littlefreshweather.common.util.Constants;
import com.zeuschan.littlefreshweather.common.util.FileUtil;
import com.zeuschan.littlefreshweather.common.util.LogUtil;
import com.zeuschan.littlefreshweather.domain.usecase.GetCityWeatherUseCase;
import com.zeuschan.littlefreshweather.model.entity.WeatherEntity;
import com.zeuschan.littlefreshweather.prsentation.receiver.AlarmReceiver;
import com.zeuschan.littlefreshweather.prsentation.receiver.WeatherAppWidget;
import com.zeuschan.littlefreshweather.prsentation.view.activity.CityWeatherActivity;

import rx.Subscriber;

/**
 * Created by chenxiong on 2016/6/22.
 */
public class WeatherUpdateService extends Service {
    public static final String UPDATE_DATA_FLAG = "update_data_flag";
    public static final String UPDATE_CITY_ID = "update_city_id";
    private static final int ONE_HOUR = 60 * 60 * 1000;

    private static final String TAG = WeatherUpdateService.class.getSimpleName();
    private GetCityWeatherUseCase mUseCase;
    private String mCityId;

    @Override
    public void onCreate() {
        LogUtil.e(TAG, "onCreate");
        LogUtil.e(TAG, "pid=" + Process.myPid());
        LogUtil.e(TAG, "uid=" + Process.myUid());
        super.onCreate();
        mCityId = FileUtil.getStringFromPreferences(getApplicationContext(), Constants.GLOBAL_SETTINGS, Constants.PRF_KEY_CITY_ID, Constants.DEFAULT_CITY_ID);
        LogUtil.e(TAG, "cityid=" + mCityId);
        mUseCase = new GetCityWeatherUseCase(getApplicationContext(), mCityId, false);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.e(TAG, "onStartCommand");
        LogUtil.e(TAG, "pid=" + Process.myPid());
        LogUtil.e(TAG, "uid=" + Process.myUid());

        String cityId = null;
        if (intent != null) {
            cityId = intent.getStringExtra(UPDATE_CITY_ID);
        }
        if (cityId != null) {
            mCityId = cityId;
            mUseCase.setCityId(mCityId);
            LogUtil.e(TAG, "upate cityid to " + cityId);
        }

        boolean shouldUpdateData = true;
        if (intent != null && !intent.getBooleanExtra(UPDATE_DATA_FLAG, true)) {
            shouldUpdateData = false;
        }

        if (shouldUpdateData) {
            mUseCase.execute(new CityWeatherSubscriber());
        }

        int updateFreq = FileUtil.getIntFromPreferences(getApplicationContext(), Constants.GLOBAL_SETTINGS, Constants.PRF_KEY_UPDATE_FREQUENCY, Constants.DEFAULT_UPDATE_FREQUENCY);
        setUpdateServiceAlarm(getApplicationContext(), updateFreq);
        LogUtil.e(TAG, "updateFreq=" + updateFreq);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        LogUtil.e(TAG, "onDestroy");
        LogUtil.e(TAG, "pid=" + Process.myPid());
        LogUtil.e(TAG, "uid=" + Process.myUid());
        super.onDestroy();
        mUseCase.unsubscribe();
        mUseCase.clear();

        System.exit(0);
    }

    public static void setUpdateServiceAlarm(Context context, int updateFreq) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager manager = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        if (updateFreq != 0) {
            manager.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + updateFreq * ONE_HOUR, pendingIntent);
        } else {
            manager.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }

    public static boolean isUpdateServeceAlarmOn(Context context) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_NO_CREATE);
        return pendingIntent != null;
    }

    private final class CityWeatherSubscriber extends Subscriber<WeatherEntity> {
        @Override
        public void onCompleted() {
            stopSelf();
        }

        @Override
        public void onError(Throwable e) {
            stopSelf();
        }

        @Override
        public void onNext(WeatherEntity weatherEntity) {
            // 更新主界面
            Intent intent = new Intent(CityWeatherActivity.WEATHER_UPDATE_ACTION);
            intent.putExtra(CityWeatherActivity.WeatherUpdateReceiver.WEATHER_ENTITY, weatherEntity);
            //LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
            sendBroadcast(intent, Constants.RECV_WEATHER_UPDATE);

            // 更新appwidget
            Intent intent1 = new Intent(WeatherAppWidget.UPDATE_WIDGET_ACTION);
            intent1.putExtra(WeatherAppWidget.WEATHER_ENTITY, weatherEntity);
            sendBroadcast(intent1, Constants.RECV_WEATHER_UPDATE);

            // 更新通知栏
            boolean shouldNotify = FileUtil.getBooleanFromPreferences(getApplicationContext(), Constants.GLOBAL_SETTINGS, Constants.PRF_KEY_NOTIFY_WEATHER, Constants.DEFAULT_NOTIFY_WEATHER);
            if (shouldNotify) {
                Intent notifyIntent = new Intent(getApplicationContext(), WeatherNotificationService.class);
                notifyIntent.putExtra(WeatherNotificationService.WEATHER_ENTITY, weatherEntity);
                startService(notifyIntent);
            }
        }
    }
}
