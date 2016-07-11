package com.zeuschan.littlefreshweather.common.util;

/**
 * Created by chenxiong on 2016/5/30.
 */
public class Constants {
    // Keys
    public static final String WEATHER_KEY = "035591c2b70c45fa9b4dd2bcabce13fe";

    // Urls
    public static final String WEATHER_BASE_URL = "https://api.heweather.com";

    // Query Params
    public static final String SEARCH_ALL_CITY = "allchina";
    public static final String SEARCH_ALL_COND = "allcond";

    // Failure Descriptions
    public static final String OK = "ok";
    public static final String INVALID_KEY = "invalid key";
    public static final String UNKNOWN_CITY = "unknown city";
    public static final String OVER_LIMIT = "no more requests";
    public static final String SERVER_TIMEOUT = "anr";
    public static final String PERMISSION_DENIED = "permission denied";

    // Permissions
    public static final String SEND_WEATHER_UPDATE = "com.zeuschan.littlefreshweather.prsentation.SEND_WEATHER_UPDATE";
    public static final String RECV_WEATHER_UPDATE = "com.zeuschan.littlefreshweather.prsentation.RECV_WEATHER_UPDATE";

    // Preferences file name
    public static final String GLOBAL_SETTINGS = "global_settings";
    // Preferences keys
    public static final String PRF_KEY_CITY_ID = "city_id";
    public static final String PRF_KEY_FIRST_STARTUP = "first_startup";
    public static final String PRF_KEY_UPDATE_FREQUENCY = "update_frequency";
    public static final String PRF_KEY_NOTIFY_WEATHER = "notify_weather";
    // Preferences values
    public static final String DEFAULT_CITY_ID = "CN101010100";
    public static final int DEFAULT_UPDATE_FREQUENCY = 2; // 单位：小时
    public static final boolean DEFAULT_NOTIFY_WEATHER = true;
}
