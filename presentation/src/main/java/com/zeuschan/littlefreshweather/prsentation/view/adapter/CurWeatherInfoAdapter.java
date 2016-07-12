package com.zeuschan.littlefreshweather.prsentation.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zeuschan.littlefreshweather.prsentation.R;
import com.zeuschan.littlefreshweather.prsentation.wrapper.CurWeatherInfoWrapper;

import java.util.List;

//import butterknife.BindView;
//import butterknife.ButterKnife;

/**
 * Created by chenxiong on 2016/6/13.
 */
public class CurWeatherInfoAdapter extends ArrayAdapter<CurWeatherInfoWrapper> {
    int mResourceId;

    public CurWeatherInfoAdapter(Context context, int resource, List<CurWeatherInfoWrapper> objects) {
        super(context, resource, objects);
        this.mResourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CurWeatherInfoWrapper weatherInfo = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (null == convertView) {
            view = LayoutInflater.from(getContext()).inflate(mResourceId, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }

        viewHolder.tvName.setText(weatherInfo.getWeatherInfoName());
        viewHolder.tvValue.setText(weatherInfo.getWeatherInfoValue());

        return view;
    }

    public static class ViewHolder {
//        @BindView(R.id.tv_city_weather_cur_weather_info_item_name) TextView tvName;
//        @BindView(R.id.tv_city_weather_cur_weather_info_item_value) TextView tvValue;

        TextView tvName;
        TextView tvValue;

        public ViewHolder(View itemView) {
            //ButterKnife.bind(this, itemView);

            tvName = (TextView)itemView.findViewById(R.id.tv_city_weather_cur_weather_info_item_name);
            tvValue = (TextView)itemView.findViewById(R.id.tv_city_weather_cur_weather_info_item_value);
        }
    }

}
