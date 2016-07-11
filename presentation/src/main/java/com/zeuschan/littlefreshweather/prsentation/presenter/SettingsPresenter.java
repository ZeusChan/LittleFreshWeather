package com.zeuschan.littlefreshweather.prsentation.presenter;

import com.zeuschan.littlefreshweather.common.util.Constants;
import com.zeuschan.littlefreshweather.common.util.FileUtil;
import com.zeuschan.littlefreshweather.prsentation.R;
import com.zeuschan.littlefreshweather.prsentation.view.SettingsView;


/**
 * Created by chenxiong on 2016/7/11.
 */
public class SettingsPresenter implements Presenter {
    private SettingsView mView;

    public SettingsPresenter(SettingsView view) {
        mView = view;
    }

    @Override
    public void destroy() {
        mView = null;
    }

    @Override
    public void start() {
        int updateFrequency = FileUtil.getIntFromPreferences(mView.getContext().getApplicationContext(), Constants.GLOBAL_SETTINGS, Constants.PRF_KEY_UPDATE_FREQUENCY, Constants.DEFAULT_UPDATE_FREQUENCY);
        String[] updateFrequencyString = mView.getContext().getApplicationContext().getResources().getStringArray(R.array.update_frequency_string);
        mView.showAutoUpdateFrequency(updateFrequencyString[updateFrequency]);

        boolean shouldNotify = FileUtil.getBooleanFromPreferences(mView.getContext().getApplicationContext(), Constants.GLOBAL_SETTINGS, Constants.PRF_KEY_NOTIFY_WEATHER, Constants.DEFAULT_NOTIFY_WEATHER);
        mView.showNotificationWeather(shouldNotify);
    }

    @Override
    public void stop() {

    }
}
