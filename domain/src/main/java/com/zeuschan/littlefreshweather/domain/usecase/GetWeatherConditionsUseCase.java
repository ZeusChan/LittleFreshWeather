package com.zeuschan.littlefreshweather.domain.usecase;

import android.content.Context;

import com.zeuschan.littlefreshweather.model.datasource.DataSourceManager;
import com.zeuschan.littlefreshweather.model.entity.WeatherConditionEntity;
import com.zeuschan.littlefreshweather.model.restapi.ServicesManager;

import java.util.List;

import rx.Observable;

/**
 * Created by chenxiong on 2016/6/2.
 */
public class GetWeatherConditionsUseCase extends UseCase<List<WeatherConditionEntity>> {
    private Context mContext;

    public GetWeatherConditionsUseCase(Context context) {
        mContext = context;
    }

    @Override
    public void clear() {
        DataSourceManager.getInstance(mContext).clear();
        mContext = null;
    }

    @Override
    protected Observable<List<WeatherConditionEntity>> buildUseCaseObservable() {
        return DataSourceManager.getInstance(mContext).getWeatherConditionEntities();
    }
}
