package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.utils.StatusBarUtil;

import java.util.Timer;
import java.util.TimerTask;

public class GgActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView time,tg;
    private int recLen = 5;
    Timer timer = new Timer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gg);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        init();
    }

    private void init() {
        time=findViewById(R.id.time);
        tg=findViewById(R.id.tg);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    // UI thread
                    @Override
                    public void run() {
                        recLen--;
                        time.setText(recLen+"s");
                        if(recLen <=0){
                            Intent intent=new Intent(GgActivity.this,MainActivity.class);
                            startActivity(intent);
                            timer.cancel();
                        }
                    }
                });
            }
        };
        timer.schedule(task, 1000, 1000);       // timeTask
        tg.setOnClickListener(this);
     }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(GgActivity.this,MainActivity.class);
        startActivity(intent);
        timer.cancel();
    }
}
