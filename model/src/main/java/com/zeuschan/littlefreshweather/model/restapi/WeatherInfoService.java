package com.zeuschan.littlefreshweather.model.restapi;

import com.zeuschan.littlefreshweather.model.response.CityWeatherResponse;
import com.zeuschan.littlefreshweather.model.response.CitysResponse;
import com.zeuschan.littlefreshweather.model.response.ConditionsResponse;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by chenxiong on 2016/5/31.
 */
public interface WeatherInfoService {
    /*https://cdn.heweather.com/china-city-list.txt*/
    @Headers("Accept-Encoding: application/json")
    @GET()
    Observable<String> getCityInfos(@Url String url);

    /*https://api.heweather.com/x3/condition?search=allcond&key=035591c2b70c45fa9b4dd2bcabce13fe*/
    @Headers("Accept-Encoding: application/json")
    @GET("x3/condition")
    Observable<ConditionsResponse> getConditionInfos(@QueryMap Map<String, String> params);

    /*https://free-api.heweather.com/v5/weather?city=CN101010300&key=035591c2b70c45fa9b4dd2bcabce13fe*/
    @Headers("Accept-Encoding: application/json")
    @GET("v5/weather")
    Observable<CityWeatherResponse> getCityWeatherInfo(@QueryMap Map<String, String> params);
}
