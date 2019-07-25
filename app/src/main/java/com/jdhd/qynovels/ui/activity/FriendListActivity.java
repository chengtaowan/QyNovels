package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.just.agentweb.AgentWeb;

public class FriendListActivity extends AppCompatActivity {
    private ImageView back;
    private AgentWeb web;
    private LinearLayout webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
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

                .go(MyApp.Url.webbaseUrl+"novel/invitaDetails.html ");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FriendListActivity.this,MainActivity.class);
                intent.putExtra("page",2);
                startActivity(intent);
            }
        });
    }
}
