package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.FunctionBean;
import com.jdhd.qynovels.ui.fragment.FuLiFragment;
import com.jdhd.qynovels.utils.AndroidBug54971Workaround;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.just.agentweb.AgentWeb;
import com.tencent.mm.opensdk.utils.Log;
import com.umeng.analytics.MobclickAgent;

public class HelpActivity extends AppCompatActivity implements View.OnClickListener {
    private AgentWeb web;
    private LinearLayout webView;
    private ImageView back;
    private TextView title;
    private String token;

    @Override
    protected void onStart() {
        super.onStart();
        if(web!=null){
           web.clearWebCache();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(web!=null){
            web.clearWebCache();
        }
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(web!=null){
            web.clearWebCache();
        }
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        AndroidBug54971Workaround.assistActivity(findViewById(android.R.id.content),this);

        MyApp.addActivity(this);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        Intent intent = getIntent();
        token=intent.getStringExtra("token");
        init();
    }

    private void init() {
        webView=findViewById(R.id.ll);
        back=findViewById(R.id.fk_back);
        back.setOnClickListener(this);
        title=findViewById(R.id.title);
        web=AgentWeb.with(this)
                .setAgentWebParent(webView, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()//进度条
                .createAgentWeb()
                .ready()
                .go(MyApp.Url.webbaseUrl+"novel/help.html");
        web.getJsInterfaceHolder().addJavaObject("android", new AndroidInterface(web, this));
        web.getWebCreator().getWebView().setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String bt) {
                if (!TextUtils.isEmpty(bt)) {
                    title.setText(bt);
                }
                super.onReceivedTitle(view, bt);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(R.id.fk_back==view.getId()){
//            Intent intent=new Intent(HelpActivity.this,MainActivity.class);
//            intent.putExtra("page",3);
//            startActivity(intent);
            finish();
        }
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
                case "help":
                    Intent intent=new Intent(HelpActivity.this,FkActivity.class);
                    intent.putExtra("token",token);
                    startActivity(intent);
                    break;

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(web!=null){
            web.clearWebCache();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
