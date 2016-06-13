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
        mUseCase = new GetCityWeatherUseCase("CN101010100");
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
            CityWeatherPresenter.this.mView.hideLoading();
            CityWeatherPresenter.this.mView.showRetry();
        }

        @Override
        public void onNext(WeatherEntity weatherEntity) {
            CityWeatherPresenter.this.mView.showContent();
            CityWeatherPresenter.this.mView.renderCityWeather(weatherEntity);
        }
    }
}
