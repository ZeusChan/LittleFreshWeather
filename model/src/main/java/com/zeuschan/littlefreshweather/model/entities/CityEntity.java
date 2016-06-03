package com.zeuschan.littlefreshweather.model.entities;

/**
 * Created by chenxiong on 2016/5/31.
 */
public class CityEntity {
    private String cityId;
    private String country;
    private String province;
    private String city;

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