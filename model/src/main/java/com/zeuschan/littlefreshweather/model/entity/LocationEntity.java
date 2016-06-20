package com.zeuschan.littlefreshweather.model.entity;

/**
 * Created by chenxiong on 2016/6/20.
 */
public class LocationEntity {
    private String country;
    private String province;
    private String city;
    private String cityCode;
    private String district;

    @Override
    public String toString() {
        return "LocationEntity{" +
                "city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", district='" + district + '\'' +
                '}';
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
