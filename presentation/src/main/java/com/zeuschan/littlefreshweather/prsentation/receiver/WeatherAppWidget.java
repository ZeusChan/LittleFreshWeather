package com.zeuschan.littlefreshweather.prsentation.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Process;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.RemoteViews;

import com.zeuschan.littlefreshweather.common.util.Constants;
import com.zeuschan.littlefreshweather.common.util.FileUtil;
import com.zeuschan.littlefreshweather.common.util.LogUtil;
import com.zeuschan.littlefreshweather.common.util.StringUtil;
import com.zeuschan.littlefreshweather.model.entity.WeatherEntity;
import com.zeuschan.littlefreshweather.prsentation.R;
import com.zeuschan.littlefreshweather.prsentation.presenter.WidgetPresenter;
import com.zeuschan.littlefreshweather.prsentation.view.activity.SplashActivity;

import java.util.Date;
import java.util.GregorianCalendar;


/**
 * Created by chenxiong on 2016/6/27.
 */
public class WeatherAppWidget extends AppWidgetProvider implements WidgetPresenter.DataCallback {
    private static final String TAG = WeatherAppWidget.class.getSimpleName();

    public static final String UPDATE_WIDGET_ACTION = "com.zeuschan.littlefreshweather.prsentation.UPDATE_WIDGET";
    public static final String WEATHER_ENTITY = "weather_entity";
    public static final int ONE_SECOND = 1000;

    private static final String UPDATE_TYPE = "update_type";
    private static final int UPDATE_WEATHER = 0;
    private static final int UPDATE_TIME = 1;

    private static String mDataDate;
    private static WeatherEntity mEntity;
    private WidgetPresenter mPresenter;
    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.e(TAG, "onReceive");
        //LogUtil.e(TAG, "pid=" + Process.myPid());
        //LogUtil.e(TAG, "uid=" + Process.myUid());
        mContext = context;
        super.onReceive(context, intent);

        if (intent != null && intent.getAction().equalsIgnoreCase(UPDATE_WIDGET_ACTION)) {

            if (UPDATE_TIME == intent.getIntExtra(UPDATE_TYPE, UPDATE_WEATHER)) {
                renderTime();
                return;
            }

            WeatherEntity weatherEntity = null;
            weatherEntity = intent.getParcelableExtra(WEATHER_ENTITY);
            if (weatherEntity != null)
                renderData(weatherEntity);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        LogUtil.e(TAG, "onUpdate");

        startUpdateService(context);
    }

