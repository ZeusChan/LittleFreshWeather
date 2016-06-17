package com.zeuschan.littlefreshweather.domain.usecase;

import android.util.Log;

import com.zeuschan.littlefreshweather.model.entity.WeatherEntity;

/**
 * Created by chenxiong on 2016/6/2.
 */
public class GetCityWeatherUseCaseDelegate extends TestSubscriberDelegate<WeatherEntity> {
    @Override
    protected void LogContent(WeatherEntity weatherEntity) {
        Log.e(TAG, weatherEntity.toString());
    }
}
