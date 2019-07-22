package com.jdhd.qynovels.ui.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.tencent.mm.opensdk.utils.Log;

/**
 * A simple {@link Fragment} subclass.
 */
public class FuLiFragment extends Fragment implements IWelfareView , IShareImgView, IPrizesView , IDrawView, IShareListView, IPrizeRecodeView {

    private AgentWeb web;
    private LinearLayout webView;
    private String ScriptMessageName="GGScriptMessageCommon";
    private IWelfarePresenterImpl welfarePresenter;
    private ImageView back;
    private TextView title;
    private IShareImgPresenterImpl shareImgPresenter;
    private IPrizePresenterImpl prizePresenter;
    private IDrawPresenterImpl drawPresenter;
    private IShareListPresenterImpl shareListPresenter;
    private SmartRefreshLayout sr;
    private boolean hasNetWork;
    private IPrizeRecodePresenterImpl prizeRecodePresenter;

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
                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if ( web.getWebCreator().getWebView().canGoBack()) { // 表示按返回键时的操作
                                web.getWebCreator().getWebView().goBack();
                                back.setVisibility(View.GONE);// 后退
                                MainActivity.rg.setVisibility(View.VISIBLE);
                                welfarePresenter.loadData();
                                // webview.goForward();//前进
                            }
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
            }
        }
    };

    public FuLiFragment() {

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
        init(view);
        return view;
    }

    private void init(View view) {
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

}
