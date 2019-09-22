package com.jdhd.qynovels.ui.fragment;


import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
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
import com.jdhd.qynovels.module.personal.EventBean;
import com.jdhd.qynovels.module.personal.FunctionBean;

import com.jdhd.qynovels.module.personal.SignInVideoBean;
import com.jdhd.qynovels.module.personal.SignSetingBean;
import com.jdhd.qynovels.module.personal.TokenBean;
import com.jdhd.qynovels.module.personal.UserEventBean;
import com.jdhd.qynovels.module.personal.VideoflBean;
import com.jdhd.qynovels.persenter.impl.personal.IDrawPresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.IPrizePresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.IPrizeRecodePresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.IShareImgPresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.IShareListPresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.ISignPresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.ISignSetingPresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.ISingInVideoPresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.IUserEventPresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.IVideoflPresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.IWelfarePresenterImpl;

import com.jdhd.qynovels.ui.activity.FriendListActivity;
import com.jdhd.qynovels.ui.activity.LoginActivity;
import com.jdhd.qynovels.ui.activity.MainActivity;

import com.jdhd.qynovels.ui.activity.YqActivity;
import com.jdhd.qynovels.ui.activity.ZpActivity;
import com.jdhd.qynovels.utils.DbUtils;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.utils.EventDbUtils;
import com.jdhd.qynovels.view.personal.IDrawView;
import com.jdhd.qynovels.view.personal.IPrizeRecodeView;
import com.jdhd.qynovels.view.personal.IPrizesView;
import com.jdhd.qynovels.view.personal.IShareImgView;
import com.jdhd.qynovels.view.personal.IShareListView;
import com.jdhd.qynovels.view.personal.ISignSetingView;
import com.jdhd.qynovels.view.personal.ISignView;
import com.jdhd.qynovels.view.personal.ISingInVideoView;
import com.jdhd.qynovels.view.personal.IUserEventView;
import com.jdhd.qynovels.view.personal.IVideoflView;
import com.jdhd.qynovels.view.personal.IWelfareView;
import com.just.agentweb.AgentWeb;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.utils.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import rx.functions.Action1;

/**
 * A simple {@link Fragment} subclass.
 */
public class FuLiFragment extends BaseFragment implements  IVideoflView, ISignSetingView, ISignView , ISingInVideoView , IUserEventView,IWelfareView {

