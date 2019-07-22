package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.CjwtAdapter;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.utils.StatusBarUtil;

public class CjwtActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView back;
    private RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cjwt);
        MyApp.addActivity(this);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);

        init();
    }

    private void init() {
        back=findViewById(R.id.wt_back);
        back.setOnClickListener(this);
        rv=findViewById(R.id.wt_rv);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        rv.setLayoutManager(manager);
        CjwtAdapter adapter=new CjwtAdapter();
        rv.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(CjwtActivity.this,MainActivity.class);
        intent.putExtra("page", 3);
        startActivity(intent);
    }
}
