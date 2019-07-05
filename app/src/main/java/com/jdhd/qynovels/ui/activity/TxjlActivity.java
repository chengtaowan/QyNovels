package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.TxjlAdapter;
import com.jdhd.qynovels.utils.StatusBarUtil;

public class TxjlActivity extends AppCompatActivity implements View.OnClickListener {
   private ImageView back;
   private RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_txjl);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);

        init();
    }

    private void init() {
        back=findViewById(R.id.tx_back);
        back.setOnClickListener(this);
        rv=findViewById(R.id.jl_rv);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        rv.setLayoutManager(manager);
        TxjlAdapter adapter=new TxjlAdapter(this);
        rv.setAdapter(adapter);
        rv.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
