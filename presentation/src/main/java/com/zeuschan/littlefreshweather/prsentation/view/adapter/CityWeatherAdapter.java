package com.zeuschan.littlefreshweather.prsentation.view.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zeuschan.littlefreshweather.common.util.StringUtil;
import com.zeuschan.littlefreshweather.model.entity.WeatherEntity;
import com.zeuschan.littlefreshweather.prsentation.R;
import com.zeuschan.littlefreshweather.prsentation.presenter.CityWeatherPresenter;
import com.zeuschan.littlefreshweather.prsentation.wrapper.CurWeatherInfoWrapper;
import com.zeuschan.littlefreshweather.prsentation.wrapper.LifeIndexWrapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import butterknife.BindView;
//import butterknife.ButterKnife;

/**
 * Created by chenxiong on 2016/6/12.
 */
public class CityWeatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int VIEW_MAIN = 0;
    public static final int VIEW_FORECAST = 1;
    public static final int VIEW_CURRENT_WEATHER_INFO = 2;
    public static final int VIEW_LIFE_INDEX = 3;

    private WeatherEntity mWeatherEntity = null;
    private Context mContext = null;
    private CityWeatherPresenter mPresenter = null;
    private ViewGroup mParent = null;
    private View mMainView = null;
    private View mForecastView = null;
    private View mCurWeatherView = null;
    private View mLifeIndexView = null;

    private List<CurWeatherInfoWrapper> mListWeatherInfo = new ArrayList<>();
    private List<LifeIndexWrapper> mListLifeIndex = new ArrayList<>();
    private List<WeatherEntity.Forecast> mListForecasts = new ArrayList<>();

    public CityWeatherAdapter(Context mContext, CityWeatherPresenter presenter, ViewGroup parent) {
        this.mContext = mContext;
        mPresenter = presenter;
        mParent = parent;

        mMainView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_city_weather_main, parent, false);
        mMainView.setTag(VIEW_MAIN);
        mForecastView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_city_weather_forecast, parent, false);
        mForecastView.setTag(VIEW_FORECAST);
        mCurWeatherView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_city_weather_cur_weather_info, parent, false);
        mCurWeatherView.setTag(VIEW_CURRENT_WEATHER_INFO);
        mLifeIndexView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_city_weather_life_index, parent, false);
        mLifeIndexView.setTag(VIEW_LIFE_INDEX);
    }

    public void setWeatherEntity(WeatherEntity mWeatherEntity) {
        mListLifeIndex.clear();
        mListWeatherInfo.clear();
        mListForecasts.clear();

        this.mWeatherEntity = mWeatherEntity;

        mListWeatherInfo.add(new CurWeatherInfoWrapper(mContext.getString(R.string.wind_dirction), mWeatherEntity.getWindDirection()));
        mListWeatherInfo.add(new CurWeatherInfoWrapper(mContext.getString(R.string.wind_scale), mWeatherEntity.getWindScale()));
        mListWeatherInfo.add(new CurWeatherInfoWrapper(mContext.getString(R.string.felt_temp), mWeatherEntity.getFeltTemperature() + "℃"));
        mListWeatherInfo.add(new CurWeatherInfoWrapper(mContext.getString(R.string.humidity), mWeatherEntity.getHumidity() + "%"));
        mListWeatherInfo.add(new CurWeatherInfoWrapper(mContext.getString(R.string.air_pressure), mWeatherEntity.getAirPressure() + "hpa"));

        mListForecasts.addAll(mWeatherEntity.getForecasts());

        mListLifeIndex.add(new LifeIndexWrapper(mContext.getString(R.string.dress_index), mWeatherEntity.getDressBrief(), mWeatherEntity.getDressDescription()));
        mListLifeIndex.add(new LifeIndexWrapper(mContext.getString(R.string.uv_index), mWeatherEntity.getUvBrief(), mWeatherEntity.getUvDescription()));
        mListLifeIndex.add(new LifeIndexWrapper(mContext.getString(R.string.carwash_index), mWeatherEntity.getCarWashBrief(), mWeatherEntity.getCarWashDescription()));
        mListLifeIndex.add(new LifeIndexWrapper(mContext.getString(R.string.travel_index), mWeatherEntity.getTravelBrief(), mWeatherEntity.getTravelDescription()));
        mListLifeIndex.add(new LifeIndexWrapper(mContext.getString(R.string.flu_index), mWeatherEntity.getFluBrief(), mWeatherEntity.getFluDescription()));
        mListLifeIndex.add(new LifeIndexWrapper(mContext.getString(R.string.sport_index), mWeatherEntity.getSportBrief(), mWeatherEntity.getSportDescription()));

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
        if (VIEW_MAIN == viewType && mMainView != null) {
            return new MainViewHolder(mMainView);
        } else if (VIEW_FORECAST == viewType && mForecastView != null) {
            return new ForecastViewHolder(mForecastView);
        } else if (VIEW_CURRENT_WEATHER_INFO == viewType && mCurWeatherView != null) {
            return new CurWeatherInfoViewHolder(mCurWeatherView);
        } else if (mLifeIndexView != null) {
            return new LifeIndexViewHolder(mLifeIndexView, mPresenter);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (null == mWeatherEntity)
            return;

        if (0 == position) {
            MainViewHolder mainViewHolder = (MainViewHolder)holder;
            mainViewHolder.tvDate.setText(StringUtil.getCurrentDateTime("yyyy-MM-dd\nEEEE"));
            mainViewHolder.tvAirQualityIndex.setText(mWeatherEntity.getAirQulityIndex());
            AirQulityRepresentation airQulityRepresentation = new AirQulityRepresentation();
            getAirQualityTypeAndColor(mWeatherEntity.getAirQulityIndex(), airQulityRepresentation);
            mainViewHolder.tvAirQualityType.setText(airQulityRepresentation.getmAirQulityType());
            GradientDrawable shapeDrawable = (GradientDrawable)mainViewHolder.tvAirQualityType.getBackground();
            shapeDrawable.setColor(mContext.getResources().getColor(airQulityRepresentation.getmAirQulityColorId()));
            mainViewHolder.tvCurTemp.setText(mWeatherEntity.getCurrentTemperature());
            String[] dateAndTime = mWeatherEntity.getDataUpdateTime().split(" ");
            Date date = StringUtil.stringToDate("yyyy-MM-dd", dateAndTime[0]);
            mainViewHolder.tvUpdateTime.setText(StringUtil.getFriendlyDateString(date, false) + " " + dateAndTime[1] + " 发布");
            mainViewHolder.tvWeatherDesc.setText(mWeatherEntity.getWeatherDescription());

            if (mListForecasts.size() >= 3) {
                Date date0 = StringUtil.stringToDate("yyyy-MM-dd", mListForecasts.get(0).getDate());
                mainViewHolder.tvForecastDate1.setText(StringUtil.getFriendlyDateString(date0, true));
                mainViewHolder.tvForecastTemp1.setText(mListForecasts.get(0).getMinTemperature() + " ~ " + mListForecasts.get(0).getMaxTemperature() + "℃");
                mPresenter.getImageViewSrc(mainViewHolder.ivForecastIcon1, getWeatherIconId(mListForecasts.get(0).getWeatherDescriptionDaytime()));

                Date date1 = StringUtil.stringToDate("yyyy-MM-dd", mListForecasts.get(1).getDate());
                mainViewHolder.tvForecastDate2.setText(StringUtil.getFriendlyDateString(date1, true));
                mainViewHolder.tvForecastTemp2.setText(mListForecasts.get(1).getMinTemperature() + " ~ " + mListForecasts.get(1).getMaxTemperature() + "℃");
                mPresenter.getImageViewSrc(mainViewHolder.ivForecastIcon2, getWeatherIconId(mListForecasts.get(1).getWeatherDescriptionDaytime()));

                Date date2 = StringUtil.stringToDate("yyyy-MM-dd", mListForecasts.get(2).getDate());
                mainViewHolder.tvForecastDate3.setText(StringUtil.getFriendlyDateString(date2, true));
                mainViewHolder.tvForecastTemp3.setText(mListForecasts.get(2).getMinTemperature() + " ~ " + mListForecasts.get(2).getMaxTemperature() + "℃");
                mPresenter.getImageViewSrc(mainViewHolder.ivForecastIcon3, getWeatherIconId(mListForecasts.get(2).getWeatherDescriptionDaytime()));
            }
        } else if (1 == position) {
            ForecastViewHolder forecastViewHolder = (ForecastViewHolder)holder;
            forecastViewHolder.tvTitleName.setText(R.string.forecast_title);
            //forecastViewHolder.lvCityWeatherForecast.setAdapter(mForecastAdapter);

            int index = 0;
            for (WeatherEntity.Forecast forecast : mListForecasts) {
                ++index;
                switch (index) {
                    case 1: {
                        Date date1 = StringUtil.stringToDate("yyyy-MM-dd", forecast.getDate());
                        forecastViewHolder.tvDate1.setText(StringUtil.getFriendlyDateString(date1, true));
                        mPresenter.getImageViewSrc(forecastViewHolder.ivIcon1, getWeatherIconId(forecast.getWeatherDescriptionDaytime()));
                        forecastViewHolder.tvDesc1.setText(forecast.getWeatherDescriptionDaytime());
                        forecastViewHolder.tvCentigrade1.setText(forecast.getMinTemperature() + " ~ " + forecast.getMaxTemperature() + "℃");
                        forecastViewHolder.tvWindScale1.setText(forecast.getWindScale());
                        forecastViewHolder.tvWindDir1.setText(forecast.getWindDirection());
                    } break;
                    case 2: {
                        Date date2 = StringUtil.stringToDate("yyyy-MM-dd", forecast.getDate());
                        forecastViewHolder.tvDate2.setText(StringUtil.getFriendlyDateString(date2, true));
                        mPresenter.getImageViewSrc(forecastViewHolder.ivIcon2, getWeatherIconId(forecast.getWeatherDescriptionDaytime()));
                        forecastViewHolder.tvDesc2.setText(forecast.getWeatherDescriptionDaytime());
                        forecastViewHolder.tvCentigrade2.setText(forecast.getMinTemperature() + " ~ " + forecast.getMaxTemperature() + "℃");
                        forecastViewHolder.tvWindScale2.setText(forecast.getWindScale());
                        forecastViewHolder.tvWindDir2.setText(forecast.getWindDirection());
                    } break;
                    case 3: {
                        Date date3 = StringUtil.stringToDate("yyyy-MM-dd", forecast.getDate());
                        forecastViewHolder.tvDate3.setText(StringUtil.getFriendlyDateString(date3, true));
                        mPresenter.getImageViewSrc(forecastViewHolder.ivIcon3, getWeatherIconId(forecast.getWeatherDescriptionDaytime()));
                        forecastViewHolder.tvDesc3.setText(forecast.getWeatherDescriptionDaytime());
                        forecastViewHolder.tvCentigrade3.setText(forecast.getMinTemperature() + " ~ " + forecast.getMaxTemperature() + "℃");
                        forecastViewHolder.tvWindScale3.setText(forecast.getWindScale());
                        forecastViewHolder.tvWindDir3.setText(forecast.getWindDirection());
                    } break;
                    case 4: {
                        Date date4 = StringUtil.stringToDate("yyyy-MM-dd", forecast.getDate());
                        forecastViewHolder.tvDate4.setText(StringUtil.getFriendlyDateString(date4, true));
                        mPresenter.getImageViewSrc(forecastViewHolder.ivIcon4, getWeatherIconId(forecast.getWeatherDescriptionDaytime()));
                        forecastViewHolder.tvDesc4.setText(forecast.getWeatherDescriptionDaytime());
                        forecastViewHolder.tvCentigrade4.setText(forecast.getMinTemperature() + " ~ " + forecast.getMaxTemperature() + "℃");
                        forecastViewHolder.tvWindScale4.setText(forecast.getWindScale());
                        forecastViewHolder.tvWindDir4.setText(forecast.getWindDirection());
                    } break;
                    case 5: {
                        Date date5 = StringUtil.stringToDate("yyyy-MM-dd", forecast.getDate());
                        forecastViewHolder.tvDate5.setText(StringUtil.getFriendlyDateString(date5, true));
                        mPresenter.getImageViewSrc(forecastViewHolder.ivIcon5, getWeatherIconId(forecast.getWeatherDescriptionDaytime()));
                        forecastViewHolder.tvDesc5.setText(forecast.getWeatherDescriptionDaytime());
                        forecastViewHolder.tvCentigrade5.setText(forecast.getMinTemperature() + " ~ " + forecast.getMaxTemperature() + "℃");
                        forecastViewHolder.tvWindScale5.setText(forecast.getWindScale());
                        forecastViewHolder.tvWindDir5.setText(forecast.getWindDirection());
                    } break;
                    case 6: {
                        Date date6 = StringUtil.stringToDate("yyyy-MM-dd", forecast.getDate());
                        forecastViewHolder.tvDate6.setText(StringUtil.getFriendlyDateString(date6, true));
                        mPresenter.getImageViewSrc(forecastViewHolder.ivIcon6, getWeatherIconId(forecast.getWeatherDescriptionDaytime()));
                        forecastViewHolder.tvDesc6.setText(forecast.getWeatherDescriptionDaytime());
                        forecastViewHolder.tvCentigrade6.setText(forecast.getMinTemperature() + " ~ " + forecast.getMaxTemperature() + "℃");
                        forecastViewHolder.tvWindScale6.setText(forecast.getWindScale());
                        forecastViewHolder.tvWindDir6.setText(forecast.getWindDirection());
                    } break;
                    case 7: {
                        Date date7 = StringUtil.stringToDate("yyyy-MM-dd", forecast.getDate());
                        forecastViewHolder.tvDate7.setText(StringUtil.getFriendlyDateString(date7, true));
                        mPresenter.getImageViewSrc(forecastViewHolder.ivIcon7, getWeatherIconId(forecast.getWeatherDescriptionDaytime()));
                        forecastViewHolder.tvDesc7.setText(forecast.getWeatherDescriptionDaytime());
                        forecastViewHolder.tvCentigrade7.setText(forecast.getMinTemperature() + " ~ " + forecast.getMaxTemperature() + "℃");
                        forecastViewHolder.tvWindScale7.setText(forecast.getWindScale());
                        forecastViewHolder.tvWindDir7.setText(forecast.getWindDirection());
                    } break;
                }
            }

        } else if (2 == position) {
            CurWeatherInfoViewHolder curWeatherInfoViewHolder = (CurWeatherInfoViewHolder)holder;
            curWeatherInfoViewHolder.tvTitleName.setText(R.string.current_weather_info);
            //curWeatherInfoViewHolder.gvCurWeatherInfo.setAdapter(mCurWeatherInfoAdapter);

            int index = 0;
            for (CurWeatherInfoWrapper info: mListWeatherInfo) {
                ++index;
                switch (index) {
                    case 1: {
                        mPresenter.getImageViewSrc(curWeatherInfoViewHolder.ivIcon1, R.drawable.ic_winddirect);
                        curWeatherInfoViewHolder.tvName1.setText(info.getWeatherInfoName());
                        curWeatherInfoViewHolder.tvValue1.setText(info.getWeatherInfoValue());
                    } break;
                    case 2: {
                        mPresenter.getImageViewSrc(curWeatherInfoViewHolder.ivIcon2, R.drawable.ic_windspeed);
                        curWeatherInfoViewHolder.tvName2.setText(info.getWeatherInfoName());
                        curWeatherInfoViewHolder.tvValue2.setText(info.getWeatherInfoValue());
                    } break;
                    case 3: {
                        mPresenter.getImageViewSrc(curWeatherInfoViewHolder.ivIcon3, R.drawable.ic_sun_rise);
                        curWeatherInfoViewHolder.tvName3.setText(info.getWeatherInfoName());
                        curWeatherInfoViewHolder.tvValue3.setText(info.getWeatherInfoValue());
                    } break;
                    case 4: {
                        mPresenter.getImageViewSrc(curWeatherInfoViewHolder.ivIcon4, R.drawable.ic_sunset);
                        curWeatherInfoViewHolder.tvName4.setText(info.getWeatherInfoName());
                        curWeatherInfoViewHolder.tvValue4.setText(info.getWeatherInfoValue());
                    } break;
                    case 5: {
                        mPresenter.getImageViewSrc(curWeatherInfoViewHolder.ivIcon5, R.drawable.ic_sun_rise);
                        curWeatherInfoViewHolder.tvName5.setText(info.getWeatherInfoName());
                        curWeatherInfoViewHolder.tvValue5.setText(info.getWeatherInfoValue());
                    } break;
                }
            }
        } else {
            LifeIndexViewHolder lifeIndexViewHolder = (LifeIndexViewHolder)holder;
            lifeIndexViewHolder.tvTitleName.setText(R.string.life_index);
            //lifeIndexViewHolder.lvLifeIndex.setAdapter(mLifeIndexAdapter);

            int index = 0;
            for (LifeIndexWrapper lifeIndex : mListLifeIndex) {
                ++index;
                switch (index) {
                    case 1: {
                        mPresenter.getImageViewSrc(lifeIndexViewHolder.ivIcon1, R.drawable.ic_life_info_chuanyi);
                        lifeIndexViewHolder.tvName1.setText(lifeIndex.getLifeIndexName());
                        lifeIndexViewHolder.tvBrief1.setText(lifeIndex.getLifeIndexBrief());
                        lifeIndexViewHolder.tvDesc1.setText(lifeIndex.getLifeIndexDesc());
                        mPresenter.getImageViewSrc(lifeIndexViewHolder.ivArrow1, R.drawable.arrow_close);
                    } break;
                    case 2: {
                        mPresenter.getImageViewSrc(lifeIndexViewHolder.ivIcon2, R.drawable.ic_life_info_ziwaixian);
                        lifeIndexViewHolder.tvName2.setText(lifeIndex.getLifeIndexName());
                        lifeIndexViewHolder.tvBrief2.setText(lifeIndex.getLifeIndexBrief());
                        lifeIndexViewHolder.tvDesc2.setText(lifeIndex.getLifeIndexDesc());
                        mPresenter.getImageViewSrc(lifeIndexViewHolder.ivArrow2, R.drawable.arrow_close);
                    } break;
                    case 3: {
                        mPresenter.getImageViewSrc(lifeIndexViewHolder.ivIcon3, R.drawable.ic_life_info_xiche);
                        lifeIndexViewHolder.tvName3.setText(lifeIndex.getLifeIndexName());
                        lifeIndexViewHolder.tvBrief3.setText(lifeIndex.getLifeIndexBrief());
                        lifeIndexViewHolder.tvDesc3.setText(lifeIndex.getLifeIndexDesc());
                        mPresenter.getImageViewSrc(lifeIndexViewHolder.ivArrow3, R.drawable.arrow_close);
                    } break;
                    case 4: {
                        mPresenter.getImageViewSrc(lifeIndexViewHolder.ivIcon4, R.drawable.ic_life_info_diaoyu);
                        lifeIndexViewHolder.tvName4.setText(lifeIndex.getLifeIndexName());
                        lifeIndexViewHolder.tvBrief4.setText(lifeIndex.getLifeIndexBrief());
                        lifeIndexViewHolder.tvDesc4.setText(lifeIndex.getLifeIndexDesc());
                        mPresenter.getImageViewSrc(lifeIndexViewHolder.ivArrow4, R.drawable.arrow_close);
                    } break;
                    case 5: {
                        mPresenter.getImageViewSrc(lifeIndexViewHolder.ivIcon5, R.drawable.ic_life_info_ganmao);
                        lifeIndexViewHolder.tvName5.setText(lifeIndex.getLifeIndexName());
                        lifeIndexViewHolder.tvBrief5.setText(lifeIndex.getLifeIndexBrief());
                        lifeIndexViewHolder.tvDesc5.setText(lifeIndex.getLifeIndexDesc());
                        mPresenter.getImageViewSrc(lifeIndexViewHolder.ivArrow5, R.drawable.arrow_close);
                    } break;
                    case 6: {
                        mPresenter.getImageViewSrc(lifeIndexViewHolder.ivIcon6, R.drawable.ic_life_info_yundong);
                        lifeIndexViewHolder.tvName6.setText(lifeIndex.getLifeIndexName());
                        lifeIndexViewHolder.tvBrief6.setText(lifeIndex.getLifeIndexBrief());
                        lifeIndexViewHolder.tvDesc6.setText(lifeIndex.getLifeIndexDesc());
                        mPresenter.getImageViewSrc(lifeIndexViewHolder.ivArrow6, R.drawable.arrow_close);
                    } break;
                }
            }
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mPresenter = null;
        mParent = null;
        mMainView = null;
        mForecastView = null;
        mCurWeatherView = null;
        mLifeIndexView = null;
        mContext = null;
        mWeatherEntity = null;
        mListForecasts.clear();
        mListForecasts = null;
        mListLifeIndex.clear();
        mListLifeIndex = null;
        mListWeatherInfo.clear();
        mListWeatherInfo = null;
    }

    public static class LifeIndexViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        @BindView(R.id.tv_cv_title_name) TextView tvTitleName;
//        //@BindView(R.id.lv_city_weather_life_index) ListView lvLifeIndex;
//
//        @BindView(R.id.rl_city_weather_life_index_1) RelativeLayout rlLifeIndex1;
//        @BindView(R.id.iv_city_weather_life_index_icon1) ImageView ivIcon1;
//        @BindView(R.id.tv_city_weather_life_index_name1) TextView tvName1;
//        @BindView(R.id.tv_city_weather_life_index_brief1) TextView tvBrief1;
//        @BindView(R.id.iv_city_weather_life_index_arrow1) ImageView ivArrow1;
//        @BindView(R.id.tv_city_weather_life_index_desc1) TextView tvDesc1;
//
//        @BindView(R.id.rl_city_weather_life_index_2) RelativeLayout rlLifeIndex2;
//        @BindView(R.id.iv_city_weather_life_index_icon2) ImageView ivIcon2;
//        @BindView(R.id.tv_city_weather_life_index_name2) TextView tvName2;
//        @BindView(R.id.tv_city_weather_life_index_brief2) TextView tvBrief2;
//        @BindView(R.id.iv_city_weather_life_index_arrow2) ImageView ivArrow2;
//        @BindView(R.id.tv_city_weather_life_index_desc2) TextView tvDesc2;
//
//        @BindView(R.id.rl_city_weather_life_index_3) RelativeLayout rlLifeIndex3;
//        @BindView(R.id.iv_city_weather_life_index_icon3) ImageView ivIcon3;
//        @BindView(R.id.tv_city_weather_life_index_name3) TextView tvName3;
//        @BindView(R.id.tv_city_weather_life_index_brief3) TextView tvBrief3;
//        @BindView(R.id.iv_city_weather_life_index_arrow3) ImageView ivArrow3;
//        @BindView(R.id.tv_city_weather_life_index_desc3) TextView tvDesc3;
//
//        @BindView(R.id.rl_city_weather_life_index_4) RelativeLayout rlLifeIndex4;
//        @BindView(R.id.iv_city_weather_life_index_icon4) ImageView ivIcon4;
//        @BindView(R.id.tv_city_weather_life_index_name4) TextView tvName4;
//        @BindView(R.id.tv_city_weather_life_index_brief4) TextView tvBrief4;
//        @BindView(R.id.iv_city_weather_life_index_arrow4) ImageView ivArrow4;
//        @BindView(R.id.tv_city_weather_life_index_desc4) TextView tvDesc4;
//
//        @BindView(R.id.rl_city_weather_life_index_5) RelativeLayout rlLifeIndex5;
//        @BindView(R.id.iv_city_weather_life_index_icon5) ImageView ivIcon5;
//        @BindView(R.id.tv_city_weather_life_index_name5) TextView tvName5;
//        @BindView(R.id.tv_city_weather_life_index_brief5) TextView tvBrief5;
//        @BindView(R.id.iv_city_weather_life_index_arrow5) ImageView ivArrow5;
//        @BindView(R.id.tv_city_weather_life_index_desc5) TextView tvDesc5;
//
//        @BindView(R.id.rl_city_weather_life_index_6) RelativeLayout rlLifeIndex6;
//        @BindView(R.id.iv_city_weather_life_index_icon6) ImageView ivIcon6;
//        @BindView(R.id.tv_city_weather_life_index_name6) TextView tvName6;
//        @BindView(R.id.tv_city_weather_life_index_brief6) TextView tvBrief6;
//        @BindView(R.id.iv_city_weather_life_index_arrow6) ImageView ivArrow6;
//        @BindView(R.id.tv_city_weather_life_index_desc6) TextView tvDesc6;

        TextView tvTitleName;

        RelativeLayout rlLifeIndex1;
        ImageView ivIcon1;
        TextView tvName1;
        TextView tvBrief1;
        ImageView ivArrow1;
        TextView tvDesc1;

        RelativeLayout rlLifeIndex2;
        ImageView ivIcon2;
        TextView tvName2;
        TextView tvBrief2;
        ImageView ivArrow2;
        TextView tvDesc2;

        RelativeLayout rlLifeIndex3;
        ImageView ivIcon3;
        TextView tvName3;
        TextView tvBrief3;
        ImageView ivArrow3;
        TextView tvDesc3;

        RelativeLayout rlLifeIndex4;
        ImageView ivIcon4;
        TextView tvName4;
        TextView tvBrief4;
        ImageView ivArrow4;
        TextView tvDesc4;

        RelativeLayout rlLifeIndex5;
        ImageView ivIcon5;
        TextView tvName5;
        TextView tvBrief5;
        ImageView ivArrow5;
        TextView tvDesc5;

        RelativeLayout rlLifeIndex6;
        ImageView ivIcon6;
        TextView tvName6;
        TextView tvBrief6;
        ImageView ivArrow6;
        TextView tvDesc6;

        public LifeIndexViewHolder(View itemView, CityWeatherPresenter presenter) {
            super(itemView);
            //ButterKnife.bind(this, itemView);

            tvTitleName = (TextView)itemView.findViewById(R.id.tv_cv_title_name);

            rlLifeIndex1 = (RelativeLayout)itemView.findViewById(R.id.rl_city_weather_life_index_1);
            ivIcon1 = (ImageView)itemView.findViewById(R.id.iv_city_weather_life_index_icon1);
            tvName1 = (TextView)itemView.findViewById(R.id.tv_city_weather_life_index_name1);
            tvBrief1 = (TextView)itemView.findViewById(R.id.tv_city_weather_life_index_brief1);
            ivArrow1 = (ImageView)itemView.findViewById(R.id.iv_city_weather_life_index_arrow1);
            tvDesc1 = (TextView)itemView.findViewById(R.id.tv_city_weather_life_index_desc1);

            rlLifeIndex2 = (RelativeLayout)itemView.findViewById(R.id.rl_city_weather_life_index_2);
            ivIcon2 = (ImageView)itemView.findViewById(R.id.iv_city_weather_life_index_icon2);
            tvName2 = (TextView)itemView.findViewById(R.id.tv_city_weather_life_index_name2);
            tvBrief2 = (TextView)itemView.findViewById(R.id.tv_city_weather_life_index_brief2);
            ivArrow2 = (ImageView)itemView.findViewById(R.id.iv_city_weather_life_index_arrow2);
            tvDesc2 = (TextView)itemView.findViewById(R.id.tv_city_weather_life_index_desc2);

            rlLifeIndex3 = (RelativeLayout)itemView.findViewById(R.id.rl_city_weather_life_index_3);
            ivIcon3 = (ImageView)itemView.findViewById(R.id.iv_city_weather_life_index_icon3);
            tvName3 = (TextView)itemView.findViewById(R.id.tv_city_weather_life_index_name3);
            tvBrief3 = (TextView)itemView.findViewById(R.id.tv_city_weather_life_index_brief3);
            ivArrow3 = (ImageView)itemView.findViewById(R.id.iv_city_weather_life_index_arrow3);
            tvDesc3 = (TextView)itemView.findViewById(R.id.tv_city_weather_life_index_desc3);

            rlLifeIndex4 = (RelativeLayout)itemView.findViewById(R.id.rl_city_weather_life_index_4);
            ivIcon4 = (ImageView)itemView.findViewById(R.id.iv_city_weather_life_index_icon4);
            tvName4 = (TextView)itemView.findViewById(R.id.tv_city_weather_life_index_name4);
            tvBrief4 = (TextView)itemView.findViewById(R.id.tv_city_weather_life_index_brief4);
            ivArrow4 = (ImageView)itemView.findViewById(R.id.iv_city_weather_life_index_arrow4);
            tvDesc4 = (TextView)itemView.findViewById(R.id.tv_city_weather_life_index_desc4);

            rlLifeIndex5 = (RelativeLayout)itemView.findViewById(R.id.rl_city_weather_life_index_5);
            ivIcon5 = (ImageView)itemView.findViewById(R.id.iv_city_weather_life_index_icon5);
            tvName5 = (TextView)itemView.findViewById(R.id.tv_city_weather_life_index_name5);
            tvBrief5 = (TextView)itemView.findViewById(R.id.tv_city_weather_life_index_brief5);
            ivArrow5 = (ImageView)itemView.findViewById(R.id.iv_city_weather_life_index_arrow5);
            tvDesc5 = (TextView)itemView.findViewById(R.id.tv_city_weather_life_index_desc5);

            rlLifeIndex6 = (RelativeLayout)itemView.findViewById(R.id.rl_city_weather_life_index_6);
            ivIcon6 = (ImageView)itemView.findViewById(R.id.iv_city_weather_life_index_icon6);
            tvName6 = (TextView)itemView.findViewById(R.id.tv_city_weather_life_index_name6);
            tvBrief6 = (TextView)itemView.findViewById(R.id.tv_city_weather_life_index_brief6);
            ivArrow6 = (ImageView)itemView.findViewById(R.id.iv_city_weather_life_index_arrow6);
            tvDesc6 = (TextView)itemView.findViewById(R.id.tv_city_weather_life_index_desc6);

            rlLifeIndex1.setOnClickListener(this);
            rlLifeIndex1.setTag(presenter);
            rlLifeIndex2.setOnClickListener(this);
            rlLifeIndex2.setTag(presenter);
            rlLifeIndex3.setOnClickListener(this);
            rlLifeIndex3.setTag(presenter);
            rlLifeIndex4.setOnClickListener(this);
            rlLifeIndex4.setTag(presenter);
            rlLifeIndex5.setOnClickListener(this);
            rlLifeIndex5.setTag(presenter);
            rlLifeIndex6.setOnClickListener(this);
            rlLifeIndex6.setTag(presenter);
        }

        @Override
        public void onClick(View v) {
            if (rlLifeIndex1.equals(v)) {
                CityWeatherPresenter presenter = (CityWeatherPresenter)rlLifeIndex1.getTag();
                if (tvDesc1.getVisibility() == View.VISIBLE) {
                    tvDesc1.setVisibility(View.GONE);
                    if (presenter != null) {
                        presenter.getImageViewSrc(ivArrow1, R.drawable.arrow_close);
                    }
                } else {
                    tvDesc1.setVisibility(View.VISIBLE);
                    if (presenter != null) {
                        presenter.getImageViewSrc(ivArrow1, R.drawable.arrow_open);
                    }
                }
            }
            if (rlLifeIndex2.equals(v)) {
                CityWeatherPresenter presenter = (CityWeatherPresenter)rlLifeIndex2.getTag();
                if (tvDesc2.getVisibility() == View.VISIBLE) {
                    tvDesc2.setVisibility(View.GONE);
                    if (presenter != null) {
                        presenter.getImageViewSrc(ivArrow2, R.drawable.arrow_close);
                    }
                } else {
                    tvDesc2.setVisibility(View.VISIBLE);
                    if (presenter != null) {
                        presenter.getImageViewSrc(ivArrow2, R.drawable.arrow_open);
                    }
                }
            }
            if (rlLifeIndex3.equals(v)) {
                CityWeatherPresenter presenter = (CityWeatherPresenter)rlLifeIndex3.getTag();
                if (tvDesc3.getVisibility() == View.VISIBLE) {
                    tvDesc3.setVisibility(View.GONE);
                    if (presenter != null) {
                        presenter.getImageViewSrc(ivArrow3, R.drawable.arrow_close);
                    }
                } else {
                    tvDesc3.setVisibility(View.VISIBLE);
                    if (presenter != null) {
                        presenter.getImageViewSrc(ivArrow3, R.drawable.arrow_open);
                    }
                }
            }
            if (rlLifeIndex4.equals(v)) {
                CityWeatherPresenter presenter = (CityWeatherPresenter)rlLifeIndex4.getTag();
                if (tvDesc4.getVisibility() == View.VISIBLE) {
                    tvDesc4.setVisibility(View.GONE);
                    if (presenter != null) {
                        presenter.getImageViewSrc(ivArrow4, R.drawable.arrow_close);
                    }
                } else {
                    tvDesc4.setVisibility(View.VISIBLE);
                    if (presenter != null) {
                        presenter.getImageViewSrc(ivArrow4, R.drawable.arrow_open);
                    }
                }
            }
            if (rlLifeIndex5.equals(v)) {
                CityWeatherPresenter presenter = (CityWeatherPresenter)rlLifeIndex5.getTag();
                if (tvDesc5.getVisibility() == View.VISIBLE) {
                    tvDesc5.setVisibility(View.GONE);
                    if (presenter != null) {
                        presenter.getImageViewSrc(ivArrow5, R.drawable.arrow_close);
                    }
                } else {
                    tvDesc5.setVisibility(View.VISIBLE);
                    if (presenter != null) {
                        presenter.getImageViewSrc(ivArrow5, R.drawable.arrow_open);
                    }
                }
            }
            if (rlLifeIndex6.equals(v)) {
                CityWeatherPresenter presenter = (CityWeatherPresenter)rlLifeIndex6.getTag();
                if (tvDesc6.getVisibility() == View.VISIBLE) {
                    tvDesc6.setVisibility(View.GONE);
                    if (presenter != null) {
                        presenter.getImageViewSrc(ivArrow6, R.drawable.arrow_close);
                    }
                } else {
                    tvDesc6.setVisibility(View.VISIBLE);
                    if (presenter != null) {
                        presenter.getImageViewSrc(ivArrow6, R.drawable.arrow_open);
                    }
                }
            }
        }
    }

    public static class CurWeatherInfoViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.tv_cv_title_name) TextView tvTitleName;
