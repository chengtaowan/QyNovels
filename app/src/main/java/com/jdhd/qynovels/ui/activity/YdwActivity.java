package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.GfAdapter;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.bookcase.BookGradeBean;
import com.jdhd.qynovels.module.bookcase.ReadEndBean;
import com.jdhd.qynovels.persenter.impl.bookcase.IBookGradePresenterImpl;
import com.jdhd.qynovels.persenter.impl.bookcase.IReadEndPresenterImpl;
import com.jdhd.qynovels.utils.AndroidBug54971Workaround;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.jdhd.qynovels.view.bookcase.IBookGradeView;
import com.jdhd.qynovels.view.bookcase.IReadEndView;
import com.jdhd.qynovels.widget.RatingBar;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class YdwActivity extends AppCompatActivity implements View.OnClickListener, IReadEndView, IBookGradeView {
    private RatingBar ratingBar;
    private ImageView close;
    private TextView hyh,readnum;
    private RecyclerView rv;
    private Button share,goshop;
    private int id;
    private IReadEndPresenterImpl readEndPresenter;
    private List<ReadEndBean.DataBean.ListBean> list=new ArrayList<>();
    private ReadEndBean readEnd=new ReadEndBean();
    private GfAdapter adapter;
    private IBookGradePresenterImpl bookGradePresenter;
    private String url="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ydw);
        AndroidBug54971Workaround.assistActivity(findViewById(android.R.id.content),this);

        MyApp.addActivity(this);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        Intent intent = getIntent();
        id=intent.getIntExtra("id",0);
        Log.e("id",id+"----");
        readEndPresenter=new IReadEndPresenterImpl(this,this);
        readEndPresenter.setId(id);
        readEndPresenter.loadData();
        bookGradePresenter=new IBookGradePresenterImpl(this,this);
        init();
    }

    private void init() {
        readnum=findViewById(R.id.readnum);
        ratingBar=findViewById(R.id.ydw_start);
        close=findViewById(R.id.ydw_close);
        share=findViewById(R.id.ydw_share);
        goshop=findViewById(R.id.ydw_goshop);
        hyh=findViewById(R.id.ydw_hyh);
        rv=findViewById(R.id.ydw_rv);
        close.setOnClickListener(this);
        share.setOnClickListener(this);
        goshop.setOnClickListener(this);
        hyh.setOnClickListener(this);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        rv.setLayoutManager(manager);
        adapter=new GfAdapter(this,0,2);
        rv.setAdapter(adapter);
        ratingBar.setmOnStarChangeListener(new RatingBar.OnStarChangeListener() {
            @Override
            public void OnStarChanged(float selectedNumber, int position) {
                Log.e("number",selectedNumber+"");
                bookGradePresenter.setId(id);
                bookGradePresenter.setGrade((int) (selectedNumber*2));
                bookGradePresenter.loadData();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(R.id.ydw_close==view.getId()){
            Intent intent=new Intent(YdwActivity.this,MainActivity.class);
            intent.putExtra("page", 1);
            startActivity(intent);
        }
        else if(R.id.ydw_hyh==view.getId()){
            list.clear();
            int[] ints = randomCommon(0, readEnd.getData().getList().size() - 1, 3);
            for(int i=0;i<ints.length;i++){
                list.add(readEnd.getData().getList().get(ints[i]));
            }
            adapter.refreshread(list);
        }
        else if(R.id.ydw_share==view.getId()){
            SharedPreferences preferences=getSharedPreferences("token", Context.MODE_PRIVATE);
            String token = preferences.getString("token", "");
            if(!token.equals("")){
                Intent intent=new Intent(YdwActivity.this,ShareActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("url",url);
                startActivity(intent);
            }else{
                Toast.makeText(YdwActivity.this,"登录后才能分享哦！",Toast.LENGTH_SHORT).show();
            }

        }
        else if(R.id.ydw_goshop==view.getId()){
            Intent intent=new Intent(YdwActivity.this,MainActivity.class);
            intent.putExtra("page", 1);
            startActivity(intent);
        }
    }

    @Override
    public void onEndSuccess(ReadEndBean readEndBean) {
       runOnUiThread(new Runnable() {
           @Override
           public void run() {
               if(readEndBean.getCode()!=200){
                   Toast.makeText(YdwActivity.this,readEndBean.getMsg(),Toast.LENGTH_SHORT).show();
               }
               else{
                   readEnd=readEndBean;
                   readnum.setText(readEndBean.getData().getReadEndNum()+"");
                   int[] ints = randomCommon(0, readEndBean.getData().getList().size() - 1, 3);
                   for(int i=0;i<ints.length;i++){
                       list.add(readEndBean.getData().getList().get(ints[i]));
                   }
                   adapter.refreshread(list);
                   url=readEndBean.getData().getQRcodeUrl();
               }

           }
       });
    }

    @Override
    public void onEndError(String error) {
       Log.e("readerror",error);
    }

    /**
     * 随机指定范围内N个不重复的数
     * 最简单最基本的方法
     * @param min 指定范围最小值
     * @param max 指定范围最大值
     * @param n 随机数个数
     */
    public static int[] randomCommon(int min, int max, int n){
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while(count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if(num == result[j]){
                    flag = false;
                    break;
                }
            }
            if(flag){
                result[count] = num;
                count++;
            }
        }
        return result;
    }

    @Override
    public void onGradeSuccess(BookGradeBean bookGradeBean) {
       runOnUiThread(new Runnable() {
           @Override
           public void run() {
               if(bookGradeBean.getCode()==200){
                   Toast.makeText(YdwActivity.this,"感谢您的评分",Toast.LENGTH_SHORT).show();

               }
               else{
                   Toast.makeText(YdwActivity.this,bookGradeBean.getMsg(),Toast.LENGTH_SHORT).show();
               }
           }
       });
    }

    @Override
    public void onGradeError(String error) {
       Log.e("gradeerror",error);
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
}
