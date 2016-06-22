package com.zeuschan.littlefreshweather.domain.usecase;

import android.content.Context;

import com.zeuschan.littlefreshweather.model.datasource.DataSourceManager;
import com.zeuschan.littlefreshweather.model.entity.WeatherEntity;

import rx.Observable;

/**
 * Created by chenxiong on 2016/6/2.
 */
public class GetCityWeatherUseCase extends UseCase<WeatherEntity> {
    private Context mContext;
    private String mCityId;

    public GetCityWeatherUseCase(Context context, String mCityId) {
        mContext = context;
        this.mCityId = mCityId;
    }

    public void setCityId(String cityId) {
        mCityId = cityId;
    }

    @Override
    protected Observable<WeatherEntity> buildUseCaseObservable() {
        return DataSourceManager.getInstance(mContext).getCityWeather(mCityId);
    }
}
