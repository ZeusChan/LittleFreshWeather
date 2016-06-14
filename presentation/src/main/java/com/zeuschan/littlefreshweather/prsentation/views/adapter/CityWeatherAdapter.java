package com.zeuschan.littlefreshweather.prsentation.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.zeuschan.littlefreshweather.model.entities.WeatherEntity;
import com.zeuschan.littlefreshweather.prsentation.R;
import com.zeuschan.littlefreshweather.prsentation.wrappers.CurWeatherInfoWrapper;
import com.zeuschan.littlefreshweather.prsentation.wrappers.LifeIndexWrapper;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxiong on 2016/6/12.
 */
public class CityWeatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static final int VIEW_MAIN = 0;
    public static final int VIEW_FORECAST = 1;
    public static final int VIEW_CURRENT_WEATHER_INFO = 2;
    public static final int VIEW_LIFE_INDEX = 3;

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
        } else if (1 == position) {
            return VIEW_FORECAST;
        } else if (2 == position) {
            return VIEW_CURRENT_WEATHER_INFO;
        } else {
            return VIEW_LIFE_INDEX;
        }
    }

    @Override
    public int getItemCount() {
        return mWeatherEntity != null ? 4 : 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (VIEW_MAIN == viewType) {
            return new MainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_city_weather_main, parent, false));
        } else if (VIEW_FORECAST == viewType) {
            return new ForecastViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_city_weather_forecast, parent, false));
        } else if (VIEW_CURRENT_WEATHER_INFO == viewType) {
            return new CurWeatherInfoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_city_weather_cur_weather_info, parent, false));
        } else {
            return new LifeIndexViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_city_weather_life_index, parent, false));
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
        } else if (1 == position) {
            ForecastViewHolder forecastViewHolder = (ForecastViewHolder)holder;
            forecastViewHolder.tvTitleName.setText(R.string.forecast_title);
            forecastViewHolder.lvCityWeatherForecast.setAdapter(new ForecastAdapter(mContext, R.layout.cv_city_weather_forecast_item, mWeatherEntity.getForecasts()));
        } else if (2 == position) {
            CurWeatherInfoViewHolder curWeatherInfoViewHolder = (CurWeatherInfoViewHolder)holder;
            curWeatherInfoViewHolder.tvTitleName.setText(R.string.current_weather_info);
            List<CurWeatherInfoWrapper> listWeatherInfo = new ArrayList<>();
            listWeatherInfo.add(new CurWeatherInfoWrapper(mContext.getString(R.string.wind_dirction), mWeatherEntity.getWindDirection()));
            listWeatherInfo.add(new CurWeatherInfoWrapper(mContext.getString(R.string.wind_scale), mWeatherEntity.getWindScale() + "级"));
            listWeatherInfo.add(new CurWeatherInfoWrapper(mContext.getString(R.string.felt_temp), mWeatherEntity.getFeltTemperature() + "℃"));
            listWeatherInfo.add(new CurWeatherInfoWrapper(mContext.getString(R.string.humidity), mWeatherEntity.getHumidity() + "%"));
            listWeatherInfo.add(new CurWeatherInfoWrapper(mContext.getString(R.string.air_pressure), mWeatherEntity.getAirPressure() + "hpa"));
            curWeatherInfoViewHolder.gvCurWeatherInfo.setAdapter(new CurWeatherInfoAdapter(mContext, R.layout.cv_city_weather_cur_weather_info_item, listWeatherInfo));
        } else {
            LifeIndexViewHolder lifeIndexViewHolder = (LifeIndexViewHolder)holder;
            lifeIndexViewHolder.tvTitleName.setText(R.string.life_index);
            List<LifeIndexWrapper> listLifeIndex = new ArrayList<>();
            listLifeIndex.add(new LifeIndexWrapper(mContext.getString(R.string.dress_index), mWeatherEntity.getDressBrief(), mWeatherEntity.getDressDescription()));
            listLifeIndex.add(new LifeIndexWrapper(mContext.getString(R.string.uv_index), mWeatherEntity.getUvBrief(), mWeatherEntity.getUvDescription()));
            listLifeIndex.add(new LifeIndexWrapper(mContext.getString(R.string.carwash_index), mWeatherEntity.getCarWashBrief(), mWeatherEntity.getCarWashDescription()));
            listLifeIndex.add(new LifeIndexWrapper(mContext.getString(R.string.travel_index), mWeatherEntity.getTravelBrief(), mWeatherEntity.getTravelDescription()));
            listLifeIndex.add(new LifeIndexWrapper(mContext.getString(R.string.flu_index), mWeatherEntity.getFluBrief(), mWeatherEntity.getFluDescription()));
            listLifeIndex.add(new LifeIndexWrapper(mContext.getString(R.string.sport_index), mWeatherEntity.getSportBrief(), mWeatherEntity.getSportDescription()));
            lifeIndexViewHolder.lvLifeIndex.setAdapter(new LifeIndexAdapter(mContext, R.layout.cv_city_weather_life_index_item, listLifeIndex));
        }
    }

    public static class LifeIndexViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_cv_title_name) TextView tvTitleName;
        @BindView(R.id.lv_city_weather_life_index) ListView lvLifeIndex;

        public LifeIndexViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class CurWeatherInfoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_cv_title_name) TextView tvTitleName;
        @BindView(R.id.gv_city_weather_cur_weather_info) GridView gvCurWeatherInfo;

        public CurWeatherInfoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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
