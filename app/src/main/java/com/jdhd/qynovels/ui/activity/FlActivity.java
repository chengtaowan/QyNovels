package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.FlAdapter;
import com.jdhd.qynovels.adapter.Fl_biaoti_Adapter;
import com.jdhd.qynovels.module.bookshop.ClassBean;
import com.jdhd.qynovels.persenter.impl.bookshop.IClassPresenterImpl;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.jdhd.qynovels.view.bookshop.IClassView;

import java.util.ArrayList;
import java.util.List;

public class FlActivity extends AppCompatActivity implements Fl_biaoti_Adapter.onTitleClick, View.OnClickListener , IClassView ,FlAdapter.onItemClick{
     private ImageView back,search;
     private RecyclerView rv;
     private RecyclerView datarv;
     private IClassPresenterImpl classPresenter;
     private Fl_biaoti_Adapter adapter;
     private FlAdapter fladapter;
     private String name="男生";
     private List<ClassBean.DataBean.ListBean> list=new ArrayList<>();
     int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fl);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        classPresenter=new IClassPresenterImpl(this,this);
        classPresenter.loadData();
        init();
    }

    private void init() {
        datarv=findViewById(R.id.rl_rv);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        datarv.setLayoutManager(manager);
        fladapter=new FlAdapter(this);
        datarv.setAdapter(fladapter);
        fladapter.setOnItemClick(this);
        back=findViewById(R.id.fl_back);
        search=findViewById(R.id.fl_ss);
        back.setOnClickListener(this);
        search.setOnClickListener(this);
        rv=findViewById(R.id.fl_rv);
        LinearLayoutManager manager1=new LinearLayoutManager(this);
        manager1.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(manager1);
        adapter=new Fl_biaoti_Adapter(this);
        rv.setAdapter(adapter);
        adapter.setOnTitleClick(this);


    }

    @Override
    public void onclick(int index) {
        classPresenter.loadData();
        for(int i=0;i<list.size();i++){
            if(i==index){
                name=list.get(index).getName();
                fladapter.refresh(list.get(index).getChild());
            }
        }

    }

    @Override
    public void onClick(View view) {
        if(R.id.fl_back==view.getId()){
            Intent intent=new Intent(FlActivity.this,MainActivity.class);
            intent.putExtra("page", 1);
            startActivity(intent);
        }
        else if(R.id.fl_ss==view.getId()){
            Intent intent=new Intent(FlActivity.this,SsActivity.class);
            intent.putExtra("ss",3);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(FlActivity.this,MainActivity.class);
        intent.putExtra("fragment_flag", 2);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onSuccess(ClassBean classBean) {
       runOnUiThread(new Runnable() {
           @Override
           public void run() {
               list=classBean.getData().getList();
               adapter.refresh(classBean.getData().getList());
               if(count==0){
                   fladapter.refresh(list.get(0).getChild());
               }
               count++;
           }
       });
    }

    @Override
    public void onError(String error) {
       Log.e("rlerror",error);
    }

    @Override
    public void onFlClick(int index) {
        Intent intent=new Intent(this, MorePhbActivity.class);
        intent.putExtra("more",1);
        if(name.equals(list.get(0).getName())){
            intent.putExtra("title",list.get(0).getChild().get(index).getName());
            intent.putExtra("id",list.get(0).getChild().get(index).getId());
        }
        else{
            intent.putExtra("title",list.get(1).getChild().get(index).getName());
            intent.putExtra("id",list.get(1).getChild().get(index).getId());
        }

        startActivity(intent);
    }
}
