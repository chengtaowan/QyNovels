package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.MuluAdapter;
import com.jdhd.qynovels.module.bookcase.BookListBean;
import com.jdhd.qynovels.persenter.impl.bookcase.IBookListPresenterImpl;
import com.jdhd.qynovels.view.bookcase.IBookListView;

public class MuluActivity extends AppCompatActivity implements IBookListView , View.OnClickListener {
    private ImageView back;
    private TextView name;
    private RecyclerView rv;
    private MuluAdapter adapter;
    private IBookListPresenterImpl bookListPresenter;
    private String bookname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mulu);
        Intent intent=getIntent();
        int id=intent.getIntExtra("id",0);
        bookname=intent.getStringExtra("name");
        bookListPresenter=new IBookListPresenterImpl(this);
        bookListPresenter.setId(id);
        bookListPresenter.loadData();
        init();
    }

    private void init() {
        back=findViewById(R.id.ml_back);
        back.setOnClickListener(this);
        name=findViewById(R.id.ml_name);
        name.setText(bookname);
        rv=findViewById(R.id.ml_rv);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        rv.setLayoutManager(manager);
        adapter=new MuluAdapter();
        rv.setAdapter(adapter);
    }

    @Override
    public void onSuccess(BookListBean bookListBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
              adapter.refresh(bookListBean.getData().getList());
            }
        });
    }

    @Override
    public void onError(String error) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bookListPresenter.destoryView();
    }

    @Override
    public void onClick(View view) {
        if(R.id.ml_back==view.getId()){
            finish();
        }
    }
}
