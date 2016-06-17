package com.zeuschan.littlefreshweather.model.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by chenxiong on 2016/6/16.
 */
public class JobExecutor implements Executor {
    private static final int INITIAL_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 8;

    private static final int KEEP_ALIVE_TIME = 10;
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

    private static JobExecutor ourInstance = new JobExecutor();
    public static JobExecutor getInstance() {
        return ourInstance;
    }

    private final BlockingQueue<Runnable> mWorkQueue;
    private final ThreadFactory mThreadFactory;
    private final ThreadPoolExecutor mThreadPoolExecutor;

    private JobExecutor() {
        this.mWorkQueue = new LinkedBlockingQueue<>();
        this.mThreadFactory = new JobThreadFactory();
        this.mThreadPoolExecutor = new ThreadPoolExecutor(INITIAL_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, mWorkQueue, mThreadFactory);
    }

    @Override
    public void execute(Runnable runnable) {
        if (null == runnable) {
            throw new IllegalArgumentException("Runnable cannot be null");
        }
        mThreadPoolExecutor.execute(runnable);
    }

    private static class JobThreadFactory implements ThreadFactory {
        private static final String THREAD_NAME = "job_";
        private int counter = 0;

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, THREAD_NAME + counter++);
        }
    }
}
