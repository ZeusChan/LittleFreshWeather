package com.zeuschan.littlefreshweather.domain.usecase;

import android.content.Context;

import com.zeuschan.littlefreshweather.domain.wrapper.BitmapCacheWrapper;
import com.zeuschan.littlefreshweather.model.cache.MemoryCacheManager;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by chenxiong on 2016/7/1.
 */
public class GetBitmapUseCase extends UseCase<BitmapCacheWrapper> {
    Context mContext;
    int mResId;

    public GetBitmapUseCase(Context context, int resId) {
        mContext = context;
        mResId = resId;
    }

    @Override
    public void clear() {
        MemoryCacheManager.getInstance().clear();
        mContext = null;
    }

    @Override
    protected Observable<BitmapCacheWrapper> buildUseCaseObservable() {
        return Observable.create(new Observable.OnSubscribe<BitmapCacheWrapper>() {
            @Override
            public void call(Subscriber<? super BitmapCacheWrapper> subscriber) {
                BitmapCacheWrapper bitmapCacheWrapper = (BitmapCacheWrapper)MemoryCacheManager.getInstance().getItemFromCache(String.valueOf(mResId));
                if (bitmapCacheWrapper == null) {
                    bitmapCacheWrapper = new BitmapCacheWrapper(mContext, mResId);
                    bitmapCacheWrapper.inflate();
                    MemoryCacheManager.getInstance().putItemToCache(String.valueOf(mResId), bitmapCacheWrapper);
                }

                subscriber.onNext(bitmapCacheWrapper);
                subscriber.onCompleted();
            }
        });
    }
}