    private AgentWeb web;
    private LinearLayout webView;
    private String ScriptMessageName="GGScriptMessageCommon";
    private IWelfarePresenterImpl welfarePresenter;
    private ISignSetingPresenterImpl signSetingPresenter;
    private ISignPresenterImpl signPresenter;
    private ImageView back;
    private TextView title,mx;
    private IPrizePresenterImpl prizePresenter;
    private IDrawPresenterImpl drawPresenter;
    private IShareListPresenterImpl shareListPresenter;
    private IVideoflPresenterImpl videoflPresenter;
    private  IShareImgPresenterImpl shareImgPresenter;
    private SmartRefreshLayout sr;
    private boolean hasNetWork;
    private static final int THUMB_SIZE = 150;
    private Bitmap map;
    private TTRewardVideoAd mttRewardVideoAd;
    private TTAdNative mTTAdNative;
    private int width,height;
    protected boolean isCreated = false;
    private String token;
    private ISingInVideoPresenterImpl singInVideoPresenter;
    private static final String TAG = "FuliFragment";
    private View rootView;
    //标志位，标志已经初始化完成
    private boolean isPrepared;
    //是否已被加载过一次，第二次就不再去请求数据了
    private boolean mHasLoadedOnce;
    private int startTime,endTime;
    private DbUtils dbUtils;
    private SQLiteDatabase database;
    private String islogin;
    private IUserEventPresenterImpl iUserEventPresenter;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            FunctionBean functionBean= (FunctionBean) msg.obj;
            Log.e("what",msg.what+"---");
            switch (msg.what){
                case 1:
                    Intent intent=new Intent(getContext(), YqActivity.class);
                    intent.putExtra("title",functionBean.getTitle());
                    intent.putExtra("path",functionBean.getPath());
                    getContext().startActivity(intent);
                    break;
                case 2:
                    Intent intent1=new Intent(getContext(), ZpActivity.class);
                    intent1.putExtra("title",functionBean.getTitle());
                    intent1.putExtra("name",functionBean.getReqParameter().getGame_name());
                    intent1.putExtra("path",functionBean.getPath());
                    getContext().startActivity(intent1);
                    break;
                case 3:
                    signPresenter.loadData();
                    break;
                case 5:
                    String str=functionBean.getCode();
                    //获取剪贴板管理器：
                    ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
// 创建普通字符型ClipData
                    ClipData mClipData = ClipData.newPlainText("Label", str);
// 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip(mClipData);
                    Toast.makeText(getContext(),"复制到剪切板",Toast.LENGTH_SHORT).show();
                    break;
                case 6:
                    SharedPreferences sharedPreferences=getActivity().getSharedPreferences("token",Context.MODE_PRIVATE);
                    String token=sharedPreferences.getString("token","");
                    String islogin=sharedPreferences.getString("islogin","");
                    if(!token.equals("")&&islogin.equals("1")){
                        Log.e("function",functionBean.toString());
                        String type=functionBean.getType();
                        String scene=functionBean.getScene();
                        String img=functionBean.getShare_img();

                        if(type.equals("1")&&scene.equals("0")){
                            getBitmap(getContext(), img, new FuLiFragment.GlideLoadBitmapCallback() {
                                @Override
                                public void getBitmapCallback(Bitmap bitmap) {
                                    map=bitmap;
                                    sharePicture(map, SendMessageToWX.Req.WXSceneSession);
                                    Log.e("asd","mapmapmap");
                                }
                            });
                        }
                        else if(type.equals("1")&&scene.equals("1")){
                            getBitmap(getContext(), img, new FuLiFragment.GlideLoadBitmapCallback() {
                                @Override
                                public void getBitmapCallback(Bitmap bitmap) {
                                    map=bitmap;
                                    Log.e("asd","mapmapmap");
                                    sharePicture(map,SendMessageToWX.Req.WXSceneTimeline);
                                }
                            });

                        }
                        else if(type.equals("2")){
                            Toast.makeText(getContext(),"功能暂未开放",Toast.LENGTH_SHORT).show();
                        }
                        else if(type.equals("0")){
                            getBitmap(getContext(), img, new FuLiFragment.GlideLoadBitmapCallback() {
                                @Override
                                public void getBitmapCallback(Bitmap bitmap) {
                                    map=bitmap;
                                    saveImageToGallery(getContext(),map);
                                    Log.e("asd","mapmapmap");
                                }
                            });
                            if(map!=null){

                            }

                        }
                    }
                    else{
                        Intent intent2=new Intent(getContext(),LoginActivity.class);
                        getContext().startActivity(intent2);
                    }

                break;
                case 7:
                    int time=DeviceInfoUtils.getTime();
                    EventDbUtils eventDbUtils=new EventDbUtils(getContext());
                    List<EventBean.DataBean> updata = eventDbUtils.updata(MyApp.kQYDataAnalysisEventType.kQYDataAnalysisTargetEvent, time, 0, MyApp.kQYoperationType.kQYSoperationTypeOpen, MyApp.kQYTargetDataAnalysis.kQYTargetDataAnalysisWelfare_watchVideo);
                    if(updata.size()==20){
                        Gson gson=new Gson();
                        String s=gson.toJson(updata);
                        iUserEventPresenter.setJson(s);
                        iUserEventPresenter.loadData();
                    }
                    loadVideoAd("926447225",TTAdConstant.VERTICAL);
                    break;
                case 8:
                    loadAd("926447225",TTAdConstant.VERTICAL);
                    break;
            }
        }
    };





    @Override
    public void onVideoSuccess(VideoflBean videoflBean) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(videoflBean.getCode()==200){
                    Log.e("shuju",videoflBean.getData().getAward()+"");
                    Toast.makeText(getContext(),"观看完成，获取"+videoflBean.getData().getAward()+"金币",Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(getContext(),videoflBean.getMsg(),Toast.LENGTH_SHORT).show();

                }


            }
        });
    }

    @Override
    public void onVideoSuccess(SignInVideoBean signInVideoBean) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                web.getJsAccessEntrace().quickCallJs("watchVideoSuccess", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        com.tencent.mm.opensdk.utils.Log.e("data",s);
                    }
                },"{}");
            }
        });
    }

    @Override
    public void onVideoError(String error) {
       Log.e("videoerror",error);
    }

    @Override
    public void onSetingSuccess(String string) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("singsuccess","1");
                Log.e("issinging",string+"----");
                web.getJsAccessEntrace().quickCallJs("signSetting", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        Log.e("data",s);
                    }
                },string);
            }
        });
    }

    @Override
    public void onSetingError(String error) {
         Log.e("singsetingerror",error);
    }

    @Override
    public void onSignSuccess(String string) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("singstring",string);
                Toast.makeText(getContext(),"签到成功",Toast.LENGTH_SHORT).show();
                SharedPreferences preferences=getActivity().getSharedPreferences("token",Context.MODE_PRIVATE);
                token=preferences.getString("token","");
                islogin=preferences.getString("islogin","");
                signSetingPresenter.setToken(token);
                signSetingPresenter.setIslogin(islogin);
                signSetingPresenter.loadData();
            }
        });
    }

    @Override
    public void onSignError(String error) {

    }

    @Override
    public void onUserEventSuccess(UserEventBean userEventBean) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(userEventBean.getCode()==200){
                    database=dbUtils.getWritableDatabase();
                    database.execSQL("delete from userevent");
                }
            }
        });
    }

    @Override
    public void onUserEventError(String error) {

    }

    @Override
    public void onWelSuccess(String string) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sr.finishRefresh();
                Log.e("string",string);
                web.getJsAccessEntrace().quickCallJs("welfare", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        Log.e("data",s);
                    }
                },string);
            }
        });
    }

    @Override
    public void onWelError(String error) {

    }


    public interface GlideLoadBitmapCallback {
        public void getBitmapCallback(Bitmap bitmap);
    }

    public static Bitmap createBitmapThumbnail(Bitmap bitMap, boolean needRecycle) {
        int width = bitMap.getWidth();
        int height = bitMap.getHeight();
        // 设置想要的大小
        int newWidth = width;
        int newHeight = height;
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newBitMap = Bitmap.createBitmap(bitMap, 0, 0, width, height,
                matrix, true);
        if (needRecycle && (newBitMap != null)) {
            bitMap.recycle();
        }
        return newBitMap;
    }

    public FuLiFragment() {

    }

    @Override
    public void onStart() {
        super.onStart();
        startTime=DeviceInfoUtils.getTime();

        Log.e(TAG,"onstart");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG,"onstartresume");
        SharedPreferences preferences=getContext().getSharedPreferences("token", Context.MODE_PRIVATE);
        token = preferences.getString("token", "");
        lazyLoad();

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getToken(TokenBean tokenBean){
        token=tokenBean.getData().getToken();
        signSetingPresenter=new ISignSetingPresenterImpl(new ISignSetingView() {
            @Override
            public void onSetingSuccess(String string) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("setingresume",string);
                        web.getJsAccessEntrace().quickCallJs("signSetting", new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String s) {
                                Log.e("data",s);
                            }
                        },string);
                    }
                });
            }

            @Override
            public void onSetingError(String error) {

            }
        },getContext());
        SharedPreferences preferences=getActivity().getSharedPreferences("token",Context.MODE_PRIVATE);
        token=preferences.getString("token","");
        islogin=preferences.getString("islogin","");
        signSetingPresenter.setToken(token);
        signSetingPresenter.setIslogin(islogin);
        signSetingPresenter.loadData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(rootView==null){
            rootView= inflater.inflate(R.layout.fragment_fu_li, container, false);
            init(rootView);
            isPrepared = true;

        }
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        iUserEventPresenter=new IUserEventPresenterImpl(this,getContext());
        dbUtils=new DbUtils(getContext());
        singInVideoPresenter=new ISingInVideoPresenterImpl(this,getContext());
        videoflPresenter=new IVideoflPresenterImpl(this,getContext());
        signPresenter=new ISignPresenterImpl(this,getContext());
        hasNetWork = DeviceInfoUtils.hasNetWork(getContext());
        //step1:初始化sdk
        TTAdManager ttAdManager = TTAdSdk.getAdManager();
        //step2:(可选，强烈建议在合适的时机调用):申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
        TTAdSdk.getAdManager().requestPermissionIfNecessary(getContext());
        //step3:创建TTAdNative对象,用于调用广告请求接口
        mTTAdNative = ttAdManager.createAdNative(MyApp.getAppContext());
        WindowManager manager = getActivity().getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;  //以要素为单位
        height = metrics.heightPixels;

