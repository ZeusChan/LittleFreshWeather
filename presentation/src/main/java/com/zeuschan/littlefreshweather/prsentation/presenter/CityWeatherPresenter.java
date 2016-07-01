package com.zeuschan.littlefreshweather.prsentation.presenter;


import android.graphics.drawable.BitmapDrawable;
import android.view.View;

import com.zeuschan.littlefreshweather.domain.usecase.GetBitmapUseCase;
import com.zeuschan.littlefreshweather.domain.usecase.GetCityWeatherUseCase;
import com.zeuschan.littlefreshweather.domain.wrapper.BitmapCacheWrapper;
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
    private GetBitmapUseCase mBitmapUseCase;
    private String mCityId;

    public void attachView(CityWeatherView view, String cityId) {
        mView = view;
        mCityId = cityId;
        mUseCase = new GetCityWeatherUseCase(mView.getContext().getApplicationContext(), mCityId, false);
        mBitmapUseCase = new GetBitmapUseCase(mView.getContext().getApplicationContext());
    }

    public void setCityId(String cityId) {
        mCityId = cityId;
        if (mUseCase != null) {
            mUseCase.setCityId(mCityId);
        }
    }

    @Override
    public void start() {
        loadData();
    }

    @Override
    public void stop() {
        if (mUseCase != null) {
            mUseCase.unsubscribe();
            mUseCase = null;
        }
        if (mBitmapUseCase != null) {
            mBitmapUseCase.unsubscribe();
            mBitmapUseCase = null;
        }
        mView = null;
    }

    public void getBackgroundImage(View view, int resId) {
        mBitmapUseCase.setResourceId(resId);
        mBitmapUseCase.execute(new BitmapSubscriber(view, resId));
    }

    public void loadData() {
        if (mView != null) {
            mView.hideRetry();
            mView.showLoading();
        }
        if (mUseCase != null) {
            mUseCase.execute(new CityWeatherSubscriber());
        }
    }

    private final class BitmapSubscriber extends Subscriber<BitmapCacheWrapper> {
        View view;
        int resId;

        public BitmapSubscriber(View view, int resId) {
            this.view = view;
            this.resId = resId;
        }

        @Override
        public void onCompleted() {
            view = null;
        }

        @Override
        public void onError(Throwable e) {
            view = null;
        }

        @Override
        public void onNext(BitmapCacheWrapper bitmapCacheWrapper) {
            if (view != null) {
                view.setBackground(new BitmapDrawable(view.getContext().getResources(), bitmapCacheWrapper.getBitmap()));
            }
        }
    }

    private final class CityWeatherSubscriber extends Subscriber<WeatherEntity> {
        @Override
        public void onCompleted() {
            if (mView != null) {
                CityWeatherPresenter.this.mView.hideLoading();
            }
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            if (mView != null) {
                CityWeatherPresenter.this.mView.hideLoading();
                CityWeatherPresenter.this.mView.showRetry();
            }
        }

        @Override
        public void onNext(WeatherEntity weatherEntity) {
            if (mView != null) {
                CityWeatherPresenter.this.mView.setToolbarCity(weatherEntity.getCityName());
                CityWeatherPresenter.this.mView.showContent();
                CityWeatherPresenter.this.mView.renderCityWeather(weatherEntity);
            }
        }
    }
}
