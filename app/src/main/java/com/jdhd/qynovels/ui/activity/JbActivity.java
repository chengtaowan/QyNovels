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
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.GoldListBean;
import com.jdhd.qynovels.persenter.impl.personal.IGoldListPresenterImpl;
import com.jdhd.qynovels.utils.AndroidBug54971Workaround;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.jdhd.qynovels.view.personal.IGoldListView;
import com.umeng.analytics.MobclickAgent;

public class JbActivity extends AppCompatActivity implements View.OnClickListener , IGoldListView {
    private ImageView back;
    private RecyclerView rv;
    private TextView tx;
    private IGoldListPresenterImpl goldListPresenter;
    private JbAdapter adapter;
    private TextView ye,money,today_gold,total_gold;
    private int yue,today,total;
    private float mone;
    private String s,wxname;
    private double v;
    private int time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jb);
        AndroidBug54971Workaround.assistActivity(findViewById(android.R.id.content),this);


        MyApp.addActivity(this);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        Intent intent=getIntent();
        yue=intent.getIntExtra("ye",0);
        mone=intent.getFloatExtra("money",0f);
        today=intent.getIntExtra("today",0);
        total=intent.getIntExtra("total",0);
        wxname=intent.getStringExtra("wxname");
        time=intent.getIntExtra("time",0);
        goldListPresenter=new IGoldListPresenterImpl(this,JbActivity.this);
        goldListPresenter.loadData();
        init();
    }

    private void init() {
        ye=findViewById(R.id.ye);
        money=findViewById(R.id.money);
        total_gold=findViewById(R.id.total_gold);
        today_gold=findViewById(R.id.today_gold);
        s = DeviceInfoUtils.NumtFormat(yue);
        v = DeviceInfoUtils.NumtoMoney(yue);
        ye.setText(s);
        money.setText("约"+mone+"元");
        total_gold.setText(total+"");
        today_gold.setText(today+"");
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
//            Intent intent=new Intent(JbActivity.this,MainActivity.class);
//            intent.putExtra("page", 3);
//            startActivity(intent);
            finish();
        }
        else if(R.id.jb_tx==view.getId()){
            Intent intent=new Intent(JbActivity.this,TxActivity.class);
            intent.putExtra("jb",s);
            intent.putExtra("money",mone);
            intent.putExtra("wxname",wxname);
            intent.putExtra("totle",total);
            intent.putExtra("time",time);
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
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
