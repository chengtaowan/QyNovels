package com.glong.reader.activities;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import rxhttp.wrapper.annotation.DefaultDomain;

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

//        //设置debug模式，此模式下有日志打印
//        HttpSender.setDebug(true);
////或者，调试模式下会有日志输出
//        HttpSender.init(new OkHttpClient(),true);

    }
    public static Context getAppContext() {
        return MyApp.context;
    }



    public class Url{
        @DefaultDomain
        public static final String baseUrl="http://api.damobi.cn/v1/";
    }

    public class ModuleType{
        // 10 banner 20 功能小图标 30 今日大热搜 40 本周看点 50 实时热搜 60 新书抢鲜 70 高分精选 80 重磅推荐 90 完结好书 100 热书更新 110 分类展示

         public static final int kSectionTypeBanner = 10,            // banner
                    kSectionTypeFunction = 20,          // 功能小图标
                    kSectionTypeTodayHotSearch =30,    // 今日大热搜
                    kSectionTypeWeekWatch = 40,         // 本周看点
                    kSectionTypeRealTimeHotSearch =50, // 实时热搜
                    kSectionTypeNewBookFresh = 60,      // 新书抢鲜
                    kSectionTypeHighMarks = 70,         // 高分精选
                    kSectionTypeBlockbusterRecommended = 80,// 重磅推荐
                    kSectionTypeFinishedGoodBook = 90,      // 完结好书
                    kSectionTypeHotBookUpdate = 100,        // 热书更新 （今日更新顶部）
                    kSectionTypeClassifyShow = 110,         // 分类展示
                    kSectionTypeHotBookUpdateList = 120;        // 热书更新 （今日更新顶部）

    }

}
