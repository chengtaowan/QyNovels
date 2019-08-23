package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
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
import com.jdhd.qynovels.persenter.impl.personal.IPrizeRecodePresenterImpl;
import com.jdhd.qynovels.utils.AndroidBug54971Workaround;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.jdhd.qynovels.view.personal.IPrizeRecodeView;
import com.just.agentweb.AgentWeb;
import com.tencent.mm.opensdk.utils.Log;
import com.umeng.analytics.MobclickAgent;

public class PrizeListActivity extends AppCompatActivity implements View.OnClickListener, IPrizeRecodeView {
    private ImageView back;
    private TextView tex;
    private AgentWeb web;
    private LinearLayout webView;
    private String title,path,datapath,page,limit;
    private IPrizeRecodePresenterImpl prizeRecodePresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prize_list);
        AndroidBug54971Workaround.assistActivity(findViewById(android.R.id.content),this);

        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);

        Intent intent=getIntent();
        title=intent.getStringExtra("title");
        page=intent.getStringExtra("page");
        path=intent.getStringExtra("path");
        datapath=intent.getStringExtra("datapath");
        limit=intent.getStringExtra("limit");
        prizeRecodePresenter=new IPrizeRecodePresenterImpl(this,this);

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
        prizeRecodePresenter.setPage(page);
        prizeRecodePresenter.setLimit(limit);
        prizeRecodePresenter.loadData();
        web.getJsInterfaceHolder().addJavaObject("android", new AndroidInterface(web, this));
    }

    @Override
    public void onClick(View view) {
       finish();
    }

    @Override
    public void onRecodeSuccess(String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("recodestr",str);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            web.getJsAccessEntrace().quickCallJs("prizeRecode", new ValueCallback<String>() {
                                @Override
                                public void onReceiveValue(String s) {
                                    Log.e("data",s);
                                }
                            },str);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();


            }
        });
    }

    @Override
    public void onRecodeError(String error) {
      Log.e("recodeerror",error);
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
        }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
