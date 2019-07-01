package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.MoreAdapter;
import com.jdhd.qynovels.utils.StatusBarUtil;

public class XssdActivity extends AppCompatActivity implements View.OnClickListener ,MoreAdapter.onItemClick{
    private ImageView back;
    private RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xssd);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        init();
    }

    private void init() {
        back=findViewById(R.id.xs_back);
        back.setOnClickListener(this);
        rv=findViewById(R.id.xs_rv);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        rv.setLayoutManager(manager);
        MoreAdapter adapter=new MoreAdapter(this,3);
        rv.setAdapter(adapter);
        adapter.setOnItemClick(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(XssdActivity.this, MainActivity.class);
        intent.putExtra("fragment_flag",2);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(XssdActivity.this, MainActivity.class);
        intent.putExtra("fragment_flag",2);
        startActivity(intent);
    }

    @Override
    public void onClick(int index) {
        Intent intent=new Intent(XssdActivity.this, XqActivity.class);
        intent.putExtra("xq",5);
        startActivity(intent);
    }
}