//        //@BindView(R.id.gv_city_weather_cur_weather_info) GridView gvCurWeatherInfo;
//
//        @BindView(R.id.tv_city_weather_cur_weather_info_item_icon1) ImageView ivIcon1;
//        @BindView(R.id.tv_city_weather_cur_weather_info_item_name1) TextView tvName1;
//        @BindView(R.id.tv_city_weather_cur_weather_info_item_value1) TextView tvValue1;
//
//        @BindView(R.id.tv_city_weather_cur_weather_info_item_icon2) ImageView ivIcon2;
//        @BindView(R.id.tv_city_weather_cur_weather_info_item_name2) TextView tvName2;
//        @BindView(R.id.tv_city_weather_cur_weather_info_item_value2) TextView tvValue2;
//
//        @BindView(R.id.tv_city_weather_cur_weather_info_item_icon3) ImageView ivIcon3;
//        @BindView(R.id.tv_city_weather_cur_weather_info_item_name3) TextView tvName3;
//        @BindView(R.id.tv_city_weather_cur_weather_info_item_value3) TextView tvValue3;
//
//        @BindView(R.id.tv_city_weather_cur_weather_info_item_icon4) ImageView ivIcon4;
//        @BindView(R.id.tv_city_weather_cur_weather_info_item_name4) TextView tvName4;
//        @BindView(R.id.tv_city_weather_cur_weather_info_item_value4) TextView tvValue4;
//
//        @BindView(R.id.tv_city_weather_cur_weather_info_item_icon5) ImageView ivIcon5;
//        @BindView(R.id.tv_city_weather_cur_weather_info_item_name5) TextView tvName5;
//        @BindView(R.id.tv_city_weather_cur_weather_info_item_value5) TextView tvValue5;

        TextView tvTitleName;

        ImageView ivIcon1;
        TextView tvName1;
        TextView tvValue1;

        ImageView ivIcon2;
        TextView tvName2;
        TextView tvValue2;

        ImageView ivIcon3;
        TextView tvName3;
        TextView tvValue3;

        ImageView ivIcon4;
        TextView tvName4;
        TextView tvValue4;

        ImageView ivIcon5;
        TextView tvName5;
        TextView tvValue5;

        public CurWeatherInfoViewHolder(View itemView) {
            super(itemView);
            //ButterKnife.bind(this, itemView);

            tvTitleName = (TextView)itemView.findViewById(R.id.tv_cv_title_name);

            ivIcon1 = (ImageView)itemView.findViewById(R.id.tv_city_weather_cur_weather_info_item_icon1);
            tvName1 = (TextView)itemView.findViewById(R.id.tv_city_weather_cur_weather_info_item_name1);
            tvValue1 = (TextView)itemView.findViewById(R.id.tv_city_weather_cur_weather_info_item_value1);

            ivIcon2 = (ImageView)itemView.findViewById(R.id.tv_city_weather_cur_weather_info_item_icon2);
            tvName2 = (TextView)itemView.findViewById(R.id.tv_city_weather_cur_weather_info_item_name2);
            tvValue2 = (TextView)itemView.findViewById(R.id.tv_city_weather_cur_weather_info_item_value2);

            ivIcon3 = (ImageView)itemView.findViewById(R.id.tv_city_weather_cur_weather_info_item_icon3);
            tvName3 = (TextView)itemView.findViewById(R.id.tv_city_weather_cur_weather_info_item_name3);
            tvValue3 = (TextView)itemView.findViewById(R.id.tv_city_weather_cur_weather_info_item_value3);

            ivIcon4 = (ImageView)itemView.findViewById(R.id.tv_city_weather_cur_weather_info_item_icon4);
            tvName4 = (TextView)itemView.findViewById(R.id.tv_city_weather_cur_weather_info_item_name4);
            tvValue4 = (TextView)itemView.findViewById(R.id.tv_city_weather_cur_weather_info_item_value4);

            ivIcon5 = (ImageView)itemView.findViewById(R.id.tv_city_weather_cur_weather_info_item_icon5);
            tvName5 = (TextView)itemView.findViewById(R.id.tv_city_weather_cur_weather_info_item_name5);
            tvValue5 = (TextView)itemView.findViewById(R.id.tv_city_weather_cur_weather_info_item_value5);
        }
    }

    public static class ForecastViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.tv_cv_title_name) TextView tvTitleName;
