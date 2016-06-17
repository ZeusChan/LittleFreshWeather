package com.zeuschan.littlefreshweather.model.exception;

/**
 * Created by chenxiong on 2016/5/31.
 */
public class WeatherServiceException extends RuntimeException {
    public WeatherServiceException(String detailMessage) {
        super(detailMessage);
    }
}
