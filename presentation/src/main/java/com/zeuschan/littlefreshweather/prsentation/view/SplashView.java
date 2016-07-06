package com.zeuschan.littlefreshweather.prsentation.view;


/**
 * Created by chenxiong on 2016/6/20.
 */
public interface SplashView extends BaseView {
    void navigateToCityWeatherActivity(String cityId);
    void navigateToCitiesActivity(String locCityId, boolean locateSucceeded, String cityId);
}
