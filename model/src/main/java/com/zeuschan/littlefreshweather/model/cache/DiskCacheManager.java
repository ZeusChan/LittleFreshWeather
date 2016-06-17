package com.zeuschan.littlefreshweather.model.cache;

import android.content.Context;

import com.jakewharton.disklrucache.DiskLruCache;
import com.zeuschan.littlefreshweather.common.util.FileUtil;
import com.zeuschan.littlefreshweather.common.util.PackageUtil;
import com.zeuschan.littlefreshweather.common.util.StringUtil;
import com.zeuschan.littlefreshweather.model.datasource.DataSource;
import com.zeuschan.littlefreshweather.model.entity.CityEntity;
import com.zeuschan.littlefreshweather.model.entity.WeatherConditionEntity;
import com.zeuschan.littlefreshweather.model.entity.WeatherEntity;
import com.zeuschan.littlefreshweather.model.exception.DiskCacheException;

import java.io.File;
import java.io.IOException;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by chenxiong on 2016/6/17.
 */
public class DiskCacheManager implements DataSource {
    private static final int DISK_CACHE_SIZE = 10 * 1024 * 1024;
    private static final String CITY_ENTITIES_CACHE_KEY = "city_entities";
    private static final String WEATHER_CONDITION_ENTITIES_CACHE_KEY = "weather_condition_entities";
    private static final String CITY_WEATHER_ENTITY_CACHE_KEY = "city_weather_entity";

    private DiskLruCache mDiskLruCache = null;
    private JsonProcesser mJsonProcesser;
    private Context mContext;
    private String mCacheDirName;

    public DiskCacheManager(Context context, String cacheDirName) {
        if (null == context || null == cacheDirName) {
            throw new IllegalArgumentException("arguments cannot be null");
        }
        this.mContext = context;
        this.mCacheDirName = cacheDirName;
        mJsonProcesser = new JsonProcesser();
        File cacheDir = FileUtil.getDiskCacheDir(mContext, mCacheDirName);
        if (!cacheDir.exists())
            cacheDir.mkdir();
        try {
            mDiskLruCache = DiskLruCache.open(cacheDir, PackageUtil.getAppVersion(mContext), 1, DISK_CACHE_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void putCityEntities(List<CityEntity> cityEntities) {
        if (null == mDiskLruCache || null == cityEntities)
            return;

        try {
            DiskLruCache.Editor editor = mDiskLruCache.edit(StringUtil.bytesToMd5String(CITY_ENTITIES_CACHE_KEY.getBytes()));
            if (editor != null) {
                editor.set(0, mJsonProcesser.cityEntitiesToJson(cityEntities));
                editor.commit();
                mDiskLruCache.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Observable<List<CityEntity>> getCityEntities() {

        if (mDiskLruCache != null) {
            try {
                DiskLruCache.Snapshot snapshot = mDiskLruCache.get(StringUtil.bytesToMd5String(CITY_ENTITIES_CACHE_KEY.getBytes()));
                if (snapshot != null) {
                    final String cityEntitiesJson = snapshot.getString(0);
                    return Observable.create(new Observable.OnSubscribe<List<CityEntity>>() {
                        @Override
                        public void call(Subscriber<? super List<CityEntity>> subscriber) {
                            subscriber.onNext(mJsonProcesser.jsonToCityEntities(cityEntitiesJson));
                            subscriber.onCompleted();
                        }
                    });
                }
                else
                    return Observable.just(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return Observable.error(new DiskCacheException("disk cache not exists"));
    }

    public void putWeatherConditionEntities(List<WeatherConditionEntity> weatherConditionEntities) {
        if (null == mDiskLruCache || null == weatherConditionEntities)
            return;

        try {
            DiskLruCache.Editor editor = mDiskLruCache.edit(StringUtil.bytesToMd5String(WEATHER_CONDITION_ENTITIES_CACHE_KEY.getBytes()));
            if (editor != null) {
                editor.set(0, mJsonProcesser.weatherConditionEntitiesToJson(weatherConditionEntities));
                editor.commit();
                mDiskLruCache.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Observable<List<WeatherConditionEntity>> getWeatherConditionEntities() {

        if (mDiskLruCache != null) {
            try {
                DiskLruCache.Snapshot snapshot = mDiskLruCache.get(StringUtil.bytesToMd5String(WEATHER_CONDITION_ENTITIES_CACHE_KEY.getBytes()));
                if (snapshot != null) {
                    final String weatherConditionEntitiesJson = snapshot.getString(0);
                    return Observable.create(new Observable.OnSubscribe<List<WeatherConditionEntity>>() {
                        @Override
                        public void call(Subscriber<? super List<WeatherConditionEntity>> subscriber) {
                            subscriber.onNext(mJsonProcesser.jsonToWeatherConditionEntities(weatherConditionEntitiesJson));
                            subscriber.onCompleted();
                        }
                    });
                }
                else
                    return Observable.just(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return Observable.error(new DiskCacheException("disk cache not exists"));
    }

    public void putCityWeather(WeatherEntity weatherEntity, String cityId) {
        if (null == mDiskLruCache || null == weatherEntity || null == cityId)
            return;

        try {
            DiskLruCache.Editor editor = mDiskLruCache.edit(StringUtil.bytesToMd5String((CITY_WEATHER_ENTITY_CACHE_KEY + cityId).getBytes()));
            if (editor != null) {
                editor.set(0, mJsonProcesser.weatherEntityToJson(weatherEntity));
                editor.commit();
                mDiskLruCache.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Observable<WeatherEntity> getCityWeather(String cityId) {

        if (mDiskLruCache != null) {
            try {
                DiskLruCache.Snapshot snapshot = mDiskLruCache.get(StringUtil.bytesToMd5String((CITY_WEATHER_ENTITY_CACHE_KEY + cityId).getBytes()));
                if (snapshot != null) {
                    final String weatherEntityJson = snapshot.getString(0);
                    return Observable.create(new Observable.OnSubscribe<WeatherEntity>() {
                        @Override
                        public void call(Subscriber<? super WeatherEntity> subscriber) {
                            subscriber.onNext(mJsonProcesser.jsonToWeatherEntity(weatherEntityJson));
                            subscriber.onCompleted();
                        }
                    });
                }
                else
                    return Observable.just(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return Observable.error(new DiskCacheException("disk cache not exists"));
    }
}
