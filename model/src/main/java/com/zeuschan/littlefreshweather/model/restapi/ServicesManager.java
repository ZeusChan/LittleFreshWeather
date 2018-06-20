package com.zeuschan.littlefreshweather.model.restapi;

import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.TextureView;

import com.zeuschan.littlefreshweather.common.util.Constants;
import com.zeuschan.littlefreshweather.model.datasource.DataSource;
import com.zeuschan.littlefreshweather.model.entity.CityEntity;
import com.zeuschan.littlefreshweather.model.entity.WeatherConditionEntity;
import com.zeuschan.littlefreshweather.model.entity.WeatherEntity;
import com.zeuschan.littlefreshweather.model.exception.WeatherServiceException;
import com.zeuschan.littlefreshweather.model.response.CityWeatherResponse;
import com.zeuschan.littlefreshweather.model.response.CitysResponse;
import com.zeuschan.littlefreshweather.model.response.ConditionsResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by chenxiong on 2016/5/31.
 */
public class ServicesManager implements DataSource {
    private static final String TAG = ServicesManager.class.getSimpleName();

    private static final int DEFAULT_TIMEOUT = 5;

    private static ServicesManager ourInstance = new ServicesManager();
    public static ServicesManager getInstance() {
        return ourInstance;
    }

    private final WeatherInfoService weatherInfoService;

    private Map<String, String> mNameMap = new android.support.v4.util.ArrayMap<>(6);
    private List<CityEntity> mCityEntities = new ArrayList<>();
    private List<WeatherConditionEntity> mWeatherCondtionEntities = new ArrayList<>();
    private List<WeatherEntity.Forecast> mForecasts = new ArrayList<>();

    private ServicesManager() {
        List<Protocol> protocols = new ArrayList<Protocol>();
        protocols.add(Protocol.HTTP_1_1);
        protocols.add(Protocol.HTTP_2);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.protocols(protocols);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.WEATHER_BASE_URL)
                .client(builder.build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        weatherInfoService = retrofit.create(WeatherInfoService.class);

        mNameMap.put("CN10101", "北京");
        mNameMap.put("CN10102", "上海");
        mNameMap.put("CN10103", "天津");
        mNameMap.put("CN10104", "重庆");
        mNameMap.put("CN10132", "香港");
        mNameMap.put("CN10133", "澳门");
    }

    @Override
    public void clear() {
        mCityEntities.clear();
        mWeatherCondtionEntities.clear();
        mForecasts.clear();
    }

