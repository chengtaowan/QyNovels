package com.jdhd.qynovels.ui.fragment;


import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
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
import com.jdhd.qynovels.module.personal.FunctionBean;

import com.jdhd.qynovels.module.personal.VideoflBean;
import com.jdhd.qynovels.persenter.impl.personal.IDrawPresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.IPrizePresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.IPrizeRecodePresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.IShareImgPresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.IShareListPresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.IVideoflPresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.IWelfarePresenterImpl;

import com.jdhd.qynovels.ui.activity.FriendListActivity;
import com.jdhd.qynovels.ui.activity.LoginActivity;
import com.jdhd.qynovels.ui.activity.MainActivity;
import com.jdhd.qynovels.ui.activity.QdActivity;

import com.jdhd.qynovels.ui.activity.YqActivity;
import com.jdhd.qynovels.ui.activity.ZpActivity;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.personal.IDrawView;
import com.jdhd.qynovels.view.personal.IPrizeRecodeView;
import com.jdhd.qynovels.view.personal.IPrizesView;
import com.jdhd.qynovels.view.personal.IShareImgView;
import com.jdhd.qynovels.view.personal.IShareListView;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import rx.functions.Action1;

/**
 * A simple {@link Fragment} subclass.
 */
public class FuLiFragment extends Fragment implements  IPrizesView , IVideoflView {

    private AgentWeb web;
    private LinearLayout webView;
    private String ScriptMessageName="GGScriptMessageCommon";
    private IWelfarePresenterImpl welfarePresenter;
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
                break;
                case 7:
                    loadAd("901121365",TTAdConstant.VERTICAL);
                    videoflPresenter.loadData();
                    break;
            }
        }
    };





    @Override
    public void onVideoSuccess(VideoflBean videoflBean) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("shuju",videoflBean.getData().getAward()+"");
                Toast.makeText(getContext(),"观看完成，获取"+videoflBean.getData().getAward()+"jinbi",Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onVideoError(String error) {
       Log.e("videoerror",error);
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
        // 标记
        isCreated = true;
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
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // 标记
        isCreated = true;
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
    }

    @Override
    public void onResume() {
        super.onResume();
        // 标记
        isCreated = true;
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fu_li, container, false);
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
        // 标记
        isCreated = true;
        prizePresenter=new IPrizePresenterImpl(this,getContext());
        videoflPresenter=new IVideoflPresenterImpl(this,getContext());
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

        //同时请求多个权限
        RxPermissions.getInstance(getContext())
                .request(Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)//多个权限用","隔开
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            //当所有权限都允许之后，返回true
                            android.util.Log.i("permissions", "btn_more_sametime：" + aBoolean);
                        } else {
                            //只要有一个权限禁止，返回false，
                            //下一次申请只申请没通过申请的权限
                            android.util.Log.i("permissions", "btn_more_sametime：" + aBoolean);
                        }
                    }
                });

        init(view);
        return view;
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

                .go(MyApp.Url.webbaseUrl+"novel/index.html");


        //webView.loadUrl();
        //声明WebSettings子类
        //WebSettings webSettings = webView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        //webSettings.setJavaScriptEnabled(true);
        //webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //webView.addJavascriptInterface(AndroidToJs(this), "test");
        //设置自适应屏幕，两者合用
//        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
//        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
//
////缩放操作
//        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
//        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
//        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
//
////其他细节操作
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
//        webSettings.setAllowFileAccess(true); //设置可以访问文件
//
//        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
//        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
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
                }

            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        //web.clearWebCache();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(web!=null){
            web.destroy();
        }

