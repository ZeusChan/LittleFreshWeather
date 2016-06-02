package com.zeuschan.littlefreshweather.domain.usecases;

import com.zeuschan.littlefreshweather.model.entities.WeatherEntity;
import com.zeuschan.littlefreshweather.model.restapi.ServicesManager;

import rx.Observable;

/**
 * Created by chenxiong on 2016/6/2.
 */
public class GetCityWeatherUseCase extends UseCase<WeatherEntity> {
    private String mCityId;

    public GetCityWeatherUseCase(String mCityId) {
        this.mCityId = mCityId;
    }

    @Override
    protected Observable<WeatherEntity> buildUseCaseObservable() {
        return ServicesManager.getInstance().getCityWeather(mCityId);
    }
}
