package com.jdhd.qynovels.ui.activity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.glong.reader.activities.CustomReaderActivity;
import com.glong.reader.activities.ExtendReaderActivity;
import com.glong.reader.activities.NormalReaderActivity;
import com.glong.reader.activities.SimpleReaderActivity;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.XqymAdapter;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.bookcase.AddBookBean;
import com.jdhd.qynovels.module.bookcase.BookInfoBean;
import com.jdhd.qynovels.module.bookcase.BookListBean;
import com.jdhd.qynovels.persenter.impl.bookcase.IAddBookRankPresenterImpl;
import com.jdhd.qynovels.persenter.impl.bookcase.IBookInfoPresenterImpl;
import com.jdhd.qynovels.persenter.impl.bookcase.IBookListPresenterImpl;
import com.jdhd.qynovels.utils.DbUtils;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.bookcase.IAddBookRankView;
import com.jdhd.qynovels.view.bookcase.IBookInfoView;
import com.jdhd.qynovels.view.bookcase.IBookListView;

import java.text.SimpleDateFormat;
import java.util.Date;


public class XqActivity extends AppCompatActivity implements View.OnClickListener , IBookInfoView, IAddBookRankView, IBookListView {
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
    private DbUtils dbUtils;
    private SQLiteDatabase database;
    private String token;
    private BookInfoBean bookBean=new BookInfoBean();
    private IBookListPresenterImpl bookListPresenter;
    private BookListBean listBean=new BookListBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xqym);
        MyApp.addActivity(this);
        dbUtils=new DbUtils(this);
        SharedPreferences preferences=getSharedPreferences("token", Context.MODE_PRIVATE);
        token = preferences.getString("token", "");
        Intent intent=getIntent();
        type=intent.getIntExtra("xq",1);
        sc_type=intent.getIntExtra("lx",1);
        id=intent.getIntExtra("id",0);
        Log.e("id",id+"---");
        bookInfoPresenter=new IBookInfoPresenterImpl(this,this);
        bookInfoPresenter.setId(id);
        bookInfoPresenter.loadData();
        addBookRankPresenter=new IAddBookRankPresenterImpl(this,this);
        bookListPresenter=new IBookListPresenterImpl(this,this);
        bookListPresenter.setId(id);
        bookListPresenter.loadData();
        init();
    }


    private void init() {
        jrsj=findViewById(R.id.xq_jrsj);
        jrsj.setOnClickListener(this);
        yd=findViewById(R.id.xq_yd);
        yd.setOnClickListener(this);
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
           addBookRankPresenter.setId(id);
           addBookRankPresenter.loadData();
           Toast.makeText(XqActivity.this,"加入书架",Toast.LENGTH_SHORT).show();
       }
       else if(R.id.xq_yd==view.getId()){
          String time= DeviceInfoUtils.getTime()+"";
         Intent intent=new Intent(XqActivity.this, ExtendReaderActivity.class);
         intent.putExtra("id",id);
         intent.putExtra("token",token);
         startActivity(intent);
         database=dbUtils.getWritableDatabase();
         if(token!=null){
             database.execSQL("delete from readhistory where user='user'and name='"+bookBean.getData().getBook().getName()+"'");
             database.execSQL("insert into readhistory(user,name,image,author,readContent,readStatus,bookStatus,bookid,backlistPercent,lastTime,backlistId)" +
                     "values('user'," +
                     "'"+bookBean.getData().getBook().getName()+"'," +
                     "'"+bookBean.getData().getBook().getImage()+"'," +
                     "'"+bookBean.getData().getBook().getAuthor()+"'," +
                     "'"+listBean.getData().getList().get(0).getName()+"'," +
                     "10," + "10," + "'"+bookBean.getData().getBook().getBookId()+"'," +
                     "0," + "'"+DeviceInfoUtils.changeData(time)+"日"+"'," + "'"+bookBean.getData().getBook().getBacklistNum()+"')");
         }
         else{
             database.execSQL("delete from readhistory where user='visitor'and name='"+bookBean.getData().getBook().getName()+"'");
             database.execSQL("insert into readhistory(user,name,image,author,readContent,readStatus,bookStatus,bookid,backlistPercent,lastTime,backlistId)" +
                     "values('visitor'," +
                     "'"+bookBean.getData().getBook().getName()+"'," +
                     "'"+bookBean.getData().getBook().getImage()+"'," +
                     "'"+bookBean.getData().getBook().getAuthor()+"'," +
                     "'"+listBean.getData().getList().get(0).getName()+"'," +
                     "10," + "10," + "'"+bookBean.getData().getBook().getBookId()+"'," +
                     "0," + "'"+DeviceInfoUtils.changeData(time)+"日"+"'," + "'"+bookBean.getData().getBook().getBacklistNum()+"',)");
         }

       }

    }

    @Override
    public void onSuccess(BookInfoBean bookInfoBean) {
       runOnUiThread(new Runnable() {
           @Override
           public void run() {
               bookBean=bookInfoBean;
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
                database=dbUtils.getWritableDatabase();
                if(!token.equals("")){
                    database.execSQL("insert into usercase(user,name,image,author,readContent,readStatus,bookStatus,bookid,backlistPercent,lastTime,backlistId) " +
                            "values('user'," +
                            "'"+bookBean.getData().getBook().getName()+"'," +
                            "'"+bookBean.getData().getBook().getImage()+"'," +
                            "'"+bookBean.getData().getBook().getAuthor()+"'," +
                            "'"+listBean.getData().getList().get(0).getName()+"'," +
                            "10+10+'"+bookBean.getData().getBook().getBookId()+"'," +
                            "0,''+'"+bookBean.getData().getBook().getBacklistNum()+"')");
                }
                else{
                    database.execSQL("insert into usercase(user,name,image,author,readContent,readStatus,bookStatus,bookid,backlistPercent,lastTime,backlistId) " +
                            "values('visitor'," +
                            "'"+bookBean.getData().getBook().getName()+"'," +
                            "'"+bookBean.getData().getBook().getImage()+"'," +
                            "'"+bookBean.getData().getBook().getAuthor()+"'," +
                            "'"+listBean.getData().getList().get(0).getName()+"'," +
                            "10+10+'"+bookBean.getData().getBook().getBookId()+"'," +
                            "0,''+'"+bookBean.getData().getBook().getBacklistNum()+"')");
                }

            }
        });
    }

    @Override
    public void onAddError(String error) {
        Log.e("adderror",error);
    }

    @Override
    public void onSuccess(BookListBean bookListBean) {
       listBean=bookListBean;
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
            intent.putExtra("page", 0);
            startActivity(intent);
        }
        else if(type==2){
            Intent intent=new Intent(XqActivity.this,MainActivity.class);
            intent.putExtra("page", 1);
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
