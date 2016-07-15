package com.zeuschan.littlefreshweather.prsentation.presenter;


import android.graphics.drawable.BitmapDrawable;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;

import com.zeuschan.littlefreshweather.domain.usecase.GetBitmapUseCase;
import com.zeuschan.littlefreshweather.domain.usecase.GetCityWeatherUseCase;
import com.zeuschan.littlefreshweather.domain.wrapper.BitmapCacheWrapper;
import com.zeuschan.littlefreshweather.model.entity.WeatherEntity;
import com.zeuschan.littlefreshweather.prsentation.R;
import com.zeuschan.littlefreshweather.prsentation.view.CityWeatherView;

import rx.Subscriber;

/**
 * Created by chenxiong on 2016/6/12.
 */
public class CityWeatherPresenter implements Presenter {
    private static final String TAG = CityWeatherPresenter.class.getSimpleName();

    private CityWeatherView mView;
    private GetCityWeatherUseCase mUseCase;
    private SparseArray<GetBitmapUseCase> mBitmapUsecases = new SparseArray<>();
    private String mCityId;

    public void attachView(CityWeatherView view, String cityId) {
        mView = view;
        mCityId = cityId;
        mUseCase = new GetCityWeatherUseCase(mView.getContext().getApplicationContext(), mCityId, false);
    }

    public void setCityId(String cityId) {
        mCityId = cityId;
        mView.updateUpdateService(mCityId);
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
        }

        for (int index = 0; index != mBitmapUsecases.size(); ++index) {
            GetBitmapUseCase bitmapUseCase = mBitmapUsecases.valueAt(index);
            if (bitmapUseCase != null) {
                bitmapUseCase.unsubscribe();
            }
        }
    }

    @Override
    public void destroy() {
        mUseCase.clear();
        mUseCase = null;
        for (int index = 0; index != mBitmapUsecases.size(); ++index) {
            GetBitmapUseCase bitmapUseCase = mBitmapUsecases.valueAt(index);
            if (bitmapUseCase != null) {
                bitmapUseCase.clear();
            }
        }
        mBitmapUsecases.clear();
        mBitmapUsecases = null;
        mView = null;
    }

    public void loadData() {
        if (mView != null) {
            mView.hideRetry();
            mView.showLoading();
            mView.showRefreshing();
        }
        if (mUseCase != null) {
            mUseCase.execute(new CityWeatherSubscriber());
        }
    }

    public void getBackgroundImage(View view, int resId) {
        GetBitmapUseCase bitmapUseCase = mBitmapUsecases.get(resId);
        if (bitmapUseCase == null) {
            mBitmapUsecases.put(resId, new GetBitmapUseCase(mView.getContext().getApplicationContext(), resId));
        }
        bitmapUseCase = mBitmapUsecases.get(resId);
        if (bitmapUseCase != null) {
            bitmapUseCase.execute(new BitmapSubscriber(view, BitmapSubscriber.VIEW_TYPE_VIEW));
        }
    }

    public void getImageViewSrc(View view, int resId) {
        GetBitmapUseCase bitmapUseCase = mBitmapUsecases.get(resId);
        if (bitmapUseCase == null) {
            mBitmapUsecases.put(resId, new GetBitmapUseCase(mView.getContext().getApplicationContext(), resId));
        }
        bitmapUseCase = mBitmapUsecases.get(resId);
        if (bitmapUseCase != null) {
            bitmapUseCase.execute(new BitmapSubscriber(view, BitmapSubscriber.VIEW_TYPE_IMAGEVIEW));
        }
    }

    private final class BitmapSubscriber extends Subscriber<BitmapCacheWrapper> {
        public static final int VIEW_TYPE_VIEW = 0;
        public static final int VIEW_TYPE_IMAGEVIEW = 1;

        private View view;
        private int viewType;

        public BitmapSubscriber(View view, int viewType) {
            this.view = view;
            this.viewType = viewType;
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
                if (viewType == VIEW_TYPE_VIEW) {
                    view.setBackground(new BitmapDrawable(view.getContext().getResources(), bitmapCacheWrapper.getBitmap()));
                } else if (viewType == VIEW_TYPE_IMAGEVIEW) {
                    ((ImageView)view).setImageDrawable(new BitmapDrawable(view.getContext().getResources(), bitmapCacheWrapper.getBitmap()));
                }
            }
        }
    }

    private final class CityWeatherSubscriber extends Subscriber<WeatherEntity> {
        @Override
        public void onCompleted() {
            if (mView != null) {
                CityWeatherPresenter.this.mView.hideRefreshing();
                CityWeatherPresenter.this.mView.hideLoading();
                CityWeatherPresenter.this.mView.showError(mView.getContext().getApplicationContext().getString(R.string.weather_updated));
            }
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            if (mView != null) {
                CityWeatherPresenter.this.mView.hideRefreshing();
                CityWeatherPresenter.this.mView.hideLoading();
                CityWeatherPresenter.this.mView.showRetry();
                CityWeatherPresenter.this.mView.showError(mView.getContext().getApplicationContext().getString(R.string.weather_update_failed));
            }
        }

        @Override
        public void onNext(WeatherEntity weatherEntity) {
            if (mView != null) {
                CityWeatherPresenter.this.mView.setToolbarCity(weatherEntity.getCityName());
                CityWeatherPresenter.this.mView.showContent();
                CityWeatherPresenter.this.mView.renderCityWeather(weatherEntity);
                CityWeatherPresenter.this.mView.updateNotification(weatherEntity);
            }
        }
    }
}
