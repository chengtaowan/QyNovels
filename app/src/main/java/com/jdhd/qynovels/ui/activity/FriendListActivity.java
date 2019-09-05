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
import com.jdhd.qynovels.persenter.impl.personal.IShareListPresenterImpl;
import com.jdhd.qynovels.utils.AndroidBug54971Workaround;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.jdhd.qynovels.view.personal.IShareListView;
import com.just.agentweb.AgentWeb;
import com.tencent.mm.opensdk.utils.Log;
import com.umeng.analytics.MobclickAgent;

public class FriendListActivity extends AppCompatActivity implements IShareListView {
    private ImageView back;
    private AgentWeb web;
    private LinearLayout webView;
    String title,path,listtitle,listpath,listpage,listlimit;
    private IShareListPresenterImpl shareListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        AndroidBug54971Workaround.assistActivity(findViewById(android.R.id.content),this);

        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        Intent intent=getIntent();
        title=intent.getStringExtra("title");
        path=intent.getStringExtra("path");
        shareListPresenter=new IShareListPresenterImpl(this,this);
        shareListPresenter.setLimit("0");
        shareListPresenter.setPage("1");
        shareListPresenter.loadData();
        init();
    }

    private void init() {
        back=findViewById(R.id.fl_back);
        webView=findViewById(R.id.webview);
        web=AgentWeb.with(this)

                .setAgentWebParent(webView, new LinearLayout.LayoutParams(-1, -1))

                .useDefaultIndicator()//进度条

                .createAgentWeb()
                .ready()

                .go(MyApp.Url.webbaseUrl+"novel/invitaDetails.html");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(FriendListActivity.this,YqActivity.class);
//                intent.putExtra("title",title);
//                intent.putExtra("path",path);
//                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onShareListSuccess(String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        web.getJsAccessEntrace().quickCallJs("shareList", new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String s) {
                                Log.e("data",s);
                            }
                        },str);
                    }
                }).start();

            }
        });
    }

    @Override
    public void onShareListError(String error) {
        Log.e("sharelisterror",error);
    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
