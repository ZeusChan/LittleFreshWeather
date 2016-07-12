package com.zeuschan.littlefreshweather.prsentation.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zeuschan.littlefreshweather.prsentation.R;
import com.zeuschan.littlefreshweather.prsentation.wrapper.LifeIndexWrapper;

import java.util.List;

//import butterknife.BindView;
//import butterknife.ButterKnife;

/**
 * Created by chenxiong on 2016/6/14.
 */
public class LifeIndexAdapter extends ArrayAdapter<LifeIndexWrapper> {
    int mResourceId;

    public LifeIndexAdapter(Context context, int resource, List<LifeIndexWrapper> objects) {
        super(context, resource, objects);
        this.mResourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LifeIndexWrapper lifeIndex = getItem(position);
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

        viewHolder.tvName.setText(lifeIndex.getLifeIndexName());
        viewHolder.tvBrief.setText(lifeIndex.getLifeIndexBrief());
        viewHolder.tvDesc.setText(lifeIndex.getLifeIndexDesc());

        return view;
    }

    public static class ViewHolder {
//        @BindView(R.id.tv_city_weather_life_index_name) TextView tvName;
//        @BindView(R.id.tv_city_weather_life_index_brief) TextView tvBrief;
//        @BindView(R.id.tv_city_weather_life_index_desc) TextView tvDesc;

        TextView tvName;
        TextView tvBrief;
        TextView tvDesc;

        public ViewHolder(View itemView) {
            //ButterKnife.bind(this, itemView);

            tvName = (TextView)itemView.findViewById(R.id.tv_city_weather_life_index_name);
            tvBrief = (TextView)itemView.findViewById(R.id.tv_city_weather_life_index_brief);
            tvDesc = (TextView)itemView.findViewById(R.id.tv_city_weather_life_index_desc);
        }
    }

}
