package com.zeuschan.littlefreshweather.prsentation.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.zeuschan.littlefreshweather.common.util.Constants;
import com.zeuschan.littlefreshweather.common.util.FileUtil;
import com.zeuschan.littlefreshweather.domain.usecase.GetCityWeatherUseCase;
import com.zeuschan.littlefreshweather.model.entity.WeatherEntity;
import com.zeuschan.littlefreshweather.prsentation.presenter.WidgetPresenter;
import com.zeuschan.littlefreshweather.prsentation.receiver.AlarmReceiver;
import com.zeuschan.littlefreshweather.prsentation.receiver.WeatherAppWidget;
import com.zeuschan.littlefreshweather.prsentation.view.activity.CityWeatherActivity;

import rx.Subscriber;

/**
 * Created by chenxiong on 2016/6/22.
 */
public class WeatherUpdateService extends Service {
    private static final String TAG = WeatherUpdateService.class.getSimpleName();
    private GetCityWeatherUseCase mUseCase;

    @Override
    public void onCreate() {
        super.onCreate();
        String cityId = FileUtil.getStringFromPreferences(getApplicationContext(), Constants.GLOBAL_SETTINGS, Constants.PRF_KEY_CITY_ID, Constants.DEFAULT_CITY_ID);
        mUseCase = new GetCityWeatherUseCase(getApplicationContext(), cityId, false);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mUseCase.execute(new CityWeatherSubscriber());

        AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + Constants.DEFAULT_UPDATE_FREQUENCY, pendingIntent);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUseCase.unsubscribe();
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
            Intent intent = new Intent(CityWeatherActivity.WEATHER_UPDATE_ACTION);
            intent.putExtra(CityWeatherActivity.WeatherUpdateReceiver.WEATHER_ENTITY, weatherEntity);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

            sendBroadcast(new Intent(WeatherAppWidget.UPDATE_WIDGET_ACTION), Constants.RECV_WEATHER_UPDATE);
            startService(new Intent(WeatherUpdateService.this, WeatherNotificationService.class));
        }
    }
}
