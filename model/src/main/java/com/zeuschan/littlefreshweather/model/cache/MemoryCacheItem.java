package com.zeuschan.littlefreshweather.model.cache;

/**
 * Created by chenxiong on 2016/7/1.
 */
public interface MemoryCacheItem {
    int getSize();
    void release();
    void inflate();
}
