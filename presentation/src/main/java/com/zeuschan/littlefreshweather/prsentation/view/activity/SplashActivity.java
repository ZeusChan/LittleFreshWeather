package com.zeuschan.littlefreshweather.prsentation.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.zeuschan.littlefreshweather.prsentation.R;
import com.zeuschan.littlefreshweather.prsentation.presenter.SplashPresenter;
import com.zeuschan.littlefreshweather.prsentation.view.SplashView;


public class SplashActivity extends BaseActivity implements SplashView {
    public static final String TAG = SplashActivity.class.getSimpleName();
    public static final int MSG_START = 0;
    public static final int MSG_NAVIGATE_CITY_WEATHER = 1;
    public static final int MSG_NAVIGATE_CITIES = 2;

    SplashPresenter mPresenter;
    UIHandler mHandler = new UIHandler();
    String mCityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mPresenter = new SplashPresenter();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        Message message = mHandler.obtainMessage(MSG_START);
        message.sendToTarget();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.stop();
    }

    @Override
    public void navigateToCityWeatherActivity(String cityId) {
        mCityId = cityId;

        Message message = mHandler.obtainMessage(MSG_NAVIGATE_CITY_WEATHER);
        message.sendToTarget();
    }

    @Override
    public void navigateToCitiesActivity(String cityId) {
        Message message = mHandler.obtainMessage(MSG_NAVIGATE_CITIES);
        message.sendToTarget();
    }

    private final class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case MSG_START: {
                    mPresenter.attachView(SplashActivity.this);
                    mPresenter.start();
                } break;
                case MSG_NAVIGATE_CITY_WEATHER: {
                    Intent intent = new Intent(SplashActivity.this, CityWeatherActivity.class);
                    intent.putExtra(CityWeatherActivity.CITY_ID, mCityId);
                    SplashActivity.this.startActivity(intent);
                    SplashActivity.this.finish();
                } break;
                case MSG_NAVIGATE_CITIES: {
                    Intent intent = new Intent(SplashActivity.this, CitiesActivity.class);
                    SplashActivity.this.startActivity(intent);
                    SplashActivity.this.finish();
                } break;
            }

            super.handleMessage(msg);
        }
    }
}
