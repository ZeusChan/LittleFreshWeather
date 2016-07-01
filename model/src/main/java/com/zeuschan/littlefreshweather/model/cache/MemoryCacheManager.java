package com.zeuschan.littlefreshweather.model.cache;

import android.support.v4.util.LruCache;

/**
 * Created by chenxiong on 2016/7/1.
 */
public class MemoryCacheManager<T extends MemoryCacheItem> {
    private LruCache<String, T> mMemoryLruCache;

    private static MemoryCacheManager ourInstance = null;

    public static MemoryCacheManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new MemoryCacheManager();
        }
        return ourInstance;
    }

    private MemoryCacheManager() {
        // 取本虚拟机可用的最大内存的1/16作为内存缓存区
        int maxMemorySize = (int)Runtime.getRuntime().maxMemory();
        int maxCacheSize = maxMemorySize >> 4;
        mMemoryLruCache = new LruCache<String, T>(maxCacheSize) {
            @Override
            protected int sizeOf(String key, T value) {
                return value.getSize();
            }

            @Override
            protected void entryRemoved(boolean evicted, String key,
                                        T oldValue, T newValue) {
                if (oldValue != null)
                    oldValue.release();
            }
        };
    }

    public void putItemToCache(String key, T value) {
        if (mMemoryLruCache != null && mMemoryLruCache.get(key) == null) {
            mMemoryLruCache.put(key, value);
        }
    }

    public T getItemFromCache(final String key) {
        if (mMemoryLruCache != null) {
            return mMemoryLruCache.get(key);
        }

        return null;
    }

    public void clear() {
        if (mMemoryLruCache != null) {
            mMemoryLruCache.evictAll();
        }
    }
}
