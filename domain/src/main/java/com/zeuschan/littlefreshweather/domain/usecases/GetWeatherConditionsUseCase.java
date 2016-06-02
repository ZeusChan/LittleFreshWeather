package com.zeuschan.littlefreshweather.domain.usecases;

import com.zeuschan.littlefreshweather.model.entities.WeatherConditionEntity;
import com.zeuschan.littlefreshweather.model.restapi.ServicesManager;

import java.util.List;

import rx.Observable;

/**
 * Created by chenxiong on 2016/6/2.
 */
public class GetWeatherConditionsUseCase extends UseCase<List<WeatherConditionEntity>> {
    @Override
    protected Observable<List<WeatherConditionEntity>> buildUseCaseObservable() {
        return ServicesManager.getInstance().getWeatherConditions();
    }
}
