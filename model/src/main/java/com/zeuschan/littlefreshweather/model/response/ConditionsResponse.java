package com.zeuschan.littlefreshweather.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chenxiong on 2016/5/30.
 */
public class ConditionsResponse extends BaseResponse {
    @Expose @SerializedName("cond_info") private List<ConditionInfo> conditions;

    public List<ConditionInfo> getConditions() {
        return conditions;
    }

    public void setConditions(List<ConditionInfo> conditions) {
        this.conditions = conditions;
    }

    @Override
    public String toString() {
        return "ConditionsResponse{" +
                "conditions=" + conditions +
                '}';
    }

    public class ConditionInfo {
        @Expose @SerializedName("code") private String conditionCode;
        @Expose @SerializedName("txt") private String weatherDescription;
        @Expose @SerializedName("icon") private String weatherIconUrl;

        public String getConditionCode() {
            return conditionCode;
        }

        public void setConditionCode(String conditionCode) {
            this.conditionCode = conditionCode;
        }

        public String getWeatherDescription() {
            return weatherDescription;
        }

        public void setWeatherDescription(String weatherDescription) {
            this.weatherDescription = weatherDescription;
        }

        public String getWeatherIconUrl() {
            return weatherIconUrl;
        }

        public void setWeatherIconUrl(String weatherIconUrl) {
            this.weatherIconUrl = weatherIconUrl;
        }

        @Override
        public String toString() {
            return "ConditionInfo{" +
                    "conditionCode=" + conditionCode +
                    ", weatherDescription='" + weatherDescription + '\'' +
                    ", weatherIconUrl='" + weatherIconUrl + '\'' +
                    '}';
        }
    }
}
