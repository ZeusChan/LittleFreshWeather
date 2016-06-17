package com.zeuschan.littlefreshweather.model.restapi;

import com.zeuschan.littlefreshweather.model.entity.CityEntity;

import java.util.List;

/**
 * Created by chenxiong on 2016/6/2.
 */
public class GetCitysDelegate extends TestSubscriberDelegate<List<CityEntity>> {
    @Override
    protected void LogContent(List<CityEntity> cityEntities) {
        for (CityEntity entity :
                cityEntities) {
            logger.info(entity.toString());
        }
    }
}
