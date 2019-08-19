package com.jdhd.qynovels.ui.activity;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTSplashAd;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.utils.AndroidBug54971Workaround;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.umeng.analytics.MobclickAgent;
import com.youth.banner.WeakHandler;

import java.util.Timer;
import java.util.TimerTask;

public class GgActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SplashActivity";
    private TextView time,tg;
    private int recLen = 5;
    private TTAdNative mTTAdNative;
    Timer timer = new Timer();
    //是否强制跳转到主页面
    private boolean mForceGoMain;

    //开屏广告加载发生超时但是SDK没有及时回调结果的时候，做的一层保护。
    private final WeakHandler mHandler = new WeakHandler();
    //开屏广告加载超时时间,建议大于3000,这里为了冷启动第一次加载到广告并且展示,示例设置了3000ms
    private static final int AD_TIME_OUT = 3000;
    private static final int MSG_GO_MAIN = 1;
    //开屏广告是否已经加载
    private boolean mHasLoaded;
    private FrameLayout mSplashContainer;
    private int width=0;
    private int height=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gg);
        AndroidBug54971Workaround.assistActivity(findViewById(android.R.id.content),this);

        StatusBarUtil.setStatusBarMode(GgActivity.this, true, R.color.c_ffffff);
        MyApp.addActivity(this);
        WindowManager manager = getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;  //以要素为单位
        height = metrics.heightPixels;

        //StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        //step2:创建TTAdNative对象
        mTTAdNative = TTAdSdk.getAdManager().createAdNative(this);
        //在合适的时机申请权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题
        //在开屏时候申请不太合适，因为该页面倒计时结束或者请求超时会跳转，在该页面申请权限，体验不好
        //TTAdSdk.getAdManager().requestPermissionIfNecessary(MyApp.getAppContext());
        //定时，AD_TIME_OUT时间到时执行，如果开屏广告没有加载则跳转到主页面
        mHandler.sendEmptyMessageDelayed(MSG_GO_MAIN, AD_TIME_OUT);
        //加载开屏广告
        init();
        loadSplashAd();
    }

    private void init() {
        mSplashContainer=findViewById(R.id.gg);
//        time=findViewById(R.id.time);
//        tg=findViewById(R.id.tg);
//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
//                runOnUiThread(new Runnable() {
//                    // UI thread
//                    @Override
//                    public void run() {
//                        recLen--;
//                        time.setText(recLen+"s");
//                        if(recLen <=0){
//                            Intent intent=new Intent(GgActivity.this,MainActivity.class);
//                            startActivity(intent);
//                            timer.cancel();
//                        }
//                    }
//                });
//            }
//        };
//        timer.schedule(task, 1000, 1000);       // timeTask
//        tg.setOnClickListener(this);
     }

    /**
     * 加载开屏广告
     */
    private void loadSplashAd() {
        //step3:创建开屏广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId("826447918")
                .setSupportDeepLink(true)
                .setImageAcceptedSize(width, height)
                .build();
        //step4:请求广告，调用开屏广告异步请求接口，对请求回调的广告作渲染处理
        mTTAdNative.loadSplashAd(adSlot, new TTAdNative.SplashAdListener() {
            @Override
            @MainThread
            public void onError(int code, String message) {
                Log.e(TAG, message);
                mHasLoaded = true;
                Toast.makeText(GgActivity.this,message,Toast.LENGTH_SHORT).show();
                goToMainActivity();
            }

            @Override
            @MainThread
            public void onTimeout() {
                mHasLoaded = true;
               // Toast.makeText(GgActivity.this,"开屏广告加载超时",Toast.LENGTH_SHORT).show();
                goToMainActivity();
            }

            @Override
            @MainThread
            public void onSplashAdLoad(TTSplashAd ad) {
                Log.d(TAG, "开屏广告请求成功");
              //  Toast.makeText(GgActivity.this,"开屏广告加载超时",Toast.LENGTH_SHORT).show();

                mHasLoaded = true;
                mHandler.removeCallbacksAndMessages(null);
                if (ad == null) {
                    return;
                }
                //获取SplashView
                View view = ad.getSplashView();
                mSplashContainer.removeAllViews();
                //把SplashView 添加到ViewGroup中,注意开屏广告view：width >=70%屏幕宽；height >=50%屏幕宽
                mSplashContainer.addView(view);
                //设置不开启开屏广告倒计时功能以及不显示跳过按钮,如果这么设置，您需要自定义倒计时逻辑
                //ad.setNotAllowSdkCountdown();

                //设置SplashView的交互监听器
                ad.setSplashInteractionListener(new TTSplashAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, int type) {
                        Log.d(TAG, "onAdClicked");
                       // Toast.makeText(GgActivity.this,"开屏广告点击",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onAdShow(View view, int type) {
                        Log.d(TAG, "onAdShow");
                       // Toast.makeText(GgActivity.this,"开屏广告展示",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onAdSkip() {
                        Log.d(TAG, "onAdSkip");
                       // Toast.makeText(GgActivity.this,"开屏广告跳过",Toast.LENGTH_SHORT).show();

                        goToMainActivity();

                    }

                    @Override
                    public void onAdTimeOver() {
                        Log.d(TAG, "onAdTimeOver");
                       // Toast.makeText(GgActivity.this,"开屏广告倒计时结束",Toast.LENGTH_SHORT).show();
                        goToMainActivity();
                    }
                });
            }
        }, AD_TIME_OUT);
    }
    private void goToMainActivity() {
        Intent intent = new Intent(GgActivity.this, MainActivity.class);
        startActivity(intent);
        mSplashContainer.removeAllViews();
        this.finish();
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(GgActivity.this,MainActivity.class);
        startActivity(intent);
        timer.cancel();
    }
    @Override
    protected void onStop() {
        super.onStop();
        mForceGoMain = true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
