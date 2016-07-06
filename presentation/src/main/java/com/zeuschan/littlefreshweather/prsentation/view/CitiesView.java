package com.zeuschan.littlefreshweather.prsentation.view;

import com.zeuschan.littlefreshweather.model.entity.CityEntity;
import com.zeuschan.littlefreshweather.prsentation.view.BaseView;

import java.util.List;

/**
 * Created by chenxiong on 2016/6/21.
 */
public interface CitiesView extends BaseView {
    public void showCityNameEdit();
    public void hideCityNameEdit();
    public void refreshCandidatesList(List<CityEntity> candidates);
    public void navigateToCityWeatherActivity(String cityId);
    public void setLocatedCityName(String locatedName, String curName);
    public void showLocatedCityName();
    public void hideLocatedCityName();
}
