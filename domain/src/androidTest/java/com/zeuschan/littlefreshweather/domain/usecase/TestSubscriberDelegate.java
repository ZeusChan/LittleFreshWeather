package com.zeuschan.littlefreshweather.domain.usecase;

import android.util.Log;

import rx.Subscriber;

/**
 * Created by chenxiong on 2016/6/2.
 */
public class TestSubscriberDelegate<T> extends Subscriber<T> {
    public static final String TAG = TestSubscriberDelegate.class.getSimpleName();

    protected void LogContent(T t) {

    }

    @Override
    public void onCompleted() {
        Log.e(TAG, "---------------------------------------------------------------------onCompleted---------------------------------------------------------------------");
        Log.e(TAG, "---------------------------------------------------------------------onCompleted---------------------------------------------------------------------");
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "---------------------------------------------------------------------onError---------------------------------------------------------------------");
        Log.e(TAG, e.getMessage());
        Log.e(TAG, "---------------------------------------------------------------------onError---------------------------------------------------------------------");    }

    @Override
    public void onNext(T t) {
        Log.e(TAG, "---------------------------------------------------------------------onNext---------------------------------------------------------------------");
        LogContent(t);
        Log.e(TAG, "---------------------------------------------------------------------onNext---------------------------------------------------------------------");
    }
}
