package com.zeuschan.littlefreshweather.prsentation.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.zeuschan.littlefreshweather.model.entities.WeatherEntity;
import com.zeuschan.littlefreshweather.prsentation.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxiong on 2016/6/12.
 */
public class CityWeatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static final int VIEW_MAIN = 0;
    public static final int VIEW_FORECAST = 1;

    private WeatherEntity mWeatherEntity = null;
    private Context mContext = null;

    public CityWeatherAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setWeatherEntity(WeatherEntity mWeatherEntity) {
        this.mWeatherEntity = mWeatherEntity;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (0 == position) {
            return VIEW_MAIN;
        } else {
            return VIEW_FORECAST;
        }
    }

    @Override
    public int getItemCount() {
        return mWeatherEntity != null ? 2 : 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (VIEW_MAIN == viewType) {
            return new MainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_city_weather_main, parent, false));
        } else {
            return new ForecastViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_city_weather_forecast, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (null == mWeatherEntity)
            return;

        if (0 == position) {
            MainViewHolder mainViewHolder = (MainViewHolder)holder;
            mainViewHolder.tvCityName.setText(mWeatherEntity.getCityName());
            mainViewHolder.tvAirQualityIndex.setText(mWeatherEntity.getAirQulityIndex());
            mainViewHolder.tvAirQualityType.setText(mWeatherEntity.getAirQulityType());
            mainViewHolder.tvWeatherDesc.setText(mWeatherEntity.getWeatherDescription());
            mainViewHolder.tvCurTemp.setText(mWeatherEntity.getCurrentTemperature());
        } else {
            ForecastViewHolder forecastViewHolder = (ForecastViewHolder)holder;
            forecastViewHolder.tvTitleName.setText(R.string.forecast_title);
            forecastViewHolder.lvCityWeatherForecast.setAdapter(new ForecastAdapter(mContext, R.layout.cv_city_weather_forecast_item, mWeatherEntity.getForecasts()));
        }
    }

    public static class ForecastViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_cv_title_name) TextView tvTitleName;
        @BindView(R.id.lv_city_weather_forecast) ListView lvCityWeatherForecast;

        public ForecastViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_city_weather_main_city_name) TextView tvCityName;
        @BindView(R.id.tv_city_weather_main_air_quality_index) TextView tvAirQualityIndex;
        @BindView(R.id.tv_city_weather_main_air_quality_type) TextView tvAirQualityType;
        @BindView(R.id.tv_city_weather_main_weather_desc) TextView tvWeatherDesc;
        @BindView(R.id.tv_city_weather_main_cur_temp) TextView tvCurTemp;

        public MainViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
