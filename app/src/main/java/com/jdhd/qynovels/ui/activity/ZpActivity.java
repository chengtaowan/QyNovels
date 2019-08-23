package com.jdhd.qynovels.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;
import com.google.gson.Gson;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.FunctionBean;
import com.jdhd.qynovels.module.personal.VideoflBean;
import com.jdhd.qynovels.persenter.impl.personal.IDrawPresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.IPrizePresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.IVideoflPresenterImpl;
import com.jdhd.qynovels.utils.AndroidBug54971Workaround;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.jdhd.qynovels.view.personal.IDrawView;
import com.jdhd.qynovels.view.personal.IPrizesView;
import com.jdhd.qynovels.view.personal.IVideoflView;
import com.just.agentweb.AgentWeb;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;

public class ZpActivity extends AppCompatActivity implements IPrizesView, View.OnClickListener, IDrawView{
    private IPrizePresenterImpl prizePresenter;
    private String title,name,path;
    private ImageView back;
    private TextView tex;
    private AgentWeb web;
    private LinearLayout webView;
    private IDrawPresenterImpl drawPresenter;
    private TTAdNative mTTAdNative;
    private int width,height;
    private TTRewardVideoAd mttRewardVideoAd;
    private SmartRefreshLayout sr;
    private boolean hasNetWork;
    private FunctionBean functionBean;
    private IVideoflPresenterImpl videoflPresenter;
    private String gamename,datapath,num;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            FunctionBean functionBean= (FunctionBean) msg.obj;
            switch (msg.what){
                case 1:
                    Intent intent=new Intent(ZpActivity.this,PrizeListActivity.class);
                    intent.putExtra("title",functionBean.getTitle());
                    intent.putExtra("path",functionBean.getPath());
                    intent.putExtra("datapath",functionBean.getDataPath());
                    intent.putExtra("page",functionBean.getReqParameter().getPage());
                    intent.putExtra("limit",functionBean.getReqParameter().getLimit());
                    startActivity(intent);
                    break;
                case 2:
                    loadAd("926447225", TTAdConstant.VERTICAL);
                    break;
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zp);
        AndroidBug54971Workaround.assistActivity(findViewById(android.R.id.content),this);


        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        hasNetWork = DeviceInfoUtils.hasNetWork(this);
        Intent intent=getIntent();
        title=intent.getStringExtra("title");
        path=intent.getStringExtra("path");
        name=intent.getStringExtra("name");
        prizePresenter=new IPrizePresenterImpl(this,this);
        prizePresenter.setGame_name(name);
        prizePresenter.loadData();
        drawPresenter=new IDrawPresenterImpl(this,this);
        TTAdManager ttAdManager = TTAdSdk.getAdManager();
        //step2:(可选，强烈建议在合适的时机调用):申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
        TTAdSdk.getAdManager().requestPermissionIfNecessary(this);
        //step3:创建TTAdNative对象,用于调用广告请求接口
        mTTAdNative = ttAdManager.createAdNative(MyApp.getAppContext());
        WindowManager manager = getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;  //以要素为单位
        height = metrics.heightPixels;
        init();

    }

    private void init() {
        sr=findViewById(R.id.sr);
        back=findViewById(R.id.ls_back);
        back.setOnClickListener(this);
        tex=findViewById(R.id.title);
        webView=findViewById(R.id.ll);
        tex.setText(title);

        web=AgentWeb.with(this)

                .setAgentWebParent(webView, new LinearLayout.LayoutParams(-1, -1))

                .useDefaultIndicator()//进度条

                .createAgentWeb()
                .ready()

                .go(MyApp.Url.webbaseUrl+path);
        web.getJsInterfaceHolder().addJavaObject("android", new AndroidInterface(web, this));
        sr.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if(!hasNetWork){
                    Toast.makeText(ZpActivity.this,"网络连接不可用",Toast.LENGTH_SHORT).show();
                    sr.finishRefresh();
                }
                else{
                    web=AgentWeb.with(ZpActivity.this)

                            .setAgentWebParent(webView, new LinearLayout.LayoutParams(-1, -1))

                            .useDefaultIndicator()//进度条

                            .createAgentWeb()
                            .ready()

                            .go(MyApp.Url.webbaseUrl+path);
                    prizePresenter.setGame_name(name);
                    prizePresenter.loadData();
                    sr.finishRefresh();
                }
            }
        });
    }

    @Override
    public void onPrizeSuccess(String string) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            web.getJsAccessEntrace().quickCallJs("getPrize", new ValueCallback<String>() {
                                @Override
                                public void onReceiveValue(String s) {
                                    com.tencent.mm.opensdk.utils.Log.e("data",s);
                                }
                            },string);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });
    }



    @Override
    public void onPrizeError(String error) {
        Log.e("prizeerror",error);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(prizePresenter!=null){
            prizePresenter.destoryView();
        }
    }

    @Override
    public void onClick(View view) {
//        Intent intent=new Intent(ZpActivity.this,MainActivity.class);
//        intent.putExtra("page",2);
//        startActivity(intent);
        finish();
    }

    @Override
    public void onDrawSuccess(String string) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                web.getJsAccessEntrace().quickCallJs("draw", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        com.tencent.mm.opensdk.utils.Log.e("data",s);
                    }
                },string);
            }
        });
    }

    @Override
    public void onDrawError(String error) {
       Log.e("drawerror",error);
    }

    public class AndroidInterface {
        private AgentWeb agent;
        private Context context;

        public AndroidInterface(AgentWeb agent, Context context) {
            this.agent = agent;
            this.context = context;
        }

        @JavascriptInterface
        public void GGScriptMessageCommon(String str) {
            Log.e("name",str);
            Gson gson=new Gson();
            functionBean = gson.fromJson(str, FunctionBean.class);
            if(functionBean.getReqName()!=null){
                if(functionBean.getReqName().equals("draw")){
                    drawPresenter.setGame_num(functionBean.getReqParameter().getGame_num());
                    drawPresenter.setDatapath(functionBean.getDataPath());
                    drawPresenter.setGame_name(functionBean.getReqParameter().getGame_name());
                    drawPresenter.loadData();

                }
            }
            switch (functionBean.getFunctionName()){
                case "drawDetails":
                    Message message=handler.obtainMessage(1);
                    message.obj=functionBean;
                    handler.sendMessage(message);
                    break;
                case "bigWheelWatchVideo":
                    Message message2=handler.obtainMessage(2);
                    message2.obj=functionBean;
                    handler.sendMessage(message2);
                    break;
            }

        }
    }
    private boolean mHasShowDownloadActive = false;
    private void loadAd(String codeId, int orientation) {

        //step4:创建广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeId)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(width, height)
                .setRewardName("金币") //奖励的名称
                .setRewardAmount(1)  //奖励的数量
                .setUserID("user123")//用户id,必传参数
                .setMediaExtra("media_extra") //附加参数，可选
                .setOrientation(orientation) //必填参数，期望视频的播放方向：TTAdConstant.HORIZONTAL 或 TTAdConstant.VERTICAL
                .build();
        //step5:请求广告
        mTTAdNative.loadRewardVideoAd(adSlot, new TTAdNative.RewardVideoAdListener() {
            @Override
            public void onError(int code, String message) {
                //Toast.makeText(ZpActivity.this,message,Toast.LENGTH_SHORT).show();
            }

            //视频广告加载后，视频资源缓存到本地的回调，在此回调后，播放本地视频，流畅不阻塞。
            @Override
            public void onRewardVideoCached() {


               //Toast.makeText(ZpActivity.this,"rewardVideoAd video cached",Toast.LENGTH_SHORT).show();
            }

            //视频广告的素材加载完毕，比如视频url等，在此回调后，可以播放在线视频，网络不好可能出现加载缓冲，影响体验。
            @Override
            public void onRewardVideoAdLoad(TTRewardVideoAd ad) {

                //Toast.makeText(ZpActivity.this,"rewardVideoAd loaded",Toast.LENGTH_SHORT).show();
                mttRewardVideoAd = ad;
//                mttRewardVideoAd.setShowDownLoadBar(false);
                if (mttRewardVideoAd != null) {
                    //step6:在获取到广告后展示

                    mttRewardVideoAd.setRewardAdInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() {

                        @Override
                        public void onAdShow() {

                            //Toast.makeText(ZpActivity.this,"rewardVideoAd show",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onAdVideoBarClick() {

                            //Toast.makeText(ZpActivity.this,"rewardVideoAd bar click",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onAdClose() {
                            Log.e("watch","观看完成3");
                            prizePresenter.setGame_name(name);
                            prizePresenter.loadData();
                            web.getJsAccessEntrace().quickCallJs("watchVideoSuccess", new ValueCallback<String>() {
                                @Override
                                public void onReceiveValue(String s) {
                                    com.tencent.mm.opensdk.utils.Log.e("watchvideo",s);
                                }
                            },"{}");

                            //Toast.makeText(ZpActivity.this,"rewardVideoAd close",Toast.LENGTH_SHORT).show();
                        }

                        //视频播放完成回调
                        @Override
                        public void onVideoComplete() {

                        }

                        @Override
                        public void onVideoError() {

                            //Toast.makeText(ZpActivity.this,"rewardVideoAd error",Toast.LENGTH_SHORT).show();
                        }

                        //视频播放完成后，奖励验证回调，rewardVerify：是否有效，rewardAmount：奖励梳理，rewardName：奖励名称
                        @Override
                        public void onRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName) {

//                        Toast.makeText(ZpActivity.this,"verify:" + rewardVerify + " amount:" + rewardAmount +
//                                " name:" + rewardName,Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onSkippedVideo() {
                            Log.e("watch","观看完成7");
                            //Toast.makeText(ZpActivity.this,"rewardVideoAd has onSkippedVideo",Toast.LENGTH_SHORT).show();
                        }
                    });

                    mttRewardVideoAd.setDownloadListener(new TTAppDownloadListener() {
                        @Override
                        public void onIdle() {
                            mHasShowDownloadActive = false;
                        }

                        @Override
                        public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                            if (!mHasShowDownloadActive) {
                                mHasShowDownloadActive = true;
                                // Toast.makeText(getContext(),"下载中，点击下载区域暂停",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                            // Toast.makeText(getContext(),"下载暂停，点击下载区域继续",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                            //Toast.makeText(getContext(),"下载失败，点击下载区域重新下载",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                            //Toast.makeText(getContext(),"下载完成，点击下载区域重新下载",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onInstalled(String fileName, String appName) {
                            // Toast.makeText(getContext(),"安装完成，点击下载区域打开",Toast.LENGTH_SHORT).show();
                        }
                    });
                    mttRewardVideoAd.showRewardVideoAd(ZpActivity.this);
                    //mttRewardVideoAd = null;
                } else {
                    Toast.makeText(ZpActivity.this,"广告加载失败，请检查网络",Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        web.clearWebCache();
        MobclickAgent.onPause(this); // 不能遗漏
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this); // 不能遗漏
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
