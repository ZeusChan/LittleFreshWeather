package com.zeuschan.littlefreshweather.prsentation.view.activity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zeuschan.littlefreshweather.common.util.Constants;
import com.zeuschan.littlefreshweather.common.util.DensityUtil;
import com.zeuschan.littlefreshweather.common.util.FileUtil;
import com.zeuschan.littlefreshweather.common.util.NetUtil;
import com.zeuschan.littlefreshweather.model.entity.WeatherEntity;
import com.zeuschan.littlefreshweather.prsentation.R;
import com.zeuschan.littlefreshweather.prsentation.presenter.CityWeatherPresenter;
import com.zeuschan.littlefreshweather.prsentation.receiver.WeatherAppWidget;
import com.zeuschan.littlefreshweather.prsentation.service.WeatherNotificationService;
import com.zeuschan.littlefreshweather.prsentation.service.WeatherUpdateService;
import com.zeuschan.littlefreshweather.prsentation.view.CityWeatherView;
import com.zeuschan.littlefreshweather.prsentation.view.adapter.CityWeatherAdapter;

import java.util.Random;

//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.Unbinder;

public class CityWeatherActivity extends BaseActivity implements CityWeatherView
        , View.OnClickListener
        , SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = CityWeatherActivity.class.getSimpleName();
    public static final String CITY_ID = "city_id";
    public static final String WEATHER_UPDATE_ACTION = "com.zeuschan.littlefreshweather.prsentation.WEATHER_UPDATE";

    public static final int MSG_WEATHER_UPDATE = 0;

    private static final int RAIN_GEN_INTERVAL = 50;
    private static final int RAIN_NUM_H = 120;
    private static final int RAIN_NUM_M = 80;
    private static final int RAIN_NUM_L = 50;
    private static final int RAIN_SPEED_H = 500;
    private static final int RAIN_SPEED_M = 1000;
    private static final int RAIN_SPEED_L = 1500;
    private static final int RAIN_SPEED_OFFSET = 500;
    private int mRainIconId = R.drawable.raindrop_l;
    private int mSpecialWeatherNumRain = 0;
    private int mSpecialWeatherNumLimitRain;
    private int mSpecialWeatherSpeedLimitRain;

    private static final int SNOW_GEN_INTERVAL = 150;
    private static final int SNOW_SPEED_H = 4500;
    private static final int SNOW_SPEED_M = 4500;
    private static final int SNOW_SPEED_L = 4500;
    private int mSnowIconDarkId = R.drawable.snow_dark_l;
    private int mSnowIconLightId = R.drawable.snow_light_l;
    private int mSpecialWeatherNumSnow = 0;
    private int mSpecialWeatherNumLimitSnow;
    private int mSpecialWeatherSpeedLimitSnow;

    private static final int CLOUD_GEN_INTERVAL = 500;
    private static final int CLOUD_SPEED_H = 40000;
    private static final int CLOUD_SPEED_M = 60000;
    private static final int CLOUD_SPEED_L = 80000;
    private int mCloudIconFrontId = R.drawable.cloudy_day_2;
    private int mCloudIconBackId = R.drawable.cloudy_day_1;
    private int mSpecialWeatherNumCloud = 0;
    private int mSpecialWeatherNumLimitCloud;
    private int mSpecialWeatherSpeedLimitCloud;

    private static final int LIGHTNING_GEN_INTERVAL = 1000;
    private static final int LIGHTNING_SPEED_H = 300;
    private static final int LIGHTNING_SPEED_L = 1000;
    private int mSpecialWeatherNumLightning = 0;
    private int mSpecialWeatherNumLimitLightning;
    private int mSpecialWeatherSpeedLimitLightning;

    private static final int SUNSHINE_SPEED_H = 3000;

    private static final int ANIMATION_RAIN_L = 0x00000001;
    private static final int ANIMATION_RAIN_M = 0x00000002;
    private static final int ANIMATION_RAIN_H = 0x00000004;
    private static final int ANIMATION_SNOW_L = 0x00000008;
    private static final int ANIMATION_SNOW_M = 0x00000010;
    private static final int ANIMATION_SNOW_H = 0x00000020;
    private static final int ANIMATION_CLOUD_D = 0x00000040;
    private static final int ANIMATION_CLOUD_N = 0x00000080;
    private static final int ANIMATION_CLOUD_F = 0x00000100;
    private static final int ANIMATION_LIGHTNING = 0x00000200;
    private static final int ANIMATION_SUNSHINE = 0x00000400;

    private Random mRandom = new Random();
    private CityWeatherPresenter mPresenter;
    private CityWeatherAdapter mCityWeatherAdapter;
    //private LocalBroadcastManager mLocalBroadcastManager;
    private WeatherUpdateReceiver mWeatherUpdateReceiver;
    private UIHandler mHandler = new UIHandler();
    //private Unbinder mUnbinder = null;
    private boolean mBackPressed = false;
    private int mAnimationType = 0;
    private PopupWindow mPopupMenu;

