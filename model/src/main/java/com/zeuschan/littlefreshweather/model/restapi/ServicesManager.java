package com.zeuschan.littlefreshweather.model.restapi;

import android.util.Log;

import com.zeuschan.littlefreshweather.common.util.Constants;
import com.zeuschan.littlefreshweather.model.datasource.DataSource;
import com.zeuschan.littlefreshweather.model.entity.CityEntity;
import com.zeuschan.littlefreshweather.model.entity.WeatherConditionEntity;
import com.zeuschan.littlefreshweather.model.entity.WeatherEntity;
import com.zeuschan.littlefreshweather.model.exception.WeatherServiceException;
import com.zeuschan.littlefreshweather.model.response.CityWeatherResponse;
import com.zeuschan.littlefreshweather.model.response.CitysResponse;
import com.zeuschan.littlefreshweather.model.response.ConditionsResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by chenxiong on 2016/5/31.
 */
public class ServicesManager implements DataSource {
    private static final String TAG = ServicesManager.class.getSimpleName();

    private static final int DEFAULT_TIMEOUT = 5;

    private static ServicesManager ourerInstance = new ServicesManager();
    public static ServicesManager getInstance() {
        return ourerInstance;
    }

    private final WeatherInfoService weatherInfoService;

    private List<CityEntity> mCityEntities = new ArrayList<>();
    private List<WeatherConditionEntity> mWeatherCondtionEntities = new ArrayList<>();
    private List<WeatherEntity.Forecast> mForecasts = new ArrayList<>();


