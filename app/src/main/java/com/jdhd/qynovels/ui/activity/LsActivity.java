package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.LsAdapter;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.BookBean;
import com.jdhd.qynovels.utils.AndroidBug54971Workaround;
import com.jdhd.qynovels.utils.DbUtils;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class LsActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView back;
    private RecyclerView rv;
    private List<BookBean> list=new ArrayList<>();
    private List<BookBean> mlist=new ArrayList<>();
    private int type;
    private DbUtils dbUtils;
    private SQLiteDatabase database;
    private Cursor cursor;
    private String token;
    private RelativeLayout wsj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ls);
        AndroidBug54971Workaround.assistActivity(findViewById(android.R.id.content),this);

        MyApp.addActivity(this);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        dbUtils=new DbUtils(this);
        SharedPreferences preferences=getSharedPreferences("token", Context.MODE_PRIVATE);
        token = preferences.getString("token", "");
        initData();
        init();
        Intent intent=getIntent();
        type=intent.getIntExtra("ls",1);
    }

    private void initData() {
        database=dbUtils.getReadableDatabase();
        if(!token.equals("")){
            cursor=database.rawQuery("select * from readhistory where user='user'",new String[]{});
            while(cursor.moveToNext()){
                BookBean bookBean=new BookBean();
                bookBean.setName(cursor.getString(cursor.getColumnIndex("name")));
                bookBean.setImg(cursor.getString(cursor.getColumnIndex("image")));
                bookBean.setDes("读到："+cursor.getString(cursor.getColumnIndex("readContent")));
                bookBean.setTime(cursor.getString(cursor.getColumnIndex("lastTime")));
                bookBean.setBookid(cursor.getInt(cursor.getColumnIndex("bookid")));
                list.add(bookBean);
            }
        }
        else{
            cursor=database.rawQuery("select * from readhistory where user='visitor'",new String[]{});
            while(cursor.moveToNext()){
                BookBean bookBean=new BookBean();
                bookBean.setName(cursor.getString(cursor.getColumnIndex("name")));
                bookBean.setImg(cursor.getString(cursor.getColumnIndex("image")));
                bookBean.setDes("读到："+cursor.getString(cursor.getColumnIndex("readContent")));
                bookBean.setTime(cursor.getString(cursor.getColumnIndex("lastTime")));
                bookBean.setBookid(cursor.getInt(cursor.getColumnIndex("bookid")));
                list.add(bookBean);
            }
        }
        Log.e("listsize",list.size()+"---");
        for(int i=list.size()-1;i>=0;i--){
            mlist.add(list.get(i));
        }
    }

    private void init() {
        wsj=findViewById(R.id.tx_wsj);
        back=findViewById(R.id.ls_back);
        back.setOnClickListener(this);
        rv=findViewById(R.id.ls_rv);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        DividerItemDecoration did=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(this,R.drawable.shape_decor);
        did.setDrawable(drawable);
        rv.addItemDecoration(did);
        rv.setLayoutManager(manager);
        if(mlist.size()==0){
           wsj.setVisibility(View.VISIBLE);
        }
        else{
            wsj.setVisibility(View.GONE);
        }
        LsAdapter adapter=new LsAdapter(this,mlist);
        rv.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
//        if(type==1){
//            Intent intent=new Intent(LsActivity.this,MainActivity.class);
//            intent.putExtra("page", 0);
//            startActivity(intent);
//        }
//        else if(type==4){
//            Intent intent=new Intent(LsActivity.this,MainActivity.class);
//            intent.putExtra("page", 3);
//            startActivity(intent);
//        }
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        if(type==1){
//            Intent intent=new Intent(LsActivity.this,MainActivity.class);
//            intent.putExtra("page", 0);
//            startActivity(intent);
//        }
//        else if(type==4){
//            Intent intent=new Intent(LsActivity.this,MainActivity.class);
//            intent.putExtra("page", 3);
//            startActivity(intent);
//        }
        finish();
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
}
