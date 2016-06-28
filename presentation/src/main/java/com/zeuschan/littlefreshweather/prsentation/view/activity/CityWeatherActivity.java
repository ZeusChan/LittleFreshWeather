package com.zeuschan.littlefreshweather.prsentation.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zeuschan.littlefreshweather.common.util.Constants;
import com.zeuschan.littlefreshweather.common.util.FileUtil;
import com.zeuschan.littlefreshweather.common.util.NetUtil;
import com.zeuschan.littlefreshweather.model.entity.WeatherEntity;
import com.zeuschan.littlefreshweather.prsentation.R;
import com.zeuschan.littlefreshweather.prsentation.presenter.CityWeatherPresenter;
import com.zeuschan.littlefreshweather.prsentation.service.WeatherNotificationService;
import com.zeuschan.littlefreshweather.prsentation.service.WeatherUpdateService;
import com.zeuschan.littlefreshweather.prsentation.view.CityWeatherView;
import com.zeuschan.littlefreshweather.prsentation.view.adapter.CityWeatherAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CityWeatherActivity extends BaseActivity implements CityWeatherView, View.OnClickListener {
    public static final String TAG = CityWeatherActivity.class.getSimpleName();
    public static final String CITY_ID = "city_id";
    public static final String WEATHER_UPDATE_ACTION = "com.zeuschan.littlefreshweather.prsentation.WEATHER_UPDATE";

    public static final int MSG_WEATHER_UPDATE = 0;

    private CityWeatherPresenter mPresenter;
    private CityWeatherAdapter mCityWeatherAdapter;
    private LocalBroadcastManager mLocalBroadcastManager;
    private WeatherUpdateReceiver mWeatherUpdateReceiver;
    private UIHandler mHandler = new UIHandler();

    @BindView(R.id.rl_loading_progress) RelativeLayout rlLoadingProgress;
    @BindView(R.id.rl_failed_retry) RelativeLayout rlFailedRetry;
    @BindView(R.id.rv_city_weather) RecyclerView rvCityWeather;
    @BindView(R.id.bt_failed_retry) Button btFailedRetry;
    @BindView(R.id.tv_city_weather_toolbar_title) TextView tvToolbarTitle;
    @BindView(R.id.ib_city_weather_toolbar_cities) ImageButton ibToolbarCities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String cityId = intent.getStringExtra(CITY_ID);
        FileUtil.putStringToPreferences(getApplicationContext(), Constants.GLOBAL_SETTINGS, Constants.PRF_KEY_CITY_ID, cityId);

        mPresenter = new CityWeatherPresenter();
        mPresenter.attachView(this, cityId);

        rvCityWeather.setLayoutManager(new LinearLayoutManager(this));
        mCityWeatherAdapter = new CityWeatherAdapter(this);
        rvCityWeather.setAdapter(mCityWeatherAdapter);

        btFailedRetry.setOnClickListener(this);
        ibToolbarCities.setOnClickListener(this);

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mWeatherUpdateReceiver = new WeatherUpdateReceiver();

        startServices();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        String cityId = intent.getStringExtra(CITY_ID);
        FileUtil.putStringToPreferences(getApplicationContext(), Constants.GLOBAL_SETTINGS, Constants.PRF_KEY_CITY_ID, cityId);
        mPresenter.setCityId(cityId);
        mPresenter.stop();
        mPresenter.start();

        startServices();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocalBroadcastManager.registerReceiver(mWeatherUpdateReceiver, new IntentFilter(WEATHER_UPDATE_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocalBroadcastManager.unregisterReceiver(mWeatherUpdateReceiver);
    }

    @Override
    public void renderCityWeather(WeatherEntity entity) {
        if (entity != null) {
            mCityWeatherAdapter.setWeatherEntity(entity);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.stop();
    }

    @Override
    public void setToolbarCity(String cityName) {
        tvToolbarTitle.setText(cityName);
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
    public void navigateToCitiesActivity() {
        Intent intent = new Intent(this, CitiesActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.bt_failed_retry: {
                mPresenter.loadData();
            } break;
            case R.id.ib_city_weather_toolbar_cities: {
                navigateToCitiesActivity();
            } break;
        }
    }

    private final class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_WEATHER_UPDATE) {
                if (!NetUtil.isNetworkAvailable(getApplication()))
                    return;

                showError(getString(R.string.weather_updated));
                renderCityWeather((WeatherEntity)msg.getData().getParcelable(WeatherUpdateReceiver.WEATHER_ENTITY));
                return;
            }
            super.handleMessage(msg);
        }
    }

    public final class WeatherUpdateReceiver extends BroadcastReceiver {
        public static final String WEATHER_ENTITY = "weather_entity";
        @Override
        public void onReceive(Context context, Intent intent) {
            Message message = mHandler.obtainMessage(MSG_WEATHER_UPDATE);
            Bundle bundle = new Bundle();
            bundle.putParcelable(WEATHER_ENTITY, intent.getParcelableExtra(WEATHER_ENTITY));
            message.setData(bundle);
            message.sendToTarget();
        }
    }

    private void startServices() {
        Intent intent1 = new Intent(this, WeatherUpdateService.class);
        startService(intent1);

        Intent intent2 = new Intent(this, WeatherNotificationService.class);
        startService(intent2);
    }
}
