package com.jdhd.qynovels.ui.activity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.XqymAdapter;
import com.jdhd.qynovels.module.bookcase.AddBookBean;
import com.jdhd.qynovels.module.bookcase.BookInfoBean;
import com.jdhd.qynovels.persenter.impl.bookcase.IAddBookRankPresenterImpl;
import com.jdhd.qynovels.persenter.impl.bookcase.IBookInfoPresenterImpl;
import com.jdhd.qynovels.view.bookcase.IAddBookRankView;
import com.jdhd.qynovels.view.bookcase.IBookInfoView;


public class XqActivity extends AppCompatActivity implements View.OnClickListener , IBookInfoView, IAddBookRankView {
    private RecyclerView rv;
    private static int type;
    private int sc_type;
    private  int lastOffset;//距离
    private  int lastPosition;//第几个item
    private  SharedPreferences sharedPreferences;
    private IBookInfoPresenterImpl bookInfoPresenter;
    private RelativeLayout jz;
    private ImageView gif;
    private XqymAdapter adapter;
    private LinearLayout jrsj;
    private TextView yd;
    private IAddBookRankPresenterImpl addBookRankPresenter;
    private int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xqym);
        Intent intent=getIntent();
        type=intent.getIntExtra("xq",1);
        sc_type=intent.getIntExtra("lx",1);
        id=intent.getIntExtra("id",0);
        Log.e("id",id+"---");
        bookInfoPresenter=new IBookInfoPresenterImpl(this);
        bookInfoPresenter.setId(id);
        bookInfoPresenter.loadData();
        addBookRankPresenter=new IAddBookRankPresenterImpl(this);

        init();
    }


    private void init() {
        jrsj=findViewById(R.id.xq_jrsj);
        jrsj.setOnClickListener(this);
        yd=findViewById(R.id.xq_yd);
        jz=findViewById(R.id.jz);
        gif=findViewById(R.id.case_gif);
        Glide.with(this).load(R.mipmap.re).into(gif);
        rv=findViewById(R.id.xq_rv);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        rv.setLayoutManager(manager);
        adapter=new XqymAdapter(this,XqActivity.this);
        rv.setAdapter(adapter);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(recyclerView.getLayoutManager() !=null) {
                    getPositionAndOffset();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
       if(R.id.xq_jrsj==view.getId()){
           Log.e("asd","点击加入"+id);
           addBookRankPresenter.setId(id);
         addBookRankPresenter.loadData();
       }
       else if(R.id.xq_yd==view.getId()){

       }

    }

    @Override
    public void onSuccess(BookInfoBean bookInfoBean) {
       runOnUiThread(new Runnable() {
           @Override
           public void run() {
               jz.setVisibility(View.GONE);
               adapter.refresh(bookInfoBean.getData());
           }
       });
    }

    @Override
    public void onSuccess(AddBookBean addBookBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("asd",addBookBean.getMsg());
            }
        });
    }

    @Override
    public void onAddError(String error) {
        Log.e("adderror",error);
    }

    @Override
    public void onError(String error) {
       Log.e("xqerror",error);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        change();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPositionAndOffset();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scrollToPosition();
    }

    public void change(){
        if(type==1){
            Intent intent=new Intent(XqActivity.this,MainActivity.class);
            intent.putExtra("fragment_flag", 1);
            startActivity(intent);
        }
        else if(type==2){
            Intent intent=new Intent(XqActivity.this,MainActivity.class);
            intent.putExtra("fragment_flag", 2);
            intent.putExtra("lx",sc_type);
            startActivity(intent);
        }
        else if(type==3){
            Intent intent=new Intent(XqActivity.this,PhbActivity.class);
            startActivity(intent);
        }
        else if(type==4){
            Intent intent=new Intent(XqActivity.this,WjjpActivity.class);
            startActivity(intent);
        }
        else if(type==5){
            Intent intent=new Intent(XqActivity.this,XssdActivity.class);
            startActivity(intent);
        }
        else if(type==6){
            Intent intent=new Intent(XqActivity.this,MorePhbActivity.class);
            startActivity(intent);
        }
    }

    private void scrollToPosition() {

        sharedPreferences= getSharedPreferences("xq", Activity.MODE_PRIVATE);

        lastOffset=sharedPreferences.getInt("lastOffset",0);

        lastPosition=sharedPreferences.getInt("lastPosition",0);

        if(rv.getLayoutManager() !=null&&lastPosition>=0) {

            ((LinearLayoutManager)rv.getLayoutManager()).scrollToPositionWithOffset(lastPosition,lastOffset);

        }

    }


    private  void getPositionAndOffset() {

        LinearLayoutManager layoutManager = (LinearLayoutManager)rv.getLayoutManager();

//获取可视的第一个view

        View topView = layoutManager.getChildAt(0);

        if(topView !=null) {

//获取与该view的顶部的偏移量

            lastOffset= topView.getTop();

//得到该View的数组位置

            lastPosition= layoutManager.getPosition(topView);

            sharedPreferences=getSharedPreferences("xq", Activity.MODE_PRIVATE);

            SharedPreferences.Editor editor =sharedPreferences.edit();

            editor.putInt("lastOffset",lastOffset);

            editor.putInt("lastPosition",lastPosition);

            editor.commit();

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bookInfoPresenter.destoryView();
        addBookRankPresenter.destoryView();
    }
}
