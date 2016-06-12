package com.zeuschan.littlefreshweather.prsentation.presenters;

import com.zeuschan.littlefreshweather.domain.usecases.GetCityWeatherUseCase;
import com.zeuschan.littlefreshweather.model.entities.WeatherEntity;
import com.zeuschan.littlefreshweather.prsentation.views.CityWeatherView;

import rx.Subscriber;

/**
 * Created by chenxiong on 2016/6/12.
 */
public class CityWeatherPresenter implements Presenter {
    private CityWeatherView mView;
    private GetCityWeatherUseCase mUseCase;

    public void attachView(CityWeatherView view) {
        mView = view;
        mUseCase = new GetCityWeatherUseCase("CN101010300");
    }

    @Override
    public void start() {
        mView.showLoading();
        mUseCase.execute(new CityWeatherSubscriber());
    }

    @Override
    public void stop() {
        mUseCase.unsubscribe();
    }

    private final class CityWeatherSubscriber extends Subscriber<WeatherEntity> {
        @Override
        public void onCompleted() {
            CityWeatherPresenter.this.mView.hideLoading();
        }

        @Override
        public void onError(Throwable e) {
            CityWeatherPresenter.this.mView.hideLoading();
            CityWeatherPresenter.this.mView.showRetry();
        }

        @Override
        public void onNext(WeatherEntity weatherEntity) {
            CityWeatherPresenter.this.mView.renderCityWeather(weatherEntity);
        }
    }
}
