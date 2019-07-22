package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.GfAdapter;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.jdhd.qynovels.widget.RatingBar;

public class YdwActivity extends AppCompatActivity implements View.OnClickListener {
    private RatingBar ratingBar;
    private ImageView close;
    private TextView hyh;
    private RecyclerView rv;
    private Button share,goshop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ydw);
        MyApp.addActivity(this);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        init();
    }

    private void init() {
        ratingBar=findViewById(R.id.ydw_start);
        close=findViewById(R.id.ydw_close);
        share=findViewById(R.id.ydw_share);
        goshop=findViewById(R.id.ydw_goshop);
        hyh=findViewById(R.id.ydw_hyh);
        rv=findViewById(R.id.ydw_rv);
        close.setOnClickListener(this);
        share.setOnClickListener(this);
        goshop.setOnClickListener(this);
        hyh.setOnClickListener(this);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        rv.setLayoutManager(manager);
        GfAdapter adapter=new GfAdapter(this,0,0);
        rv.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if(R.id.ydw_close==view.getId()){
            Intent intent=new Intent(YdwActivity.this,MainActivity.class);
            intent.putExtra("fragment_flag", 1);
            startActivity(intent);
        }
        else if(R.id.ydw_hyh==view.getId()){

        }
        else if(R.id.ydw_share==view.getId()){
            Intent intent=new Intent(YdwActivity.this,ShareActivity.class);
            startActivity(intent);
        }
        else if(R.id.ydw_goshop==view.getId()){
            Intent intent=new Intent(YdwActivity.this,MainActivity.class);
            intent.putExtra("fragment_flag", 2);
            startActivity(intent);
        }
    }
}
