package com.zeuschan.littlefreshweather.model.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chenxiong on 2016/5/31.
 */
public class WeatherConditionEntity {
    public static final String DEFAULT_VALUE = "--";

    @SerializedName("weather_code") private String weatherCode;
    @SerializedName("weather_desc") private String weatherDescription;
    @SerializedName("weather_ic_url") private String weatherIconUrl;

    public WeatherConditionEntity() {
        weatherCode = DEFAULT_VALUE;
        weatherDescription = DEFAULT_VALUE;
        weatherIconUrl = DEFAULT_VALUE;
    }

    @Override
    public String toString() {
        return "WeatherConditionEntity{" +
                "weatherCode=" + weatherCode +
                ", weatherDescription='" + weatherDescription + '\'' +
                ", weatherIconUrl='" + weatherIconUrl + '\'' +
                '}';
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public String getWeatherIconUrl() {
        return weatherIconUrl;
    }

    public void setWeatherIconUrl(String weatherIconUrl) {
        this.weatherIconUrl = weatherIconUrl;
    }
}