//    @BindView(R.id.rl_loading_progress) RelativeLayout rlLoadingProgress;
//    @BindView(R.id.rl_failed_retry) RelativeLayout rlFailedRetry;
//    @BindView(R.id.srl_city_weather) SwipeRefreshLayout srlCityWeather;
//    @BindView(android.R.id.list) RecyclerView rvCityWeather;
//    @BindView(R.id.bt_failed_retry) Button btFailedRetry;
//    @BindView(R.id.tv_city_weather_toolbar_title) TextView tvToolbarTitle;
//    @BindView(R.id.ib_city_weather_toolbar_cities) ImageButton ibToolbarCities;
//    @BindView(R.id.ib_city_weather_toolbar_menu) ImageButton ibToolbarMenu;
//    @BindView(R.id.rl_city_weather_background_view) RelativeLayout rlBackgroundView;

    private RelativeLayout rlLoadingProgress;
    private RelativeLayout rlFailedRetry;
    private SwipeRefreshLayout srlCityWeather;
    private RecyclerView rvCityWeather;
    private Button btFailedRetry;
    private TextView tvToolbarTitle;
    private ImageButton ibToolbarCities;
    private ImageButton ibToolbarMenu;
    private RelativeLayout rlBackgroundView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        //mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mWeatherUpdateReceiver = new WeatherUpdateReceiver();
        startServices();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_city_weather);
        //mUnbinder = ButterKnife.bind(this);

        rlLoadingProgress = (RelativeLayout)findViewById(R.id.rl_loading_progress);
        rlFailedRetry = (RelativeLayout)findViewById(R.id.rl_failed_retry);
        srlCityWeather = (SwipeRefreshLayout)findViewById(R.id.srl_city_weather);
        rvCityWeather = (RecyclerView)findViewById(android.R.id.list);
        btFailedRetry = (Button)findViewById(R.id.bt_failed_retry);
        tvToolbarTitle = (TextView)findViewById(R.id.tv_city_weather_toolbar_title);
        ibToolbarCities = (ImageButton)findViewById(R.id.ib_city_weather_toolbar_cities);
        ibToolbarMenu = (ImageButton)findViewById(R.id.ib_city_weather_toolbar_menu);
        rlBackgroundView = (RelativeLayout)findViewById(R.id.rl_city_weather_background_view);

        btFailedRetry.setOnClickListener(this);
        ibToolbarCities.setOnClickListener(this);
        ibToolbarMenu.setOnClickListener(this);
        srlCityWeather.setOnRefreshListener(this);
        srlCityWeather.setColorSchemeResources(R.color.colorLightGreen, R.color.colorLigthBlue, R.color.colorLightRed);

        Intent intent = getIntent();
        String cityId = intent.getStringExtra(CITY_ID);
        FileUtil.putStringToPreferences(getApplicationContext(), Constants.GLOBAL_SETTINGS, Constants.PRF_KEY_CITY_ID, cityId);

        mPresenter = new CityWeatherPresenter();
        mPresenter.attachView(this, cityId);
        mPresenter.start();

        rvCityWeather.setHasFixedSize(true);
        rvCityWeather.setLayoutManager(new LinearLayoutManager(this));
        mCityWeatherAdapter = new CityWeatherAdapter(this, mPresenter, rvCityWeather);
        rvCityWeather.setAdapter(mCityWeatherAdapter);
        rvCityWeather.setItemViewCacheSize(4);
        rvCityWeather.addOnScrollListener(new CityWeatherScrollListener());

        View popMenuView = getLayoutInflater().inflate(R.layout.ll_city_weather_pop_menu, null);
        if (popMenuView != null) {
            mPopupMenu = new PopupWindow(popMenuView, DensityUtil.dp2px(getApplicationContext(), 120), ViewGroup.LayoutParams.WRAP_CONTENT);
            mPopupMenu.setBackgroundDrawable(new ColorDrawable(0));
            mPopupMenu.setFocusable(true);
            View refreshView = popMenuView.findViewById(R.id.ll_pop_menu_item_refresh);
            refreshView.setOnClickListener(this);
            View settingsView = popMenuView.findViewById(R.id.ll_pop_menu_item_settings);
            settingsView.setOnClickListener(this);
        }
    }

    @Override
    protected void uninitView() {
        rvCityWeather.clearOnScrollListeners();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        mPresenter.stop();
        String cityId = intent.getStringExtra(CITY_ID);
        FileUtil.putStringToPreferences(getApplicationContext(), Constants.GLOBAL_SETTINGS, Constants.PRF_KEY_CITY_ID, cityId);
        mPresenter.setCityId(cityId);
        mPresenter.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.getBackgroundImage(rlBackgroundView, R.drawable.night);
        mPresenter.getImageViewSrc(ibToolbarCities, R.drawable.ic_edit_location_white_24dp);
        mPresenter.getImageViewSrc(ibToolbarMenu, R.drawable.ic_menu_white_24dp);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //smLocalBroadcastManager.registerReceiver(mWeatherUpdateReceiver, new IntentFilter(WEATHER_UPDATE_ACTION));
        registerReceiver(mWeatherUpdateReceiver, new IntentFilter(WEATHER_UPDATE_ACTION), Constants.SEND_WEATHER_UPDATE, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //mLocalBroadcastManager.unregisterReceiver(mWeatherUpdateReceiver);
        unregisterReceiver(mWeatherUpdateReceiver);
    }

    @Override
    public void renderCityWeather(WeatherEntity entity) {
        if (entity != null) {
            setToolbarCity(entity.getCityName());
            mCityWeatherAdapter.setWeatherEntity(entity);
            startAnimation(entity);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.stop();
    }

    @Override
    protected void clearMemory() {
        stopAnimation();
        mPresenter.destroy();
        mHandler.removeMessages(MSG_WEATHER_UPDATE);
        mHandler.removeCallbacks(rainProc);
        mHandler.removeCallbacks(snowProc);
        mHandler.removeCallbacks(cloudProc);
        mHandler.removeCallbacks(lightningProc);
        mHandler.removeCallbacks(sunshaineProc);
        mHandler.removeCallbacks(quitProc);
        //mUnbinder.unbind();

        uninitView();

        //System.exit(0);
    }

    @Override
    public void onBackPressed() {
        if (!mBackPressed) {
            mBackPressed = true;
            mHandler.postDelayed(quitProc, 2000);
            showError(getString(R.string.press_to_quit));
        } else {
            super.onBackPressed();
        }
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
        srlCityWeather.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideContent() {
        srlCityWeather.setVisibility(View.GONE);
    }

    @Override
    public void hideRefreshing() {
        if (srlCityWeather != null) {
            srlCityWeather.setRefreshing(false);
        }
    }

    @Override
    public void showRefreshing() {
        if (srlCityWeather != null) {
            srlCityWeather.setRefreshing(true);
        }
    }

    @Override
    public void navigateToCitiesActivity() {
        Intent intent = new Intent(this.getApplicationContext(), CitiesActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToSettingsActivity() {
        Intent intent = new Intent(this.getApplicationContext(), SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void updateNotification(WeatherEntity entity) {
        // 更新appwidget
        Intent intent = new Intent(WeatherAppWidget.UPDATE_WIDGET_ACTION);
        intent.putExtra(WeatherAppWidget.WEATHER_ENTITY, entity);
        sendBroadcast(intent, Constants.RECV_WEATHER_UPDATE);

        // 更新通知栏
        boolean shouldNotify = FileUtil.getBooleanFromPreferences(getApplicationContext(), Constants.GLOBAL_SETTINGS, Constants.PRF_KEY_NOTIFY_WEATHER, Constants.DEFAULT_NOTIFY_WEATHER);
        if (shouldNotify) {
            Intent notifyIntent = new Intent(getApplicationContext(), WeatherNotificationService.class);
            notifyIntent.putExtra(WeatherNotificationService.WEATHER_ENTITY, entity);
            startService(notifyIntent);
        }
    }

    @Override
    public void updateUpdateService(String cityId) {
        // 更新WeatherUpdateService的城市
        Intent intent = new Intent(getApplicationContext(), WeatherUpdateService.class);
        intent.putExtra(WeatherUpdateService.UPDATE_CITY_ID, cityId);
        startService(intent);
    }

    @Override
    public void onRefresh() {
        updateData();
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
            case R.id.ib_city_weather_toolbar_menu: {
                mPopupMenu.showAsDropDown(v);
            } break;
            case R.id.ll_pop_menu_item_refresh: {
                updateData();
                mPopupMenu.dismiss();
            } break;
            case R.id.ll_pop_menu_item_settings: {
//                if (mAnimationType == 0) {
//                    mAnimationType = 1;
//                } else {
//                    mAnimationType *= 2;
//                }
//                if (mAnimationType > ANIMATION_SUNSHINE) {
//                    mAnimationType = 0;
//                }
//                startAnimation(new WeatherEntity());

                navigateToSettingsActivity();
                mPopupMenu.dismiss();
            } break;
        }
    }

    private final class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_WEATHER_UPDATE) {
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

    private final class CityWeatherScrollListener extends RecyclerView.OnScrollListener {
        boolean mScrollDown = true;
//        int mScreenHeight = 0;
        public CityWeatherScrollListener() {
            super();
//            mScreenHeight = DensityUtil.getScreenHeight(CityWeatherActivity.this.getApplicationContext());
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy > 0) {
                mScrollDown = true;
            } else if (dy < 0) {
                mScrollDown = false;
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (RecyclerView.SCROLL_STATE_IDLE == newState) {
                View view = recyclerView.getChildAt(0);
//                Rect rect = new Rect();
//                Point point = new Point();
//                recyclerView.getChildVisibleRect(view, rect, point);
                if ((int)view.getTag() == CityWeatherAdapter.VIEW_MAIN) {
                    if (mScrollDown) {
                        recyclerView.smoothScrollToPosition(1);
                    } else {
                        recyclerView.smoothScrollToPosition(0);
                    }
                }
            }
        }
    }

    private void updateData() {
        mPresenter.stop();
        mPresenter.start();
    }

    private void startServices() {
        int updateFreq = FileUtil.getIntFromPreferences(getApplicationContext(), Constants.GLOBAL_SETTINGS, Constants.PRF_KEY_UPDATE_FREQUENCY, Constants.DEFAULT_UPDATE_FREQUENCY);
        if (updateFreq != 0) {
            Intent intent1 = new Intent(this.getApplicationContext(), WeatherUpdateService.class);
            intent1.putExtra(WeatherUpdateService.UPDATE_DATA_FLAG, false);
            startService(intent1);
        }

        boolean shouldNotify = FileUtil.getBooleanFromPreferences(getApplicationContext(), Constants.GLOBAL_SETTINGS, Constants.PRF_KEY_NOTIFY_WEATHER, Constants.DEFAULT_NOTIFY_WEATHER);
        if (shouldNotify) {
            Intent intent2 = new Intent(this.getApplicationContext(), WeatherNotificationService.class);
            startService(intent2);
        }
    }

    private Runnable quitProc = new Runnable() {
        @Override
        public void run() {
            mBackPressed = false;
        }
    };

    private Runnable rainProc = new Runnable() {
        @Override
        public void run() {
            ++mSpecialWeatherNumRain;
            if (mSpecialWeatherNumRain <= mSpecialWeatherNumLimitRain) {
                int screenHeight = DensityUtil.getScreenHeight(CityWeatherActivity.this);
                int screenWidth = DensityUtil.getScreenWidth(CityWeatherActivity.this);
                int fX = mRandom.nextInt(screenWidth << 1);
                int tX = fX - (int)(screenHeight * 0.58);

                ImageView imageView = new ImageView(CityWeatherActivity.this);
                imageView.setVisibility(View.VISIBLE);
                mPresenter.getImageViewSrc(imageView, mRainIconId);
                //imageView.setImageResource(mRainIconId);
                imageView.setRotation(30);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(fX, 0, 0, 0);
                rlBackgroundView.addView(imageView, layoutParams);

                PropertyValuesHolder holderY = PropertyValuesHolder.ofFloat("translationY", -100, screenHeight + 100);
                PropertyValuesHolder holderX = PropertyValuesHolder.ofFloat("translationX", fX, tX);
                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(imageView, holderX, holderY);
                if ((mSpecialWeatherNumRain & 0x1) == 0) {
                    animator.setDuration(mSpecialWeatherSpeedLimitRain);
                } else {
                    animator.setDuration(mSpecialWeatherSpeedLimitRain + RAIN_SPEED_OFFSET);
                }
                animator.setRepeatMode(ObjectAnimator.RESTART);
                animator.setRepeatCount(ObjectAnimator.INFINITE);
                animator.setInterpolator(new LinearInterpolator());
                animator.start();

                mHandler.postDelayed(rainProc, RAIN_GEN_INTERVAL);
            }
        }
    };

    private Runnable snowProc = new Runnable() {
        @Override
        public void run() {
            ++mSpecialWeatherNumSnow;
            if (mSpecialWeatherNumSnow <= mSpecialWeatherNumLimitSnow) {
                int screenHeight = DensityUtil.getScreenHeight(CityWeatherActivity.this);
                int screenWidth = DensityUtil.getScreenWidth(CityWeatherActivity.this);
                int fX = mRandom.nextInt(screenWidth);

                ImageView imageView = new ImageView(CityWeatherActivity.this);
                imageView.setVisibility(View.VISIBLE);
                if ((mSpecialWeatherNumSnow & 0x1) == 0) {
                    //imageView.setImageResource(mSnowIconLightId);
                    mPresenter.getImageViewSrc(imageView, mSnowIconLightId);
                } else {
                    //imageView.setImageResource(mSnowIconDarkId);
                    mPresenter.getImageViewSrc(imageView, mSnowIconDarkId);
                }
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(fX, 0, 0, 0);
                rlBackgroundView.addView(imageView, layoutParams);

                ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "translationY", -100, screenHeight + 100);
                if ((mSpecialWeatherNumSnow & 0x1) == 0) {
                    animator.setDuration(mSpecialWeatherSpeedLimitSnow);
                } else {
                    animator.setDuration(mSpecialWeatherSpeedLimitSnow + RAIN_SPEED_OFFSET);
                }
                animator.setRepeatMode(ObjectAnimator.RESTART);
                animator.setRepeatCount(ObjectAnimator.INFINITE);
                animator.setInterpolator(new LinearInterpolator());
                animator.start();

                mHandler.postDelayed(snowProc, SNOW_GEN_INTERVAL);
            }
        }
    };

    private Runnable cloudProc = new Runnable() {
        @Override
        public void run() {
            ++mSpecialWeatherNumCloud;
            if (mSpecialWeatherNumCloud <= mSpecialWeatherNumLimitCloud) {
                int toolbarHeight = 0;
                int screenWidth = DensityUtil.getScreenWidth(CityWeatherActivity.this);

                boolean isBack = (mSpecialWeatherNumCloud & 0x1) == 0;

                ImageView imageView = new ImageView(CityWeatherActivity.this);
                imageView.setVisibility(View.VISIBLE);
                if (isBack) {
                    //imageView.setImageResource(mCloudIconBackId);
                    mPresenter.getImageViewSrc(imageView, mCloudIconBackId);
                } else {
                    //imageView.setImageResource(mCloudIconFrontId);
                    mPresenter.getImageViewSrc(imageView, mCloudIconFrontId);
                }
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                if (isBack) {
                    layoutParams.setMargins(0, toolbarHeight, 0, 0);
                } else {
                    layoutParams.setMargins(0, toolbarHeight + mRandom.nextInt(100), 0, 0);
                }
                rlBackgroundView.addView(imageView, layoutParams);

                ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "translationX", -(screenWidth * 2 / 3), screenWidth);
                if (isBack) {
                    animator.setDuration(CLOUD_SPEED_L);
                } else {
                    animator.setDuration(CLOUD_SPEED_H);
                }
                animator.setRepeatMode(ObjectAnimator.RESTART);
                animator.setRepeatCount(ObjectAnimator.INFINITE);
                animator.setInterpolator(new LinearInterpolator());
                animator.start();

                mHandler.postDelayed(cloudProc, CLOUD_GEN_INTERVAL);
            }
        }
    };

    private Runnable lightningProc = new Runnable() {
        @Override
        public void run() {
            ++mSpecialWeatherNumLightning;
            if (mSpecialWeatherNumLightning <= mSpecialWeatherNumLimitLightning) {
                int toolbarHeight = 0;
                int screenHeight = DensityUtil.getScreenHeight(CityWeatherActivity.this);
                int screenWidth = DensityUtil.getScreenWidth(CityWeatherActivity.this);

                ImageView imageView = new ImageView(CityWeatherActivity.this);
                imageView.setVisibility(View.VISIBLE);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                if ((mSpecialWeatherNumLightning & 0x1) == 0) {
                    //imageView.setImageResource(R.drawable.lightning_2);
                    mPresenter.getImageViewSrc(imageView, R.drawable.lightning_2);
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    layoutParams.setMargins(0, toolbarHeight + mRandom.nextInt(screenHeight >> 2), screenWidth >> 2 + mRandom.nextInt(screenWidth >> 2), 0);
                } else {
                    //imageView.setImageResource(R.drawable.lightning_1);
                    mPresenter.getImageViewSrc(imageView, R.drawable.lightning_1);
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    layoutParams.setMargins(mRandom.nextInt(screenWidth >> 2), toolbarHeight + mRandom.nextInt(screenHeight >> 2), 0, 0);
                }
                rlBackgroundView.addView(imageView, layoutParams);

                ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "alpha", 0, 1);
                animator.setDuration(LIGHTNING_SPEED_H);
                animator.setRepeatMode(ObjectAnimator.REVERSE);
                animator.setRepeatCount(3);
                animator.setInterpolator(new AccelerateInterpolator());
                animator.start();

                mHandler.postDelayed(lightningProc, LIGHTNING_GEN_INTERVAL + mRandom.nextInt(LIGHTNING_GEN_INTERVAL));
            } else {
                mSpecialWeatherNumLightning = 0;
                mHandler.postDelayed(lightningProc, LIGHTNING_GEN_INTERVAL + mRandom.nextInt(LIGHTNING_GEN_INTERVAL));
            }
        }
    };

    private Runnable sunshaineProc = new Runnable() {
        @Override
        public void run() {
            int toolbarHeight = 0;

            ImageView imageView = new ImageView(CityWeatherActivity.this);
            imageView.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            //imageView.setImageResource(R.drawable.sunshine_2);
            mPresenter.getImageViewSrc(imageView, R.drawable.sunshine_2);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams.setMargins(0, toolbarHeight, 0, 0);
            rlBackgroundView.addView(imageView, layoutParams);

            ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "alpha", 0.6f, 1f);
            animator.setDuration(SUNSHINE_SPEED_H);
            animator.setRepeatMode(ObjectAnimator.REVERSE);
            animator.setRepeatCount(ObjectAnimator.INFINITE);
            animator.setInterpolator(new AccelerateInterpolator());
            animator.start();
        }
    };

    private void stopAnimation() {
        mHandler.removeCallbacks(rainProc);
        mHandler.removeCallbacks(snowProc);
        mHandler.removeCallbacks(cloudProc);
        mHandler.removeCallbacks(lightningProc);
        mHandler.removeCallbacks(sunshaineProc);
        for (int i = 0; i != rlBackgroundView.getChildCount(); ++i) {
            View view = rlBackgroundView.getChildAt(i);
            if (view != null) {
                view.clearAnimation();
            }
        }
        rlBackgroundView.removeAllViews();
        mSpecialWeatherNumRain = 0;
        mSpecialWeatherNumCloud = 0;
        mSpecialWeatherNumSnow = 0;
        mSpecialWeatherNumLightning = 0;
    }

    private void startAnimation(WeatherEntity entity) {
        stopAnimation();

        if (entity != null) {
            int animationType = 0;
            String weatherDesc = entity.getWeatherDescription();
            if (weatherDesc.equalsIgnoreCase(getString(R.string.light_rain))
                    || weatherDesc.equalsIgnoreCase(getString(R.string.drizzle_rain))
                    || weatherDesc.equalsIgnoreCase(getString(R.string.drizzle_rain_1))) {
                animationType |= ANIMATION_RAIN_L;
            }
            if (weatherDesc.equalsIgnoreCase(getString(R.string.shower_rain))
                    || weatherDesc.equalsIgnoreCase(getString(R.string.thunder_shower))
                    || weatherDesc.equalsIgnoreCase(getString(R.string.moderate_rain))
                    || weatherDesc.equalsIgnoreCase(getString(R.string.freezing_rain))) {
                animationType |= ANIMATION_RAIN_M;
            }
            if (weatherDesc.equalsIgnoreCase(getString(R.string.heavy_shower_rain))
                    || weatherDesc.equalsIgnoreCase(getString(R.string.heavy_thunderstorm))
                    || weatherDesc.equalsIgnoreCase(getString(R.string.hail))
                    || weatherDesc.equalsIgnoreCase(getString(R.string.heavy_rain))
                    || weatherDesc.equalsIgnoreCase(getString(R.string.extreme_rain))
                    || weatherDesc.equalsIgnoreCase(getString(R.string.storm))
                    || weatherDesc.equalsIgnoreCase(getString(R.string.heavy_storm))
                    || weatherDesc.equalsIgnoreCase(getString(R.string.severe_storm))) {
                animationType |= ANIMATION_RAIN_H;
            }
            if (weatherDesc.equalsIgnoreCase(getString(R.string.light_snow))
                    || weatherDesc.equalsIgnoreCase(getString(R.string.sleet))
                    || weatherDesc.equalsIgnoreCase(getString(R.string.rain_snow))
                    || weatherDesc.equalsIgnoreCase(getString(R.string.shower_snow))) {
                animationType |= ANIMATION_SNOW_L;
            }
            if (weatherDesc.equalsIgnoreCase(getString(R.string.moderate_snow))
                    || weatherDesc.equalsIgnoreCase(getString(R.string.snow_flurry))) {
                animationType |= ANIMATION_SNOW_M;
            }
            if (weatherDesc.equalsIgnoreCase(getString(R.string.heavy_snow))
                    || weatherDesc.equalsIgnoreCase(getString(R.string.snow_storm))) {
                animationType |= ANIMATION_SNOW_H;
            }
            if (weatherDesc.equalsIgnoreCase(getString(R.string.cloudy))
                    || weatherDesc.equalsIgnoreCase(getString(R.string.partly_cloudy))
                    || weatherDesc.equalsIgnoreCase(getString(R.string.few_cloud))) {
                animationType |= ANIMATION_CLOUD_D;
            }
            if (weatherDesc.equalsIgnoreCase(getString(R.string.mist))
                    || weatherDesc.equalsIgnoreCase(getString(R.string.foggy))) {
                animationType |= ANIMATION_CLOUD_F;
            }
            if (weatherDesc.equalsIgnoreCase(getString(R.string.sunny))) {
                animationType |= ANIMATION_SUNSHINE;
            }
            if (weatherDesc.equalsIgnoreCase(getString(R.string.thunder_shower))
                    || weatherDesc.equalsIgnoreCase(getString(R.string.heavy_thunderstorm))
                    || weatherDesc.equalsIgnoreCase(getString(R.string.hail))) {
                animationType |= ANIMATION_LIGHTNING;
            }

            if (animationType == 0)
                animationType = mAnimationType;

            if ((animationType & ANIMATION_RAIN_L) != 0) {
                mRainIconId = R.drawable.raindrop_l;
                mSpecialWeatherNumLimitRain = RAIN_NUM_L;
                mSpecialWeatherSpeedLimitRain = RAIN_SPEED_L;
                mHandler.postDelayed(rainProc, 20);
            }
            if ((animationType & ANIMATION_RAIN_M) != 0) {
                mRainIconId = R.drawable.raindrop_m;
                mSpecialWeatherNumLimitRain = RAIN_NUM_M;
                mSpecialWeatherSpeedLimitRain = RAIN_SPEED_M;
                mHandler.postDelayed(rainProc, 20);
            }
            if ((animationType & ANIMATION_RAIN_H) != 0) {
                mRainIconId = R.drawable.raindrop_h;
                mSpecialWeatherNumLimitRain = RAIN_NUM_H;
                mSpecialWeatherSpeedLimitRain = RAIN_SPEED_H;
                mHandler.postDelayed(rainProc, 20);
            }
            if ((animationType & ANIMATION_SNOW_L) != 0) {
                mSnowIconLightId = R.drawable.snow_light_l;
                mSnowIconDarkId = R.drawable.snow_dark_l;
                mSpecialWeatherNumLimitSnow = RAIN_NUM_L;
                mSpecialWeatherSpeedLimitSnow = SNOW_SPEED_L;
                mHandler.postDelayed(snowProc, 20);
            }
            if ((animationType & ANIMATION_SNOW_M) != 0) {
                mSnowIconLightId = R.drawable.snow_light_m;
                mSnowIconDarkId = R.drawable.snow_dark_m;
                mSpecialWeatherNumLimitSnow = RAIN_NUM_M;
                mSpecialWeatherSpeedLimitSnow = SNOW_SPEED_M;
                mHandler.postDelayed(snowProc, 20);
            }
            if ((animationType & ANIMATION_SNOW_H) != 0) {
                mSnowIconLightId = R.drawable.snow_light_h;
                mSnowIconDarkId = R.drawable.snow_dark_h;
                mSpecialWeatherNumLimitSnow = RAIN_NUM_H;
                mSpecialWeatherSpeedLimitSnow = SNOW_SPEED_H;
                mHandler.postDelayed(snowProc, 20);
            }
            if ((animationType & ANIMATION_CLOUD_D) != 0) {
                mCloudIconBackId = R.drawable.cloudy_day_1;
                mCloudIconFrontId = R.drawable.cloudy_day_2;
                mSpecialWeatherNumLimitCloud = 2;
                mHandler.postDelayed(cloudProc, 20);
            }
            if ((animationType & ANIMATION_CLOUD_N) != 0) {
                mCloudIconBackId = R.drawable.cloudy_night1;
                mCloudIconFrontId = R.drawable.cloudy_night2;
                mSpecialWeatherNumLimitCloud = 2;
                mHandler.postDelayed(cloudProc, 20);
            }
            if ((animationType & ANIMATION_CLOUD_F) != 0) {
                mCloudIconBackId = R.drawable.fog_cloud_1;
                mCloudIconFrontId = R.drawable.fog_cloud_2;
                mSpecialWeatherNumLimitCloud = 2;
                mHandler.postDelayed(cloudProc, 20);
            }
            if ((animationType & ANIMATION_LIGHTNING) != 0) {
                mSpecialWeatherNumLimitLightning = 2;
                mHandler.postDelayed(lightningProc, 20);
            }
            if ((animationType & ANIMATION_SUNSHINE) != 0) {
                mHandler.postDelayed(sunshaineProc, 20);
            }
        }
    }
}
