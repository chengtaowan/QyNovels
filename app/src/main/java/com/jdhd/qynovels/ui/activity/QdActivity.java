package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.QdAdapter;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.SignBean;
import com.jdhd.qynovels.module.personal.SignSetingBean;
import com.jdhd.qynovels.persenter.impl.personal.ISignPresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.ISignSetingPresenterImpl;
import com.jdhd.qynovels.utils.AndroidBug54971Workaround;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.jdhd.qynovels.view.personal.ISignSetingView;
import com.jdhd.qynovels.view.personal.ISignView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class QdActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout qd;
    private TextView ljqd,cg,sp,tian,yqd;
    private ImageView back;
    private RecyclerView rv;
    private QdAdapter adapter;
    private TextView day1,day2,day3,day4,day5,day6,day7;
    private TextView jb1,jb2,jb3,jb4,jb5,jb6,jb7;
    private ImageView qd1,qd2,qd3,qd4,qd5,qd6,qd7;
    private List<TextView> daylist=new ArrayList<>();
    private List<TextView> jblist=new ArrayList<>();
    private List<ImageView> qdlist=new ArrayList<>();
    private ISignSetingPresenterImpl signSetingPresenter;
    private ISignPresenterImpl signPresenter;
    private SignSetingBean signSeting=new SignSetingBean();
    private TTAdNative mTTAdNative;
    private int width,height;
    private TTRewardVideoAd mttRewardVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qd);
        AndroidBug54971Workaround.assistActivity(findViewById(android.R.id.content),this);


        MyApp.addActivity(this);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
//        signSetingPresenter=new ISignSetingPresenterImpl(this,this);
//        signSetingPresenter.loadData();
       // signPresenter=new ISignPresenterImpl(this,this);
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
        yqd=findViewById(R.id.qd_yqd);
        tian=findViewById(R.id.qd_tian);
        qd=findViewById(R.id.qd_qd);
        qd.setOnClickListener(this);
        ljqd=findViewById(R.id.qd_ljqd);
        cg=findViewById(R.id.qd_cg);
        sp=findViewById(R.id.qd_sp);
        qd.setOnClickListener(this);
        back=findViewById(R.id.qd_back);
        back.setOnClickListener(this);
        rv=findViewById(R.id.qd_rv);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        rv.setLayoutManager(manager);
        adapter=new QdAdapter();
        rv.setAdapter(adapter);
        day1=findViewById(R.id.day1);
        day2=findViewById(R.id.day2);
        day3=findViewById(R.id.day3);
        day4=findViewById(R.id.day4);
        day5=findViewById(R.id.day5);
        day6=findViewById(R.id.day6);
        day7=findViewById(R.id.day7);
        jb1=findViewById(R.id.jb1);
        jb2=findViewById(R.id.jb2);
        jb3=findViewById(R.id.jb3);
        jb4=findViewById(R.id.jb4);
        jb5=findViewById(R.id.jb5);
        jb6=findViewById(R.id.jb6);
        jb7=findViewById(R.id.jb7);
        qd1=findViewById(R.id.qd1);
        qd2=findViewById(R.id.qd2);
        qd3=findViewById(R.id.qd3);
        qd4=findViewById(R.id.qd4);
        qd5=findViewById(R.id.qd5);
        qd6=findViewById(R.id.qd6);
        qd7=findViewById(R.id.qd7);
        daylist.add(day1);
        daylist.add(day2);
        daylist.add(day3);
        daylist.add(day4);
        daylist.add(day5);
        daylist.add(day6);
        daylist.add(day7);
        jblist.add(jb1);
        jblist.add(jb2);
        jblist.add(jb3);
        jblist.add(jb4);
        jblist.add(jb5);
        jblist.add(jb6);
        jblist.add(jb7);
        qdlist.add(qd1);
        qdlist.add(qd2);
        qdlist.add(qd3);
        qdlist.add(qd4);
        qdlist.add(qd5);
        qdlist.add(qd6);
        qdlist.add(qd7);
    }

    @Override
    public void onClick(View view) {
        if(R.id.qd_back==view.getId()){
            finish();
        }
        else{
            signPresenter.loadData();
            qd.setBackgroundResource(R.drawable.shape_qd_on);
            ljqd.setVisibility(View.GONE);
            cg.setVisibility(View.VISIBLE);
            sp.setVisibility(View.VISIBLE);

            for(int i=0;i<signSeting.getData().getSignData().size();i++){
                if(signSeting.getData().getSignData().get(i).getDate().equals("今天")){
                    qdlist.get(i).setImageResource(R.mipmap.qd_jb_wc);
                    jblist.get(i).setVisibility(View.GONE);
                }
            }
            int is_sign = signSeting.getData().getIs_sign();
            Log.e("issign",is_sign+"---");
            switch (is_sign){
                case 1:
                    sp.setText("看视频再领"+signSeting.getData().getDouble_award()+"金币");
                    qd.setBackgroundResource(R.drawable.shape_qd_on);
                    ljqd.setVisibility(View.GONE);
                    cg.setVisibility(View.VISIBLE);
                    yqd.setVisibility(View.GONE);
                    sp.setVisibility(View.VISIBLE);
                    loadAd("926447225", TTAdConstant.VERTICAL);
                    //Toast.makeText(QdActivity.this,"看小视频",Toast.LENGTH_SHORT).show();
                    signPresenter.loadData();
                    break;
                case 2:
                    qd.setBackgroundResource(R.drawable.shap_qd);
                    ljqd.setVisibility(View.GONE);
                    cg.setVisibility(View.GONE);
                    yqd.setVisibility(View.VISIBLE);
                    sp.setVisibility(View.GONE);
                    Toast.makeText(QdActivity.this,"您已签到",Toast.LENGTH_SHORT).show();
                    break;
            }

        }

    }

