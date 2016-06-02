package com.zeuschan.littlefreshweather.model.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chenxiong on 2016/5/30.
 */
public class CitysResponse extends BaseResponse {
    @Expose @SerializedName("city_info") private List<CityInfo> citys;

    public List<CityInfo> getCitys() {
        return citys;
    }

    public void setCitys(List<CityInfo> citys) {
        this.citys = citys;
    }

    @Override
    public String toString() {
        return "CitysResponse{" +
                "citys=" + citys +
                '}';
    }

    public class CityInfo {
        @Expose @SerializedName("id") private String id;
        @Expose @SerializedName("cnty") private String country;
        @Expose @SerializedName("prov") private String province;
        @Expose @SerializedName("city") private String city;
        @Expose @SerializedName("lat") private double latitude;
        @Expose @SerializedName("long") private double longitude;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        @Override
        public String toString() {
            return "CityInfo{" +
                    "city='" + city + '\'' +
                    ", id='" + id + '\'' +
                    ", country='" + country + '\'' +
                    ", province='" + province + '\'' +
                    ", latitude=" + latitude +
                    ", longitude=" + longitude +
                    '}';
        }
    }
}
