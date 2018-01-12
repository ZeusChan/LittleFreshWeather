package com.zeuschan.littlefreshweather.common.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Process;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by chenxiong on 2018/1/9.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = CrashHandler.class.getSimpleName();
    private static final String CRASH_DUMP_DIR = "crash";
    private static final String CRASH_DUMP_FILE = "crash.dmp";

    private static CrashHandler ourInstance = null;
    public static CrashHandler getInstance(Context context) {
        if (null == ourInstance) {
            ourInstance = new CrashHandler(context);
        }

        return ourInstance;
    }

    private Context mContext = null;
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler = null;

    private CrashHandler(Context context) {
        if (null == context)
            throw new IllegalArgumentException("context cannot be null");

        mContext = context;
    }

    public void init() {
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @SuppressWarnings("deprecation")
    private void dumpPhoneInfo(PrintWriter pw) {
        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            pw.print("App Version: ");
            pw.print(pi.versionName);
            pw.print("_");
            pw.println(pi.versionCode);

            pw.print("OS Version: ");
            pw.print(Build.VERSION.RELEASE);
            pw.print("_");
            pw.println(Build.VERSION.SDK_INT);

            pw.print("Vendor: ");
            pw.println(Build.MANUFACTURER);

            pw.print("Model: ");
            pw.println(Build.MODEL);

            pw.print("CPU API: ");
            pw.println(Build.CPU_ABI);
        } catch (PackageManager.NameNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private void dumpException2SDCard(Throwable ex) throws IOException {
        File dumpDir = FileUtil.getSDCardDir(mContext, CRASH_DUMP_DIR);
        if (null == dumpDir)
            return;

        if (!dumpDir.exists())
            dumpDir.mkdir();

        String time = StringUtil.getCurrentDateTime("yyyy-MM-dd_HH:mm:ss_");
        File dumpFile = new File(dumpDir.getPath() + File.separator + time + CRASH_DUMP_FILE);

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(dumpFile)));
        pw.println(time);
        dumpPhoneInfo(pw);
        pw.println();
        ex.printStackTrace(pw);
        pw.close();
    }

    private void uploadException2Server(Throwable ex) throws IOException {
        //TODO: Upload exception information to your server.
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            dumpException2SDCard(ex);
            uploadException2Server(ex);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ex.printStackTrace();
        if (null != mDefaultCrashHandler) {
            mDefaultCrashHandler.uncaughtException(thread, ex);
        } else {
            Process.killProcess(Process.myPid());
        }
    }
}
