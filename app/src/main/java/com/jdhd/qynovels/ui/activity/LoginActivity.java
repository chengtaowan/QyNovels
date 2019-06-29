package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.utils.StatusBarUtil;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView gb,wx;
    private EditText phone,yzm;
    private TextView yy;
    private Button but;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        init();
    }

    private void init() {
        gb=findViewById(R.id.dl_gb);
        wx=findViewById(R.id.dl_wx);
        phone=findViewById(R.id.dl_phone);
        yzm=findViewById(R.id.dl_yzm);
        yy=findViewById(R.id.dl_yy);
        but=findViewById(R.id.dl_but);
        but.setOnClickListener(this);
        gb.setOnClickListener(this);
        wx.setOnClickListener(this);
        yy.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(R.id.dl_gb==view.getId()){
           finish();
        }
        else if(R.id.dl_wx==view.getId()){

        }
        else if(R.id.dl_yy==view.getId()){

        }
        else if(R.id.dl_but==view.getId()){
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            intent.putExtra("fragment_flag", 4);
            startActivity(intent);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
