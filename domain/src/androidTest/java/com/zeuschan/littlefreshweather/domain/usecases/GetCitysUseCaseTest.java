package com.zeuschan.littlefreshweather.domain.usecases;

import android.support.test.runner.AndroidJUnit4;

import com.zeuschan.littlefreshweather.model.entities.CityEntity;

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
public class GetCitysUseCaseTest {
    public static final String TAG = GetCitysUseCaseTest.class.getSimpleName();

    private UseCase<List<CityEntity>> mUseCase = null;

    @Before
    public void createGetCitysUseCase() {
        mUseCase = new GetCitysUseCase();
    }

    @Test
    public void testGetCitysUseCase() throws Exception {
        assertNotNull(mUseCase);
        TestSubscriber<List<CityEntity>> testSubscriber = TestSubscriber.create(new GetCitysUseCaseDelegate());
        mUseCase.execute(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        //testSubscriber.assertError(Exception.class);
        testSubscriber.assertNoErrors();
    }
}
