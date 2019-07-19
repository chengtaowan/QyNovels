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
import android.view.View;
import android.widget.ImageView;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.LsAdapter;
import com.jdhd.qynovels.module.BookBean;
import com.jdhd.qynovels.utils.DbUtils;
import com.jdhd.qynovels.utils.StatusBarUtil;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ls);
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
                list.add(bookBean);
            }
        }
        for(int i=list.size()-1;i>=0;i--){
            mlist.add(list.get(i));
        }
    }

    private void init() {
        back=findViewById(R.id.ls_back);
        back.setOnClickListener(this);
        rv=findViewById(R.id.ls_rv);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        DividerItemDecoration did=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(this,R.drawable.shape_decor);
        did.setDrawable(drawable);
        rv.addItemDecoration(did);
        rv.setLayoutManager(manager);
        LsAdapter adapter=new LsAdapter(this,mlist);
        rv.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if(type==1){
            Intent intent=new Intent(LsActivity.this,MainActivity.class);
            intent.putExtra("fragment_flag", 1);
            startActivity(intent);
        }
        else if(type==4){
            Intent intent=new Intent(LsActivity.this,MainActivity.class);
            intent.putExtra("fragment_flag", 4);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(type==1){
            Intent intent=new Intent(LsActivity.this,MainActivity.class);
            intent.putExtra("fragment_flag", 1);
            startActivity(intent);
        }
        else if(type==4){
            Intent intent=new Intent(LsActivity.this,MainActivity.class);
            intent.putExtra("fragment_flag", 4);
            startActivity(intent);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
