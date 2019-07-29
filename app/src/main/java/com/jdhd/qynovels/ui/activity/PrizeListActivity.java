package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.ValueCallback;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.persenter.impl.personal.IPrizeRecodePresenterImpl;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.jdhd.qynovels.view.personal.IPrizeRecodeView;
import com.just.agentweb.AgentWeb;
import com.tencent.mm.opensdk.utils.Log;

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
      Log.e("recodeerror",error);
    }
}
