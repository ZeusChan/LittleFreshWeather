package com.zeuschan.littlefreshweather.model.datasource;

import com.zeuschan.littlefreshweather.model.entity.CityEntity;
import com.zeuschan.littlefreshweather.model.entity.WeatherConditionEntity;
import com.zeuschan.littlefreshweather.model.entity.WeatherEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by chenxiong on 2016/6/17.
 */
public interface DataSource {
    public Observable<List<CityEntity>> getCityEntities();
    public Observable<List<WeatherConditionEntity>> getWeatherConditionEntities();
    public Observable<WeatherEntity> getCityWeather(String cityId, boolean fromCache);
    public void clear();
}
