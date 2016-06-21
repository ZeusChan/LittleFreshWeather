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

    // Preferences file name
    public static final String GLOBAL_SETTINGS = "global_settings";
    // Preferences keys
    public static final String PRF_KEY_CITY_ID = "city_id";
    // Preferences Values
    public static final String DEFAULT_CITY_ID = "CN101010100";
}