//        if(prizePresenter!=null){
//            prizePresenter.destoryView();
//        }
//        if(drawPresenter!=null){
//           drawPresenter.destoryView();
//        }
    }
    @Override
    public void onPrizeSuccess(String string) {
       getActivity().runOnUiThread(new Runnable() {
           @Override
           public void run() {
               web.getJsAccessEntrace().quickCallJs("getPrize", new ValueCallback<String>() {
                   @Override
                   public void onReceiveValue(String s) {
                       Log.e("data",s);
                   }
               },string);
           }
       });
    }

    @Override
    public void onPrizeError(String error) {
      Log.e("prizeerror",error);
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
            FunctionBean functionBean = gson.fromJson(str, FunctionBean.class);

                switch (functionBean.getFunctionName()){
                    case "signin":
                        Intent intent=new Intent(context, QdActivity.class);
                        startActivity(intent);
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
                        Message message=handler.obtainMessage(1);
                        message.obj=functionBean;
                        handler.sendMessage(message);
                        break;
                    case "bigWheel":
                        Log.e("path",functionBean.getPath());
                        Message message1=handler.obtainMessage(2);
                        message1.obj=functionBean;
                        handler.sendMessage(message1);
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


    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        int i;
        int j;
        if (bmp.getHeight() > bmp.getWidth()) {
            i = bmp.getWidth();
            j = bmp.getWidth();
        } else {
            i = bmp.getHeight();
            j = bmp.getHeight();
        }

        Bitmap localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.RGB_565);
        Canvas localCanvas = new Canvas(localBitmap);

        while (true) {
            localCanvas.drawBitmap(bmp, new Rect(0, 0, i, j), new Rect(0, 0,i, j), null);
            if (needRecycle)
                bmp.recycle();
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            localBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                    localByteArrayOutputStream);
            localBitmap.recycle();
            byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
            try {
                localByteArrayOutputStream.close();
                return arrayOfByte;
            } catch (Exception e) {
                //F.out(e);
            }
            i = bmp.getHeight();
            j = bmp.getHeight();
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
                Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
            }

            //视频广告加载后，视频资源缓存到本地的回调，在此回调后，播放本地视频，流畅不阻塞。
            @Override
            public void onRewardVideoCached() {
                Toast.makeText(getContext(),"rewardVideoAd video cached",Toast.LENGTH_SHORT).show();
            }

            //视频广告的素材加载完毕，比如视频url等，在此回调后，可以播放在线视频，网络不好可能出现加载缓冲，影响体验。
            @Override
            public void onRewardVideoAdLoad(TTRewardVideoAd ad) {
                Toast.makeText(getContext(),"rewardVideoAd loaded",Toast.LENGTH_SHORT).show();
                mttRewardVideoAd = ad;
//                mttRewardVideoAd.setShowDownLoadBar(false);
                if (mttRewardVideoAd != null) {
                    //step6:在获取到广告后展示
                    mttRewardVideoAd.showRewardVideoAd(getActivity());
                    mttRewardVideoAd = null;
                } else {
                    Toast.makeText(getContext(),"请先加载广告",Toast.LENGTH_SHORT).show();
                }
                mttRewardVideoAd.setRewardAdInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() {

                    @Override
                    public void onAdShow() {
                        Toast.makeText(getContext(),"rewardVideoAd show",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdVideoBarClick() {
                        Toast.makeText(getContext(),"rewardVideoAd bar click",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdClose() {
                        Toast.makeText(getContext(),"rewardVideoAd close",Toast.LENGTH_SHORT).show();
                    }

                    //视频播放完成回调
                    @Override
                    public void onVideoComplete() {
                        Toast.makeText(getContext(),"rewardVideoAd complete",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onVideoError() {
                        Toast.makeText(getContext(),"rewardVideoAd error",Toast.LENGTH_SHORT).show();
                    }

                    //视频播放完成后，奖励验证回调，rewardVerify：是否有效，rewardAmount：奖励梳理，rewardName：奖励名称
                    @Override
                    public void onRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName) {

                        Toast.makeText(getContext(),"verify:" + rewardVerify + " amount:" + rewardAmount +
                                " name:" + rewardName,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSkippedVideo() {
                        Toast.makeText(getContext(),"rewardVideoAd has onSkippedVideo",Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getContext(),"下载中，点击下载区域暂停",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                        Toast.makeText(getContext(),"下载暂停，点击下载区域继续",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                        Toast.makeText(getContext(),"下载失败，点击下载区域重新下载",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                        Toast.makeText(getContext(),"下载完成，点击下载区域重新下载",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onInstalled(String fileName, String appName) {
                        Toast.makeText(getContext(),"安装完成，点击下载区域打开",Toast.LENGTH_SHORT).show();
                    }
                });
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
    /**
     * 此方法目前仅适用于标示ViewPager中的Fragment是否真实可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (!isCreated) {
            return;
        }

        if (isVisibleToUser) {
            if(welfarePresenter!=null){
                welfarePresenter.loadData();
            }

        }
    }
}
