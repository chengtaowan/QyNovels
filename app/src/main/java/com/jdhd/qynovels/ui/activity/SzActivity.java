package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.utils.StatusBarUtil;

public class SzActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout sz,zh,gy;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sz);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
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
        intent.putExtra("fragment_flag", 4);
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

        }
        else if(R.id.sz_zh==view.getId()){
           Intent intent=new Intent(SzActivity.this,ZhglActivity.class);
           startActivity(intent);
        }
        else if(R.id.sz_gy==view.getId()){
            Intent intent=new Intent(SzActivity.this,GywmActivity.class);
            startActivity(intent);
        }
        else if(R.id.sz_back==view.getId()){
            Intent intent=new Intent(SzActivity.this,MainActivity.class);
            intent.putExtra("fragment_flag",4);
            startActivity(intent);
        }
    }
}
