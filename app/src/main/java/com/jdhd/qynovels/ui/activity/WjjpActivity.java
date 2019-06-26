package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.jdhd.qynovels.R;

public class WjjpActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView back;
    private RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wjjp);
        init();
    }

    private void init() {
        back=findViewById(R.id.wj_back);
        rv=findViewById(R.id.wj_rv);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        rv.setLayoutManager(manager);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
