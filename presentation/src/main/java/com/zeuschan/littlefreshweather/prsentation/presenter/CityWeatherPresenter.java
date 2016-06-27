package com.zeuschan.littlefreshweather.prsentation.presenter;


import com.zeuschan.littlefreshweather.domain.usecase.GetCityWeatherUseCase;
import com.zeuschan.littlefreshweather.model.entity.WeatherEntity;
import com.zeuschan.littlefreshweather.prsentation.view.CityWeatherView;

import rx.Subscriber;

/**
 * Created by chenxiong on 2016/6/12.
 */
public class CityWeatherPresenter implements Presenter {
    private static final String TAG = CityWeatherPresenter.class.getSimpleName();

    private CityWeatherView mView;
    private GetCityWeatherUseCase mUseCase;
    private String mCityId;

    public void attachView(CityWeatherView view, String cityId) {
        mView = view;
        mCityId = cityId;
        mUseCase = new GetCityWeatherUseCase(mView.getContext().getApplicationContext(), mCityId, false);
    }

    public void setCityId(String cityId) {
        mCityId = cityId;
        mUseCase.setCityId(mCityId);
    }

    @Override
    public void start() {
        loadData();
    }

    @Override
    public void stop() {
        mUseCase.unsubscribe();
    }

    public void loadData() {
        mView.hideRetry();
        mView.showLoading();
        mUseCase.execute(new CityWeatherSubscriber());
    }

    private final class CityWeatherSubscriber extends Subscriber<WeatherEntity> {
        @Override
        public void onCompleted() {
            CityWeatherPresenter.this.mView.hideLoading();
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            CityWeatherPresenter.this.mView.hideLoading();
            CityWeatherPresenter.this.mView.showRetry();
        }

        @Override
        public void onNext(WeatherEntity weatherEntity) {
            CityWeatherPresenter.this.mView.setToolbarCity(weatherEntity.getCityName());
            CityWeatherPresenter.this.mView.showContent();
            CityWeatherPresenter.this.mView.renderCityWeather(weatherEntity);
        }
    }
}
