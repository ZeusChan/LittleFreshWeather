package com.zeuschan.littlefreshweather.model.cache;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zeuschan.littlefreshweather.model.entity.CityEntity;
import com.zeuschan.littlefreshweather.model.entity.WeatherConditionEntity;
import com.zeuschan.littlefreshweather.model.entity.WeatherEntity;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by chenxiong on 2016/6/16.
 */
public class JsonProcesser {

    private final Gson mGson = new Gson();

    public JsonProcesser() {

    }

    public String cityEntitiesToJson(List<CityEntity> cityEntities) {
        Type listOfCityEntityType = new TypeToken<List<CityEntity>>() {}.getType();
        return mGson.toJson(cityEntities, listOfCityEntityType);
    }

    public List<CityEntity> jsonToCityEntities(String cityEntitiesString) {
        Type listOfCityEntityType = new TypeToken<List<CityEntity>>() {}.getType();
        return mGson.fromJson(cityEntitiesString, listOfCityEntityType);
    }

    public String weatherConditionEntitiesToJson(List<WeatherConditionEntity> weatherConditionEntities) {
        Type listOfWeatherConditionEntitiesType = new TypeToken<List<WeatherConditionEntity>>() {}.getType();
        return mGson.toJson(weatherConditionEntities, listOfWeatherConditionEntitiesType);
    }

    public List<WeatherConditionEntity> jsonToWeatherConditionEntities(String weatherConditionEntities) {
        Type listOfWeatherConditionEntitiesType = new TypeToken<List<WeatherConditionEntity>>() {}.getType();
        return mGson.fromJson(weatherConditionEntities, listOfWeatherConditionEntitiesType);
    }

    public String weatherEntityToJson(WeatherEntity weatherEntity) {
        Type weatherEntityType = new TypeToken<WeatherEntity>() {}.getType();
        return mGson.toJson(weatherEntity, weatherEntityType);
    }

    public WeatherEntity jsonToWeatherEntity(String weatherEntityString) {
        Type weatherEntityType = new TypeToken<WeatherEntity>() {}.getType();
        return mGson.fromJson(weatherEntityString, weatherEntityType);
    }
}
