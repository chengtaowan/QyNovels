package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.utils.StatusBarUtil;

public class GrzlActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView back,tx;
    private RelativeLayout nc,xb,zh;
    private TextView xgnc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grzl);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        init();
        Intent intent=getIntent();
        String name=intent.getStringExtra("nc");
        xgnc.setText(name);
    }

    private void init() {
        back=findViewById(R.id.zl_back);
        tx=findViewById(R.id.xg_tx);
        nc=findViewById(R.id.zl_nc);
        xb=findViewById(R.id.zl_xb);
        zh=findViewById(R.id.zl_zh);
        xgnc=findViewById(R.id.nc);
        back.setOnClickListener(this);
        tx.setOnClickListener(this);
        nc.setOnClickListener(this);
        xb.setOnClickListener(this);
        zh.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(GrzlActivity.this,MainActivity.class);
        intent.putExtra("fragment_flag", 4);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if(R.id.zl_back==view.getId()){
            Intent intent=new Intent(GrzlActivity.this,MainActivity.class);
            intent.putExtra("fragment_flag", 4);
            startActivity(intent);
        }
        else if(R.id.xg_tx==view.getId()){

        }
        else if(R.id.zl_nc==view.getId()){
          Intent intent=new Intent(GrzlActivity.this,XgncActivity.class);
          startActivity(intent);
        }
        else if(R.id.zl_xb==view.getId()){

        }
        else if(R.id.zl_zh==view.getId()){

        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
