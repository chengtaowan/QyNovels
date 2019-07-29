package com.jdhd.qynovels.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.FunctionBean;
import com.jdhd.qynovels.persenter.impl.personal.IDrawPresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.IPrizePresenterImpl;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.jdhd.qynovels.view.personal.IDrawView;
import com.jdhd.qynovels.view.personal.IPrizesView;
import com.just.agentweb.AgentWeb;

public class ZpActivity extends AppCompatActivity implements IPrizesView, View.OnClickListener, IDrawView {
    private IPrizePresenterImpl prizePresenter;
    private String title,name,path;
    private ImageView back;
    private TextView tex;
    private AgentWeb web;
    private LinearLayout webView;
    private IDrawPresenterImpl drawPresenter;
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
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zp);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);

        Intent intent=getIntent();
        title=intent.getStringExtra("title");
        path=intent.getStringExtra("path");
        name=intent.getStringExtra("name");
        prizePresenter=new IPrizePresenterImpl(this,this);
        prizePresenter.setGame_name(name);
        prizePresenter.loadData();
        drawPresenter=new IDrawPresenterImpl(this,this);
        init();

    }

    private void init() {
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
    }

    @Override
    public void onPrizeSuccess(String string) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                web.getJsAccessEntrace().quickCallJs("getPrize", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        com.tencent.mm.opensdk.utils.Log.e("data",s);
                    }
                },string);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        web.clearWebCache();
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
        Intent intent=new Intent(ZpActivity.this,MainActivity.class);
        intent.putExtra("page",2);
        startActivity(intent);
    }

    @Override
    public void onDrawSuccess(String string) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                com.tencent.mm.opensdk.utils.Log.e("draw",string);
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
            com.tencent.mm.opensdk.utils.Log.e("name",str);
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
                case "drawDetails":
                    Message message=handler.obtainMessage(1);
                    message.obj=functionBean;
                    handler.sendMessage(message);
                    break;
            }

        }
    }
}
