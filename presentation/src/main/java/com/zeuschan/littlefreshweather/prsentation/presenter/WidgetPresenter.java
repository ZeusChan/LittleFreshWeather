package com.zeuschan.littlefreshweather.prsentation.presenter;


import android.content.Context;
import android.telecom.Call;

import com.zeuschan.littlefreshweather.domain.usecase.GetCityWeatherUseCase;
import com.zeuschan.littlefreshweather.model.entity.WeatherEntity;

import rx.Subscriber;

/**
 * Created by chenxiong on 2016/6/27.
 */
public class WidgetPresenter implements Presenter {
    private GetCityWeatherUseCase mUseCase;
    private String mCityId;
    private DataCallback mCallback;

    public interface DataCallback {
        void renderData(WeatherEntity entity);
    }

    public WidgetPresenter(Context context, String cityId, DataCallback callback) {
        mCityId = cityId;
        mUseCase = new GetCityWeatherUseCase(context, mCityId, true);
        mCallback = callback;
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

    @Override
    public void destroy() {
        mUseCase.clear();
    }

    private final class CityWeatherSubscriber extends Subscriber<WeatherEntity> {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            if (mCallback != null) {
                mCallback.renderData(null);
            }
        }

        @Override
        public void onNext(WeatherEntity weatherEntity) {
            if (mCallback != null) {
                mCallback.renderData(weatherEntity);
            }
        }
    }
}
