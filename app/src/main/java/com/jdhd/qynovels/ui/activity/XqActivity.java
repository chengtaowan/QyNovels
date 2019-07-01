package com.jdhd.qynovels.ui.activity;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.XqAdapter;
import com.jdhd.qynovels.ui.fragment.JxFragment;
import com.jdhd.qynovels.utils.FastBlurUtil;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.jdhd.qynovels.widget.PersonalScrollView;
import java.util.ArrayList;
import java.util.List;


public class XqActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout ll;
    private ImageView bj,back;
    private List<String> list=new ArrayList<>();
    private String url="http://www.qubaobei.com//ios//cf//uploadfile//132//3//2127.jpg";
    private ScrollView sv;
    private TextView num;
    private RecyclerView rv;
    private int type;
    private int sc_type;
    private  int lastOffset;//距离

    private  int lastPosition;//第几个item

    private  SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xq);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        init();
        Intent intent=getIntent();
        type=intent.getIntExtra("xq",1);
        sc_type=intent.getIntExtra("lx",1);
    }


    private void init() {
//        finishtype=findViewById(R.id.xq_finishtype);
//        finishtype.getBackground().setAlpha(51);
        back=findViewById(R.id.xq_back);
        back.setOnClickListener(this);
        ll=findViewById(R.id.xq_ll);
        bj=findViewById(R.id.xq_bj);
        sv=findViewById(R.id.xq_sv);
        num=findViewById(R.id.xq_num);
        rv=findViewById(R.id.xq_rv);
//        sv.setImageView(bj);
//        sv.setLine_up(ll);
        num.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/Oswald-Bold.otf"));
        LinearLayoutManager manager=new LinearLayoutManager(this);
        rv.setLayoutManager(manager);
        XqAdapter adapter=new XqAdapter(this);
        rv.setAdapter(adapter);
        //new MyThread().start();
        Resources res = getResources();
        Bitmap scaledBitmap = BitmapFactory.decodeResource(res, R.mipmap.a);

        //        scaledBitmap为目标图像，10是缩放的倍数（越大模糊效果越高）
//        Bitmap blurBitmap = FastBlurUtil.toBlur(scaledBitmap, 2);
//        bj.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        bj.setImageBitmap(blurBitmap);
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
        if(R.id.xq_back==view.getId()){
           change();
        }

    }

    class MyThread extends Thread{
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
    public void onBackPressed() {
        super.onBackPressed();
        //change();
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
}
