package com.zeuschan.littlefreshweather.prsentation.views;

import com.zeuschan.littlefreshweather.model.entities.WeatherEntity;

/**
 * Created by chenxiong on 2016/6/3.
 */
public interface CityWeatherView extends BaseView {
    /**
     * Render a WeatherEntity in the UI.
     */
    void renderCityWeather(WeatherEntity entity);
}
