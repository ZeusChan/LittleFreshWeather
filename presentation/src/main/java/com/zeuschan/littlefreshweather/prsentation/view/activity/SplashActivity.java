package com.zeuschan.littlefreshweather.prsentation.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.zeuschan.littlefreshweather.prsentation.R;
import com.zeuschan.littlefreshweather.prsentation.presenter.SplashPresenter;
import com.zeuschan.littlefreshweather.prsentation.view.SplashView;


public class SplashActivity extends BaseActivity implements SplashView {
    public static final String TAG = SplashActivity.class.getSimpleName();

    SplashPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mPresenter = new SplashPresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.stop();
    }

    @Override
    public void navigateToCityWeatherActivity(String cityId) {
        Intent intent = new Intent(this, CityWeatherActivity.class);
        intent.putExtra(CityWeatherActivity.CITY_ID, cityId);
        startActivity(intent);
        finish();
    }

    @Override
    public void navigateToCitiesActivity(String cityId) {
        Intent intent = new Intent(this, CitiesActivity.class);
        startActivity(intent);
        finish();
    }
}
