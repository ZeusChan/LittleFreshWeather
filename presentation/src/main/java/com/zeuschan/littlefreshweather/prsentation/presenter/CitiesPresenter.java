package com.zeuschan.littlefreshweather.prsentation.presenter;


import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;

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
    private SparseArray<GetBitmapUseCase> mBitmapUsecases = new SparseArray<>();
    private List<CityEntity> mCities;
    private List<CityEntity> mCandidates = new ArrayList<>();
    private String mLocatedCityId;
    private String mCurCityId;

    public CitiesPresenter(CitiesView view) {
        mView = view;
        mUseCase = new GetCitiesUseCase(mView.getContext().getApplicationContext());
    }

    @Override
    public void start() {
        loadData();
    }

    @Override
    public void stop() {
        mUseCase.unsubscribe();
        for (int index = 0; index != mBitmapUsecases.size(); ++index) {
            GetBitmapUseCase bitmapUseCase = mBitmapUsecases.valueAt(index);
            if (bitmapUseCase != null) {
                bitmapUseCase.unsubscribe();
            }
        }
    }

    @Override
    public void destroy() {
        if (mCities != null) {
            mCities.clear();
        }
        mCandidates.clear();
        mUseCase.clear();
        mBitmapUsecases.clear();
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

    public void loadData() {
        mView.hideLocatedCityName();
        mView.hideCityNameEdit();
        mView.hideRetry();
        mView.showLoading();
        mUseCase.execute(new CitiesSubscriber());
    }

    public void setLocatedCityId(String locatedId, String curCityId) {
        mLocatedCityId = locatedId;
        mCurCityId = curCityId;
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
        public static final int VIEW_TYPE_VIEW = 0;
        public static final int VIEW_TYPE_IMAGEVIEW = 1;
        View view;
        int viewType;

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

    private final class CitiesSubscriber extends Subscriber<List<CityEntity>> {
        @Override
        public void onCompleted() {
            mView.hideLoading();
            mView.showCityNameEdit();
            String cityName = getCityName(mLocatedCityId);
            String curCityName = getCityName(mCurCityId);
            if (!TextUtils.isEmpty(cityName) && !TextUtils.isEmpty(curCityName)) {
                mView.setLocatedCityName(mView.getContext().getString(R.string.located_city) + cityName, mView.getContext().getString(R.string.cur_city) + curCityName);
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
                        return entity.getCity();
                    }
                }
            }

            return null;
        }
    }
}
