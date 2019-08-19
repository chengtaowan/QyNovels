package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.TxjlAdapter;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.DrawListBean;
import com.jdhd.qynovels.persenter.impl.personal.IDrawListPresenterImpl;
import com.jdhd.qynovels.utils.AndroidBug54971Workaround;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.jdhd.qynovels.view.personal.IDrawListView;
import com.umeng.analytics.MobclickAgent;

public class TxjlActivity extends AppCompatActivity implements View.OnClickListener , IDrawListView {
   private ImageView back;
   private RecyclerView rv;
   private IDrawListPresenterImpl drawListPresenter;
   private TxjlAdapter adapter;
   private RelativeLayout wsj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_txjl);
        AndroidBug54971Workaround.assistActivity(findViewById(android.R.id.content),this);


        MyApp.addActivity(this);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        drawListPresenter=new IDrawListPresenterImpl(this,this);
        drawListPresenter.loadData();
        init();
    }

    private void init() {
        wsj=findViewById(R.id.tx_wsj);
        back=findViewById(R.id.tx_back);
        back.setOnClickListener(this);
        rv=findViewById(R.id.jl_rv);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        rv.setLayoutManager(manager);
        adapter=new TxjlAdapter(this);
        rv.setAdapter(adapter);
        rv.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onClick(View view) {
        finish();
    }

    @Override
    public void onSuccess(DrawListBean drawListBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(drawListBean.getCode()!=200){
                    Toast.makeText(TxjlActivity.this,drawListBean.getMsg(),Toast.LENGTH_SHORT).show();
                }
                else{
                    if(drawListBean.getData().getList().size()!=0){
                        wsj.setVisibility(View.GONE);
                    }
                    adapter.refresh(drawListBean.getData().getList());
                    Log.e("listsize",drawListBean.getData().getList().size()+"");
                }

            }
        });
    }

    @Override
    public void onError(String error) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this); // 不能遗漏
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this); // 不能遗漏
    }
}