//   @Override
//    public void onSetingSuccess(SignSetingBean signSetingBean) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                signSeting=signSetingBean;
//                Log.e("issign",signSetingBean.getData().getIs_sign()+"+++");
//                adapter.refresh(signSetingBean.getData().getRule());
//                sp.setText("看视频再领"+signSeting.getData().getDouble_award()+"金币");
//                tian.setText(signSetingBean.getData().getSignNum()+"");
//                for(int i=0;i<signSetingBean.getData().getSignData().size();i++){
//                    daylist.get(i).setText(signSetingBean.getData().getSignData().get(i).getDate());
//                    jblist.get(i).setText(signSetingBean.getData().getSignData().get(i).getAward()+"");
//                    if(signSetingBean.getData().getSignData().get(i).getDate().equals("今天")&&signSetingBean.getData().getSignData().get(i).getIs_sign()==0){
//                        qdlist.get(i).setImageResource(R.mipmap.qd_jb);
//                        qd.setBackgroundResource(R.drawable.shap_qd);
//                        ljqd.setVisibility(View.VISIBLE);
//                        cg.setVisibility(View.GONE);
//                        sp.setVisibility(View.GONE);
//                    }
//                    else if(signSetingBean.getData().getSignData().get(i).getDate().equals("今天")&&signSetingBean.getData().getSignData().get(i).getIs_sign()==1){
//                        qdlist.get(i).setImageResource(R.mipmap.qd_jb_wc);
//                        qd.setBackgroundResource(R.drawable.shape_qd_on);
//                        ljqd.setVisibility(View.GONE);
//                        cg.setVisibility(View.VISIBLE);
//                        sp.setVisibility(View.VISIBLE);
//                        jblist.get(i).setVisibility(View.GONE);
//                    }
//                    else if(signSetingBean.getData().getSignData().get(i).getIs_sign()==0){
//                        qdlist.get(i).setImageResource(R.mipmap.qd_jb_on);
//                    }
//                    else{
//                        qdlist.get(i).setImageResource(R.mipmap.qd_jb_wc);
//                        jblist.get(i).setVisibility(View.GONE);
//                    }
//                }
//              signPresenter.loadData();
//            }
//        });
//    }
//
//    @Override
//    public void onSetingError(String error) {
//        Log.e("setingerror",error);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(signSetingPresenter!=null){
            signSetingPresenter.destoryView();
        }
        if(signPresenter!=null){
           signPresenter.destoryView();
        }
    }

