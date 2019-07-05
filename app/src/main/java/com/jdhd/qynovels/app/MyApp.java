package com.jdhd.qynovels.app;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import okhttp3.OkHttpClient;
import rxhttp.HttpSender;
import rxhttp.wrapper.annotation.DefaultDomain;

public class MyApp extends Application {
    private static Context context;
    private static IWXAPI api;
    private static final String APP_ID = "wxf2f9d368f73b6719";
    @Override
    public void onCreate() {
        super.onCreate();
        MyApp.context=getApplicationContext();
        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        ZXingLibrary.initDisplayOpinion(this);
//        //设置debug模式，此模式下有日志打印
//        HttpSender.setDebug(true);
////或者，调试模式下会有日志输出
//        HttpSender.init(new OkHttpClient(),true);
        regToWx();

    }
    public static Context getAppContext() {
        return MyApp.context;
    }

    public static IWXAPI getApi() {
        return api;
    }
    private void regToWx() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, APP_ID, true);

        // 将应用的appId注册到微信
        api.registerApp(APP_ID);

//        //建议动态监听微信启动广播进行注册到微信
//        registerReceiver(new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                // 将该app注册到微信
//                api.registerApp(SyncStateContract.Constants._ID);
//            }
//        }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));

    }

    public class Url{
        @DefaultDomain()
        public static final String baseUrl = "http://15492b50l3.51mypc.cn:37652/api.php/v1/";
    }

}
