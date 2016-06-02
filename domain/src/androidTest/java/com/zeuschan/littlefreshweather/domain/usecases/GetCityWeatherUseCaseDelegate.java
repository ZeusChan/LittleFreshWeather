package com.zeuschan.littlefreshweather.domain.usecases;

import android.util.Log;

import com.zeuschan.littlefreshweather.model.entities.WeatherEntity;

/**
 * Created by chenxiong on 2016/6/2.
 */
public class GetCityWeatherUseCaseDelegate extends TestSubscriberDelegate<WeatherEntity> {
    @Override
    protected void LogContent(WeatherEntity weatherEntity) {
        Log.e(TAG, weatherEntity.toString());
    }
}
