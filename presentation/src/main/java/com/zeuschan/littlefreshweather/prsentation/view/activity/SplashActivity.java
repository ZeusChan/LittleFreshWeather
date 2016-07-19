package com.zeuschan.littlefreshweather.prsentation.view.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.CycleInterpolator;
import android.widget.ImageView;

import com.zeuschan.littlefreshweather.prsentation.R;
import com.zeuschan.littlefreshweather.prsentation.presenter.SplashPresenter;
import com.zeuschan.littlefreshweather.prsentation.view.SplashView;

//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.Unbinder;


public class SplashActivity extends BaseActivity implements SplashView {
    public static final String TAG = SplashActivity.class.getSimpleName();
    public static final int MSG_START = 0;
    public static final int MSG_NAVIGATE_CITY_WEATHER = 1;
    public static final int MSG_NAVIGATE_CITIES = 2;

    private static final int FIRE_DELAY = 1000;

    private SplashPresenter mPresenter;
    private UIHandler mHandler = new UIHandler();
    private String mCityId;
    private String mLocateCityId;
    private boolean mIsLocateSucceeded = false;
    //Unbinder mUnbinder;

    //@BindView(R.id.iv_splash_icon) ImageView ivSplashIcon;
    private ImageView ivSplashIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_splash);
        //mUnbinder = ButterKnife.bind(this);

        ivSplashIcon = (ImageView)findViewById(R.id.iv_splash_icon);

        mPresenter = new SplashPresenter();
    }

    @Override
    protected void uninitView() {

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mHandler.postDelayed(delayStartProc, FIRE_DELAY);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.stop();
        stopAnimation();
    }

    @Override
    protected void clearMemory() {
        mPresenter.destroy();
        mHandler.removeMessages(MSG_START);
        mHandler.removeMessages(MSG_NAVIGATE_CITY_WEATHER);
        mHandler.removeMessages(MSG_NAVIGATE_CITIES);
        mHandler.removeCallbacks(delayStartProc);
        uninitView();
        //mUnbinder.unbind();
        //setContentView(new FrameLayout(this));
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void navigateToCityWeatherActivity(String cityId) {
        mCityId = cityId;
        Message message = mHandler.obtainMessage(MSG_NAVIGATE_CITY_WEATHER);
        message.sendToTarget();
    }

    @Override
    public void navigateToCitiesActivity(String locCityId, boolean locateSucceeded, String cityId) {
        mCityId = cityId;
        mLocateCityId = locCityId;
        mIsLocateSucceeded = locateSucceeded;
        Message message = mHandler.obtainMessage(MSG_NAVIGATE_CITIES);
        message.sendToTarget();
    }

    private final class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case MSG_START: {
                    startAnimation();
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
                    intent.putExtra(CitiesActivity.LOC_CITY_ID, mLocateCityId);
                    intent.putExtra(CitiesActivity.CITY_ID, mCityId);
                    intent.putExtra(CitiesActivity.LOCATE_RESULT, mIsLocateSucceeded);
                    SplashActivity.this.startActivity(intent);
                    SplashActivity.this.finish();
                } break;
            }

            super.handleMessage(msg);
        }
    }

    private Runnable delayStartProc = new Runnable() {
        @Override
        public void run() {
            Message message = mHandler.obtainMessage(MSG_START);
            message.sendToTarget();
        }
    };

    private void startAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(ivSplashIcon, "translationY", 0, -(ivSplashIcon.getHeight() >> 1));
        animator.setDuration(800);
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.setRepeatMode(ObjectAnimator.RESTART);
        animator.setInterpolator(new CycleInterpolator(0.5f));
        animator.start();
    }

    private void stopAnimation() {
        ivSplashIcon.clearAnimation();
    }
}
