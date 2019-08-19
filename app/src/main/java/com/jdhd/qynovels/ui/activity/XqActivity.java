package com.jdhd.qynovels.ui.activity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.glong.reader.activities.ExtendReaderActivity;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.XqymAdapter;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.bookcase.AddBookBean;
import com.jdhd.qynovels.module.bookcase.BookInfoBean;
import com.jdhd.qynovels.module.bookcase.BookListBean;
import com.jdhd.qynovels.module.bookcase.CaseBean;
import com.jdhd.qynovels.persenter.impl.bookcase.IAddBookRankPresenterImpl;
import com.jdhd.qynovels.persenter.impl.bookcase.IBookInfoPresenterImpl;
import com.jdhd.qynovels.persenter.impl.bookcase.IBookListPresenterImpl;
import com.jdhd.qynovels.persenter.impl.bookcase.ICasePresenterImpl;
import com.jdhd.qynovels.utils.AndroidBug54971Workaround;
import com.jdhd.qynovels.utils.DbUtils;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.utils.FastBlurUtil;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.jdhd.qynovels.view.bookcase.IAddBookRankView;
import com.jdhd.qynovels.view.bookcase.IBookInfoView;
import com.jdhd.qynovels.view.bookcase.IBookListView;
import com.jdhd.qynovels.view.bookcase.ICaseView;
import com.jdhd.qynovels.widget.RatingBar;
import com.umeng.analytics.MobclickAgent;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;


