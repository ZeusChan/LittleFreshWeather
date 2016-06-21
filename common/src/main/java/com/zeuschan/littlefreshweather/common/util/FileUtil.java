package com.zeuschan.littlefreshweather.common.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import java.io.File;

/**
 * Created by chenxiong on 2016/6/16.
 */
public class FileUtil {
    public static File getDiskCacheDir(Context context, String dirName) {
        String cacheDir;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cacheDir = context.getExternalCacheDir().getPath();
        } else {
            cacheDir = context.getCacheDir().getPath();
        }

        return  new File(cacheDir + File.separator + dirName);
    }

    public static void putStringToPreferences(Context context, String prefName, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getStringFromPreferences(Context context, String prefName, String key, String defValue) {
        SharedPreferences preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return preferences.getString(key, defValue);
    }
}
