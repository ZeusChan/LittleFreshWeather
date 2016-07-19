package com.zeuschan.littlefreshweather.prsentation.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zeuschan.littlefreshweather.common.util.Constants;
import com.zeuschan.littlefreshweather.common.util.FileUtil;
import com.zeuschan.littlefreshweather.prsentation.R;
import com.zeuschan.littlefreshweather.prsentation.presenter.SettingsPresenter;
import com.zeuschan.littlefreshweather.prsentation.service.WeatherNotificationService;
import com.zeuschan.littlefreshweather.prsentation.service.WeatherUpdateService;
import com.zeuschan.littlefreshweather.prsentation.view.SettingsView;
import com.zeuschan.littlefreshweather.prsentation.view.fragment.AboutDialogFragment;
import com.zeuschan.littlefreshweather.prsentation.view.fragment.UpdateFrequencyDialogFragment;

//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.Unbinder;

public class SettingsActivity extends BaseActivity implements SettingsView, View.OnClickListener, UpdateFrequencyDialogFragment.UpdateFrequencyDialogListener {
    private static final String UPDATE_FREQ_DIALOG_TAG = "update_freq_dialog";
    private static final String ABOUT_DIALOG_TAG = "about_dialog";

    private SettingsPresenter mPresenter;
    //private Unbinder mUnbinder;

//    @BindView(R.id.ib_settings_toolbar_back) ImageButton ibToolbarBack;
//    @BindView(R.id.rl_settings_auto_update) RelativeLayout rlAutoUpdate;
//    @BindView(R.id.tv_settings_auto_update_value) TextView tvAutoUpdateVal;
//    @BindView(R.id.ll_settings_notification_weather) LinearLayout llNotificationWeather;
//    @BindView(R.id.iv_settings_notification_weather_value) ImageView ivShouldNotify;
//    @BindView(R.id.ll_settings_about) LinearLayout llAbout;

    private ImageButton ibToolbarBack;
    private RelativeLayout rlAutoUpdate;
    private TextView tvAutoUpdateVal;
    private LinearLayout llNotificationWeather;
    private ImageView ivShouldNotify;
    private LinearLayout llAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_settings);
        //mUnbinder = ButterKnife.bind(this);

        ibToolbarBack = (ImageButton)findViewById(R.id.ib_settings_toolbar_back);
        rlAutoUpdate = (RelativeLayout)findViewById(R.id.rl_settings_auto_update);
        tvAutoUpdateVal = (TextView)findViewById(R.id.tv_settings_auto_update_value);
        llNotificationWeather = (LinearLayout)findViewById(R.id.ll_settings_notification_weather);
        ivShouldNotify = (ImageView)findViewById(R.id.iv_settings_notification_weather_value);
        llAbout = (LinearLayout)findViewById(R.id.ll_settings_about);

        ibToolbarBack.setOnClickListener(this);
        rlAutoUpdate.setOnClickListener(this);
        llNotificationWeather.setOnClickListener(this);
        llAbout.setOnClickListener(this);

        mPresenter = new SettingsPresenter(this);
    }

    @Override
    protected void uninitView() {
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter != null)
            mPresenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPresenter != null)
            mPresenter.stop();
    }

    @Override
    protected void clearMemory() {
        if (mPresenter != null) {
            mPresenter.destroy();
        }

        uninitView();
        //mUnbinder.unbind();
    }

    @Override
    public void showAutoUpdateFrequency(String freqString) {
        tvAutoUpdateVal.setText(freqString);
    }

    @Override
    public void showNotificationWeather(boolean shouldNotify) {
        ivShouldNotify.setImageResource(shouldNotify ? R.drawable.preference_checkbox_on : R.drawable.preference_checkbox_off);
        if (!shouldNotify) {
            stopService(new Intent(getApplicationContext(), WeatherNotificationService.class));
        } else {
            startService(new Intent(getApplicationContext(), WeatherNotificationService.class));
        }
    }

    @Override
    public void showUpdateFreqDialog() {
        DialogFragment dialogFragment = new UpdateFrequencyDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), UPDATE_FREQ_DIALOG_TAG);
    }

    @Override
    public void onDialogItemClick(int which) {
        FileUtil.putIntToPreferences(getContext().getApplicationContext(), Constants.GLOBAL_SETTINGS, Constants.PRF_KEY_UPDATE_FREQUENCY, which);
        showAutoUpdateFrequency(getContext().getApplicationContext().getResources().getStringArray(R.array.update_frequency_string)[which]);

        if (WeatherUpdateService.isUpdateServeceAlarmOn(getApplicationContext())) {
            WeatherUpdateService.setUpdateServiceAlarm(getApplicationContext(), 0);
        }
        WeatherUpdateService.setUpdateServiceAlarm(getApplicationContext(), which);
        if (0 == which) {
            stopService(new Intent(getApplicationContext(), WeatherUpdateService.class));
        }
    }

    @Override
    public void showAboutDialog() {
        DialogFragment dialogFragment = new AboutDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), ABOUT_DIALOG_TAG);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ib_settings_toolbar_back: {
                onBackPressed();
            } break;
            case R.id.rl_settings_auto_update: {
                showUpdateFreqDialog();
            } break;
            case R.id.ll_settings_notification_weather: {
                boolean shouldNotify = FileUtil.getBooleanFromPreferences(getContext().getApplicationContext(), Constants.GLOBAL_SETTINGS, Constants.PRF_KEY_NOTIFY_WEATHER, Constants.DEFAULT_NOTIFY_WEATHER);
                FileUtil.putBooleanToPreferences(getContext().getApplicationContext(), Constants.GLOBAL_SETTINGS, Constants.PRF_KEY_NOTIFY_WEATHER, !shouldNotify);
                showNotificationWeather(!shouldNotify);
            } break;
            case R.id.ll_settings_about: {
                showAboutDialog();
            } break;
        }
    }
}
