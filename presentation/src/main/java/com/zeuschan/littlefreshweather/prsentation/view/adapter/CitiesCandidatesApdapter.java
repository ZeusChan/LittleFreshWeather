package com.zeuschan.littlefreshweather.prsentation.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zeuschan.littlefreshweather.model.entity.CityEntity;
import com.zeuschan.littlefreshweather.prsentation.R;

import java.util.List;

//import butterknife.BindView;
//import butterknife.ButterKnife;

/**
 * Created by chenxiong on 2016/6/21.
 */
public class CitiesCandidatesApdapter extends ArrayAdapter<CityEntity> {
    int mResourceId;

    public CitiesCandidatesApdapter(Context context, int resource, List<CityEntity> objects) {
        super(context, resource, objects);
        this.mResourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CityEntity entity = getItem(position);
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

        viewHolder.tvCandidateName.setText(entity.getProvince() + " - " + entity.getCity());

        return view;
    }

    public static class ViewHolder {
        //@BindView(R.id.tv_cities_canditate_name) TextView tvCandidateName;
        TextView tvCandidateName;

        public ViewHolder(View itemView) {
            //ButterKnife.bind(this, itemView);
            tvCandidateName = (TextView)itemView.findViewById(R.id.tv_cities_canditate_name);
        }
    }
}
