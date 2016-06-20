package com.zeuschan.littlefreshweather.domain.usecase;

import android.content.Context;

import com.zeuschan.littlefreshweather.model.datasource.DataSourceManager;
import com.zeuschan.littlefreshweather.model.entity.CityEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by chenxiong on 2016/6/2.
 */
public class GetCitiesUseCase extends UseCase<List<CityEntity>> {
    private Context mContext;

    public GetCitiesUseCase(Context context) {
        mContext = context;
    }

    @Override
    protected Observable<List<CityEntity>> buildUseCaseObservable() {
        return DataSourceManager.getInstance(mContext).getCityEntities();
    }
}
