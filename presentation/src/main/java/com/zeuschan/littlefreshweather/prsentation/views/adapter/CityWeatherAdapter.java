package com.zeuschan.littlefreshweather.prsentation.views.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zeuschan.littlefreshweather.model.entities.WeatherEntity;
import com.zeuschan.littlefreshweather.prsentation.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxiong on 2016/6/12.
 */
public class CityWeatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private WeatherEntity weatherEntity = null;

    public void setWeatherEntity(WeatherEntity weatherEntity) {
        this.weatherEntity = weatherEntity;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return weatherEntity != null ? 1 : 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_city_weather_main, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (weatherEntity != null) {
            MainViewHolder mainViewHolder = (MainViewHolder)holder;
            mainViewHolder.tvCityName.setText(weatherEntity.getCityName());
            mainViewHolder.tvAirQualityIndex.setText(weatherEntity.getAirQulityIndex());
            mainViewHolder.tvCurTemp.setText(weatherEntity.getCurrentTemperature());
        }
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_city_weather_main_city_name) TextView tvCityName;
        @BindView(R.id.tv_city_weather_main_air_quality_index) TextView tvAirQualityIndex;
        @BindView(R.id.tv_city_weather_main_cur_temp) TextView tvCurTemp;

        public MainViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
