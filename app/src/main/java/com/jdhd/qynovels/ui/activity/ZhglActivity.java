package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.utils.StatusBarUtil;

public class ZhglActivity extends AppCompatActivity implements View.OnClickListener {
    private String mobile="",wxname="";
    private int uid;
    private ImageView back;
    private TextView zh,sj,wx;
    private RelativeLayout zh_zh,zh_sj,zh_wx;
    private int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhgl);
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

    private void init() {
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
                wx.setText("未绑定");
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
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View view) {
       if(R.id.zh_back==view.getId()){
           if(type==1){
              Intent intent=new Intent(ZhglActivity.this,GrzlActivity.class);

              startActivity(intent);
           }
           else{
               Intent intent=new Intent(ZhglActivity.this,SzActivity.class);
               startActivity(intent);
           }
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
           if(wx.getText().equals("未绑定")){

           }
           else{
               zh_wx.setClickable(false);
               Toast.makeText(ZhglActivity.this,"您已绑定微信",Toast.LENGTH_SHORT).show();
           }
       }
    }
}
