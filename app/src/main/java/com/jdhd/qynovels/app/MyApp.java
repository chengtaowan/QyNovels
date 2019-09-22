package com.jdhd.qynovels.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.StrictMode;


import com.bytedance.sdk.openadsdk.TTAdConfig;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Function;
import okhttp3.OkHttpClient;
import rxhttp.HttpSender;
import rxhttp.wrapper.annotation.DefaultDomain;
import rxhttp.wrapper.annotation.Param;
import rxhttp.wrapper.param.DeleteRequest;
import rxhttp.wrapper.param.GetRequest;
import rxhttp.wrapper.param.PostRequest;
import rxhttp.wrapper.param.PutRequest;

public class MyApp extends Application {
    private static Context context;
    private static IWXAPI api;
    private static final String APP_ID = "wxf2f9d368f73b6719";
    public static List<Activity> list=new ArrayList<>();
    public static int raduis=10;
    public static Handler handler;
    @Override
    public void onCreate() {
        super.onCreate();
        String name= DeviceInfoUtils.getChannelName(this);
        UMConfigure.init(this, "5d50e0b90cafb24cf000049c", name, UMConfigure.DEVICE_TYPE_PHONE, null);
        // 选用LEGACY_AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.LEGACY_AUTO);
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

       //穿山甲初始化
        TTAdSdk.init(this,new TTAdConfig.Builder()
                .appId("5026447")
                //.appId("5001121")
                .useTextureView(true) //使用TextureView控件播放视频,默认为SurfaceView,当有SurfaceView冲突的场景，可以使用TextureView
                .appName("趣阅小说_android")
               // .appName("APP测试媒体")
                .titleBarTheme(TTAdConstant.TITLE_BAR_THEME_DARK)
                .allowShowNotify(true) //是否允许sdk展示通知栏提示
                .allowShowPageWhenScreenLock(true) //是否在锁屏场景支持展示广告落地页
                .debug(true) //测试阶段打开，可以通过日志排查问题，上线时去除该调用
                .directDownloadNetworkType(TTAdConstant.NETWORK_STATE_WIFI, TTAdConstant.NETWORK_STATE_3G) //允许直接下载的网络状态集合
                .supportMultiProcess(false)//是否支持多进程
                //.httpStack(new MyOkStack3())//自定义网络库，demo中给出了okhttp3版本的样例，其余请自行开发或者咨询工作人员。
                .build());

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
        //public static final String baseUrl="http://192.168.1.199:19919/api.php/v1/";
        public static final String baseUrl = "http://api.damobi.cn/v1/";
        //public static final String webbaseUrl="http://h5.damobi.cn/";
        public static final String webbaseUrl="http://192.168.1.127:8848/";
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

    // 进行数据分析的数据类型
    public class kQYDataAnalysisEventType{
    public static final int kQYDataAnalysisSystemEvent = 10;    // 10系统事件
    public static final int kQYDataAnalysisTargetEvent = 20;    // 20按钮点击事件
    public static final int kQYDataAnalysisPageEvent = 30;      // 30页面统计事件",
    } ;

// 事件内容 10 打开关闭app 20 我的-点击登录-登录页 30 红包--登录立即提现-登录页 40 书架-搜索页 50 书城-搜索页 60 福利-邀请好友-分享 70 福利-点击观看小视频 80 书城页面 90 福利页面 100 书架页面"

    // 系操作类型枚举
    public class kQYoperationType {
        public static final int kQYSoperationTypeOpen = 10;       // 10 打开/关闭页面
        public static final int kQoperationTypeCloseApp = 20;    // 20关闭app
    } ;


// 用户交互事件枚举（主要用于记录统计按钮点击事件）
    public class kQYTargetDataAnalysis {
    public static final int kQYTargetDataAnalysisMine_login = 20;           // 20 我的-点击登录-登录页
    public static final int kQYTargetDataAnalysisActivity_login = 30;      // 30 红包--登录立即提现-登录页
    public static final int kQYTargetDataAnalysisBookshelf_search = 40;     // 40 书架-搜索页
    public static final int kQYTargetDataAnalysisBookCity_search = 50;      // 50 书城-搜索页
    public static final int kQYTargetDataAnalysisWelfare_invite_share = 60; // 60 福利-邀请好友-分享
    public static final int kQYTargetDataAnalysisWelfare_watchVideo = 70;   // 70 福利-点击观看小视频
    } ;

// 页面枚举
    public class kQYPageDataAnalysis {
    public static final int kQYPageDataAnalysisBookCity = 80;       // 80 书城页面
    public static final int kQYPageDataAnalysisWelfare = 90;        // 90 福利页面
    public static final int kQYPageDataAnalysisBookShelf = 100;     // 100 书架页面
    } ;

    public static void addActivity(Activity activity){
        if(!list.contains(activity)){
            list.add(activity);
        }

    }

    public static void romoveActivity(Activity activity){
        if(!list.contains(activity)){
            list.remove(activity);
            if(activity!=null){
                activity.finish();
            }
        }

    }

    public static void removeallActivity(){
        for(int i=0;i<list.size();i++){
            if(list.get(i)!=null){
                list.get(i).finish();
            }
        }
        //杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
