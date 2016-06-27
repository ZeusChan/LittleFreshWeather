package com.zeuschan.littlefreshweather.prsentation.receiver;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.zeuschan.littlefreshweather.common.util.Constants;
import com.zeuschan.littlefreshweather.common.util.FileUtil;
import com.zeuschan.littlefreshweather.model.entity.WeatherEntity;
import com.zeuschan.littlefreshweather.prsentation.R;
import com.zeuschan.littlefreshweather.prsentation.presenter.WidgetPresenter;

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
        Log.e(TAG, "onReceive: context=" + context);
        super.onReceive(context, intent);

        if (intent.getAction().equalsIgnoreCase(UPDATE_WIDGET_ACTION)) {
            Log.e(TAG, "update action");
            startUpdateWeather(context);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.e(TAG, "onUpdate: context=" + context);
        startUpdateWeather(context);
    }

    @Override
    public void onEnabled(Context context) {
        Log.e(TAG, "onEnabled: context=" + context);
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        Log.e(TAG, "onDisabled: context=" + context);
        super.onDisabled(context);
        if (mPresenter != null)
            mPresenter.stop();
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        Log.e(TAG, "onDeleted: context=" + context);
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
            views.setTextViewText(R.id.tv_widget_cur_temp, entity.getCurrentTemperature() + "℃");
            views.setTextViewText(R.id.tv_widget_cur_city, entity.getCityName());
            views.setTextViewText(R.id.tv_widget_weather_desc, entity.getWeatherDescription());
            views.setTextViewText(R.id.tv_widget_cur_aqi, entity.getAirQulityType());
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    public void startUpdateWeather(Context context) {
        Log.e(TAG, "startUpdateWeather: context=" + context);
        mContext = context;
        String cityId = FileUtil.getStringFromPreferences(context.getApplicationContext(), Constants.GLOBAL_SETTINGS, Constants.PRF_KEY_CITY_ID, Constants.DEFAULT_CITY_ID);
        if (mPresenter == null) {
            mPresenter = new WidgetPresenter(context.getApplicationContext(), cityId, this);
        }
        mPresenter.setCityId(cityId);
        mPresenter.start();
    }
}
