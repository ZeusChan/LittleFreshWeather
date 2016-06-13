package com.zeuschan.littlefreshweather.model.entities;

import java.util.List;

/**
 * Created by chenxiong on 2016/5/30.
 */
public class WeatherEntity {
    /**
     * 城市基本信息
     */
    // 城市ID
    private String cityId;
    // 城市名称
    private String cityName;
    // 数据更新当地时间
    private String dataUpdateTime;

    /**
     * 空气质量
     */
    // 空气质量指数
    private String airQulityIndex;
    // PM2.5一小时均值（ug/m3）
    private String pm25;
    // PM10一小时均值（ug/m3）
    private String pm10;
    // 二氧化硫一小时均值（ug/m3）
    private String so2;
    // 二氧化氮一小时均值（ug/m3）
    private String no2;
    // 一氧化碳一小时均值（ug/m3）
    private String co;
    // 臭氧一小时均值（ug/m3）
    private String o3;
    // 空气质量类别
    private String airQulityType;

    /**
     * 实况天气
     */
    // 天气代码
    private String weatherCode;
    // 天气描述
    private String weatherDescription;
    // 当前温度
    private String currentTemperature;
    // 体感温度
    private String feltTemperature;
    // 降雨量（mm）
    private String rainfall;
    // 湿度（%）
    private String humidity;
    // 气压
    private String airPressure;
    // 能见度（km）
    private String visibility;
    // 风速（kmph）
    private String windSpeed;
    // 风力等级
    private String windScale;
    // 风向
    private String windDirection;

    /**
     * 生活指数
     */
    // 穿衣指数
    private String dressBrief;
    private String dressDescription;
    // 紫外线指数
    private String uvBrief;
    private String uvDescription;
    // 洗车指数
    private String carWashBrief;
    private String carWashDescription;
    // 旅游指数
    private String travelBrief;
    private String travelDescription;
    // 感冒指数
    private String fluBrief;
    private String fluDescription;
    // 运动指数
    private String sportBrief;
    private String sportDescription;

    /**
     * 一周天气
     */
    private List<Forecast> forecasts;

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

    public static class Forecast {
        // 日期
        private String date;

        // 日出时间
        private String sunriseTime;
        // 日落时间
        private String sunsetTime;

        // 最高温度（摄氏度）
        private String maxTemperature;
        // 最低温度（摄氏度）
        private String minTemperature;

        // 风速（kmph）
        private String windSpeed;
        // 风力等级
        private String windScale;
        // 风向
        private String windDirection;

        // 白天天气代码
        private String weatherCodeDaytime;
        // 白天天气描述
        private String weatherDescriptionDaytime;
        // 夜间天气代码
        private String weatherCodeNight;
        // 夜间天气描述
        private String weatherDescriptionNight;

        // 降雨量（mm）
        private double rainfall;
        // 降水概率
        private String rainProbability;
        // 湿度（%）
        private String humidity;
        // 气压
        private String airPressure;
        // 能见度（km）
        private String visibility;

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

        public double getRainfall() {
            return rainfall;
        }

        public void setRainfall(double rainfall) {
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
    }
}
