package com.zeuschan.littlefreshweather.prsentation.presenter;


import android.text.TextUtils;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.zeuschan.littlefreshweather.common.util.Constants;
import com.zeuschan.littlefreshweather.common.util.FileUtil;
import com.zeuschan.littlefreshweather.common.util.NetUtil;
import com.zeuschan.littlefreshweather.domain.usecase.GetCitiesUseCase;
import com.zeuschan.littlefreshweather.model.entity.CityEntity;
import com.zeuschan.littlefreshweather.model.entity.LocationEntity;
import com.zeuschan.littlefreshweather.prsentation.R;
import com.zeuschan.littlefreshweather.prsentation.view.SplashView;

import java.util.List;

import rx.Subscriber;

/**
 * Created by chenxiong on 2016/6/20.
 */
public class SplashPresenter implements Presenter, AMapLocationListener {
    private static final int LOCATION_UPPER_BOUND = 2;
    private SplashView mView;
    private GetCitiesUseCase mUseCase;

    private AMapLocationClient mLocationClient = null;
    private AMapLocationClientOption mLocationClientOption = null;
    private int mLocationCounter = 0;

    private List<CityEntity> mListCities = null;
    private LocationEntity mLocationEntity = null;
    private String mCityId = null;
    private boolean mIsNetworkAvailable = false;
    private String mDefaultCityId = null;
    private boolean mIsDownloadDone = false;
    private boolean mIsLocationDone = false;

    public void attachView(SplashView view) {
        mView = view;
        mDefaultCityId = FileUtil.getStringFromPreferences(mView.getContext().getApplicationContext(), Constants.GLOBAL_SETTINGS, Constants.PRF_KEY_CITY_ID, Constants.DEFAULT_CITY_ID);
        mIsNetworkAvailable = NetUtil.isNetworkAvailable(mView.getContext().getApplicationContext());
        if (mIsNetworkAvailable) {
            mUseCase = new GetCitiesUseCase(mView.getContext().getApplicationContext());
            initLocation();
        }
    }

    @Override
    public void start() {
        if (!mIsNetworkAvailable) {
            mView.showError(mView.getContext().getString(R.string.network_unavailable));
            mView.navigateToCityWeatherActivity(mDefaultCityId);
            return;
        }

        mIsDownloadDone = false;
        mIsLocationDone = false;
        mUseCase.execute(new GetCitiesSubscriber());
        mLocationCounter = 0;
        mLocationClient.startLocation();
    }

    @Override
    public void stop() {
        if (!mIsNetworkAvailable)
            return;

        mLocationClient.onDestroy();
        mUseCase.unsubscribe();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        ++mLocationCounter;

        if (null == aMapLocation || TextUtils.isEmpty(aMapLocation.getCountry()) || TextUtils.isEmpty(aMapLocation.getProvince())
                || TextUtils.isEmpty(aMapLocation.getCity())) {
            if (mLocationCounter > LOCATION_UPPER_BOUND) {
                //mView.showError("定位失败");
                mIsLocationDone = true;
                mLocationClient.stopLocation();
                mView.navigateToCitiesActivity(mDefaultCityId);
            }
            return;
        }

        mLocationEntity = new LocationEntity();
        mLocationEntity.setCountry(aMapLocation.getCountry());
        mLocationEntity.setProvince(aMapLocation.getProvince());
        mLocationEntity.setCity(aMapLocation.getCity());
        mLocationEntity.setCityCode(aMapLocation.getCityCode());
        mLocationEntity.setDistrict(aMapLocation.getDistrict());
        //mView.showError("定位成功");
        mIsLocationDone = true;
        mLocationClient.stopLocation();

        if (getCityIdFromLocation())
            mView.navigateToCityWeatherActivity(mCityId);
        else if (mIsDownloadDone) {
            mView.navigateToCityWeatherActivity(mDefaultCityId);
        }
    }

    private boolean getCityIdFromLocation() {
        if (mLocationEntity != null && mListCities != null) {
            for (CityEntity cityEntity : mListCities) {
                if (/*(mLocationEntity.getProvince().contains(cityEntity.getProvince()) || cityEntity.getProvince().contains(mLocationEntity.getProvince()))
                        &&*/ ((mLocationEntity.getCity().contains(cityEntity.getCity()) || cityEntity.getCity().contains(mLocationEntity.getCity()))
                        || (mLocationEntity.getDistrict().contains(cityEntity.getCity()) || cityEntity.getCity().contains(mLocationEntity.getDistrict())))) {
                    mCityId = cityEntity.getCityId();
                    FileUtil.putStringToPreferences(mView.getContext().getApplicationContext(), Constants.GLOBAL_SETTINGS, Constants.PRF_KEY_CITY_ID, mCityId);
                    return true;
                }
            }
        }

        return false;
    }

    private void initLocation() {
        mLocationClient = new AMapLocationClient(mView.getContext().getApplicationContext());
        mLocationClientOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationClientOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationClientOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationClientOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationClientOption.setMockEnable(true);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationClientOption.setInterval(1000);
        mLocationClient.setLocationOption(mLocationClientOption);
        mLocationClient.setLocationListener(this);
    }

    private final class GetCitiesSubscriber extends Subscriber<List<CityEntity>> {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            mIsDownloadDone = true;
        }

        @Override
        public void onNext(List<CityEntity> cityEntities) {
            mIsDownloadDone = true;
            mListCities = cityEntities;
            if (getCityIdFromLocation())
                mView.navigateToCityWeatherActivity(mCityId);
            else if (mIsLocationDone) {
                mView.navigateToCityWeatherActivity(mDefaultCityId);
            }
        }
    }
}
