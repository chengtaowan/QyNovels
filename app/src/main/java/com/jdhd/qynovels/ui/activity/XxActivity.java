package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.XxAdapter;
import com.jdhd.qynovels.module.personal.MessageBean;
import com.jdhd.qynovels.persenter.impl.personal.IMessagePresenterImpl;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.jdhd.qynovels.view.personal.IMessageView;

public class XxActivity extends AppCompatActivity implements IMessageView,View.OnClickListener {
    private ImageView back;
    private RecyclerView rv;
    private XxAdapter adapter;
    private IMessagePresenterImpl messagePresenter;
    private RelativeLayout wxx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xx);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        messagePresenter=new IMessagePresenterImpl(this,this);
        messagePresenter.loadData();
        init();
    }

    private void init() {
        back=findViewById(R.id.xx_back);
        back.setOnClickListener(this);
        wxx=findViewById(R.id.tx_wsj);
        rv=findViewById(R.id.xx_rv);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        rv.setLayoutManager(manager);
        adapter=new XxAdapter();
        rv.setAdapter(adapter);
    }

    @Override
    public void onMessageSuccess(MessageBean messageBean) {
       runOnUiThread(new Runnable() {
           @Override
           public void run() {
               if(messageBean.getData().getList().size()!=0){
                   wxx.setVisibility(View.GONE);
                   adapter.refresh(messageBean.getData().getList());
               }

           }
       });
    }

    @Override
    public void onMessageError(String error) {
        Log.e("messageerror",error);
    }

    @Override
    public void onClick(View view) {
        if(R.id.xx_back==view.getId()){
            Intent intent=new Intent(XxActivity.this,MainActivity.class);
            intent.putExtra("page",3);
            startActivity(intent);
        }
    }
}