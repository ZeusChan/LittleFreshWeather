package com.zeuschan.littlefreshweather.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxiong on 2016/5/30.
 */
public class WeatherEntity implements Parcelable {
    public static final String DEFAULT_VALUE = "--";

    /**
     * 城市基本信息
     */
    // 城市ID
    @SerializedName("city_id") private String cityId;
    // 城市名称
    @SerializedName("city_name") private String cityName;
    // 数据更新当地时间
    @SerializedName("data_update_time") private String dataUpdateTime;

    /**
     * 空气质量
     */
    // 空气质量指数
    @SerializedName("air_quality_index") private String airQulityIndex;
    // PM2.5一小时均值（ug/m3）
    @SerializedName("pm25") private String pm25;
    // PM10一小时均值（ug/m3）
    @SerializedName("pm10") private String pm10;
    // 二氧化硫一小时均值（ug/m3）
    @SerializedName("so2") private String so2;
    // 二氧化氮一小时均值（ug/m3）
    @SerializedName("no2") private String no2;
    // 一氧化碳一小时均值（ug/m3）
    @SerializedName("co") private String co;
    // 臭氧一小时均值（ug/m3）
    @SerializedName("o3") private String o3;
    // 空气质量类别
    @SerializedName("air_quality_type") private String airQulityType;

    /**
     * 实况天气
     */
    // 天气代码
    @SerializedName("weather_code") private String weatherCode;
    // 天气描述
    @SerializedName("weather_desc") private String weatherDescription;
    // 当前温度
    @SerializedName("cur_temp") private String currentTemperature;
    // 体感温度
    @SerializedName("felt_temp") private String feltTemperature;
    // 降雨量（mm）
    @SerializedName("rain_fall") private String rainfall;
    // 湿度（%）
    @SerializedName("humidity") private String humidity;
    // 气压
    @SerializedName("air_press") private String airPressure;
    // 能见度（km）
    @SerializedName("visibility") private String visibility;
    // 风速（kmph）
    @SerializedName("wind_speed") private String windSpeed;
    // 风力等级
    @SerializedName("wind_scale") private String windScale;
    // 风向
    @SerializedName("wind_direction") private String windDirection;

    /**
     * 生活指数
     */
    // 穿衣指数
    @SerializedName("dress_br") private String dressBrief;
    @SerializedName("dress_desc") private String dressDescription;
    // 紫外线指数
    @SerializedName("uv_br") private String uvBrief;
    @SerializedName("uv_desc") private String uvDescription;
    // 洗车指数
    @SerializedName("car_wash_br") private String carWashBrief;
    @SerializedName("car_wash_desc") private String carWashDescription;
    // 旅游指数
    @SerializedName("travel_br") private String travelBrief;
    @SerializedName("travel_desc") private String travelDescription;
    // 感冒指数
    @SerializedName("flu_br") private String fluBrief;
    @SerializedName("flu_desc") private String fluDescription;
    // 运动指数
    @SerializedName("sport_br") private String sportBrief;
    @SerializedName("sport_desc") private String sportDescription;

    /**
     * 一周天气
     */
    @SerializedName("forecasts") private List<Forecast> forecasts;

