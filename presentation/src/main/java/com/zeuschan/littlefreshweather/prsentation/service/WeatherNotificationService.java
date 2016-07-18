package com.zeuschan.littlefreshweather.prsentation.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Entity;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.RemoteViews;

import com.zeuschan.littlefreshweather.common.util.LogUtil;
import com.zeuschan.littlefreshweather.common.util.StringUtil;
import com.zeuschan.littlefreshweather.model.entity.WeatherEntity;
import com.zeuschan.littlefreshweather.prsentation.R;
import com.zeuschan.littlefreshweather.prsentation.presenter.WidgetPresenter;
import com.zeuschan.littlefreshweather.prsentation.view.activity.SplashActivity;

import java.util.Date;

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
    private Context mContext;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        LogUtil.e(TAG, "onCreate");
        LogUtil.e(TAG, "pid=" + Process.myPid());
        LogUtil.e(TAG, "uid=" + Process.myUid());
        super.onCreate();
        mContext = this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.e(TAG, "onStartCommand");
        LogUtil.e(TAG, "pid=" + Process.myPid());
        LogUtil.e(TAG, "uid=" + Process.myUid());

        WeatherEntity weatherEntity = null;
        if (intent != null) {
            weatherEntity = intent.getParcelableExtra(WEATHER_ENTITY);
            renderData(weatherEntity);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        LogUtil.e(TAG, "onDestroy");
        LogUtil.e(TAG, "pid=" + Process.myPid());
        LogUtil.e(TAG, "uid=" + Process.myUid());
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.stop();
            mPresenter.destroy();
        }

        System.exit(0);
    }

    private void startUpdateWeather(String cityId) {
        LogUtil.e(TAG, "cityid=" + cityId);
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
        if (entity == null) {
            return;
        }

        if (mNotificationManager == null) {
            mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Intent intent = new Intent(this, SplashActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        RemoteViews views = new RemoteViews(getPackageName(), R.layout.app_notification_layout_small);
        RemoteViews bigViews = new RemoteViews(getPackageName(), R.layout.app_notification_layout_big);

        views.setImageViewResource(R.id.iv_app_notification_small_weather_icon, getWeatherIconId(entity.getWeatherDescription()));
        views.setTextViewText(R.id.tv_app_notification_small_temp, entity.getCurrentTemperature() + "℃");
        views.setTextViewText(R.id.tv_app_notification_small_city_name, entity.getCityName());
        views.setTextViewText(R.id.tv_app_notification_small_weather_desc, entity.getWeatherDescription());
        if (entity.getForecasts().size() > 0)
            views.setTextViewText(R.id.tv_app_notification_small_temp_span, entity.getForecasts().get(0).getMinTemperature() + "~" + entity.getForecasts().get(0).getMaxTemperature() + "℃");
        views.setTextViewText(R.id.tv_app_notification_small_date, StringUtil.getCurrentDateTime("yyyy-MM-dd"));
        views.setTextViewText(R.id.tv_app_notification_small_dayofweek, StringUtil.getCurrentDateTime("EEEE"));
        views.setTextViewText(R.id.tv_app_notification_small_air_quality_index, entity.getAirQulityIndex());
        AirQulityRepresentation airQulityRepresentation = new AirQulityRepresentation();
        getAirQualityTypeAndColor(entity.getAirQulityIndex(), airQulityRepresentation);
        views.setTextViewText(R.id.tv_app_notification_small_air_quality_type, airQulityRepresentation.getmAirQulityType());

        bigViews.setImageViewResource(R.id.iv_app_notification_small_weather_icon, getWeatherIconId(entity.getWeatherDescription()));
        bigViews.setTextViewText(R.id.tv_app_notification_small_temp, entity.getCurrentTemperature() + "℃");
        bigViews.setTextViewText(R.id.tv_app_notification_small_city_name, entity.getCityName());
        bigViews.setTextViewText(R.id.tv_app_notification_small_weather_desc, entity.getWeatherDescription());
        if (entity.getForecasts().size() > 0)
            bigViews.setTextViewText(R.id.tv_app_notification_small_temp_span, entity.getForecasts().get(0).getMinTemperature() + "~" + entity.getForecasts().get(0).getMaxTemperature() + "℃");
        bigViews.setTextViewText(R.id.tv_app_notification_small_date, StringUtil.getCurrentDateTime("yyyy-MM-dd"));
        bigViews.setTextViewText(R.id.tv_app_notification_small_dayofweek, StringUtil.getCurrentDateTime("EEEE"));
        bigViews.setTextViewText(R.id.tv_app_notification_small_air_quality_index, entity.getAirQulityIndex());
        bigViews.setTextViewText(R.id.tv_app_notification_small_air_quality_type, airQulityRepresentation.getmAirQulityType());

        if (entity.getForecasts().size() >= 3) {
            bigViews.setImageViewResource(R.id.iv_app_notification_big_forecast_icon1, getWeatherIconId(entity.getForecasts().get(0).getWeatherDescriptionDaytime()));
            Date date0 = StringUtil.stringToDate("yyyy-MM-dd", entity.getForecasts().get(0).getDate());
            bigViews.setTextViewText(R.id.tv_app_notification_big_forecast_date1, StringUtil.getFriendlyDateString(date0, true));
            bigViews.setTextViewText(R.id.tv_app_notification_big_forecast_temp1, entity.getForecasts().get(0).getMinTemperature() + "~" + entity.getForecasts().get(0).getMaxTemperature() + "℃");

            bigViews.setImageViewResource(R.id.iv_app_notification_big_forecast_icon2, getWeatherIconId(entity.getForecasts().get(1).getWeatherDescriptionDaytime()));
            Date date1 = StringUtil.stringToDate("yyyy-MM-dd", entity.getForecasts().get(1).getDate());
            bigViews.setTextViewText(R.id.tv_app_notification_big_forecast_date2, StringUtil.getFriendlyDateString(date1, true));
            bigViews.setTextViewText(R.id.tv_app_notification_big_forecast_temp2, entity.getForecasts().get(1).getMinTemperature() + "~" + entity.getForecasts().get(1).getMaxTemperature() + "℃");

            bigViews.setImageViewResource(R.id.iv_app_notification_big_forecast_icon3, getWeatherIconId(entity.getForecasts().get(2).getWeatherDescriptionDaytime()));
            Date date2 = StringUtil.stringToDate("yyyy-MM-dd", entity.getForecasts().get(2).getDate());
            bigViews.setTextViewText(R.id.tv_app_notification_big_forecast_date3, StringUtil.getFriendlyDateString(date2, true));
            bigViews.setTextViewText(R.id.tv_app_notification_big_forecast_temp3, entity.getForecasts().get(2).getMinTemperature() + "~" + entity.getForecasts().get(2).getMaxTemperature() + "℃");
        }

        Notification notification = null;
        if (mNotificationBuilder == null) {
            mNotificationBuilder = new NotificationCompat.Builder(this);
        }

        mNotificationBuilder.setSmallIcon(R.mipmap.ic_launcher).setPriority(Notification.PRIORITY_MAX);
        notification = mNotificationBuilder.build();
        notification.bigContentView = bigViews;
        notification.contentView = views;
        bigViews.setOnClickPendingIntent(R.id.ll_app_notification_big_root, pendingIntent);
        views.setOnClickPendingIntent(R.id.ll_app_notification_small_root, pendingIntent);

        //mNotificationManager.notify(1, notification);
        startForeground(1, notification);

        LogUtil.e(TAG, "rendData");
    }

    private static class AirQulityRepresentation {
        private String mAirQulityType;
        private int mAirQulityColorId;

        public int getmAirQulityColorId() {
            return mAirQulityColorId;
        }

        public void setmAirQulityColorId(int mAirQulityColorId) {
            this.mAirQulityColorId = mAirQulityColorId;
        }

        public String getmAirQulityType() {
            return mAirQulityType;
        }

        public void setmAirQulityType(String mAirQulityType) {
            this.mAirQulityType = mAirQulityType;
        }
    }

    private boolean getAirQualityTypeAndColor(String airQulityIndexString, AirQulityRepresentation airQulityRepresentation) {
        int airQulityIndex = 0;
        boolean ret = true;
        try {
            airQulityIndex = Integer.parseInt(airQulityIndexString);
        } catch (Exception e) {
            ret = false;
            airQulityRepresentation.setmAirQulityType("---");
            airQulityRepresentation.setmAirQulityColorId(R.color.colorAirOne);
        }

        if (ret) {
            if (airQulityIndex <= 50) {
                airQulityRepresentation.setmAirQulityType("空气优");
                airQulityRepresentation.setmAirQulityColorId(R.color.colorAirOne);
            } else if (airQulityIndex <= 100) {
                airQulityRepresentation.setmAirQulityType("空气良");
                airQulityRepresentation.setmAirQulityColorId(R.color.colorAirTwo);
            } else if (airQulityIndex <= 150) {
                airQulityRepresentation.setmAirQulityType("轻度污染");
                airQulityRepresentation.setmAirQulityColorId(R.color.colorAirThree);
            } else if (airQulityIndex <= 200) {
                airQulityRepresentation.setmAirQulityType("中度污染");
                airQulityRepresentation.setmAirQulityColorId(R.color.colorAirFour);
            } else if (airQulityIndex <= 300) {
                airQulityRepresentation.setmAirQulityType("重度污染");
                airQulityRepresentation.setmAirQulityColorId(R.color.colorAirFive);
            } else {
                airQulityRepresentation.setmAirQulityType("严重污染");
                airQulityRepresentation.setmAirQulityColorId(R.color.colorAirSix);
            }
        }
        return ret;
    }

    private int getWeatherIconId(final String desc) {
        if (!TextUtils.isEmpty(desc)) {
            if (desc.equalsIgnoreCase(mContext.getString(R.string.sunny))) {
                return R.drawable.iclockweather_w1;
            } else if (desc.equalsIgnoreCase(mContext.getString(R.string.partly_cloudy))
                    || desc.equalsIgnoreCase(mContext.getString(R.string.cloudy))
                    || desc.equalsIgnoreCase(mContext.getString(R.string.few_cloud))) {
                return R.drawable.iclockweather_w2;
            } else if (desc.equalsIgnoreCase(mContext.getString(R.string.overcast))) {
                return R.drawable.iclockweather_w3;
            } else if (desc.equalsIgnoreCase(mContext.getString(R.string.shower_rain))) {
                return R.drawable.iclockweather_w8;
            } else if (desc.equalsIgnoreCase(mContext.getString(R.string.heavy_shower_rain))) {
                return R.drawable.iclockweather_w8;
            } else if (desc.equalsIgnoreCase(mContext.getString(R.string.thunder_shower))
                    || desc.equalsIgnoreCase(mContext.getString(R.string.heavy_thunderstorm))) {
                return R.drawable.iclockweather_w9;
            } else if (desc.equalsIgnoreCase(mContext.getString(R.string.hail))) {
                return R.drawable.iclockweather_w18;
            } else if (desc.equalsIgnoreCase(mContext.getString(R.string.light_rain))
                    || desc.equalsIgnoreCase(mContext.getString(R.string.drizzle_rain))
                    || desc.equalsIgnoreCase(mContext.getString(R.string.drizzle_rain_1))) {
                return R.drawable.iclockweather_w4;
            } else if (desc.equalsIgnoreCase(mContext.getString(R.string.moderate_rain))) {
                return R.drawable.iclockweather_w5;
            } else if (desc.equalsIgnoreCase(mContext.getString(R.string.heavy_rain))
                    || desc.equalsIgnoreCase(mContext.getString(R.string.storm))) {
                return R.drawable.iclockweather_w6;
            } else if (desc.equalsIgnoreCase(mContext.getString(R.string.extreme_rain))
                    || desc.equalsIgnoreCase(mContext.getString(R.string.heavy_storm))
                    || desc.equalsIgnoreCase(mContext.getString(R.string.severe_storm))) {
                return R.drawable.iclockweather_w7;
            } else if (desc.equalsIgnoreCase(mContext.getString(R.string.freezing_rain))) {
                return R.drawable.iclockweather_w15;
            } else if (desc.equalsIgnoreCase(mContext.getString(R.string.light_snow))
                    || desc.equalsIgnoreCase(mContext.getString(R.string.snow_flurry))) {
                return R.drawable.iclockweather_w11;
            } else if (desc.equalsIgnoreCase(mContext.getString(R.string.moderate_snow))) {
                return R.drawable.iclockweather_w12;
            } else if (desc.equalsIgnoreCase(mContext.getString(R.string.heavy_snow))) {
                return R.drawable.iclockweather_w13;
            } else if (desc.equalsIgnoreCase(mContext.getString(R.string.snow_storm))) {
                return R.drawable.iclockweather_w14;
            } else if (desc.equalsIgnoreCase(mContext.getString(R.string.sleet))
                    || desc.equalsIgnoreCase(mContext.getString(R.string.rain_snow))
                    || desc.equalsIgnoreCase(mContext.getString(R.string.shower_snow))) {
                return R.drawable.iclockweather_w10;
            } else if (desc.equalsIgnoreCase(mContext.getString(R.string.mist))
                    || desc.equalsIgnoreCase(mContext.getString(R.string.foggy))) {
                return R.drawable.iclockweather_w16;
            } else if (desc.equalsIgnoreCase(mContext.getString(R.string.haze))
                    || desc.equalsIgnoreCase(mContext.getString(R.string.sand))
                    || desc.equalsIgnoreCase(mContext.getString(R.string.dust))
                    || desc.equalsIgnoreCase(mContext.getString(R.string.volcanic_ash))
                    || desc.equalsIgnoreCase(mContext.getString(R.string.dust_storm))
                    || desc.equalsIgnoreCase(mContext.getString(R.string.sand_storm))) {
                return R.drawable.iclockweather_w17;
            }
        }

        return R.drawable.iclockweather_w2;
    }
}
