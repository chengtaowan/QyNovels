package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.utils.StatusBarUtil;

public class SzActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout sz,zh,gy;
    private ImageView back;
    private String avatar,nickname,sex,mobile,wxname;
    private int uid,bindwx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sz);
        MyApp.addActivity(this);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        Intent perintent=getIntent();
            nickname=perintent.getStringExtra("name");
            mobile=perintent.getStringExtra("mobile");
            avatar=perintent.getStringExtra("avatar");
            sex=perintent.getStringExtra("sex");
            uid=perintent.getIntExtra("uid",0);
            bindwx=perintent.getIntExtra("bindwx",0);
            wxname=perintent.getStringExtra("wxname");

        init();
    }

    private void init() {
        sz=findViewById(R.id.sz_sz);
        zh=findViewById(R.id.sz_zh);
        gy=findViewById(R.id.sz_gy);
        back=findViewById(R.id.sz_back);
        back.setOnClickListener(this);
        sz.setOnClickListener(this);
        zh.setOnClickListener(this);
        gy.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(SzActivity.this,MainActivity.class);
        intent.putExtra("page", 3);
        startActivity(intent);
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onClick(View view) {
        if(R.id.sz_sz==view.getId()){
           Intent intent=new Intent(SzActivity.this,GrzlActivity.class);
            intent.putExtra("name",nickname);
            intent.putExtra("avatar",avatar);
            intent.putExtra("sex",sex);
            intent.putExtra("uid",uid);
            intent.putExtra("mobile",mobile+"");
            intent.putExtra("bindwx",bindwx);
            intent.putExtra("wxname",wxname);
            intent.putExtra("type",2);
           startActivity(intent);
        }
        else if(R.id.sz_zh==view.getId()){
           Intent intent=new Intent(SzActivity.this,ZhglActivity.class);
            intent.putExtra("uid",uid);
            intent.putExtra("mobile",mobile+"");
            intent.putExtra("wxname",wxname);
            intent.putExtra("type",2);
           startActivity(intent);
        }
        else if(R.id.sz_gy==view.getId()){
            Intent intent=new Intent(SzActivity.this,GywmActivity.class);
            startActivity(intent);
        }
        else if(R.id.sz_back==view.getId()){
            Intent intent=new Intent(SzActivity.this,MainActivity.class);
            intent.putExtra("page",3);
            startActivity(intent);
        }
    }
}
