package com.zeuschan.littlefreshweather.prsentation.receiver;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.zeuschan.littlefreshweather.common.util.Constants;
import com.zeuschan.littlefreshweather.common.util.FileUtil;
import com.zeuschan.littlefreshweather.domain.usecase.GetCityWeatherUseCase;

/**
 * Created by chenxiong on 2016/6/27.
 */
public class WeatherAppWidget extends AppWidgetProvider {
    private static final String TAG = WeatherAppWidget.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //updateWeather(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    /*public void startUpdateWeather(Context context) {
        String cityId = FileUtil.getStringFromPreferences(context.getApplicationContext(), Constants.GLOBAL_SETTINGS, Constants.PRF_KEY_CITY_ID, Constants.DEFAULT_CITY_ID);
        GetCityWeatherUseCase getCityWeatherUseCase = new GetCityWeatherUseCase(context.getApplicationContext(), cityId, true);
    }

    public void updateWeather(Context context) {
        ComponentName thisWidget = new ComponentName(context, WeatherAppWidget.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        updateWeather(context, appWidgetManager, appWidgetIds);
    }

    public void updateWeather(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {


    }*/
}
