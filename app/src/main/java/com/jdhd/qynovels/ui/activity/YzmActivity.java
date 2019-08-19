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
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.utils.AndroidBug54971Workaround;
import com.jdhd.qynovels.utils.StatusBarUtil;

import java.util.Timer;
import java.util.TimerTask;

public class YzmActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView gb;
    private TextView phone;
    private EditText yzm;
    private TextView again;
    private Button login;
    private ImageView wxlogin;
    private TextView xy,ys;
    private String tel;
    private int recLen=60;
    private Timer timer=new Timer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yzm);
        AndroidBug54971Workaround.assistActivity(findViewById(android.R.id.content),this);
        MyApp.addActivity(this);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        Intent intent=getIntent();
        tel=intent.getStringExtra("phone");
        init();
    }

    private void init() {
        gb=findViewById(R.id.dl_gb);
        phone=findViewById(R.id.phone);
        yzm=findViewById(R.id.yzm);
        again=findViewById(R.id.again);
        login=findViewById(R.id.login);
        wxlogin=findViewById(R.id.dl_wx);
        xy=findViewById(R.id.xy);
        ys=findViewById(R.id.ys);
        phone.setText(tel);
        again.setOnClickListener(this);
        login.setOnClickListener(this);
        wxlogin.setOnClickListener(this);
        xy.setOnClickListener(this);
        ys.setOnClickListener(this);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    // UI thread
                    @Override
                    public void run() {
                        recLen--;

                        if(recLen <=0){
                            again.setText("重新发送");
                            recLen=60;
                            timer.cancel();
                            timer=new Timer();
                        }
                    }
                });
            }
        };
        timer.schedule(task, 1000, 1000);

    }

    @Override
    public void onClick(View view) {

    }
}
