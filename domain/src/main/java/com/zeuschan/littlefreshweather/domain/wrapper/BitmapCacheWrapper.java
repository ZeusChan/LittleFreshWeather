package com.zeuschan.littlefreshweather.domain.wrapper;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.zeuschan.littlefreshweather.model.cache.MemoryCacheItem;

/**
 * Created by chenxiong on 2016/7/1.
 */
public class BitmapCacheWrapper implements MemoryCacheItem {
    private Context mContext;
    private int mResId;
    private Bitmap mBitmap;

    public BitmapCacheWrapper(Context context, int resId) {
        mContext = context;
        mResId = resId;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    @Override
    public int getSize() {
        return mBitmap.getByteCount();
    }

    @Override
    public void release() {
        mBitmap.recycle();
        mContext = null;
    }

    @Override
    public void inflate() {
        if (mContext != null) {
            mBitmap = BitmapFactory.decodeResource(mContext.getResources(), mResId);
        }
    }
}
