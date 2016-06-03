package com.zeuschan.littlefreshweather.prsentation.views.activity;

import android.content.Context;
import android.os.Bundle;

import com.zeuschan.littlefreshweather.model.entities.WeatherEntity;
import com.zeuschan.littlefreshweather.prsentation.R;
import com.zeuschan.littlefreshweather.prsentation.views.CityWeatherView;

import butterknife.ButterKnife;

public class CityWeatherActivity extends BaseActivity implements CityWeatherView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);
        ButterKnife.bind(this);
    }

    @Override
    public void renderCityWeather(WeatherEntity entity) {

    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void hideError() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }
}
