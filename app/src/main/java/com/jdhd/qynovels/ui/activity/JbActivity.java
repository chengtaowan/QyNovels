package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.JbAdapter;
import com.jdhd.qynovels.module.personal.GoldListBean;
import com.jdhd.qynovels.persenter.impl.personal.IGoldListPresenterImpl;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.jdhd.qynovels.view.personal.IGoldListView;

public class JbActivity extends AppCompatActivity implements View.OnClickListener , IGoldListView {
    private ImageView back;
    private RecyclerView rv;
    private TextView tx;
    private IGoldListPresenterImpl goldListPresenter;
    private JbAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jb);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        goldListPresenter=new IGoldListPresenterImpl(this,JbActivity.this);
        goldListPresenter.loadData();
        init();
    }

    private void init() {
        back=findViewById(R.id.jb_back);
        rv=findViewById(R.id.jb_rv);
        tx=findViewById(R.id.jb_tx);
        back.setOnClickListener(this);
        tx.setOnClickListener(this);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        rv.setLayoutManager(manager);
        adapter=new JbAdapter();
        rv.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if(R.id.jb_back==view.getId()){
            Intent intent=new Intent(JbActivity.this,MainActivity.class);
            intent.putExtra("fragment_flag", 4);
            startActivity(intent);
        }
        else if(R.id.jb_tx==view.getId()){
            Intent intent=new Intent(JbActivity.this,TxActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onSuccess(GoldListBean goldListBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
               adapter.refresh(goldListBean.getData().getList());
            }
        });
    }

    @Override
    public void onError(String error) {
        Log.e("jberror",error);
    }
}
