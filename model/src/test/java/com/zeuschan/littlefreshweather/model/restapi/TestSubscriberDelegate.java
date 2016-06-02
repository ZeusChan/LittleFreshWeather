package com.zeuschan.littlefreshweather.model.restapi;


import java.util.logging.Logger;

import rx.Subscriber;

/**
 * Created by chenxiong on 2016/6/2.
 */
public class TestSubscriberDelegate<T> extends Subscriber<T> {
    public static final String TAG = TestSubscriberDelegate.class.getSimpleName();
    protected final Logger logger = Logger.getLogger(TAG);

    protected void LogContent(T t) {

    }

    @Override
    public void onCompleted() {
        logger.info("-----------------------------------------onCompleted-----------------------------------------");
        logger.info("-----------------------------------------onCompleted-----------------------------------------");
    }

    @Override
    public void onError(Throwable e) {
        logger.info("-----------------------------------------onError-----------------------------------------");
        logger.info(e.getMessage());
        logger.info("-----------------------------------------onError-----------------------------------------");
    }

    @Override
    public void onNext(T t) {
        logger.info("-----------------------------------------onNext-----------------------------------------");
        LogContent(t);
        logger.info("-----------------------------------------onNext-----------------------------------------");
    }
}
