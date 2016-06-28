package com.zeuschan.littlefreshweather.prsentation.receiver;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.zeuschan.littlefreshweather.common.util.Constants;
import com.zeuschan.littlefreshweather.common.util.FileUtil;
import com.zeuschan.littlefreshweather.model.entity.WeatherEntity;
import com.zeuschan.littlefreshweather.prsentation.R;
import com.zeuschan.littlefreshweather.prsentation.presenter.WidgetPresenter;
import com.zeuschan.littlefreshweather.prsentation.view.activity.SplashActivity;

/**
 * Created by chenxiong on 2016/6/27.
 */
public class WeatherAppWidget extends AppWidgetProvider implements WidgetPresenter.DataCallback {
    private static final String TAG = WeatherAppWidget.class.getSimpleName();
    public static final String UPDATE_WIDGET_ACTION = "com.zeuschan.littlefreshweather.prsentation.UPDATE_WIDGET";

    private WidgetPresenter mPresenter;
    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (intent.getAction().equalsIgnoreCase(UPDATE_WIDGET_ACTION)) {
            startUpdateWeather(context);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        startUpdateWeather(context);

        Intent intent = new Intent(context, SplashActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget_layout);
        remoteViews.setOnClickPendingIntent(R.id.rl_city_weather, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        if (mPresenter != null)
            mPresenter.stop();
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void renderData(WeatherEntity entity) {
        ComponentName thisWidget = new ComponentName(mContext, WeatherAppWidget.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

        for (int i = 0; i < appWidgetIds.length; ++i) {
            int appWidgetId = appWidgetIds[i];
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.app_widget_layout);

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

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

        Log.e(TAG, "rendData");
    }

    private void startUpdateWeather(Context context) {
        mContext = context;
        String cityId = FileUtil.getStringFromPreferences(context.getApplicationContext(), Constants.GLOBAL_SETTINGS, Constants.PRF_KEY_CITY_ID, Constants.DEFAULT_CITY_ID);
        if (mPresenter == null) {
            mPresenter = new WidgetPresenter(context.getApplicationContext(), cityId, this);
        } else {
            mPresenter.stop();
        }
        mPresenter.setCityId(cityId);
        mPresenter.start();
    }
}
