package com.zeuschan.littlefreshweather.prsentation.presenter;


import com.zeuschan.littlefreshweather.domain.usecase.GetCitiesUseCase;
import com.zeuschan.littlefreshweather.model.entity.CityEntity;
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
    private List<CityEntity> mCities;
    private List<CityEntity> mCandidates = new ArrayList<>();

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
    }

    public void loadData() {
        mView.hideCityNameEdit();
        mView.hideRetry();
        mView.showLoading();
        mUseCase.execute(new CitiesSubscriber());
    }

    public void getCandidates(String keyWord) {
        if (mCities != null) {
            mCandidates.clear();
            for (CityEntity entity :
                    mCities) {
                if (entity.getCity().contains(keyWord) || entity.getProvince().contains(keyWord)) {
                    mCandidates.add(entity);
                }
            }
            mView.refreshCandidatesList(mCandidates);
        }
    }

    private final class CitiesSubscriber extends Subscriber<List<CityEntity>> {
        @Override
        public void onCompleted() {
            mView.hideLoading();
            mView.showCityNameEdit();
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
    }
}