//    @Override
//    public void onSignSuccess(SignBean signBean) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//               tian.setText(signBean.getData().getSign_num()+"");
//
//            }
//        });
//    }
//
//    @Override
//    public void onSignError(String error) {
//       Log.e("signerror",error);
//    }
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
                Toast.makeText(QdActivity.this,message,Toast.LENGTH_SHORT).show();
            }

            //视频广告加载后，视频资源缓存到本地的回调，在此回调后，播放本地视频，流畅不阻塞。
            @Override
            public void onRewardVideoCached() {
                // Toast.makeText(ZpActivity.this,"rewardVideoAd video cached",Toast.LENGTH_SHORT).show();
            }

            //视频广告的素材加载完毕，比如视频url等，在此回调后，可以播放在线视频，网络不好可能出现加载缓冲，影响体验。
            @Override
            public void onRewardVideoAdLoad(TTRewardVideoAd ad) {
                //Toast.makeText(getContext(),"rewardVideoAd loaded",Toast.LENGTH_SHORT).show();
                mttRewardVideoAd = ad;
//                mttRewardVideoAd.setShowDownLoadBar(false);
                if (mttRewardVideoAd != null) {
                    //step6:在获取到广告后展示
                    mttRewardVideoAd.showRewardVideoAd(QdActivity.this);
                    mttRewardVideoAd = null;
                } else {
                    //Toast.makeText(ZpActivity.this,"请先加载广告",Toast.LENGTH_SHORT).show();
                }
                mttRewardVideoAd.setRewardAdInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() {

                    @Override
                    public void onAdShow() {
                        // Toast.makeText(ZpActivity.this,"rewardVideoAd show",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdVideoBarClick() {
                        //Toast.makeText(ZpActivity.this,"rewardVideoAd bar click",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdClose() {
                        //Toast.makeText(ZpActivity.this,"rewardVideoAd close",Toast.LENGTH_SHORT).show();
                    }

                    //视频播放完成回调
                    @Override
                    public void onVideoComplete() {
                        Toast.makeText(QdActivity.this,"观看完成，获取双倍金币",Toast.LENGTH_SHORT).show();
                        qd.setBackgroundResource(R.drawable.shap_qd);
                        ljqd.setVisibility(View.GONE);
                        cg.setVisibility(View.GONE);
                        yqd.setVisibility(View.VISIBLE);
                        sp.setVisibility(View.GONE);
                        signPresenter.loadData();
                        //Toast.makeText(ZpActivity.this,"rewardVideoAd complete",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onVideoError() {
                        //Toast.makeText(ZpActivity.this,"rewardVideoAd error",Toast.LENGTH_SHORT).show();
                    }

                    //视频播放完成后，奖励验证回调，rewardVerify：是否有效，rewardAmount：奖励梳理，rewardName：奖励名称
                    @Override
                    public void onRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName) {
                        Toast.makeText(QdActivity.this,"观看完成，获取双倍金币",Toast.LENGTH_SHORT).show();
                        qd.setBackgroundResource(R.drawable.shap_qd);
                        ljqd.setVisibility(View.GONE);
                        cg.setVisibility(View.GONE);
                        yqd.setVisibility(View.VISIBLE);
                        sp.setVisibility(View.GONE);
                        signPresenter.loadData();
                        //Toast.makeText(ZpActivity.this,"verify:" + rewardVerify + " amount:" + rewardAmount +
                        //" name:" + rewardName,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSkippedVideo() {
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
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this); // 不能遗漏
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this); // 不能遗漏
    }
}