    private ServicesManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.WEATHER_BASE_URL)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        weatherInfoService = retrofit.create(WeatherInfoService.class);
    }

    @Override
    public Observable<List<CityEntity>> getCityEntities() {
        HashMap<String, String> params = new HashMap<>(2);
        params.put("search", Constants.SEARCH_ALL_CITY);
        params.put("key", Constants.WEATHER_KEY);

        return weatherInfoService.getCityInfos(params)
                .map(new Func1<CitysResponse, List<CityEntity>>() {
                    @Override
                    public List<CityEntity> call(CitysResponse citysResponse) {
                        if (!citysResponse.getResultCode().equals(Constants.OK)) {
                            throw new WeatherServiceException(citysResponse.getResultCode());
                        }

                        mCityEntities.clear();
                        List<CitysResponse.CityInfo> cityInfos = citysResponse.getCitys();
                        for (CitysResponse.CityInfo cityInfo : cityInfos) {
                            CityEntity cityEntity = new CityEntity();
                            cityEntity.setCityId(cityInfo.getId());
                            cityEntity.setCountry(cityInfo.getCountry());
                            cityEntity.setProvince(cityInfo.getProvince());
                            cityEntity.setCity(cityInfo.getCity());
                            mCityEntities.add(cityEntity);
                        }
                        return mCityEntities;
                    }
                });
    }

    @Override
    public Observable<List<WeatherConditionEntity>> getWeatherConditionEntities() {
        HashMap<String, String> params = new HashMap<>(2);
        params.put("search", Constants.SEARCH_ALL_COND);
        params.put("key", Constants.WEATHER_KEY);

        return weatherInfoService.getConditionInfos(params)
                .map(new Func1<ConditionsResponse, List<WeatherConditionEntity>>() {
                    @Override
                    public List<WeatherConditionEntity> call(ConditionsResponse conditionsResponse) {
                        if (!conditionsResponse.getResultCode().equals(Constants.OK)) {
                            throw new WeatherServiceException(conditionsResponse.getResultCode());
                        }

                        mWeatherCondtionEntities.clear();
                        List<ConditionsResponse.ConditionInfo> weatherConditionInfos = conditionsResponse.getConditions();
                        for (ConditionsResponse.ConditionInfo conditionInfo : weatherConditionInfos) {
                            WeatherConditionEntity conditionEntity = new WeatherConditionEntity();
                            conditionEntity.setWeatherCode(conditionInfo.getConditionCode());
                            conditionEntity.setWeatherDescription(conditionInfo.getWeatherDescription());
                            conditionEntity.setWeatherIconUrl(conditionInfo.getWeatherIconUrl());
                            mWeatherCondtionEntities.add(conditionEntity);
                        }
                        return mWeatherCondtionEntities;
                    }
                });
    }

    @Override
    public Observable<WeatherEntity> getCityWeather(String cityId) {
        HashMap<String, String> params = new HashMap<>(2);
        params.put("cityid", cityId);
        params.put("key", Constants.WEATHER_KEY);

        return weatherInfoService.getCityWeatherInfo(params)
                .map(new Func1<CityWeatherResponse, WeatherEntity>() {
                    @Override
                    public WeatherEntity call(CityWeatherResponse cityWeatherResponse) {
                        CityWeatherResponse.CityWeatherInfo cityWeatherInfo = cityWeatherResponse.getCityWeatherInfos().get(0);
                        if (!cityWeatherInfo.getResultCode().equals(Constants.OK)) {
                            throw new WeatherServiceException(cityWeatherInfo.getResultCode());
                        }
                        WeatherEntity weatherEntity = new WeatherEntity();

                        weatherEntity.setCityId(cityWeatherInfo.getBasic().getId());
                        weatherEntity.setCityName(cityWeatherInfo.getBasic().getCity());
                        weatherEntity.setDataUpdateTime(cityWeatherInfo.getBasic().getUpdate().getLoc());

                        weatherEntity.setAirQulityIndex(cityWeatherInfo.getAqi().getCity().getAqi());
                        weatherEntity.setPm25(cityWeatherInfo.getAqi().getCity().getPm25());
                        weatherEntity.setPm10(cityWeatherInfo.getAqi().getCity().getPm10());
                        weatherEntity.setSo2(cityWeatherInfo.getAqi().getCity().getSo2());
                        weatherEntity.setNo2(cityWeatherInfo.getAqi().getCity().getNo2());
                        weatherEntity.setCo(cityWeatherInfo.getAqi().getCity().getCo());
                        weatherEntity.setO3(cityWeatherInfo.getAqi().getCity().getO3());
                        weatherEntity.setAirQulityType(cityWeatherInfo.getAqi().getCity().getQlty());

                        weatherEntity.setWeatherCode(cityWeatherInfo.getNow().getCond().getCode());
                        weatherEntity.setWeatherDescription(cityWeatherInfo.getNow().getCond().getTxt());
                        weatherEntity.setCurrentTemperature(cityWeatherInfo.getNow().getTmp());
                        weatherEntity.setFeltTemperature(cityWeatherInfo.getNow().getFl());
                        weatherEntity.setRainfall(cityWeatherInfo.getNow().getPcpn());
                        weatherEntity.setHumidity(cityWeatherInfo.getNow().getHum());
                        weatherEntity.setAirPressure(cityWeatherInfo.getNow().getPres());
                        weatherEntity.setVisibility(cityWeatherInfo.getNow().getVis());
                        weatherEntity.setWindSpeed(cityWeatherInfo.getNow().getWind().getSpd());
                        weatherEntity.setWindScale(cityWeatherInfo.getNow().getWind().getSc());
                        weatherEntity.setWindDirection(cityWeatherInfo.getNow().getWind().getDir());

                        weatherEntity.setDressBrief(cityWeatherInfo.getSuggestion().getDrsg().getBrf());
                        weatherEntity.setDressDescription(cityWeatherInfo.getSuggestion().getDrsg().getTxt());
                        weatherEntity.setUvBrief(cityWeatherInfo.getSuggestion().getUv().getBrf());
                        weatherEntity.setUvDescription(cityWeatherInfo.getSuggestion().getUv().getTxt());
                        weatherEntity.setCarWashBrief(cityWeatherInfo.getSuggestion().getCw().getBrf());
                        weatherEntity.setCarWashDescription(cityWeatherInfo.getSuggestion().getCw().getTxt());
                        weatherEntity.setTravelBrief(cityWeatherInfo.getSuggestion().getTrav().getBrf());
                        weatherEntity.setTravelDescription(cityWeatherInfo.getSuggestion().getTrav().getTxt());
                        weatherEntity.setFluBrief(cityWeatherInfo.getSuggestion().getFlu().getBrf());
                        weatherEntity.setFluDescription(cityWeatherInfo.getSuggestion().getFlu().getTxt());
                        weatherEntity.setSportBrief(cityWeatherInfo.getSuggestion().getSport().getBrf());
                        weatherEntity.setSportDescription(cityWeatherInfo.getSuggestion().getSport().getTxt());

                        mForecasts.clear();
                        List<CityWeatherResponse.CityWeatherInfo.DailyForecast> forecastsResponse = cityWeatherInfo.getDaily_forecast();
                        for (CityWeatherResponse.CityWeatherInfo.DailyForecast forecastResponse :
                                forecastsResponse) {
                            WeatherEntity.Forecast forcast = new WeatherEntity.Forecast();

                            forcast.setDate(forecastResponse.getDate());

                            forcast.setSunriseTime(forecastResponse.getAstro().getSr());
                            forcast.setSunsetTime(forecastResponse.getAstro().getSs());

                            forcast.setMaxTemperature(forecastResponse.getTmp().getMax());
                            forcast.setMinTemperature(forecastResponse.getTmp().getMin());

                            forcast.setWindSpeed(forecastResponse.getWind().getSpd());
                            forcast.setWindScale(forecastResponse.getWind().getSc());
                            forcast.setWindDirection(forecastResponse.getWind().getDir());

                            forcast.setWeatherCodeDaytime(forecastResponse.getCond().getCode_d());
                            forcast.setWeatherCodeNight(forecastResponse.getCond().getCode_n());
                            forcast.setWeatherDescriptionDaytime(forecastResponse.getCond().getTxt_d());
                            forcast.setWeatherDescriptionNight(forecastResponse.getCond().getTxt_n());

                            forcast.setRainfall(forecastResponse.getPcpn());
                            forcast.setRainProbability(forecastResponse.getPop());
                            forcast.setHumidity(forecastResponse.getHum());
                            forcast.setAirPressure(forecastResponse.getPres());
                            forcast.setVisibility(forecastResponse.getVis());

                            mForecasts.add(forcast);
                        }
                        weatherEntity.setForecasts(mForecasts);

                        return weatherEntity;
                    }
                });
    }
}
