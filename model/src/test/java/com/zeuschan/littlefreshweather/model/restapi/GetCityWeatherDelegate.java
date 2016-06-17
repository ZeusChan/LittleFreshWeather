package com.zeuschan.littlefreshweather.model.restapi;

import com.zeuschan.littlefreshweather.model.entity.WeatherEntity;

/**
 * Created by chenxiong on 2016/6/2.
 */
public class GetCityWeatherDelegate extends TestSubscriberDelegate<WeatherEntity> {
    @Override
    protected void LogContent(WeatherEntity weatherEntity) {
        logger.info(weatherEntity.toString());
    }
}
