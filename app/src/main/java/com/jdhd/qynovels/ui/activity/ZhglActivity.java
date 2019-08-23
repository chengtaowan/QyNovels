package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.BindWxBean;
import com.jdhd.qynovels.utils.AndroidBug54971Workaround;
import com.jdhd.qynovels.utils.DbUtils;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ZhglActivity extends AppCompatActivity implements View.OnClickListener {
    private String mobile="",wxname="";
    private int uid;
    private ImageView back;
    private TextView zh,sj,wx;
    private RelativeLayout zh_zh,zh_sj,zh_wx;
    private int type;
    private Button tc;
    private DbUtils dbUtils;
    private SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhgl);
        AndroidBug54971Workaround.assistActivity(findViewById(android.R.id.content),this);
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        dbUtils=new DbUtils(this);
        MyApp.addActivity(this);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        Intent intent=getIntent();
        uid=intent.getIntExtra("uid",0);
        mobile=intent.getStringExtra("mobile");
        wxname=intent.getStringExtra("wxname");
        type=intent.getIntExtra("type",1);
        Intent bindintent=getIntent();
        String action = bindintent.getAction();
        if(bindintent!=null&&action!=null){
            if(action.equals("bindtel")){
                mobile=bindintent.getStringExtra("mobile");
            }
        }
        init();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getwxBean(BindWxBean bindWxBean){
        if(!bindWxBean.getData().getWx_name().equals("")){
           wx.setText(bindWxBean.getData().getWx_name());
           wx.setClickable(false);
        }
    }

    private void init() {
        tc=findViewById(R.id.zh_tc);
        tc.setOnClickListener(this);
        back=findViewById(R.id.zh_back);
        zh=findViewById(R.id.zh);
        sj=findViewById(R.id.sj);
        wx=findViewById(R.id.wx);
        zh_zh=findViewById(R.id.zh_zh);
        zh_sj=findViewById(R.id.zh_sj);
        zh_wx=findViewById(R.id.zh_wx);
        back.setOnClickListener(this);
        zh_sj.setOnClickListener(this);
        zh_wx.setOnClickListener(this);
        zh.setText(uid+"");
        if(mobile.equals("")){
           sj.setText("未绑定");
        }
        else{
            sj.setText(mobile);
        }
        if(wxname.equals("")){
            if(wx.getText().toString().equals("")){
                wx.setText("立即绑定");
            }
            else{
                wxname=wx.getText().toString();
                wx.setText(wxname);
            }

        }
        else{
            wx.setText(wxname);
        }

    }



    @Override
    public void onClick(View view) {
       if(R.id.zh_back==view.getId()){
//           if(type==1){
//              Intent intent=new Intent(ZhglActivity.this,GrzlActivity.class);
//
//              startActivity(intent);
//           }
//           else{
//               Intent intent=new Intent(ZhglActivity.this,SzActivity.class);
//               startActivity(intent);
//           }
           finish();
       }
       else if(R.id.zh_sj==view.getId()){
           if(sj.getText().equals("未绑定")){
              Intent intent=new Intent(ZhglActivity.this,BindMobileActivity.class);
              startActivity(intent);
           }
           else{
               zh_sj.setClickable(false);
               Toast.makeText(ZhglActivity.this,"您已绑定手机号",Toast.LENGTH_SHORT).show();
           }
       }
       else if(R.id.zh_wx==view.getId()){
           if(wx.getText().equals("立即绑定")){
               if(!MyApp.getApi().isWXAppInstalled()){
                   Toast.makeText(ZhglActivity.this,"请先安装微信客户端",Toast.LENGTH_SHORT).show();
                   return;
               }
               SendAuth.Req req = new SendAuth.Req();
               req.scope = "snsapi_userinfo";
               req.state = "bindwechat";
               MyApp.getApi().sendReq(req);
//               Intent intent=new Intent(ZhglActivity.this,MainActivity.class);
//               intent.putExtra("page",3);
//               startActivity(intent);
           }
           else{
               zh_wx.setClickable(false);
               Toast.makeText(ZhglActivity.this,"您已绑定微信",Toast.LENGTH_SHORT).show();
           }
       }
       else if(R.id.zh_tc==view.getId()){
           SharedPreferences preferences=getSharedPreferences("token", Context.MODE_PRIVATE);
           SharedPreferences.Editor editor=preferences.edit();
           editor.clear();
           editor.commit();
           database=dbUtils.getWritableDatabase();
           database.execSQL("delete from usercase");
           database.execSQL("delete from readhistory");
           Intent intent=new Intent(ZhglActivity.this,MainActivity.class);
           intent.putExtra("page",3);
           intent.setAction("exit");
           startActivity(intent);
           MobclickAgent.onProfileSignOff();
           EventBus.getDefault().post("200");
       }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
//        Intent intent=new Intent(ZhglActivity.this,MainActivity.class);
//        intent.putExtra("page", 3);
//        startActivity(intent);
        finish();
    }
}
