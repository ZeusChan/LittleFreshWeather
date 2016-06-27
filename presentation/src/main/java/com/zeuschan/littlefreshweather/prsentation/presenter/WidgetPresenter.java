package com.zeuschan.littlefreshweather.prsentation.presenter;


import android.content.Context;

import com.zeuschan.littlefreshweather.domain.usecase.GetCityWeatherUseCase;
import com.zeuschan.littlefreshweather.model.entity.WeatherEntity;

import rx.Subscriber;

/**
 * Created by chenxiong on 2016/6/27.
 */
public class WidgetPresenter implements Presenter {
    GetCityWeatherUseCase mUseCase;
    String mCityId;

    public WidgetPresenter(Context context, String cityId) {
        mCityId = cityId;
        mUseCase = new GetCityWeatherUseCase(context, mCityId, true);
    }

    public void setCityId(String cityId) {
        mCityId = cityId;
        mUseCase.setCityId(mCityId);
    }

    @Override
    public void start() {
        mUseCase.execute(new CityWeatherSubscriber());
    }

    @Override
    public void stop() {
        mUseCase.unsubscribe();
    }

    private final class CityWeatherSubscriber extends Subscriber<WeatherEntity> {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
        }

        @Override
        public void onNext(WeatherEntity weatherEntity) {

        }
    }
}