    @Override
    public void onEnabled(Context context) {
        LogUtil.e(TAG, "onEnabled");
        updateTimeSequence();
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        LogUtil.e(TAG, "onDisabled");
        setUpdateTimeAlarm(context, false, 0);
        mDataDate = null;
        super.onDisabled(context);
        if (mPresenter != null) {
            mPresenter.stop();
            mPresenter.destroy();
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void renderData(WeatherEntity entity) {
        if (entity == null) {
            return;
        }

        mEntity = entity;

        ComponentName thisWidget = new ComponentName(mContext, WeatherAppWidget.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

        for (int i = 0; i < appWidgetIds.length; ++i) {
            int appWidgetId = appWidgetIds[i];
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.app_widget_layout);

            Intent intent = new Intent(mContext, SplashActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
            views.setOnClickPendingIntent(R.id.ll_app_widget_root, pendingIntent);

            if (entity.getForecasts().size() > 0) {
                mDataDate = entity.getForecasts().get(0).getDate();
            }

            if (mDataDate != null && mDataDate.compareToIgnoreCase(StringUtil.getCurrentDateTime("yyyy-MM-dd")) != 0) {
                views.setTextColor(R.id.tv_app_widget_weather_desc, mContext.getApplicationContext().getResources().getColor(R.color.colorAirFour));
                views.setTextViewText(R.id.tv_app_widget_weather_desc, mContext.getApplicationContext().getString(R.string.data_out_of_date));
            } else {
                views.setTextColor(R.id.tv_app_widget_weather_desc, mContext.getApplicationContext().getResources().getColor(R.color.colorLightGray));
                views.setTextViewText(R.id.tv_app_widget_weather_desc, entity.getWeatherDescription());
            }
            views.setImageViewResource(R.id.iv_app_widget_weather_icon, getWeatherIconId(entity.getWeatherDescription()));
            views.setTextViewText(R.id.tv_app_widget_city_name, entity.getCityName());
            views.setTextViewText(R.id.tv_app_widget_temp, entity.getCurrentTemperature() + "℃");
            AirQulityRepresentation airQulityRepresentation = new AirQulityRepresentation();
            getAirQualityTypeAndColor(entity.getAirQulityIndex(), airQulityRepresentation);
            //views.setTextColor(R.id.tv_app_widget_air, airQulityRepresentation.getmAirQulityColorId());
            views.setTextViewText(R.id.tv_app_widget_air, airQulityRepresentation.getmAirQulityType());

            views.setTextViewText(R.id.tv_app_widget_time, StringUtil.getCurrentDateTime("HH:mm"));
            views.setTextViewText(R.id.tv_app_widget_dayofweek, StringUtil.getCurrentDateTime("EEEE"));
            views.setTextViewText(R.id.tv_app_widget_date, StringUtil.getCurrentDateTime("M月d日"));
            String[] dateAndTime = entity.getDataUpdateTime().split(" ");
            Date date = StringUtil.stringToDate("yyyy-MM-dd", dateAndTime[0]);
            views.setTextViewText(R.id.tv_app_widget_update_time, StringUtil.getFriendlyDateString(date, false) + " " + dateAndTime[1] + " 发布");

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

        LogUtil.e(TAG, "renderData");
    }

    private void renderTime() {
        ComponentName thisWidget = new ComponentName(mContext, WeatherAppWidget.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        for (int i = 0; i < appWidgetIds.length; ++i) {
            int appWidgetId = appWidgetIds[i];
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.app_widget_layout);

            if (mDataDate != null && mDataDate.compareToIgnoreCase(StringUtil.getCurrentDateTime("yyyy-MM-dd")) != 0) {
                views.setTextColor(R.id.tv_app_widget_weather_desc, mContext.getApplicationContext().getResources().getColor(R.color.colorAirFour));
                views.setTextViewText(R.id.tv_app_widget_weather_desc, mContext.getApplicationContext().getString(R.string.data_out_of_date));
            }

            views.setTextViewText(R.id.tv_app_widget_time, StringUtil.getCurrentDateTime("HH:mm"));
            views.setTextViewText(R.id.tv_app_widget_dayofweek, StringUtil.getCurrentDateTime("EEEE"));
            views.setTextViewText(R.id.tv_app_widget_date, StringUtil.getCurrentDateTime("M月d日"));

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

        renderData(mEntity);

        updateTimeSequence();
        LogUtil.e(TAG, "renderTime");
    }

    private void startUpdateService(Context context) {
        //LogUtil.e(TAG, "startUpdateService");

        Intent intent = new Intent(context, AlarmReceiver.class);
        context.sendBroadcast(intent, Constants.RECV_WEATHER_UPDATE);
    }

    private void startUpdateWeather(Context context) {
        mContext = context;
        String cityId = FileUtil.getStringFromPreferences(context.getApplicationContext(), Constants.GLOBAL_SETTINGS, Constants.PRF_KEY_CITY_ID, Constants.DEFAULT_CITY_ID);
        LogUtil.e(TAG, "cityid=" + cityId);
        if (mPresenter == null) {
            mPresenter = new WidgetPresenter(context.getApplicationContext(), cityId, this);
        } else {
            mPresenter.stop();
        }
        mPresenter.setCityId(cityId);
        mPresenter.start();
    }

    private static void setUpdateTimeAlarm(Context context, boolean on, final int updateSequency) {
        //LogUtil.e(TAG, "setUpdateTimeAlarm, on=" + on);

        Intent intent = new Intent(UPDATE_WIDGET_ACTION);
        intent.putExtra(UPDATE_TYPE, UPDATE_TIME);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        if (on) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                //LogUtil.e(TAG, "setRepeating");
                manager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + updateSequency, updateSequency, pendingIntent);
            } else {
                //LogUtil.e(TAG, "setExact");
                manager.setExact(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + updateSequency, pendingIntent);
            }
        } else {
            manager.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }

    private static boolean isUpdateTimeAlarmOn(Context context) {
        Intent intent = new Intent(UPDATE_WIDGET_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_NO_CREATE);
        return pendingIntent != null;
    }

    private void updateTimeSequence() {
        setUpdateTimeAlarm(mContext, false, 0);

        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        int updateFrequence = gregorianCalendar.get(GregorianCalendar.SECOND);
        updateFrequence *= ONE_SECOND;
        //LogUtil.e(TAG, "current second=" + updateFrequence);
        if (updateFrequence > 8 * ONE_SECOND && updateFrequence < 52 * ONE_SECOND) {
            updateFrequence = 5 * ONE_SECOND;
        } else {
            updateFrequence = ONE_SECOND >> 1;
        }

        //LogUtil.e(TAG, "updateSequence=" + updateFrequence);
        setUpdateTimeAlarm(mContext, true, updateFrequence);
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