    public WeatherEntity() {
        cityId = DEFAULT_VALUE;
        cityName = DEFAULT_VALUE;
        dataUpdateTime = DEFAULT_VALUE;
        airQulityIndex = DEFAULT_VALUE;
        pm25 = DEFAULT_VALUE;
        pm10 = DEFAULT_VALUE;
        so2 = DEFAULT_VALUE;
        no2 = DEFAULT_VALUE;
        co = DEFAULT_VALUE;
        o3 = DEFAULT_VALUE;
        airQulityType = DEFAULT_VALUE;
        weatherCode = DEFAULT_VALUE;
        weatherDescription = DEFAULT_VALUE;
        currentTemperature = DEFAULT_VALUE;
        feltTemperature = DEFAULT_VALUE;
        rainfall = DEFAULT_VALUE;
        humidity = DEFAULT_VALUE;
        airPressure = DEFAULT_VALUE;
        visibility = DEFAULT_VALUE;
        windSpeed = DEFAULT_VALUE;
        windScale = DEFAULT_VALUE;
        windDirection = DEFAULT_VALUE;
        dressBrief = DEFAULT_VALUE;
        dressDescription = DEFAULT_VALUE;
        uvBrief = DEFAULT_VALUE;
        uvDescription = DEFAULT_VALUE;
        carWashBrief = DEFAULT_VALUE;
        carWashDescription = DEFAULT_VALUE;
        travelBrief = DEFAULT_VALUE;
        travelDescription = DEFAULT_VALUE;
        fluBrief = DEFAULT_VALUE;
        fluDescription = DEFAULT_VALUE;
        sportBrief = DEFAULT_VALUE;
        sportDescription = DEFAULT_VALUE;
        forecasts = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "WeatherEntity{" +
                "airPressure=" + airPressure +
                ", cityId='" + cityId + '\'' +
                ", cityName='" + cityName + '\'' +
                ", dataUpdateTime='" + dataUpdateTime + '\'' +
                ", airQulityIndex=" + airQulityIndex +
                ", pm25=" + pm25 +
                ", pm10=" + pm10 +
                ", so2=" + so2 +
                ", no2=" + no2 +
                ", co=" + co +
                ", o3=" + o3 +
                ", airQulityType='" + airQulityType + '\'' +
                ", weatherCode=" + weatherCode +
                ", weatherDescription='" + weatherDescription + '\'' +
                ", currentTemperature=" + currentTemperature +
                ", feltTemperature=" + feltTemperature +
                ", rainfall=" + rainfall +
                ", humidity=" + humidity +
                ", visibility=" + visibility +
                ", windSpeed=" + windSpeed +
                ", windScale=" + windScale +
                ", windDirection='" + windDirection + '\'' +
                ", dressBrief='" + dressBrief + '\'' +
                ", dressDescription='" + dressDescription + '\'' +
                ", uvBrief='" + uvBrief + '\'' +
                ", uvDescription='" + uvDescription + '\'' +
                ", carWashBrief='" + carWashBrief + '\'' +
                ", carWashDescription='" + carWashDescription + '\'' +
                ", travelBrief='" + travelBrief + '\'' +
                ", travelDescription='" + travelDescription + '\'' +
                ", fluBrief='" + fluBrief + '\'' +
                ", fluDescription='" + fluDescription + '\'' +
                ", sportBrief='" + sportBrief + '\'' +
                ", sportDescription='" + sportDescription + '\'' +
                ", forecasts=" + forecasts +
                '}';
    }

    public String getAirPressure() {
        return airPressure;
    }

    public void setAirPressure(String airPressure) {
        this.airPressure = airPressure;
    }

    public String getAirQulityIndex() {
        return airQulityIndex;
    }

    public void setAirQulityIndex(String airQulityIndex) {
        this.airQulityIndex = airQulityIndex;
    }

    public String getAirQulityType() {
        return airQulityType;
    }

    public void setAirQulityType(String airQulityType) {
        this.airQulityType = airQulityType;
    }

    public String getCarWashBrief() {
        return carWashBrief;
    }

    public void setCarWashBrief(String carWashBrief) {
        this.carWashBrief = carWashBrief;
    }

    public String getCarWashDescription() {
        return carWashDescription;
    }

