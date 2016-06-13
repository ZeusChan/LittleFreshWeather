package com.zeuschan.littlefreshweather.prsentation.views.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.zeuschan.littlefreshweather.model.entities.WeatherEntity;
import com.zeuschan.littlefreshweather.prsentation.R;
import com.zeuschan.littlefreshweather.prsentation.presenters.CityWeatherPresenter;
import com.zeuschan.littlefreshweather.prsentation.views.CityWeatherView;
import com.zeuschan.littlefreshweather.prsentation.views.adapter.CityWeatherAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CityWeatherActivity extends BaseActivity implements CityWeatherView, View.OnClickListener {

    public static final String TAG = CityWeatherActivity.class.getSimpleName();
    private CityWeatherPresenter mPresenter;
    private CityWeatherAdapter mCityWeatherAdapter;

    @BindView(R.id.rl_loading_progress) RelativeLayout rlLoadingProgress;
    @BindView(R.id.rl_failed_retry) RelativeLayout rlFailedRetry;
    @BindView(R.id.rv_city_weather) RecyclerView rvCityWeather;
    @BindView(R.id.bt_failed_retry) Button btFailedRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);
        ButterKnife.bind(this);

        mPresenter = new CityWeatherPresenter();
        mPresenter.attachView(this);

        rvCityWeather.setLayoutManager(new LinearLayoutManager(this));
        mCityWeatherAdapter = new CityWeatherAdapter();
        rvCityWeather.setAdapter(mCityWeatherAdapter);

        btFailedRetry.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    public void renderCityWeather(WeatherEntity entity) {
        if (entity != null) {
            mCityWeatherAdapter.setWeatherEntity(entity);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showLoading() {
        rlLoadingProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        rlLoadingProgress.setVisibility(View.GONE);
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void hideError() {

    }

    @Override
    public void showRetry() {
        rlFailedRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        rlFailedRetry.setVisibility(View.GONE);
    }

    @Override
    public void showContent() {
        rvCityWeather.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideContent() {
        rvCityWeather.setVisibility(View.GONE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.stop();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.bt_failed_retry: {
                mPresenter.loadData();
            } break;
        }
    }
}
