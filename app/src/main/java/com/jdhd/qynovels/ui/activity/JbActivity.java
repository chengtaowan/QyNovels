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
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.jdhd.qynovels.view.personal.IGoldListView;

public class JbActivity extends AppCompatActivity implements View.OnClickListener , IGoldListView {
    private ImageView back;
    private RecyclerView rv;
    private TextView tx;
    private IGoldListPresenterImpl goldListPresenter;
    private JbAdapter adapter;
    private TextView ye,money,today_gold,total_gold;
    private int yue,today,total;
    private float mone;
    private String s;
    private double v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jb);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        Intent intent=getIntent();
        yue=intent.getIntExtra("ye",0);
        mone=intent.getFloatExtra("money",0f);
        today=intent.getIntExtra("today",0);
        total=intent.getIntExtra("total",0);
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
        money.setText("约"+v+"元");
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
            Intent intent=new Intent(JbActivity.this,MainActivity.class);
            intent.putExtra("fragment_flag", 4);
            startActivity(intent);
        }
        else if(R.id.jb_tx==view.getId()){
            Intent intent=new Intent(JbActivity.this,TxActivity.class);
            intent.putExtra("jb",s);
            intent.putExtra("money",v);
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
