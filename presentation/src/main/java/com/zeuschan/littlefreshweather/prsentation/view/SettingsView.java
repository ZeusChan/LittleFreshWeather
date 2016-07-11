package com.zeuschan.littlefreshweather.prsentation.view;

/**
 * Created by chenxiong on 2016/7/8.
 */
public interface SettingsView extends BaseView {
    void showAutoUpdateFrequency(String freqString);
    void showNotificationWeather(boolean shouldNotify);
    void showUpdateFreqDialog();
    void showAboutDialog();
}
