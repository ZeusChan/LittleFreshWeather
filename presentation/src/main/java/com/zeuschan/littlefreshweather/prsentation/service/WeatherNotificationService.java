package com.zeuschan.littlefreshweather.prsentation.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.zeuschan.littlefreshweather.model.entity.WeatherEntity;
import com.zeuschan.littlefreshweather.prsentation.R;
import com.zeuschan.littlefreshweather.prsentation.presenter.WidgetPresenter;
import com.zeuschan.littlefreshweather.prsentation.view.activity.SplashActivity;

/**
 * Created by chenxiong on 2016/6/28.
 */
public class WeatherNotificationService extends Service implements WidgetPresenter.DataCallback {
    private static final String TAG = WeatherNotificationService.class.getSimpleName();

    public static final String NOTIFY_CITY_ID = "notify_city_id";
    public static final String WEATHER_ENTITY = "weather_entity";

    private WidgetPresenter mPresenter;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mNotificationBuilder;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
        Log.e(TAG, "pid=" + Process.myPid());
        Log.e(TAG, "uid=" + Process.myUid());
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        Log.e(TAG, "pid=" + Process.myPid());
        Log.e(TAG, "uid=" + Process.myUid());

        WeatherEntity weatherEntity = null;
        if (intent != null) {
            weatherEntity = intent.getParcelableExtra(WEATHER_ENTITY);
        }
        renderData(weatherEntity);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        Log.e(TAG, "pid=" + Process.myPid());
        Log.e(TAG, "uid=" + Process.myUid());
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.stop();
            mPresenter.destroy();
        }

        System.exit(0);
    }

    private void startUpdateWeather(String cityId) {
        Log.e(TAG, "cityid=" + cityId);
        if (mPresenter == null) {
            mPresenter = new WidgetPresenter(this.getApplicationContext(), cityId, this);
        } else {
            mPresenter.stop();
        }
        mPresenter.setCityId(cityId);
        mPresenter.start();
    }

    @Override
    public void renderData(WeatherEntity entity) {
        if (mNotificationManager == null) {
            mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Intent intent = new Intent(this, SplashActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        RemoteViews views = new RemoteViews(getPackageName(), R.layout.app_notification_layout);
        if (entity == null) {
            views.setViewVisibility(R.id.tv_widget_no_data, View.VISIBLE);
            views.setViewVisibility(R.id.rl_widget_city_weather, View.INVISIBLE);
            views.setViewVisibility(R.id.tv_widget_separator, View.INVISIBLE);
        } else {
            views.setViewVisibility(R.id.tv_widget_no_data, View.INVISIBLE);
            views.setViewVisibility(R.id.rl_widget_city_weather, View.VISIBLE);
            views.setViewVisibility(R.id.tv_widget_separator, View.VISIBLE);
            views.setTextViewText(R.id.tv_widget_cur_city, entity.getCityName());
            views.setTextViewText(R.id.tv_widget_cur_temp, entity.getCurrentTemperature() + "℃");
            views.setTextViewText(R.id.tv_widget_weather_desc, entity.getWeatherDescription());
            views.setTextViewText(R.id.tv_widget_cur_aqi, entity.getAirQulityType());
            int index = 0;
            for (WeatherEntity.Forecast forecast : entity.getForecasts()) {
                ++index;
                if (1 == index) {
                    views.setTextViewText(R.id.tv_widget_date1, forecast.getDate());
                    views.setTextViewText(R.id.tv_widget_temp1, forecast.getMinTemperature() + " ~ " + forecast.getMaxTemperature() + "℃");
                    views.setTextViewText(R.id.tv_widget_weather_desc1, forecast.getWeatherDescriptionDaytime());
                } else if (2 == index) {
                    views.setTextViewText(R.id.tv_widget_date2, forecast.getDate());
                    views.setTextViewText(R.id.tv_widget_temp2, forecast.getMinTemperature() + " ~ " + forecast.getMaxTemperature() + "℃");
                    views.setTextViewText(R.id.tv_widget_weather_desc2, forecast.getWeatherDescriptionDaytime());
                } else if (3 == index) {
                    views.setTextViewText(R.id.tv_widget_date3, forecast.getDate());
                    views.setTextViewText(R.id.tv_widget_temp3, forecast.getMinTemperature() + " ~ " + forecast.getMaxTemperature() + "℃");
                    views.setTextViewText(R.id.tv_widget_weather_desc3, forecast.getWeatherDescriptionDaytime());
                } else if (index > 3) {
                    break;
                }
            }
        }

        Notification notification = null;
        if (mNotificationBuilder == null) {
            mNotificationBuilder = new NotificationCompat.Builder(this);
        }

        mNotificationBuilder.setSmallIcon(R.mipmap.ic_launcher).setPriority(Notification.PRIORITY_MAX);
        notification = mNotificationBuilder.build();
        notification.bigContentView = views;
        notification.contentView = views;
        views.setOnClickPendingIntent(R.id.rl_city_weather, pendingIntent);

        //mNotificationManager.notify(1, notification);
        startForeground(1, notification);

        Log.e(TAG, "rendData");
    }
}
