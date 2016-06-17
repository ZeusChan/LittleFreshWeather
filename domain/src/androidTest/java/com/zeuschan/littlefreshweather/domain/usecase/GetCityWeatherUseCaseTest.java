package com.zeuschan.littlefreshweather.domain.usecase;

import android.support.test.runner.AndroidJUnit4;

import com.zeuschan.littlefreshweather.model.entity.WeatherEntity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import rx.observers.TestSubscriber;

import static org.junit.Assert.*;

/**
 * Created by chenxiong on 2016/6/2.
 */
@RunWith(AndroidJUnit4.class)
public class GetCityWeatherUseCaseTest {
    private UseCase<WeatherEntity> mUseCase = null;

    @Before
    public void createUseCase() {
        //mUseCase = new GetCityWeatherUseCase("CN101010300");
    }

    @Test
    public void testGetCityWeatherUseCase() throws Exception {
        assertNotNull(mUseCase);
        TestSubscriber<WeatherEntity> testSubscriber = TestSubscriber.create(new GetCityWeatherUseCaseDelegate());
        mUseCase.execute(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        testSubscriber.assertNoErrors();
    }
}
