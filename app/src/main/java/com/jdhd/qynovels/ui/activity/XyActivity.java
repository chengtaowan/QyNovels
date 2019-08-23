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
import com.jdhd.qynovels.persenter.impl.personal.IShareImgPresenterImpl;
import com.jdhd.qynovels.utils.AndroidBug54971Workaround;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.jdhd.qynovels.view.personal.IShareImgView;
import com.just.agentweb.AgentWeb;
import com.tencent.mm.opensdk.utils.Log;
import com.umeng.analytics.MobclickAgent;

public class XyActivity extends AppCompatActivity implements View.OnClickListener, IShareImgView {
    private String title;
    private int type;
    private ImageView back;
    private TextView tex;
    private AgentWeb web;
    private LinearLayout webView;
    private IShareImgPresenterImpl shareImgPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xy);
        AndroidBug54971Workaround.assistActivity(findViewById(android.R.id.content),this);


        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        shareImgPresenter=new IShareImgPresenterImpl(this,this);
        Intent intent = getIntent();
        title=intent.getStringExtra("title");
        type=intent.getIntExtra("type",0);
        init();
        shareImgPresenter.loadData();
    }

    private void init() {
        back=findViewById(R.id.ls_back);
        back.setOnClickListener(this);
        tex=findViewById(R.id.title);
        webView=findViewById(R.id.ll);
        tex.setText(title);
        if(type==1){
            web=AgentWeb.with(this)

                    .setAgentWebParent(webView, new LinearLayout.LayoutParams(-1, -1))

                    .useDefaultIndicator()//进度条

                    .createAgentWeb()
                    .ready()

                    .go(MyApp.Url.webbaseUrl+"novel/userProtocol.html");
        }
        else if(type==2){
            web=AgentWeb.with(this)

                    .setAgentWebParent(webView, new LinearLayout.LayoutParams(-1, -1))

                    .useDefaultIndicator()//进度条

                    .createAgentWeb()
                    .ready()

                    .go(MyApp.Url.webbaseUrl+"novel/protocol.html");
        }
    }

    @Override
    public void onClick(View view) {
//        Intent intent=new Intent(XyActivity.this,LoginActivity.class);
//        startActivity(intent);
        finish();
    }

    @Override
    public void onShareSuccess(String string) {
        runOnUiThread(new Runnable() {
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
    protected void onDestroy() {
        super.onDestroy();
        if(shareImgPresenter!=null){
            shareImgPresenter.destoryView();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
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