    public void setCarWashDescription(String carWashDescription) {
        this.carWashDescription = carWashDescription;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public String getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(String currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public String getDataUpdateTime() {
        return dataUpdateTime;
    }

    public void setDataUpdateTime(String dataUpdateTime) {
        this.dataUpdateTime = dataUpdateTime;
    }

    public String getDressBrief() {
        return dressBrief;
    }

    public void setDressBrief(String dressBrief) {
        this.dressBrief = dressBrief;
    }

    public String getDressDescription() {
        return dressDescription;
    }

    public void setDressDescription(String dressDescription) {
        this.dressDescription = dressDescription;
    }

    public String getFeltTemperature() {
        return feltTemperature;
    }

    public void setFeltTemperature(String feltTemperature) {
        this.feltTemperature = feltTemperature;
    }

    public String getFluBrief() {
        return fluBrief;
    }

    public void setFluBrief(String fluBrief) {
        this.fluBrief = fluBrief;
    }

    public String getFluDescription() {
        return fluDescription;
    }

    public void setFluDescription(String fluDescription) {
        this.fluDescription = fluDescription;
    }

    public List<Forecast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<Forecast> forecasts) {
        this.forecasts = forecasts;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getNo2() {
        return no2;
    }

    public void setNo2(String no2) {
        this.no2 = no2;
    }

    public String getO3() {
        return o3;
    }

    public void setO3(String o3) {
        this.o3 = o3;
    }

    public String getPm10() {
        return pm10;
    }

    public void setPm10(String pm10) {
        this.pm10 = pm10;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getRainfall() {
        return rainfall;
    }

    public void setRainfall(String rainfall) {
        this.rainfall = rainfall;
    }

    public String getSo2() {
        return so2;
    }

    public void setSo2(String so2) {
        this.so2 = so2;
    }

    public String getSportBrief() {
        return sportBrief;
    }

    public void setSportBrief(String sportBrief) {
        this.sportBrief = sportBrief;
    }

    public String getSportDescription() {
        return sportDescription;
    }

    public void setSportDescription(String sportDescription) {
        this.sportDescription = sportDescription;
    }

    public String getTravelBrief() {
        return travelBrief;
    }

    public void setTravelBrief(String travelBrief) {
        this.travelBrief = travelBrief;
    }

    public String getTravelDescription() {
        return travelDescription;
    }

    public void setTravelDescription(String travelDescription) {
        this.travelDescription = travelDescription;
    }

    public String getUvBrief() {
        return uvBrief;
    }

    public void setUvBrief(String uvBrief) {
        this.uvBrief = uvBrief;
    }

    public String getUvDescription() {
        return uvDescription;
    }

    public void setUvDescription(String uvDescription) {
        this.uvDescription = uvDescription;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getWindScale() {
        return windScale;
    }

    public void setWindScale(String windScale) {
        this.windScale = windScale;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public static class Forecast implements Parcelable {
        // 日期
        @SerializedName("date") private String date;

        // 日出时间
        @SerializedName("sun_rise") private String sunriseTime;
        // 日落时间
        @SerializedName("sun_set") private String sunsetTime;

        // 最高温度（摄氏度）
        @SerializedName("max_temp") private String maxTemperature;
        // 最低温度（摄氏度）
        @SerializedName("min_temp") private String minTemperature;

        // 风速（kmph）
        @SerializedName("wind_speed") private String windSpeed;
        // 风力等级
        @SerializedName("wind_scale") private String windScale;
        // 风向
        @SerializedName("wind_direction") private String windDirection;

        // 白天天气代码
        @SerializedName("weather_code_d") private String weatherCodeDaytime;
        // 白天天气描述
        @SerializedName("weather_desc_d") private String weatherDescriptionDaytime;
        // 夜间天气代码
        @SerializedName("weather_code_n") private String weatherCodeNight;
        // 夜间天气描述
        @SerializedName("weather_desc_n") private String weatherDescriptionNight;

        // 降雨量（mm）
        @SerializedName("rain_fall") private String rainfall;
        // 降水概率
        @SerializedName("rain_prob") private String rainProbability;
        // 湿度（%）
        @SerializedName("humidity") private String humidity;
        // 气压
        @SerializedName("air_press") private String airPressure;
        // 能见度（km）
        @SerializedName("visibility") private String visibility;

        public Forecast() {
            date = DEFAULT_VALUE;
            sunriseTime = DEFAULT_VALUE;
            sunsetTime = DEFAULT_VALUE;
            maxTemperature = DEFAULT_VALUE;
            minTemperature = DEFAULT_VALUE;
            windSpeed = DEFAULT_VALUE;
            windScale = DEFAULT_VALUE;
            windDirection = DEFAULT_VALUE;
            weatherCodeDaytime = DEFAULT_VALUE;
            weatherDescriptionDaytime = DEFAULT_VALUE;
            weatherCodeNight = DEFAULT_VALUE;
            weatherDescriptionNight = DEFAULT_VALUE;
            rainfall = DEFAULT_VALUE;
            rainProbability = DEFAULT_VALUE;
            humidity = DEFAULT_VALUE;
            airPressure = DEFAULT_VALUE;
            visibility = DEFAULT_VALUE;
        }

        @Override
        public String toString() {
            return "Forecast{" +
                    "airPressure=" + airPressure +
                    ", date='" + date + '\'' +
                    ", sunriseTime='" + sunriseTime + '\'' +
                    ", sunsetTime='" + sunsetTime + '\'' +
                    ", maxTemperature=" + maxTemperature +
                    ", minTemperature=" + minTemperature +
                    ", windSpeed=" + windSpeed +
                    ", windScale=" + windScale +
                    ", windDirection='" + windDirection + '\'' +
                    ", weatherCodeDaytime=" + weatherCodeDaytime +
                    ", weatherDescriptionDaytime='" + weatherDescriptionDaytime + '\'' +
                    ", weatherCodeNight=" + weatherCodeNight +
                    ", weatherDescriptionNight='" + weatherDescriptionNight + '\'' +
                    ", rainfall=" + rainfall +
                    ", rainProbability=" + rainProbability +
                    ", humidity=" + humidity +
                    ", visibility=" + visibility +
                    '}';
        }

        public String getAirPressure() {
            return airPressure;
        }

        public void setAirPressure(String airPressure) {
            this.airPressure = airPressure;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public String getMaxTemperature() {
            return maxTemperature;
        }

        public void setMaxTemperature(String maxTemperature) {
            this.maxTemperature = maxTemperature;
        }

        public String getMinTemperature() {
            return minTemperature;
        }

        public void setMinTemperature(String minTemperature) {
            this.minTemperature = minTemperature;
        }

        public String getRainfall() {
            return rainfall;
        }

        public void setRainfall(String rainfall) {
            this.rainfall = rainfall;
        }

        public String getRainProbability() {
            return rainProbability;
        }

        public void setRainProbability(String rainProbability) {
            this.rainProbability = rainProbability;
        }

        public String getSunriseTime() {
            return sunriseTime;
        }

        public void setSunriseTime(String sunriseTime) {
            this.sunriseTime = sunriseTime;
        }

        public String getSunsetTime() {
            return sunsetTime;
        }

        public void setSunsetTime(String sunsetTime) {
            this.sunsetTime = sunsetTime;
        }

        public String getVisibility() {
            return visibility;
        }

        public void setVisibility(String visibility) {
            this.visibility = visibility;
        }

        public String getWeatherCodeDaytime() {
            return weatherCodeDaytime;
        }

        public void setWeatherCodeDaytime(String weatherCodeDaytime) {
            this.weatherCodeDaytime = weatherCodeDaytime;
        }

        public String getWeatherCodeNight() {
            return weatherCodeNight;
        }

        public void setWeatherCodeNight(String weatherCodeNight) {
            this.weatherCodeNight = weatherCodeNight;
        }

        public String getWeatherDescriptionDaytime() {
            return weatherDescriptionDaytime;
        }

        public void setWeatherDescriptionDaytime(String weatherDescriptionDaytime) {
            this.weatherDescriptionDaytime = weatherDescriptionDaytime;
        }

        public String getWeatherDescriptionNight() {
            return weatherDescriptionNight;
        }

        public void setWeatherDescriptionNight(String weatherDescriptionNight) {
            this.weatherDescriptionNight = weatherDescriptionNight;
        }

        public String getWindDirection() {
            return windDirection;
        }

        public void setWindDirection(String windDirection) {
            this.windDirection = windDirection;
        }

        public String getWindScale() {
            return windScale;
        }

        public void setWindScale(String windScale) {
            this.windScale = windScale;
        }

        public String getWindSpeed() {
            return windSpeed;
        }

        public void setWindSpeed(String windSpeed) {
            this.windSpeed = windSpeed;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.date);
            dest.writeString(this.sunriseTime);
            dest.writeString(this.sunsetTime);
            dest.writeString(this.maxTemperature);
            dest.writeString(this.minTemperature);
            dest.writeString(this.windSpeed);
            dest.writeString(this.windScale);
            dest.writeString(this.windDirection);
            dest.writeString(this.weatherCodeDaytime);
            dest.writeString(this.weatherDescriptionDaytime);
            dest.writeString(this.weatherCodeNight);
            dest.writeString(this.weatherDescriptionNight);
            dest.writeString(this.rainfall);
            dest.writeString(this.rainProbability);
            dest.writeString(this.humidity);
            dest.writeString(this.airPressure);
            dest.writeString(this.visibility);
        }

        protected Forecast(Parcel in) {
            this.date = in.readString();
            this.sunriseTime = in.readString();
            this.sunsetTime = in.readString();
            this.maxTemperature = in.readString();
            this.minTemperature = in.readString();
            this.windSpeed = in.readString();
            this.windScale = in.readString();
            this.windDirection = in.readString();
            this.weatherCodeDaytime = in.readString();
            this.weatherDescriptionDaytime = in.readString();
            this.weatherCodeNight = in.readString();
            this.weatherDescriptionNight = in.readString();
            this.rainfall = in.readString();
            this.rainProbability = in.readString();
            this.humidity = in.readString();
            this.airPressure = in.readString();
            this.visibility = in.readString();
        }

        public static final Creator<Forecast> CREATOR = new Creator<Forecast>() {
            @Override
            public Forecast createFromParcel(Parcel source) {
                return new Forecast(source);
            }

            @Override
            public Forecast[] newArray(int size) {
                return new Forecast[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cityId);
        dest.writeString(this.cityName);
        dest.writeString(this.dataUpdateTime);
        dest.writeString(this.airQulityIndex);
        dest.writeString(this.pm25);
        dest.writeString(this.pm10);
        dest.writeString(this.so2);
        dest.writeString(this.no2);
        dest.writeString(this.co);
        dest.writeString(this.o3);
        dest.writeString(this.airQulityType);
        dest.writeString(this.weatherCode);
        dest.writeString(this.weatherDescription);
        dest.writeString(this.currentTemperature);
        dest.writeString(this.feltTemperature);
        dest.writeString(this.rainfall);
        dest.writeString(this.humidity);
        dest.writeString(this.airPressure);
        dest.writeString(this.visibility);
        dest.writeString(this.windSpeed);
        dest.writeString(this.windScale);
        dest.writeString(this.windDirection);
        dest.writeString(this.dressBrief);
        dest.writeString(this.dressDescription);
        dest.writeString(this.uvBrief);
        dest.writeString(this.uvDescription);
        dest.writeString(this.carWashBrief);
        dest.writeString(this.carWashDescription);
        dest.writeString(this.travelBrief);
        dest.writeString(this.travelDescription);
        dest.writeString(this.fluBrief);
        dest.writeString(this.fluDescription);
        dest.writeString(this.sportBrief);
        dest.writeString(this.sportDescription);
        dest.writeTypedList(this.forecasts);
    }

    protected WeatherEntity(Parcel in) {
        this.cityId = in.readString();
        this.cityName = in.readString();
        this.dataUpdateTime = in.readString();
        this.airQulityIndex = in.readString();
        this.pm25 = in.readString();
        this.pm10 = in.readString();
        this.so2 = in.readString();
        this.no2 = in.readString();
        this.co = in.readString();
        this.o3 = in.readString();
        this.airQulityType = in.readString();
        this.weatherCode = in.readString();
        this.weatherDescription = in.readString();
        this.currentTemperature = in.readString();
        this.feltTemperature = in.readString();
        this.rainfall = in.readString();
        this.humidity = in.readString();
        this.airPressure = in.readString();
        this.visibility = in.readString();
        this.windSpeed = in.readString();
        this.windScale = in.readString();
        this.windDirection = in.readString();
        this.dressBrief = in.readString();
        this.dressDescription = in.readString();
        this.uvBrief = in.readString();
        this.uvDescription = in.readString();
        this.carWashBrief = in.readString();
        this.carWashDescription = in.readString();
        this.travelBrief = in.readString();
        this.travelDescription = in.readString();
        this.fluBrief = in.readString();
        this.fluDescription = in.readString();
        this.sportBrief = in.readString();
        this.sportDescription = in.readString();
        this.forecasts = in.createTypedArrayList(Forecast.CREATOR);
    }

    public static final Creator<WeatherEntity> CREATOR = new Creator<WeatherEntity>() {
        @Override
        public WeatherEntity createFromParcel(Parcel source) {
            return new WeatherEntity(source);
        }

        @Override
        public WeatherEntity[] newArray(int size) {
            return new WeatherEntity[size];
        }
    };
}
