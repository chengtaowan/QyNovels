package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.utils.AndroidBug54971Workaround;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.umeng.analytics.MobclickAgent;

public class GywmActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gywm);
        AndroidBug54971Workaround.assistActivity(findViewById(android.R.id.content),this);


        MyApp.addActivity(this);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        init();
    }

    private void init() {
        back=findViewById(R.id.gy_back);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        finish();
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
