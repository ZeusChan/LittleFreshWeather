package com.zeuschan.littlefreshweather.prsentation.presenter;


import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.zeuschan.littlefreshweather.domain.usecase.GetBitmapUseCase;
import com.zeuschan.littlefreshweather.domain.usecase.GetCitiesUseCase;
import com.zeuschan.littlefreshweather.domain.wrapper.BitmapCacheWrapper;
import com.zeuschan.littlefreshweather.model.entity.CityEntity;
import com.zeuschan.littlefreshweather.prsentation.R;
import com.zeuschan.littlefreshweather.prsentation.view.CitiesView;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by chenxiong on 2016/6/21.
 */
public class CitiesPresenter implements Presenter {
    private CitiesView mView;
    private GetCitiesUseCase mUseCase;
    private GetBitmapUseCase mBitmapUseCase;
    private List<CityEntity> mCities;
    private List<CityEntity> mCandidates = new ArrayList<>();
    private String mLocatedCityId;

    public CitiesPresenter(CitiesView view) {
        mView = view;
        mUseCase = new GetCitiesUseCase(mView.getContext().getApplicationContext());
        mBitmapUseCase = new GetBitmapUseCase(mView.getContext().getApplicationContext());
    }

    @Override
    public void start() {
        loadData();
    }

    @Override
    public void stop() {
        mUseCase.unsubscribe();
        mBitmapUseCase.unsubscribe();
        if (mCities != null) {
            mCities.clear();
            mCities = null;
        }
        mCandidates.clear();
        mCandidates = null;
        mUseCase = null;
        mBitmapUseCase = null;
        mView = null;
    }

    public void getBackgroundImage(View view, int resId) {
        mBitmapUseCase.setResourceId(resId);
        mBitmapUseCase.execute(new BitmapSubscriber(view, resId));
    }

    public void loadData() {
        mView.hideLocatedCityName();
        mView.hideCityNameEdit();
        mView.hideRetry();
        mView.showLoading();
        mUseCase.execute(new CitiesSubscriber());
    }

    public void setLocatedCityId(String locatedId) {
        mLocatedCityId = locatedId;
    }

    public void getCandidates(String keyWord) {
        if (mCities != null) {
            mCandidates.clear();
            for (CityEntity entity : mCities) {
                if (entity.getCity().contains(keyWord) || entity.getProvince().contains(keyWord)) {
                    mCandidates.add(entity);
                }
            }
            mView.refreshCandidatesList(mCandidates);
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

    private final class CitiesSubscriber extends Subscriber<List<CityEntity>> {
        @Override
        public void onCompleted() {
            mView.hideLoading();
            mView.showCityNameEdit();
            String cityName = getCityName(mLocatedCityId);
            if (!TextUtils.isEmpty(cityName)) {
                mView.setLocatedCityName(mView.getContext().getString(R.string.located_city) + cityName);
                mView.showLocatedCityName();
            } else {
                mView.hideLocatedCityName();
            }
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            mView.hideLoading();
            mView.showRetry();
        }

        @Override
        public void onNext(List<CityEntity> cityEntities) {
            mCities = cityEntities;
        }

        private String getCityName(String cityId) {
            if (cityId != null) {
                for (CityEntity entity : mCities) {
                    if (cityId.equalsIgnoreCase(entity.getCityId())) {
                        return entity.getProvince() + " - " + entity.getCity();
                    }
                }
            }

            return null;
        }
    }
}
