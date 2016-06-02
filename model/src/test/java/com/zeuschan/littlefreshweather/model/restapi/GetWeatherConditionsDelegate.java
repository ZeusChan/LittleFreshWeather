package com.zeuschan.littlefreshweather.model.restapi;

import com.zeuschan.littlefreshweather.model.entities.WeatherConditionEntity;

import java.util.List;

/**
 * Created by chenxiong on 2016/6/2.
 */
public class GetWeatherConditionsDelegate extends TestSubscriberDelegate<List<WeatherConditionEntity>> {
    @Override
    protected void LogContent(List<WeatherConditionEntity> weatherConditionEntities) {
        for (WeatherConditionEntity entity :
                weatherConditionEntities) {
            logger.info(entity.toString());
        }
    }
}