public class XqActivity extends AppCompatActivity implements View.OnClickListener , IBookInfoView, IAddBookRankView, IBookListView, ICaseView {
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
    private TextView yd,jr;
    private IAddBookRankPresenterImpl addBookRankPresenter;
    private int id;
    private DbUtils dbUtils;
    private SQLiteDatabase database;
    private String token;
    private BookInfoBean bookBean=new BookInfoBean();
    private IBookListPresenterImpl bookListPresenter;
    private BookListBean listBean=new BookListBean();
    private ImageView back;
    private Toolbar toolbar;
    private TextView title;
    private ImageView  icon;
    private boolean iscase;
    private ICasePresenterImpl casePresenter;
    private View.OnTouchListener onTouchListener= new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return findViewById(R.id.rl).dispatchTouchEvent(event);
        }
    };;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xqym);
        AndroidBug54971Workaround.assistActivity(findViewById(android.R.id.content),this);
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
        casePresenter=new ICasePresenterImpl(this,this);

    }


    private void init() {
        icon=findViewById(R.id.icon);
        jr=findViewById(R.id.jr);
        toolbar=findViewById(R.id.toolbar);
        title=findViewById(R.id.tex);
        title.setVisibility(View.GONE);
        back=findViewById(R.id.xq_back);
        back.setOnClickListener(this);
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
            Drawable drawable= ContextCompat.getDrawable(XqActivity.this, R.drawable.tool_bj);
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                StatusBarUtil.setStatusBarMode(XqActivity.this, true, R.color.c_ffffff);
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                StatusBarUtil.setStatusBarMode(XqActivity.this, true, R.color.c_ffffff);
                Log.e("juli",dy+"---");
                int toolBarHeight = toolbar.getMeasuredHeight();
                if ((recyclerView.computeVerticalScrollOffset()) >= (toolBarHeight * 2.5)) { // >=Toolbar高度的2.5倍时全显背景图
                    drawable.setAlpha(255);
                    toolbar.setBackground(drawable);
                    back.setImageResource(R.mipmap.back_h);
                    title.setVisibility(View.VISIBLE);
                    title.setText(bookBean.getData().getBook().getName());
                    toolbar.setOnTouchListener(null);
                } else if((recyclerView.computeVerticalScrollOffset()) >= toolBarHeight){ // >=Toolbar高度&&<Toolbar高度的2.5倍时开始渐变背景图
                    drawable.setAlpha((int) (255 * ((recyclerView.computeVerticalScrollOffset() - toolBarHeight)/(toolBarHeight * 1.5F))));
                    toolbar.setBackground(drawable);
                    title.setVisibility(View.VISIBLE);
                    title.setText(bookBean.getData().getBook().getName());
                    back.setImageResource(R.mipmap.back_h);
                    toolbar.setOnTouchListener(onTouchListener);
                } else { // 小于Toolbar高度时不设置背景图
                    toolbar.setBackground(null);
                    title.setVisibility(View.GONE);
                    back.setImageResource(R.mipmap.back_b);
                    toolbar.setOnTouchListener(onTouchListener);
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
           jr.setText("已加入书架");
           jr.setTextColor(Color.parseColor("#999999"));
           icon.setImageResource(R.mipmap.xqy_jrsjon);
       }
       else if(R.id.xq_back==view.getId()){
           change();
       }
       else if(R.id.xq_yd==view.getId()){
          String time= DeviceInfoUtils.getTime()+"";
         Intent intent=new Intent(XqActivity.this, ExtendReaderActivity.class);
         intent.putExtra("id",id);
         intent.putExtra("token",token);
           intent.putExtra("img",bookBean.getData().getBook().getImage());
           intent.putExtra("name",bookBean.getData().getBook().getName());
           intent.putExtra("author",bookBean.getData().getBook().getAuthor());
         startActivity(intent);
         database=dbUtils.getWritableDatabase();
         Log.e("token--",token+"===");
         if(!token.equals("")){
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
                     "0," + "'"+DeviceInfoUtils.changeData(time)+"日"+"'," + "'"+bookBean.getData().getBook().getBacklistNum()+"')");
         }

       }

    }

    @Override
    public void onBookinfoSuccess(BookInfoBean bookInfoBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bookBean=bookInfoBean;
                jz.setVisibility(View.GONE);
                adapter.refresh(bookInfoBean.getData());
                casePresenter.loadData();
            }
        });
    }

    @Override
    public void onBookinfoError(String error) {
       Log.e("bookinfoerror",error);
    }

    public class MyThread extends Thread{
        private String url;
        private ImageView bj;

        public MyThread(String url, ImageView bj) {
            this.url = url;
            this.bj = bj;
        }

        @Override
        public void run() {
            super.run();
            final Bitmap blurBitmap2 = FastBlurUtil.GetUrlBitmap(url, 2);
            // 刷新ui必须在主线程中执行
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    bj.setImageBitmap(blurBitmap2);
                }
            });
        }
    }

    @Override
    public void onSuccess(AddBookBean addBookBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                database=dbUtils.getWritableDatabase();
                if(!token.equals("")){
                    if(bookBean.getData().getBook()!=null){
                        database.execSQL("insert into usercase(user,name,image,author,readContent,readStatus,bookStatus,bookid,backlistPercent,lastTime,backlistId) " +
                                "values('user'," +
                                "'"+bookBean.getData().getBook().getName()+"'," +
                                "'"+bookBean.getData().getBook().getImage()+"'," +
                                "'"+bookBean.getData().getBook().getAuthor()+"'," +
                                "'"+listBean.getData().getList().get(0).getName()+"'," +
                                "10,+10,+'"+bookBean.getData().getBook().getBookId()+"'," +
                                "0,'',+'"+bookBean.getData().getBook().getBacklistNum()+"')");
                    }

                }
                else{
                    if(bookBean.getData().getBook()!=null){
                        database.execSQL("insert into usercase(user,name,image,author,readContent,readStatus,bookStatus,bookid,backlistPercent,lastTime,backlistId) " +
                                "values('visitor'," +
                                "'"+bookBean.getData().getBook().getName()+"'," +
                                "'"+bookBean.getData().getBook().getImage()+"'," +
                                "'"+bookBean.getData().getBook().getAuthor()+"'," +
                                "'"+listBean.getData().getList().get(0).getName()+"'," +
                                "10,+10,+'"+bookBean.getData().getBook().getBookId()+"'," +
                                "0,'',+'"+bookBean.getData().getBook().getBacklistNum()+"')");
                    }

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
    public void onSuccess(CaseBean caseBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("ceshi",(bookBean.getData()==null)+"");
                if(bookBean.getData()!=null){
                    for(int i=0;i<caseBean.getData().getList().size();i++){
                        Log.e("ceshi",bookBean.getData().getBook().getBookId()+"--"+caseBean.getData().getList().get(i).getBookId());
                        if((caseBean.getData().getList().get(i).getBookId()+"").equals(bookBean.getData().getBook().getBookId()+"")){
                            iscase=true;
                            break;
                        }
                        else{
                            iscase=false;
                        }
                    }
                }
                if(iscase){
                  jr.setText("已加入书架");
                  jr.setTextColor(Color.parseColor("#999999"));
                  icon.setImageResource(R.mipmap.xqy_jrsjon);
                }
                else{
                    jr.setText("加入书架");
                    jr.setTextColor(Color.parseColor("#2F3236"));
                    icon.setImageResource(R.mipmap.xqy_jrsj);
                }

            }
        });
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
        MobclickAgent.onPause(this); // 不能遗漏
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this); // 不能遗漏
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
        else if(type==7){
            Intent intent=new Intent(XqActivity.this,SsActivity.class);
            startActivity(intent);
        }
        else if(type==8){
            Intent intent=new Intent(XqActivity.this,MorePhbActivity.class);
            startActivity(intent);
        }
        else if(type==9){
            Intent intent=new Intent(XqActivity.this,LsActivity.class);
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
