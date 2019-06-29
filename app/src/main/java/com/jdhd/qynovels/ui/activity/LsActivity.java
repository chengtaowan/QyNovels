package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.LsAdapter;
import com.jdhd.qynovels.module.BookBean;
import com.jdhd.qynovels.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

public class LsActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView back;
    private RecyclerView rv;
    private List<BookBean> list=new ArrayList<>();
    private int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ls);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        init();
        Intent intent=getIntent();
        type=intent.getIntExtra("ls",1);
    }

    private void init() {
        for(int i=0;i<10;i++){
            list.add(new BookBean(R.mipmap.a,"冰与火之歌"+i,"读到：第一章：标题名"));
        }
        back=findViewById(R.id.ls_back);
        back.setOnClickListener(this);
        rv=findViewById(R.id.ls_rv);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        DividerItemDecoration did=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(this,R.drawable.shape_decor);
        did.setDrawable(drawable);
        rv.addItemDecoration(did);
        rv.setLayoutManager(manager);
        LsAdapter adapter=new LsAdapter(this,list);
        rv.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if(type==1){
            Intent intent=new Intent(LsActivity.this,MainActivity.class);
            intent.putExtra("fragment_flag", 1);
            startActivity(intent);
        }
        else if(type==4){
            Intent intent=new Intent(LsActivity.this,MainActivity.class);
            intent.putExtra("fragment_flag", 4);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(type==1){
            Intent intent=new Intent(LsActivity.this,MainActivity.class);
            intent.putExtra("fragment_flag", 1);
            startActivity(intent);
        }
        else if(type==4){
            Intent intent=new Intent(LsActivity.this,MainActivity.class);
            intent.putExtra("fragment_flag", 4);
            startActivity(intent);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
