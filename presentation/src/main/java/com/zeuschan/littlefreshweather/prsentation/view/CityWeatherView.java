package com.zeuschan.littlefreshweather.prsentation.view;

import com.zeuschan.littlefreshweather.model.entity.WeatherEntity;

/**
 * Created by chenxiong on 2016/6/3.
 */
public interface CityWeatherView extends BaseView {
    /**
     * Render a WeatherEntity in the UI.
     */
    void renderCityWeather(WeatherEntity entity);
    void setToolbarCity(String cityName);
    void navigateToCitiesActivity();
    void navigateToSettingsActivity();
    void showRefreshing();
    void hideRefreshing();
    void updateNotification(WeatherEntity entity);
    void updateUpdateService(String cityId);
}
