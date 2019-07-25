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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.FunctionBean;

import com.jdhd.qynovels.persenter.impl.personal.IDrawPresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.IPrizePresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.IPrizeRecodePresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.IShareImgPresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.IShareListPresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.IWelfarePresenterImpl;

import com.jdhd.qynovels.ui.activity.FriendListActivity;
import com.jdhd.qynovels.ui.activity.LoginActivity;
import com.jdhd.qynovels.ui.activity.MainActivity;
import com.jdhd.qynovels.ui.activity.QdActivity;

import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.personal.IDrawView;
import com.jdhd.qynovels.view.personal.IPrizeRecodeView;
import com.jdhd.qynovels.view.personal.IPrizesView;
import com.jdhd.qynovels.view.personal.IShareImgView;
import com.jdhd.qynovels.view.personal.IShareListView;
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
public class FuLiFragment extends Fragment implements IWelfareView , IShareImgView, IPrizesView , IDrawView, IShareListView, IPrizeRecodeView {

    private AgentWeb web;
    private LinearLayout webView;
    private String ScriptMessageName="GGScriptMessageCommon";
    private IWelfarePresenterImpl welfarePresenter;
    private ImageView back;
    private TextView title,mx;
    private IShareImgPresenterImpl shareImgPresenter;
    private IPrizePresenterImpl prizePresenter;
    private IDrawPresenterImpl drawPresenter;
    private IShareListPresenterImpl shareListPresenter;
    private SmartRefreshLayout sr;
    private boolean hasNetWork;
    private IPrizeRecodePresenterImpl prizeRecodePresenter;
    private static final int THUMB_SIZE = 150;
    private Bitmap map;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            FunctionBean functionBean= (FunctionBean) msg.obj;
            Log.e("what",msg.what+"---");
            switch (msg.what){
                case 1:
                    shareImgPresenter.loadData();
                    web.getWebCreator().getWebView().loadUrl(MyApp.Url.webbaseUrl+functionBean.getPath());
                    title.setText(functionBean.getTitle());
                    back.setVisibility(View.VISIBLE);
                    mx.setVisibility(View.VISIBLE);
                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if ( web.getWebCreator().getWebView().canGoBack()) { // 表示按返回键时的操作
                                web.getWebCreator().getWebView().goBack();
                                back.setVisibility(View.GONE);// 后退
                                MainActivity.rg.setVisibility(View.VISIBLE);
                                mx.setVisibility(View.GONE);
                                welfarePresenter.loadData();
                                // webview.goForward();//前进
                            }
                        }
                    });
                    mx.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent=new Intent(getContext(),FriendListActivity.class);
                            startActivity(intent);
                        }
                    });
                    MainActivity.rg.setVisibility(View.GONE);
                    break;
                case 2:
                    prizePresenter.setGame_name(functionBean.getReqParameter().getGame_name());
                    prizePresenter.loadData();
                    web.getWebCreator().getWebView().loadUrl(MyApp.Url.webbaseUrl+functionBean.getPath());
                    title.setText(functionBean.getTitle());
                    back.setVisibility(View.VISIBLE);
                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if ( web.getWebCreator().getWebView().canGoBack()) { // 表示按返回键时的操作
                                web.getWebCreator().getWebView().goBack(); // 后退
                                back.setVisibility(View.GONE);
                                MainActivity.rg.setVisibility(View.VISIBLE);
                                welfarePresenter.loadData();
                                title.setText("福利中心");
                                // webview.goForward();//前进
                            }
                        }
                    });
                    MainActivity.rg.setVisibility(View.GONE);
                    break;
                case 3:
                    shareListPresenter.setPage(functionBean.getReqParameter().getPage());
                    shareListPresenter.setLimit(functionBean.getReqParameter().getLimit());
                    shareListPresenter.loadData();
                    web.getWebCreator().getWebView().loadUrl(MyApp.Url.webbaseUrl+functionBean.getPath());
                    title.setText(functionBean.getTitle());
                    back.setVisibility(View.VISIBLE);
                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if ( web.getWebCreator().getWebView().canGoBack()) { // 表示按返回键时的操作
                                web.getWebCreator().getWebView().goBack();
                                web.getWebCreator().getWebView().goBack(); // 后退
                                back.setVisibility(View.GONE);
                                MainActivity.rg.setVisibility(View.VISIBLE);
                                title.setText("福利中心");
                                welfarePresenter.loadData();
                                // webview.goForward();//前进
                            }
                        }
                    });
                    MainActivity.rg.setVisibility(View.GONE);
                    break;
                case 4:
                    Log.e("page",functionBean.getReqParameter().getPage()+"---"+functionBean.getReqParameter().getLimit());
                    prizeRecodePresenter.setPage(functionBean.getReqParameter().getPage());
                    prizeRecodePresenter.setLimit(functionBean.getReqParameter().getLimit());
                    prizeRecodePresenter.loadData();
                    web.getWebCreator().getWebView().loadUrl(MyApp.Url.webbaseUrl+functionBean.getPath());
                    title.setText(functionBean.getTitle());
                    back.setVisibility(View.VISIBLE);
                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if ( web.getWebCreator().getWebView().canGoBack()) { // 表示按返回键时的操作
                                web.getWebCreator().getWebView().goBack();
                                web.getWebCreator().getWebView().goBack(); // 后退
                                back.setVisibility(View.GONE);
                                MainActivity.rg.setVisibility(View.VISIBLE);
                                title.setText("福利中心");
                                welfarePresenter.loadData();
                                // webview.goForward();//前进
                            }
                        }
                    });
                    MainActivity.rg.setVisibility(View.GONE);
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
                    String type=functionBean.getType();
                    String scene=functionBean.getScene();
                    String img=functionBean.getShare_img();
                    if(type.equals("1")&&scene.equals("0")){

                        getBitmap(getContext(), img, new GlideLoadBitmapCallback() {
                            @Override
                            public void getBitmapCallback(Bitmap bitmap) {
                                map=bitmap;
                                Log.e("asd","mapmapmap");
                            }
                        });
                        sharePicture(map,SendMessageToWX.Req.WXSceneSession);
                    }
                    else if(type.equals("1")&&scene.equals("1")){
                        getBitmap(getContext(), img, new GlideLoadBitmapCallback() {
                            @Override
                            public void getBitmapCallback(Bitmap bitmap) {
                                map=bitmap;
                                Log.e("asd","mapmapmap");
                            }
                        });
                        sharePicture(map,SendMessageToWX.Req.WXSceneTimeline);
                    }
                    else if(type.equals("0")){
                        getBitmap(getContext(), img, new GlideLoadBitmapCallback() {
                            @Override
                            public void getBitmapCallback(Bitmap bitmap) {
                                map=bitmap;
                                Log.e("asd","mapmapmap");
                            }
                        });
                        if(map!=null){
                            saveImageToGallery(getContext(),map);
                        }

                    }
                    break;
            }
        }
    };

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
    public void getBitmap(Context context, String uri, final GlideLoadBitmapCallback callback) {
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

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fu_li, container, false);
        welfarePresenter=new IWelfarePresenterImpl(this,getContext());
        welfarePresenter.loadData();
        shareImgPresenter=new IShareImgPresenterImpl(this,getContext());
        prizePresenter=new IPrizePresenterImpl(this,getContext());
        drawPresenter=new IDrawPresenterImpl(this,getContext());
        shareListPresenter=new IShareListPresenterImpl(this,getContext());
        prizeRecodePresenter=new IPrizeRecodePresenterImpl(this,getContext());
        hasNetWork = DeviceInfoUtils.hasNetWork(getContext());
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(welfarePresenter!=null){
            welfarePresenter.destoryView();
        }
        if(shareImgPresenter!=null){
            shareImgPresenter.destoryView();
        }
        if(prizePresenter!=null){
            prizePresenter.destoryView();
        }
        if(drawPresenter!=null){
           drawPresenter.destoryView();
        }
    }

    @Override
    public void onShareSuccess(String string) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("share",string);
                web.getJsAccessEntrace().quickCallJs("shareImg", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        Log.e("data",s);
                    }
                },string);
            }
        });
    }

    @Override
    public void onShareError(String error) {
       Log.e("shareerror",error);
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

    @Override
    public void onDrawSuccess(String string) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("draw",string);
                web.getJsAccessEntrace().quickCallJs("draw", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        Log.e("data",s);
                    }
                },string);
            }
        });
    }

    @Override
    public void onDrawError(String error) {
       Log.e("drawerror",error);
    }

    @Override
    public void onShareListSuccess(String str) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                web.getJsAccessEntrace().quickCallJs("shareList", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        Log.e("data",s);
                    }
                },str);
            }
        });
    }

    @Override
    public void onShareListError(String error) {
       Log.e("sharelisterror",error);
    }

    @Override
    public void onRecodeSuccess(String str) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("recodestr",str);
                web.getJsAccessEntrace().quickCallJs("prizeRecode", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        Log.e("data",s);
                    }
                },str);
            }
        });
    }

    @Override
    public void onRecodeError(String error) {
       Log.e("prizerecodeerror",error);
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
            if(functionBean.getReqName()!=null){
                if(functionBean.getReqName().equals("draw")){
                    drawPresenter.setGame_num(functionBean.getReqParameter().getGame_num());
                    drawPresenter.setDatapath(functionBean.getDataPath());
                    drawPresenter.setGame_name(functionBean.getReqParameter().getGame_name());
                    drawPresenter.loadData();
                }
            }
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
                        break;
                    case "dailyShare":
                        Message message6=handler.obtainMessage(6);
                        message6.obj=functionBean;
                        handler.sendMessage(message6);
                        break;
                    case "shareList":
                        Message message3=handler.obtainMessage(3);
                        message3.obj=functionBean;
                        handler.sendMessage(message3);
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


}
