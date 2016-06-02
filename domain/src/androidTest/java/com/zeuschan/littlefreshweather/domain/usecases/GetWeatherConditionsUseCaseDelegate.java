package com.zeuschan.littlefreshweather.domain.usecases;

import android.util.Log;

import com.zeuschan.littlefreshweather.model.entities.WeatherConditionEntity;

import java.util.List;

/**
 * Created by chenxiong on 2016/6/2.
 */
public class GetWeatherConditionsUseCaseDelegate extends TestSubscriberDelegate<List<WeatherConditionEntity>> {
    @Override
    protected void LogContent(List<WeatherConditionEntity> weatherConditionEntities) {
        for (WeatherConditionEntity entity :
                weatherConditionEntities) {
            Log.e(TAG, entity.toString());
        }
    }
}
