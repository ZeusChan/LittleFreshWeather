package com.zeuschan.littlefreshweather.domain.usecase;

import android.support.test.runner.AndroidJUnit4;

import com.zeuschan.littlefreshweather.model.entity.WeatherConditionEntity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import rx.observers.TestSubscriber;

import static org.junit.Assert.*;

/**
 * Created by chenxiong on 2016/6/2.
 */
@RunWith(AndroidJUnit4.class)
public class GetWeatherConditionsUseCaseTest {
    public static final String TAG = GetWeatherConditionsUseCaseTest.class.getSimpleName();

    private UseCase<List<WeatherConditionEntity>> mUseCase = null;

    @Before
    public void createGetWeatherConditionsUseCase() {
        //mUseCase = new GetWeatherConditionsUseCase();
    }

    @Test
    public void testGetWeatherConditionsUseCase() throws Exception {
        assertNotNull(mUseCase);
        TestSubscriber<List<WeatherConditionEntity>> testSubscriber = TestSubscriber.create(new GetWeatherConditionsUseCaseDelegate());
        mUseCase.execute(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        testSubscriber.assertNoErrors();
    }

}
