package com.jdhd.qynovels.app;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

public class MyApp extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        MyApp.context=getApplicationContext();
        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

    }
    public static Context getAppContext() {
        return MyApp.context;
    }
}
