package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.UserBean;
import com.jdhd.qynovels.utils.AndroidBug54971Workaround;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SzActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout sz,zh,gy;
    private ImageView back;
    private String token;
    private String avatar="",nickname="",sex="",mobile="",wxname;
    private int uid,bindwx;
    private UserBean user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sz);
        AndroidBug54971Workaround.assistActivity(findViewById(android.R.id.content),this);

        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        MyApp.addActivity(this);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        Intent perintent=getIntent();
        int type=perintent.getIntExtra("type",0);
        if(type==1){
            SharedPreferences preferences=getSharedPreferences("token",MODE_PRIVATE);
            token=preferences.getString("token","");
            if(!token.equals("")){
                nickname=perintent.getStringExtra("name");
                mobile=perintent.getStringExtra("mobile");
                avatar=perintent.getStringExtra("avatar");
                sex=perintent.getStringExtra("sex");
                uid=perintent.getIntExtra("uid",0);
                bindwx=perintent.getIntExtra("bindwx",0);
                wxname=perintent.getStringExtra("wxname");
            }
        }
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
//        Intent intent=new Intent(SzActivity.this,MainActivity.class);
//        intent.putExtra("page", 3);
//        startActivity(intent);
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void notifyData(UserBean userBean){
        user=userBean;
    }
    @Override
    public void onClick(View view) {
        if(R.id.sz_sz==view.getId()){
            if(nickname.equals("")&&avatar.equals("")&&sex.equals("")){
                Toast.makeText(SzActivity.this,"请登录",Toast.LENGTH_SHORT).show();
            }
            else{
                Intent intent=new Intent(SzActivity.this,GrzlActivity.class);
                intent.putExtra("name",nickname);
                intent.putExtra("avatar",avatar);
                intent.putExtra("sex",sex);
                intent.putExtra("uid",uid);
                intent.putExtra("mobile",mobile+"");
                intent.putExtra("bindwx",bindwx);
                intent.putExtra("wxname",wxname);
                intent.putExtra("type","sz");
                startActivity(intent);
            }

        }
        else if(R.id.sz_zh==view.getId()){
            if(nickname.equals("")&&avatar.equals("")&&sex.equals("")){
                Toast.makeText(SzActivity.this,"请登录",Toast.LENGTH_SHORT).show();
            }
            else{
                Intent intent=new Intent(SzActivity.this,ZhglActivity.class);
                intent.putExtra("uid",uid);
                intent.putExtra("mobile",mobile+"");
                intent.putExtra("wxname",wxname);
                intent.putExtra("type",2);
                startActivity(intent);            }

        }
        else if(R.id.sz_gy==view.getId()){
            Intent intent=new Intent(SzActivity.this,GywmActivity.class);
            startActivity(intent);
        }
        else if(R.id.sz_back==view.getId()){
//            Intent intent=new Intent(SzActivity.this,MainActivity.class);
//            intent.putExtra("page",3);
//            startActivity(intent);
            finish();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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

}
