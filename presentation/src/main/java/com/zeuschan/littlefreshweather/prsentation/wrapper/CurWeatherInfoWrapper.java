package com.zeuschan.littlefreshweather.prsentation.wrapper;

/**
 * Created by chenxiong on 2016/6/13.
 */
public class CurWeatherInfoWrapper {
    private String weatherInfoName;
    private String weatherInfoValue;

    public CurWeatherInfoWrapper(String weatherInfoName, String weatherInfoValue) {
        this.weatherInfoName = weatherInfoName;
        this.weatherInfoValue = weatherInfoValue;
    }

    public String getWeatherInfoName() {
        return weatherInfoName;
    }

    public void setWeatherInfoName(String weatherInfoName) {
        this.weatherInfoName = weatherInfoName;
    }

    public String getWeatherInfoValue() {
        return weatherInfoValue;
    }

    public void setWeatherInfoValue(String weatherInfoValue) {
        this.weatherInfoValue = weatherInfoValue;
    }

    @Override
    public String toString() {
        return "CurWeatherInfoWrapper{" +
                "weatherInfoName='" + weatherInfoName + '\'' +
                ", weatherInfoValue='" + weatherInfoValue + '\'' +
                '}';
    }
}