//        //同时请求多个权限
//        RxPermissions.getInstance(getContext())
//                .request(Manifest.permission.ACCESS_NETWORK_STATE,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE)//多个权限用","隔开
//                .subscribe(new Action1<Boolean>() {
//                    @Override
//                    public void call(Boolean aBoolean) {
//                        if (aBoolean) {
//                            //当所有权限都允许之后，返回true
//                            android.util.Log.i("permissions", "btn_more_sametime：" + aBoolean);
//                        } else {
//                            //只要有一个权限禁止，返回false，
//                            //下一次申请只申请没通过申请的权限
//                            android.util.Log.i("permissions", "btn_more_sametime：" + aBoolean);
//                        }
//                    }
//                });


        return rootView;
    }

    private void init(View view) {
        mx=view.findViewById(R.id.mx);
        sr=view.findViewById(R.id.sr);
        back=view.findViewById(R.id.fl_back);
        title=view.findViewById(R.id.fl_title);
        webView=view.findViewById(R.id.webview);
        web=AgentWeb.with(getActivity())

                .setAgentWebParent(webView, new LinearLayout.LayoutParams(-1, -1))

                .useDefaultIndicator()//进度条

                .createAgentWeb()
                .ready()
                .go(MyApp.Url.webbaseUrl+"novel/index1.html");
        web.getJsInterfaceHolder().addJavaObject("android", new AndroidInterface(web, getContext()));
        sr.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                hasNetWork = DeviceInfoUtils.hasNetWork(getContext());
                if(!hasNetWork){
                    Toast.makeText(getContext(),"网络连接不可用",Toast.LENGTH_SHORT).show();
                    sr.finishRefresh();
                }
                else{
                    welfarePresenter.loadData();
                    SharedPreferences sharedPreferences=getActivity().getSharedPreferences("token",Context.MODE_PRIVATE);
                    token=sharedPreferences.getString("token","");
                    islogin=sharedPreferences.getString("islogin","");
                    signSetingPresenter.setIslogin(islogin);
                    signSetingPresenter.setToken(token);
                    signSetingPresenter.loadData();
                }

            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        if(web!=null){
            web.clearWebCache();
        }
        endTime=DeviceInfoUtils.getTime();
        EventDbUtils eventDbUtils=new EventDbUtils(getContext());
        List<EventBean.DataBean> updata = eventDbUtils.updata(MyApp.kQYDataAnalysisEventType.kQYDataAnalysisPageEvent, startTime, endTime, MyApp.kQYoperationType.kQYSoperationTypeOpen, MyApp.kQYPageDataAnalysis.kQYPageDataAnalysisWelfare);
        if(updata.size()==20){
            Gson gson=new Gson();
            String s=gson.toJson(updata);
            iUserEventPresenter.setJson(s);
            iUserEventPresenter.loadData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(web!=null){
            web.destroy();
        }
        EventBus.getDefault().unregister(this);

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
            SharedPreferences sharedPreferences=getActivity().getSharedPreferences("token",Context.MODE_PRIVATE);
            String token=sharedPreferences.getString("token","");
            String islogin=sharedPreferences.getString("islogin","");
            FunctionBean functionBean = gson.fromJson(str, FunctionBean.class);

                switch (functionBean.getFunctionName()){
                    case "signin":
                        Message message3=handler.obtainMessage(3);
                        message3.obj=functionBean;
                        handler.sendMessage(message3);
                        break;
                    case "reading30s":
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                toFragment(1);
                                MainActivity.rb_shop.setChecked(true);
                            }
                        });
                        break;
                    case "dailyShare":
                        Message message6=handler.obtainMessage(6);
                        message6.obj=functionBean;
                        handler.sendMessage(message6);
                        break;
                    case "dailyReading":
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                toFragment(1);
                                MainActivity.rb_shop.setChecked(true);
                            }
                        });
                        break;
                    case "inviteFriend":
                        Log.e("path",functionBean.getPath());

                        if(!token.equals("")&&islogin.equals("1")){
                            Message message=handler.obtainMessage(1);
                            message.obj=functionBean;
                            handler.sendMessage(message);
                        }
                        else{
                            Intent intent=new Intent(getContext(),LoginActivity.class);
                            getActivity().startActivity(intent);
                        }

                        break;
                    case "bigWheel":
                        Log.e("path",functionBean.getPath());
                        if(!token.equals("")&&islogin.equals("1")){
                            Message message1=handler.obtainMessage(2);
                            message1.obj=functionBean;
                            handler.sendMessage(message1);
                        }
                        else{
                            Intent intent=new Intent(getContext(),LoginActivity.class);
                            getActivity().startActivity(intent);
                        }

                        break;
                    case "watchVideo":
                        Message message7=handler.obtainMessage(7);
                        message7.obj=functionBean;
                        handler.sendMessage(message7);
                        break;
                    case "drawDetails":
                        Message message4=handler.obtainMessage(4);
                        message4.obj=functionBean;
                        handler.sendMessage(message4);
                        break;
                    case "clipboard":
                        Message message5=handler.obtainMessage(5);
                        message5.obj=functionBean;
                        handler.sendMessage(message5);
                        break;
                    case "signIn_watchVideo":
                        Message message8=handler.obtainMessage(8);
                        message8.obj=functionBean;
                        handler.sendMessage(message8);
                        break;

                }
        }
    }

    private void toFragment(final int i) {
        final MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.setFragment2Fragment(new MainActivity.Fragment2Fragment() {
            @Override
            public void gotoFragment(ViewPager viewPager) {
                viewPager.setCurrentItem(i);
                Log.e("asd","跳转");
            }
        });
        mainActivity.forSkip();
    }



    private boolean mHasShowDownloadActive = false;

    private void loadVideoAd(String codeId, int orientation) {
        //step4:创建广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeId)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(width, height)
                .setRewardName("金币") //奖励的名称
                .setRewardAmount(1)  //奖励的数量
                .setUserID("user1234")//用户id,必传参数
                .setMediaExtra("media_extra") //附加参数，可选
                .setOrientation(orientation) //必填参数，期望视频的播放方向：TTAdConstant.HORIZONTAL 或 TTAdConstant.VERTICAL
                .build();
        //step5:请求广告
        mTTAdNative.loadRewardVideoAd(adSlot, new TTAdNative.RewardVideoAdListener() {
            @Override
            public void onError(int code, String message) {
                Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
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
                            videoflPresenter.loadData();
                            welfarePresenter.loadData();
                        }
                        //Toast.makeText(ZpActivity.this,"rewardVideoAd close",Toast.LENGTH_SHORT).show();

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
                            android.util.Log.e("watch","观看完成7");
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
                    mttRewardVideoAd.showRewardVideoAd(getActivity());
                    //mttRewardVideoAd = null;
                } else {
                    Toast.makeText(getContext(),"广告加载失败，请检查网络",Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
    }



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
                Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
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
                            Log.e("watch","签到观看完成3");
                            singInVideoPresenter.loadData();
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
                            android.util.Log.e("watch","观看完成7");
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
                    mttRewardVideoAd.showRewardVideoAd(getActivity());
                    //mttRewardVideoAd = null;
                } else {
                    Toast.makeText(getContext(),"广告加载失败，请检查网络",Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
    }

    public void getBitmap(Context context, String uri, final FuLiFragment.GlideLoadBitmapCallback callback) {
        Glide.with(context)
                .load(uri)
                //.override(80, 80)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        Bitmap bitmap = Bitmap.createBitmap(
                                resource.getIntrinsicWidth(),
                                resource.getIntrinsicHeight(),
                                resource.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                        : Bitmap.Config.RGB_565);
                        Canvas canvas = new Canvas(bitmap);
                        //canvas.setBitmap(bitmap);
                        resource.setBounds(0, 0, resource.getIntrinsicWidth(), resource.getIntrinsicHeight());
                        resource.draw(canvas);
                        callback.getBitmapCallback(createBitmapThumbnail(bitmap, false));
                    }
                });

    }

    private void sharePicture(Bitmap bitmap, int shareType) {
        WXImageObject imgObj = new WXImageObject(bitmap);

        WXMediaMessage msgs = new WXMediaMessage();
        msgs.mediaObject = imgObj;

//构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msgs;
        req.scene = shareType;
        //req.userOpenId = getOpenId();
//调用api接口，发送数据到微信
        MyApp.getApi().sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public static boolean saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "dearxy";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
            fos.flush();
            fos.close();

            //把文件插入到系统图库
            //MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            Toast.makeText(context,"保存到本地",Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce){
            return;
        }
        welfarePresenter=new IWelfarePresenterImpl(new IWelfareView() {
            @Override
            public void onWelSuccess(String string) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sr.finishRefresh();
                        Log.e("string",string);
                        web.getJsAccessEntrace().quickCallJs("welfare", new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String s) {
                                Log.e("data",s);
                            }
                        },string);
                    }
                });
            }

            @Override
            public void onWelError(String error) {
                android.util.Log.e("welerror",error);
            }
        }, getContext());
        welfarePresenter.loadData();
        SharedPreferences preferences=getActivity().getSharedPreferences("token",Context.MODE_PRIVATE);
        token=preferences.getString("token","");
        islogin=preferences.getString("islogin","");
        signSetingPresenter=new ISignSetingPresenterImpl(this,getContext());
        signSetingPresenter.setToken(token);
        signSetingPresenter.setIslogin(islogin);
        signSetingPresenter.loadData();
        Log.e(TAG,TAG+"加载数据");
       // mHasLoadedOnce = true;
    }
}
