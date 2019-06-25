package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.Fl_Title_Adapter;
import com.jdhd.qynovels.module.Fl_Title_Bean;
import com.jdhd.qynovels.ui.fragment.FlFragment;

import java.util.ArrayList;
import java.util.List;

public class FlActivity extends AppCompatActivity implements Fl_Title_Adapter.onTitleClick, View.OnClickListener {
     private ImageView back,search;
     private RecyclerView rv;
     private List<Fl_Title_Bean> list=new ArrayList<>();
     private List<Fragment> fragmentList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fl);
        init();
    }

    private void init() {
        back=findViewById(R.id.fl_back);
        search=findViewById(R.id.fl_ss);
        back.setOnClickListener(this);
        search.setOnClickListener(this);
        rv=findViewById(R.id.fl_rv);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(manager);
        list.add(new Fl_Title_Bean("女生"));
        list.add(new Fl_Title_Bean("男生"));
        list.add(new Fl_Title_Bean("图书"));
        Fl_Title_Adapter adapter=new Fl_Title_Adapter(this,list);
        rv.setAdapter(adapter);
        adapter.setOnTitleClick(this);
        for(int i=0;i<list.size();i++){
            fragmentList.add(new FlFragment());
        }
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.rv_fr,fragmentList.get(0));
        transaction.commit();
    }

    @Override
    public void onclick(int index) {
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.rv_fr,fragmentList.get(index));
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        if(R.id.fl_back==view.getId()){
            finish();
        }
        else if(R.id.fl_ss==view.getId()){
            Intent intent=new Intent(FlActivity.this,SsActivity.class);
            startActivity(intent);
        }
    }
}