//        //@BindView(R.id.lv_city_weather_forecast) ListView lvCityWeatherForecast;
//
//        @BindView(R.id.tv_city_weather_forecast_item_date1) TextView tvDate1;
//        @BindView(R.id.tv_city_weather_forecast_item_centigrade1) TextView tvCentigrade1;
//        @BindView(R.id.tv_city_weather_forecast_item_weather_desc1) TextView tvDesc1;
//        @BindView(R.id.iv_city_weather_forecast_item_weather_icon1) ImageView ivIcon1;
//        @BindView(R.id.tv_city_weather_forecast_item_wind_scale1) TextView tvWindScale1;
//        @BindView(R.id.tv_city_weather_forecast_item_wind_dir1) TextView tvWindDir1;
//
//        @BindView(R.id.tv_city_weather_forecast_item_date2) TextView tvDate2;
//        @BindView(R.id.tv_city_weather_forecast_item_centigrade2) TextView tvCentigrade2;
//        @BindView(R.id.tv_city_weather_forecast_item_weather_desc2) TextView tvDesc2;
//        @BindView(R.id.iv_city_weather_forecast_item_weather_icon2) ImageView ivIcon2;
//        @BindView(R.id.tv_city_weather_forecast_item_wind_scale2) TextView tvWindScale2;
//        @BindView(R.id.tv_city_weather_forecast_item_wind_dir2) TextView tvWindDir2;
//
//        @BindView(R.id.tv_city_weather_forecast_item_date3) TextView tvDate3;
//        @BindView(R.id.tv_city_weather_forecast_item_centigrade3) TextView tvCentigrade3;
//        @BindView(R.id.tv_city_weather_forecast_item_weather_desc3) TextView tvDesc3;
//        @BindView(R.id.iv_city_weather_forecast_item_weather_icon3) ImageView ivIcon3;
//        @BindView(R.id.tv_city_weather_forecast_item_wind_scale3) TextView tvWindScale3;
//        @BindView(R.id.tv_city_weather_forecast_item_wind_dir3) TextView tvWindDir3;
//
//        @BindView(R.id.tv_city_weather_forecast_item_date4) TextView tvDate4;
//        @BindView(R.id.tv_city_weather_forecast_item_centigrade4) TextView tvCentigrade4;
//        @BindView(R.id.tv_city_weather_forecast_item_weather_desc4) TextView tvDesc4;
//        @BindView(R.id.iv_city_weather_forecast_item_weather_icon4) ImageView ivIcon4;
//        @BindView(R.id.tv_city_weather_forecast_item_wind_scale4) TextView tvWindScale4;
//        @BindView(R.id.tv_city_weather_forecast_item_wind_dir4) TextView tvWindDir4;
//
//        @BindView(R.id.tv_city_weather_forecast_item_date5) TextView tvDate5;
//        @BindView(R.id.tv_city_weather_forecast_item_centigrade5) TextView tvCentigrade5;
//        @BindView(R.id.tv_city_weather_forecast_item_weather_desc5) TextView tvDesc5;
//        @BindView(R.id.iv_city_weather_forecast_item_weather_icon5) ImageView ivIcon5;
//        @BindView(R.id.tv_city_weather_forecast_item_wind_scale5) TextView tvWindScale5;
//        @BindView(R.id.tv_city_weather_forecast_item_wind_dir5) TextView tvWindDir5;
//
//        @BindView(R.id.tv_city_weather_forecast_item_date6) TextView tvDate6;
//        @BindView(R.id.tv_city_weather_forecast_item_centigrade6) TextView tvCentigrade6;
//        @BindView(R.id.tv_city_weather_forecast_item_weather_desc6) TextView tvDesc6;
//        @BindView(R.id.iv_city_weather_forecast_item_weather_icon6) ImageView ivIcon6;
//        @BindView(R.id.tv_city_weather_forecast_item_wind_scale6) TextView tvWindScale6;
//        @BindView(R.id.tv_city_weather_forecast_item_wind_dir6) TextView tvWindDir6;
//
//        @BindView(R.id.tv_city_weather_forecast_item_date7) TextView tvDate7;
//        @BindView(R.id.tv_city_weather_forecast_item_centigrade7) TextView tvCentigrade7;
//        @BindView(R.id.tv_city_weather_forecast_item_weather_desc7) TextView tvDesc7;
//        @BindView(R.id.iv_city_weather_forecast_item_weather_icon7) ImageView ivIcon7;
//        @BindView(R.id.tv_city_weather_forecast_item_wind_scale7) TextView tvWindScale7;
//        @BindView(R.id.tv_city_weather_forecast_item_wind_dir7) TextView tvWindDir7;

        TextView tvTitleName;

        TextView tvDate1;
        TextView tvCentigrade1;
        TextView tvDesc1;
        ImageView ivIcon1;
        TextView tvWindScale1;
        TextView tvWindDir1;

        TextView tvDate2;
        TextView tvCentigrade2;
        TextView tvDesc2;
        ImageView ivIcon2;
        TextView tvWindScale2;
        TextView tvWindDir2;

        TextView tvDate3;
        TextView tvCentigrade3;
        TextView tvDesc3;
        ImageView ivIcon3;
        TextView tvWindScale3;
        TextView tvWindDir3;

        TextView tvDate4;
        TextView tvCentigrade4;
        TextView tvDesc4;
        ImageView ivIcon4;
        TextView tvWindScale4;
        TextView tvWindDir4;

        TextView tvDate5;
        TextView tvCentigrade5;
        TextView tvDesc5;
        ImageView ivIcon5;
        TextView tvWindScale5;
        TextView tvWindDir5;

        TextView tvDate6;
        TextView tvCentigrade6;
        TextView tvDesc6;
        ImageView ivIcon6;
        TextView tvWindScale6;
        TextView tvWindDir6;

        TextView tvDate7;
        TextView tvCentigrade7;
        TextView tvDesc7;
        ImageView ivIcon7;
        TextView tvWindScale7;
        TextView tvWindDir7;

        public ForecastViewHolder(View itemView) {
            super(itemView);
            //ButterKnife.bind(this, itemView);

            tvTitleName = (TextView)itemView.findViewById(R.id.tv_cv_title_name);

            tvDate1 = (TextView)itemView.findViewById(R.id.tv_city_weather_forecast_item_date1);
            tvCentigrade1 = (TextView)itemView.findViewById(R.id.tv_city_weather_forecast_item_centigrade1);
            tvDesc1 = (TextView)itemView.findViewById(R.id.tv_city_weather_forecast_item_weather_desc1);
            ivIcon1 = (ImageView)itemView.findViewById(R.id.iv_city_weather_forecast_item_weather_icon1);
            tvWindScale1 = (TextView)itemView.findViewById(R.id.tv_city_weather_forecast_item_wind_scale1);
            tvWindDir1 = (TextView)itemView.findViewById(R.id.tv_city_weather_forecast_item_wind_dir1);

            tvDate2 = (TextView)itemView.findViewById(R.id.tv_city_weather_forecast_item_date2);
            tvCentigrade2 = (TextView)itemView.findViewById(R.id.tv_city_weather_forecast_item_centigrade2);
            tvDesc2 = (TextView)itemView.findViewById(R.id.tv_city_weather_forecast_item_weather_desc2);
            ivIcon2 = (ImageView)itemView.findViewById(R.id.iv_city_weather_forecast_item_weather_icon2);
            tvWindScale2 = (TextView)itemView.findViewById(R.id.tv_city_weather_forecast_item_wind_scale2);
            tvWindDir2 = (TextView)itemView.findViewById(R.id.tv_city_weather_forecast_item_wind_dir2);

            tvDate3 = (TextView)itemView.findViewById(R.id.tv_city_weather_forecast_item_date3);
            tvCentigrade3 = (TextView)itemView.findViewById(R.id.tv_city_weather_forecast_item_centigrade3);
            tvDesc3 = (TextView)itemView.findViewById(R.id.tv_city_weather_forecast_item_weather_desc3);
            ivIcon3 = (ImageView)itemView.findViewById(R.id.iv_city_weather_forecast_item_weather_icon3);
            tvWindScale3 = (TextView)itemView.findViewById(R.id.tv_city_weather_forecast_item_wind_scale3);
            tvWindDir3 = (TextView)itemView.findViewById(R.id.tv_city_weather_forecast_item_wind_dir3);

            tvDate4 = (TextView)itemView.findViewById(R.id.tv_city_weather_forecast_item_date4);
            tvCentigrade4 = (TextView)itemView.findViewById(R.id.tv_city_weather_forecast_item_centigrade4);
            tvDesc4 = (TextView)itemView.findViewById(R.id.tv_city_weather_forecast_item_weather_desc4);
            ivIcon4 = (ImageView)itemView.findViewById(R.id.iv_city_weather_forecast_item_weather_icon4);
            tvWindScale4 = (TextView)itemView.findViewById(R.id.tv_city_weather_forecast_item_wind_scale4);
            tvWindDir4 = (TextView)itemView.findViewById(R.id.tv_city_weather_forecast_item_wind_dir4);

            tvDate5 = (TextView)itemView.findViewById(R.id.tv_city_weather_forecast_item_date5);
            tvCentigrade5 = (TextView)itemView.findViewById(R.id.tv_city_weather_forecast_item_centigrade5);
            tvDesc5 = (TextView)itemView.findViewById(R.id.tv_city_weather_forecast_item_weather_desc5);
            ivIcon5 = (ImageView)itemView.findViewById(R.id.iv_city_weather_forecast_item_weather_icon5);
            tvWindScale5 = (TextView)itemView.findViewById(R.id.tv_city_weather_forecast_item_wind_scale5);
            tvWindDir5 = (TextView)itemView.findViewById(R.id.tv_city_weather_forecast_item_wind_dir5);

            tvDate6 = (TextView)itemView.findViewById(R.id.tv_city_weather_forecast_item_date6);
            tvCentigrade6 = (TextView)itemView.findViewById(R.id.tv_city_weather_forecast_item_centigrade6);
            tvDesc6 = (TextView)itemView.findViewById(R.id.tv_city_weather_forecast_item_weather_desc6);
            ivIcon6 = (ImageView)itemView.findViewById(R.id.iv_city_weather_forecast_item_weather_icon6);
            tvWindScale6 = (TextView)itemView.findViewById(R.id.tv_city_weather_forecast_item_wind_scale6);
            tvWindDir6 = (TextView)itemView.findViewById(R.id.tv_city_weather_forecast_item_wind_dir6);

            tvDate7 = (TextView)itemView.findViewById(R.id.tv_city_weather_forecast_item_date7);
            tvCentigrade7 = (TextView)itemView.findViewById(R.id.tv_city_weather_forecast_item_centigrade7);
            tvDesc7 = (TextView)itemView.findViewById(R.id.tv_city_weather_forecast_item_weather_desc7);
            ivIcon7 = (ImageView)itemView.findViewById(R.id.iv_city_weather_forecast_item_weather_icon7);
            tvWindScale7 = (TextView)itemView.findViewById(R.id.tv_city_weather_forecast_item_wind_scale7);
            tvWindDir7 = (TextView)itemView.findViewById(R.id.tv_city_weather_forecast_item_wind_dir7);
        }
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.tv_city_weather_main_date) TextView tvDate;
//        @BindView(R.id.tv_city_weather_main_air_quality_index) TextView tvAirQualityIndex;
//        @BindView(R.id.tv_city_weather_main_air_quality_type) TextView tvAirQualityType;
//        @BindView(R.id.tv_city_weather_main_weather_desc) TextView tvWeatherDesc;
//        @BindView(R.id.tv_city_weather_main_cur_temp) TextView tvCurTemp;
//        @BindView(R.id.tv_city_weather_main_update_time) TextView tvUpdateTime;
//        @BindView(R.id.tv_city_weather_main_forecast_date1) TextView tvForecastDate1;
//        @BindView(R.id.tv_city_weather_main_forecast_temp1) TextView tvForecastTemp1;
//        @BindView(R.id.iv_city_weather_main_forecast_icon1) ImageView ivForecastIcon1;
//        @BindView(R.id.tv_city_weather_main_forecast_date2) TextView tvForecastDate2;
//        @BindView(R.id.tv_city_weather_main_forecast_temp2) TextView tvForecastTemp2;
//        @BindView(R.id.iv_city_weather_main_forecast_icon2) ImageView ivForecastIcon2;
//        @BindView(R.id.tv_city_weather_main_forecast_date3) TextView tvForecastDate3;
//        @BindView(R.id.tv_city_weather_main_forecast_temp3) TextView tvForecastTemp3;
//        @BindView(R.id.iv_city_weather_main_forecast_icon3) ImageView ivForecastIcon3;

        TextView tvDate;
        TextView tvAirQualityIndex;
        TextView tvAirQualityType;
        TextView tvWeatherDesc;
        TextView tvCurTemp;
        TextView tvUpdateTime;
        TextView tvForecastDate1;
        TextView tvForecastTemp1;
        ImageView ivForecastIcon1;
        TextView tvForecastDate2;
        TextView tvForecastTemp2;
        ImageView ivForecastIcon2;
        TextView tvForecastDate3;
        TextView tvForecastTemp3;
        ImageView ivForecastIcon3;

        public MainViewHolder(View itemView) {
            super(itemView);
            //ButterKnife.bind(this, itemView);

            tvDate = (TextView)itemView.findViewById(R.id.tv_city_weather_main_date);
            tvAirQualityIndex = (TextView)itemView.findViewById(R.id.tv_city_weather_main_air_quality_index);
            tvAirQualityType = (TextView)itemView.findViewById(R.id.tv_city_weather_main_air_quality_type);
            tvWeatherDesc = (TextView)itemView.findViewById(R.id.tv_city_weather_main_weather_desc);
            tvCurTemp = (TextView)itemView.findViewById(R.id.tv_city_weather_main_cur_temp);
            tvUpdateTime = (TextView)itemView.findViewById(R.id.tv_city_weather_main_update_time);
            tvForecastDate1 = (TextView)itemView.findViewById(R.id.tv_city_weather_main_forecast_date1);
            tvForecastTemp1 = (TextView)itemView.findViewById(R.id.tv_city_weather_main_forecast_temp1);
            ivForecastIcon1 = (ImageView)itemView.findViewById(R.id.iv_city_weather_main_forecast_icon1);
            tvForecastDate2 = (TextView)itemView.findViewById(R.id.tv_city_weather_main_forecast_date2);
            tvForecastTemp2 = (TextView)itemView.findViewById(R.id.tv_city_weather_main_forecast_temp2);
            ivForecastIcon2 = (ImageView)itemView.findViewById(R.id.iv_city_weather_main_forecast_icon2);
            tvForecastDate3 = (TextView)itemView.findViewById(R.id.tv_city_weather_main_forecast_date3);
            tvForecastTemp3 = (TextView)itemView.findViewById(R.id.tv_city_weather_main_forecast_temp3);
            ivForecastIcon3 = (ImageView)itemView.findViewById(R.id.iv_city_weather_main_forecast_icon3);
        }
    }

    private static class AirQulityRepresentation {
        private String mAirQulityType;
        private int mAirQulityColorId;

        public int getmAirQulityColorId() {
            return mAirQulityColorId;
        }

        public void setmAirQulityColorId(int mAirQulityColorId) {
            this.mAirQulityColorId = mAirQulityColorId;
        }

        public String getmAirQulityType() {
            return mAirQulityType;
        }

        public void setmAirQulityType(String mAirQulityType) {
            this.mAirQulityType = mAirQulityType;
        }
    }

    private boolean getAirQualityTypeAndColor(String airQulityIndexString, AirQulityRepresentation airQulityRepresentation) {
        int airQulityIndex = 0;
        boolean ret = true;
        try {
            airQulityIndex = Integer.parseInt(airQulityIndexString);
        } catch (Exception e) {
            ret = false;
            airQulityRepresentation.setmAirQulityType("---");
            airQulityRepresentation.setmAirQulityColorId(R.color.colorAirOne);
        }

        if (ret) {
            if (airQulityIndex <= 50) {
                airQulityRepresentation.setmAirQulityType("空气优");
                airQulityRepresentation.setmAirQulityColorId(R.color.colorAirOne);
            } else if (airQulityIndex <= 100) {
                airQulityRepresentation.setmAirQulityType("空气良");
                airQulityRepresentation.setmAirQulityColorId(R.color.colorAirTwo);
            } else if (airQulityIndex <= 150) {
                airQulityRepresentation.setmAirQulityType("轻度污染");
                airQulityRepresentation.setmAirQulityColorId(R.color.colorAirThree);
            } else if (airQulityIndex <= 200) {
                airQulityRepresentation.setmAirQulityType("中度污染");
                airQulityRepresentation.setmAirQulityColorId(R.color.colorAirFour);
            } else if (airQulityIndex <= 300) {
                airQulityRepresentation.setmAirQulityType("重度污染");
                airQulityRepresentation.setmAirQulityColorId(R.color.colorAirFive);
            } else {
                airQulityRepresentation.setmAirQulityType("严重污染");
                airQulityRepresentation.setmAirQulityColorId(R.color.colorAirSix);
            }
        }
        return ret;
    }

    private int getWeatherIconId(final String desc) {
        if (!TextUtils.isEmpty(desc)) {
            if (desc.equalsIgnoreCase(mContext.getString(R.string.sunny))) {
                return R.drawable.iclockweather_w1;
            } else if (desc.equalsIgnoreCase(mContext.getString(R.string.partly_cloudy))
                    || desc.equalsIgnoreCase(mContext.getString(R.string.cloudy))
                    || desc.equalsIgnoreCase(mContext.getString(R.string.few_cloud))) {
                return R.drawable.iclockweather_w2;
            } else if (desc.equalsIgnoreCase(mContext.getString(R.string.overcast))) {
                return R.drawable.iclockweather_w3;
            } else if (desc.equalsIgnoreCase(mContext.getString(R.string.shower_rain))) {
                return R.drawable.iclockweather_w8;
            } else if (desc.equalsIgnoreCase(mContext.getString(R.string.heavy_shower_rain))) {
                return R.drawable.iclockweather_w8;
            } else if (desc.equalsIgnoreCase(mContext.getString(R.string.thunder_shower))
                    || desc.equalsIgnoreCase(mContext.getString(R.string.heavy_thunderstorm))) {
                return R.drawable.iclockweather_w9;
            } else if (desc.equalsIgnoreCase(mContext.getString(R.string.hail))) {
                return R.drawable.iclockweather_w18;
            } else if (desc.equalsIgnoreCase(mContext.getString(R.string.light_rain))
                    || desc.equalsIgnoreCase(mContext.getString(R.string.drizzle_rain))
                    || desc.equalsIgnoreCase(mContext.getString(R.string.drizzle_rain_1))) {
                return R.drawable.iclockweather_w4;
            } else if (desc.equalsIgnoreCase(mContext.getString(R.string.moderate_rain))) {
                return R.drawable.iclockweather_w5;
            } else if (desc.equalsIgnoreCase(mContext.getString(R.string.heavy_rain))
                    || desc.equalsIgnoreCase(mContext.getString(R.string.storm))) {
                return R.drawable.iclockweather_w6;
            } else if (desc.equalsIgnoreCase(mContext.getString(R.string.extreme_rain))
                    || desc.equalsIgnoreCase(mContext.getString(R.string.heavy_storm))
                    || desc.equalsIgnoreCase(mContext.getString(R.string.severe_storm))) {
                return R.drawable.iclockweather_w7;
            } else if (desc.equalsIgnoreCase(mContext.getString(R.string.freezing_rain))) {
                return R.drawable.iclockweather_w15;
            } else if (desc.equalsIgnoreCase(mContext.getString(R.string.light_snow))
                    || desc.equalsIgnoreCase(mContext.getString(R.string.snow_flurry))) {
                return R.drawable.iclockweather_w11;
            } else if (desc.equalsIgnoreCase(mContext.getString(R.string.moderate_snow))) {
                return R.drawable.iclockweather_w12;
            } else if (desc.equalsIgnoreCase(mContext.getString(R.string.heavy_snow))) {
                return R.drawable.iclockweather_w13;
            } else if (desc.equalsIgnoreCase(mContext.getString(R.string.snow_storm))) {
                return R.drawable.iclockweather_w14;
            } else if (desc.equalsIgnoreCase(mContext.getString(R.string.sleet))
                    || desc.equalsIgnoreCase(mContext.getString(R.string.rain_snow))
                    || desc.equalsIgnoreCase(mContext.getString(R.string.shower_snow))) {
                return R.drawable.iclockweather_w10;
            } else if (desc.equalsIgnoreCase(mContext.getString(R.string.mist))
                    || desc.equalsIgnoreCase(mContext.getString(R.string.foggy))) {
                return R.drawable.iclockweather_w16;
            } else if (desc.equalsIgnoreCase(mContext.getString(R.string.haze))
                    || desc.equalsIgnoreCase(mContext.getString(R.string.sand))
                    || desc.equalsIgnoreCase(mContext.getString(R.string.dust))
                    || desc.equalsIgnoreCase(mContext.getString(R.string.volcanic_ash))
                    || desc.equalsIgnoreCase(mContext.getString(R.string.dust_storm))
                    || desc.equalsIgnoreCase(mContext.getString(R.string.sand_storm))) {
                return R.drawable.iclockweather_w17;
            }
        }

        return R.drawable.iclockweather_w2;
    }
}
