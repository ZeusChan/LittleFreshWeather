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
    private boolean mFromCache;

    public GetCityWeatherUseCase(Context context, String cityId, boolean fromCache) {
        mContext = context;
        mCityId = cityId;
        mFromCache = fromCache;
    }

    @Override
    public void clear() {
        DataSourceManager.getInstance(mContext).clear();
        mContext = null;
        mCityId = null;
    }

    public void setCityId(String cityId) {
        mCityId = cityId;
    }

    @Override
    protected Observable<WeatherEntity> buildUseCaseObservable() {
        return DataSourceManager.getInstance(mContext).getCityWeather(mCityId, mFromCache);
    }
}