    @Override
    public Observable<List<CityEntity>> getCityEntities() {

        return weatherInfoService.getCityInfos(Constants.CITY_LIST_URL)
                .map(new Func1<String, List<CityEntity>>() {
                    @Override
                    public List<CityEntity> call(String citysResponse) {
                        /*
                        城市/地区编码	    英文	        中文	  国家代码	国家英文	    国家中文	省英文	省中文	所属上级市英文    所属上级市中文	纬度   	经度
                        CN101010100	    beijing	    北京	     CN	     China	     中国	beijing	北京	       beijing	          北京	39.904989	116.405285
                        CN101010200	    haidian	    海淀	     CN	     China	     中国	beijing	北京	       beijing	          北京	39.956074	116.310316
                        CN101010300	    chaoyang	朝阳	     CN	     China	     中国	beijing	北京	       beijing	          北京	39.921489	116.486409
                        */

                        if (TextUtils.isEmpty(citysResponse)) {
                            throw new WeatherServiceException("no data");
                        }

                        mCityEntities.clear();
                        BufferedReader reader = null;
                        try {
                            reader = new BufferedReader(new StringReader(citysResponse));
                            String tempString;
                            while ((tempString = reader.readLine()) != null) {
                                if (tempString.startsWith(Constants.CITY_LIST_LINE_HEADER) || tempString.startsWith(Constants.CITY_LIST_LINE_HEADER_1)) {
                                    tempString = tempString.replace("|", "");
                                    tempString = tempString.trim();
                                    String [] cityFileds = tempString.split("\\s+");
                                    if (cityFileds.length >= Constants.CITY_LIST_FIELD_NUM) {
                                        CityEntity cityEntity = new CityEntity();
                                        cityEntity.setCityId(TextUtils.isEmpty(cityFileds[Constants.CITY_LIST_FIELD_ID]) ? CityEntity.DEFAULT_VALUE : cityFileds[Constants.CITY_LIST_FIELD_ID]);
                                        cityEntity.setCountry(TextUtils.isEmpty(cityFileds[Constants.CITY_LIST_FIELD_COUNTY]) ? CityEntity.DEFAULT_VALUE : cityFileds[Constants.CITY_LIST_FIELD_COUNTY]);
                                        cityEntity.setProvince(TextUtils.isEmpty(cityFileds[Constants.CITY_LIST_FIELD_PROVINCE]) ? CityEntity.DEFAULT_VALUE : cityFileds[Constants.CITY_LIST_FIELD_PROVINCE]);
                                        cityEntity.setCity(TextUtils.isEmpty(cityFileds[Constants.CITY_LIST_FIELD_CITY]) ? CityEntity.DEFAULT_VALUE : cityFileds[Constants.CITY_LIST_FIELD_CITY]);
                                        mCityEntities.add(cityEntity);
                                    }
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (reader != null) {
                                try {
                                    reader.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        /*if (!citysResponse.getResultCode().equals(Constants.OK)) {
                            throw new WeatherServiceException("error code: " + citysResponse.getResultCode());
                        }

                        mCityEntities.clear();
                        List<CitysResponse.CityInfo> cityInfos = citysResponse.getCitys();
                        if (cityInfos != null) {
                            for (CitysResponse.CityInfo cityInfo : cityInfos) {
                                if (cityInfo != null) {
                                    if (cityInfo.getId() != null && cityInfo.getId().length() > 7 && mNameMap.get(cityInfo.getId().subSequence(0, 7)) != null) {
                                        cityInfo.setProvince(mNameMap.get(cityInfo.getId().subSequence(0, 7)));
                                    }
                                    CityEntity cityEntity = new CityEntity();
                                    cityEntity.setCityId(TextUtils.isEmpty(cityInfo.getId()) ? CityEntity.DEFAULT_VALUE : cityInfo.getId());
                                    cityEntity.setCountry(TextUtils.isEmpty(cityInfo.getCountry()) ? CityEntity.DEFAULT_VALUE : cityInfo.getCountry());
                                    cityEntity.setProvince(TextUtils.isEmpty(cityInfo.getProvince()) ? CityEntity.DEFAULT_VALUE : cityInfo.getProvince());
                                    cityEntity.setCity(TextUtils.isEmpty(cityInfo.getCity()) ? CityEntity.DEFAULT_VALUE : cityInfo.getCity());
                                    mCityEntities.add(cityEntity);
                                }
                            }
                        }*/
                        return mCityEntities;
                    }
                });
    }

    @Override
    public Observable<List<WeatherConditionEntity>> getWeatherConditionEntities() {
        Map<String, String> params = new android.support.v4.util.ArrayMap<>(2);
        params.put("search", Constants.SEARCH_ALL_COND);
        params.put("key", Constants.WEATHER_KEY);

        return weatherInfoService.getConditionInfos(params)
                .map(new Func1<ConditionsResponse, List<WeatherConditionEntity>>() {
                    @Override
                    public List<WeatherConditionEntity> call(ConditionsResponse conditionsResponse) {
                        if (null == conditionsResponse) {
                            throw new WeatherServiceException("no data");
                        }
                        if (!conditionsResponse.getResultCode().equals(Constants.OK)) {
                            throw new WeatherServiceException("error code: " + conditionsResponse.getResultCode());
                        }

                        mWeatherCondtionEntities.clear();
                        List<ConditionsResponse.ConditionInfo> weatherConditionInfos = conditionsResponse.getConditions();
                        if (weatherConditionInfos != null) {
                            for (ConditionsResponse.ConditionInfo conditionInfo : weatherConditionInfos) {
                                if (conditionInfo != null) {
                                    WeatherConditionEntity conditionEntity = new WeatherConditionEntity();
                                    conditionEntity.setWeatherCode(TextUtils.isEmpty(conditionInfo.getConditionCode()) ? WeatherConditionEntity.DEFAULT_VALUE : conditionInfo.getConditionCode());
                                    conditionEntity.setWeatherDescription(TextUtils.isEmpty(conditionInfo.getWeatherDescription()) ? WeatherConditionEntity.DEFAULT_VALUE : conditionInfo.getWeatherDescription());
                                    conditionEntity.setWeatherIconUrl(TextUtils.isEmpty(conditionInfo.getWeatherIconUrl()) ? WeatherConditionEntity.DEFAULT_VALUE : conditionInfo.getWeatherIconUrl());
                                    mWeatherCondtionEntities.add(conditionEntity);
                                }
                            }
                        }
                        return mWeatherCondtionEntities;
                    }
                });
    }

    @Override
    public Observable<WeatherEntity> getCityWeather(String cityId, boolean fromCache) {
        Map<String, String> params = new android.support.v4.util.ArrayMap<>(2);
        params.put("city", cityId);
        params.put("key", Constants.WEATHER_KEY);

        return weatherInfoService.getCityWeatherInfo(params)
                .map(new Func1<CityWeatherResponse, WeatherEntity>() {
                    @Override
                    public WeatherEntity call(CityWeatherResponse cityWeatherResponse) {
                        if (null == cityWeatherResponse || null == cityWeatherResponse.getCityWeatherInfos()) {
                            throw new WeatherServiceException("no data");
                        }
                        CityWeatherResponse.CityWeatherInfo cityWeatherInfo = cityWeatherResponse.getCityWeatherInfos().get(0);
                        if (null == cityWeatherInfo) {
                            throw new WeatherServiceException("no data");
                        }
                        if (!cityWeatherInfo.getResultCode().equals(Constants.OK)) {
                            throw new WeatherServiceException("error code: " + cityWeatherInfo.getResultCode());
                        }
                        WeatherEntity weatherEntity = new WeatherEntity();

                        CityWeatherResponse.CityWeatherInfo.Basic basic = cityWeatherInfo.getBasic();
                        if (basic != null) {
                            if (basic.getId() != null)
                                weatherEntity.setCityId(basic.getId());
                            if (basic.getCity() != null)
                                weatherEntity.setCityName(basic.getCity());
                            if (basic.getUpdate() != null && basic.getUpdate().getLoc() != null)
                                weatherEntity.setDataUpdateTime(basic.getUpdate().getLoc());
                        }

                        CityWeatherResponse.CityWeatherInfo.Aqi aqi = cityWeatherInfo.getAqi();
                        if (aqi != null) {
                            CityWeatherResponse.CityWeatherInfo.Aqi.City city = aqi.getCity();
                            if (city != null) {
                                if (city.getAqi() != null)
                                    weatherEntity.setAirQulityIndex(city.getAqi());
                                if (city.getPm25() != null)
                                    weatherEntity.setPm25(city.getPm25());
                                if (city.getPm10() != null)
                                    weatherEntity.setPm10(city.getPm10());
                                if (city.getSo2() != null)
                                    weatherEntity.setSo2(city.getSo2());
                                if (city.getNo2() != null)
                                    weatherEntity.setNo2(city.getNo2());
                                if (city.getCo() != null)
                                    weatherEntity.setCo(city.getCo());
                                if (city.getO3() != null)
                                    weatherEntity.setO3(city.getO3());
                                if (city.getQlty() != null)
                                    weatherEntity.setAirQulityType(city.getQlty());
                            }
                        }

                        CityWeatherResponse.CityWeatherInfo.Now now = cityWeatherInfo.getNow();
                        if (now != null) {
                            CityWeatherResponse.CityWeatherInfo.Now.Cond cond = now.getCond();
                            if (cond != null) {
                                if (cond.getCode() != null)
                                    weatherEntity.setWeatherCode(cond.getCode());
                                if (cond.getTxt() != null)
                                    weatherEntity.setWeatherDescription(cond.getTxt());
                            }
                            if (now.getTmp() != null)
                                weatherEntity.setCurrentTemperature(now.getTmp());
                            if (now.getFl() != null)
                                weatherEntity.setFeltTemperature(now.getFl());
                            if (now.getPcpn() != null)
                                weatherEntity.setRainfall(now.getPcpn());
                            if (now.getHum() != null)
                                weatherEntity.setHumidity(now.getHum());
                            if (now.getPres() != null)
                                weatherEntity.setAirPressure(now.getPres());
                            if (now.getVis() != null)
                                weatherEntity.setVisibility(now.getVis());
                            CityWeatherResponse.CityWeatherInfo.Now.Wind wind = now.getWind();
                            if (wind != null) {
                                if (wind.getSpd() != null)
                                    weatherEntity.setWindSpeed(wind.getSpd());
                                if (wind.getSc() != null)
                                    weatherEntity.setWindScale(wind.getSc());
                                if (wind.getDir() != null)
                                    weatherEntity.setWindDirection(wind.getDir());
                            }
                        }

                        CityWeatherResponse.CityWeatherInfo.Suggestion suggestion = cityWeatherInfo.getSuggestion();
                        if (suggestion != null) {
                            CityWeatherResponse.CityWeatherInfo.Suggestion.Drsg drsg = suggestion.getDrsg();
                            if (drsg != null) {
                                if (drsg.getBrf() != null)
                                    weatherEntity.setDressBrief(drsg.getBrf());
                                if (drsg.getTxt() != null)
                                    weatherEntity.setDressDescription(drsg.getTxt());
                            }
                            CityWeatherResponse.CityWeatherInfo.Suggestion.Uv uv = suggestion.getUv();
                            if (uv != null) {
                                if (uv.getBrf() != null)
                                    weatherEntity.setUvBrief(uv.getBrf());
                                if (uv.getTxt() != null)
                                    weatherEntity.setUvDescription(uv.getTxt());
                            }
                            CityWeatherResponse.CityWeatherInfo.Suggestion.Cw cw = suggestion.getCw();
                            if (cw != null) {
                                if (cw.getBrf() != null)
                                    weatherEntity.setCarWashBrief(cw.getBrf());
                                if (cw.getTxt() != null)
                                    weatherEntity.setCarWashDescription(cw.getTxt());
                            }
                            CityWeatherResponse.CityWeatherInfo.Suggestion.Trav trav = suggestion.getTrav();
                            if (trav != null) {
                                if (trav.getBrf() != null)
                                    weatherEntity.setTravelBrief(trav.getBrf());
                                if (trav.getTxt() != null)
                                    weatherEntity.setTravelDescription(trav.getTxt());
                            }
                            CityWeatherResponse.CityWeatherInfo.Suggestion.Flu flu = suggestion.getFlu();
                            if (flu != null) {
                                if (flu.getBrf() != null)
                                    weatherEntity.setFluBrief(flu.getBrf());
                                if (flu.getTxt() != null)
                                    weatherEntity.setFluDescription(flu.getTxt());
                            }
                            CityWeatherResponse.CityWeatherInfo.Suggestion.Sport sport = suggestion.getSport();
                            if (sport != null) {
                                if (sport.getBrf() != null)
                                    weatherEntity.setSportBrief(sport.getBrf());
                                if (sport.getTxt() != null)
                                    weatherEntity.setSportDescription(sport.getTxt());
                            }
                        }

                        mForecasts.clear();
                        List<CityWeatherResponse.CityWeatherInfo.DailyForecast> forecastsResponse = cityWeatherInfo.getDaily_forecast();
                        if (forecastsResponse != null) {
                            for (CityWeatherResponse.CityWeatherInfo.DailyForecast forecastResponse :
                                    forecastsResponse) {
                                if (forecastResponse != null) {
                                    WeatherEntity.Forecast forcast = new WeatherEntity.Forecast();

                                    if (forecastResponse.getDate() != null)
                                        forcast.setDate(forecastResponse.getDate());

                                    CityWeatherResponse.CityWeatherInfo.DailyForecast.Astro astro = forecastResponse.getAstro();
                                    if (astro != null) {
                                        if (astro.getSr() != null)
                                            forcast.setSunriseTime(astro.getSr());
                                        if (astro.getSs() != null)
                                            forcast.setSunsetTime(astro.getSs());
                                    }

                                    CityWeatherResponse.CityWeatherInfo.DailyForecast.Tmp tmp = forecastResponse.getTmp();
                                    if (tmp != null) {
                                        forcast.setMaxTemperature(tmp.getMax());
                                        forcast.setMinTemperature(tmp.getMin());
                                    }

                                    CityWeatherResponse.CityWeatherInfo.DailyForecast.Wind wind = forecastResponse.getWind();
                                    if (wind != null) {
                                        if (wind.getSpd() != null)
                                            forcast.setWindSpeed(wind.getSpd());
                                        if (wind.getSc() != null)
                                            forcast.setWindScale(wind.getSc());
                                        if (wind.getDir() != null)
                                            forcast.setWindDirection(wind.getDir());
                                    }

                                    CityWeatherResponse.CityWeatherInfo.DailyForecast.Cond cond = forecastResponse.getCond();
                                    if (cond != null) {
                                        if (cond.getCode_d() != null)
                                            forcast.setWeatherCodeDaytime(cond.getCode_d());
                                        if (cond.getCode_n() != null)
                                            forcast.setWeatherCodeNight(cond.getCode_n());
                                        if (cond.getTxt_d() != null)
                                            forcast.setWeatherDescriptionDaytime(cond.getTxt_d());
                                        if (cond.getTxt_n() != null)
                                            forcast.setWeatherDescriptionNight(cond.getTxt_n());
                                    }

                                    if (forecastResponse.getPcpn() != null)
                                        forcast.setRainfall(forecastResponse.getPcpn());
                                    if (forecastResponse.getPop() != null)
                                        forcast.setRainProbability(forecastResponse.getPop());
                                    if (forecastResponse.getHum() != null)
                                        forcast.setHumidity(forecastResponse.getHum());
                                    if (forecastResponse.getPres() != null)
                                        forcast.setAirPressure(forecastResponse.getPres());
                                    if (forecastResponse.getVis() != null)
                                        forcast.setVisibility(forecastResponse.getVis());

                                    mForecasts.add(forcast);
                                }
                            }
                        }
                        weatherEntity.setForecasts(mForecasts);

                        return weatherEntity;
                    }
                });
    }
}
