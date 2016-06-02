package com.zeuschan.littlefreshweather.domain.usecases;

import com.zeuschan.littlefreshweather.model.entities.CityEntity;
import com.zeuschan.littlefreshweather.model.restapi.ServicesManager;

import java.util.List;

import rx.Observable;

/**
 * Created by chenxiong on 2016/6/2.
 */
public class GetCitysUseCase extends UseCase<List<CityEntity>> {
    @Override
    protected Observable<List<CityEntity>> buildUseCaseObservable() {
        return ServicesManager.getInstance().getCitys();
    }
}
