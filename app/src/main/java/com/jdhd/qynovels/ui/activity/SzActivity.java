package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.utils.StatusBarUtil;

public class SzActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sz);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);

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
}
