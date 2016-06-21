package com.zeuschan.littlefreshweather.model.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chenxiong on 2016/5/31.
 */
public class CityEntity {
    public static final String DEFAULT_VALUE = "--";

    @SerializedName("city_id") private String cityId;
    @SerializedName("country") private String country;
    @SerializedName("province") private String province;
    @SerializedName("city") private String city;

    public CityEntity() {
        cityId = DEFAULT_VALUE;
        country = DEFAULT_VALUE;
        province = DEFAULT_VALUE;
        city = DEFAULT_VALUE;
    }

    @Override
    public String toString() {
        return "CityEntity{" +
                "city='" + city + '\'' +
                ", cityId='" + cityId + '\'' +
                ", country='" + country + '\'' +
                ", province='" + province + '\'' +
                '}';
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
