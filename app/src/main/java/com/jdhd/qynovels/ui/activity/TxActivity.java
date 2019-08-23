package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.TxAdapter;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.DrawSetBean;
import com.jdhd.qynovels.persenter.impl.personal.IDrawSetPresenterImpl;
import com.jdhd.qynovels.utils.AndroidBug54971Workaround;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.jdhd.qynovels.view.personal.IDrawSetView;
import com.jdhd.qynovels.widget.CustomPopWindow;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class TxActivity extends AppCompatActivity implements View.OnClickListener, IDrawSetView {
    private LinearLayout tx10,tx15,tx20,tx25,tx30,tx50;
    private ImageView back,img;
    private TextView txjl,txxj10,txxj15,txxj20,txxj25,txxj30,txxj50,txjb10,txjb15,txjb20,txjb25,txjb30,txjb50;
    private List<LinearLayout> linearLayouts=new ArrayList<>();
    private TextView ye,money;
    private List<TextView> xjlist=new ArrayList<>();
    private List<TextView> jblist=new ArrayList<>();
    private String yue,wxname;
    private float mone;
    private RecyclerView rv;
    private TxAdapter adapter;
    private int totle,time;
    private IDrawSetPresenterImpl drawSetPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tx);
        AndroidBug54971Workaround.assistActivity(findViewById(android.R.id.content),this);

        MyApp.addActivity(this);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        Intent intent=getIntent();
        time=intent.getIntExtra("time",0);
        yue=intent.getStringExtra("jb");
        mone=intent.getFloatExtra("money",0f);
        wxname=intent.getStringExtra("wxname");
        totle=intent.getIntExtra("totle",0);
        Log.e("totle",totle+"");
        drawSetPresenter=new IDrawSetPresenterImpl(this,this);
        drawSetPresenter.loadData();
        init();
    }

    private void init() {
        rv=findViewById(R.id.tx_rv);
        ye=findViewById(R.id.ye);
        money=findViewById(R.id.money);
        ye.setText(yue);
        money.setText("约"+mone+"元");
        tx10=findViewById(R.id.tx10);
        tx15=findViewById(R.id.tx15);
        tx20=findViewById(R.id.tx20);
        tx25=findViewById(R.id.tx25);
        tx30=findViewById(R.id.tx30);
        tx50=findViewById(R.id.tx50);
        back=findViewById(R.id.tx_back);
        txjl=findViewById(R.id.txjl);
        img=findViewById(R.id.img);
        back.setOnClickListener(this);
        txjl.setOnClickListener(this);
        txxj10=findViewById(R.id.txxj10);
        txxj15=findViewById(R.id.txxj15);
        txxj20=findViewById(R.id.txxj20);
        txxj25=findViewById(R.id.txxj25);
        txxj30=findViewById(R.id.txxj30);
        txxj50=findViewById(R.id.txxj50);
        txjb10=findViewById(R.id.txjb10);
        txjb15=findViewById(R.id.txjb15);
        txjb20=findViewById(R.id.txjb20);
        txjb25=findViewById(R.id.txjb25);
        txjb30=findViewById(R.id.txjb30);
        txjb50=findViewById(R.id.txjb50);
        linearLayouts.add(tx10);
        linearLayouts.add(tx15);
        linearLayouts.add(tx20);
        linearLayouts.add(tx25);
        linearLayouts.add(tx30);
        linearLayouts.add(tx50);
        xjlist.add(txxj10);
        xjlist.add(txxj15);
        xjlist.add(txxj20);
        xjlist.add(txxj25);
        xjlist.add(txxj30);
        xjlist.add(txxj50);
        jblist.add(txjb10);
        jblist.add(txjb15);
        jblist.add(txjb20);
        jblist.add(txjb25);
        jblist.add(txjb30);
        jblist.add(txjb50);
        tx10.setOnClickListener(this);
        tx15.setOnClickListener(this);
        tx20.setOnClickListener(this);
        tx25.setOnClickListener(this);
        tx30.setOnClickListener(this);
        tx50.setOnClickListener(this);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        rv.setLayoutManager(manager);
        adapter=new TxAdapter();
        rv.setAdapter(adapter);

    }
    @Override
    public void onSuccess(DrawSetBean drawSetBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
              adapter.refresh(drawSetBean.getData().getRule());
              for(int i=0;i<drawSetBean.getData().getSetting().size();i++){
                  xjlist.get(i).setText("提现"+drawSetBean.getData().getSetting().get(i).getMoney()+"元");
                  jblist.get(i).setText("需"+drawSetBean.getData().getSetting().get(i).getGold()+"金币");
              }
            }
        });
    }

    @Override
    public void onError(String error) {
       Log.e("txerror",error);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        drawSetPresenter.destoryView();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent=new Intent(TxActivity.this,MainActivity.class);
//        intent.putExtra("page", 3);
//        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this); // 不能遗漏
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this); // 不能遗漏
    }

    @Override
    public void onClick(View view) {
        if(R.id.tx_back==view.getId()){
//            Intent intent=new Intent(TxActivity.this,MainActivity.class);
//            intent.putExtra("page",3);
//            startActivity(intent);
            finish();
        }
        else if(R.id.txjl==view.getId()){
            Intent intent=new Intent(TxActivity.this,TxjlActivity.class);
            startActivity(intent);
        }
        else if(R.id.tx10==view.getId()){
            showPopWindow(img,10,100000);
            for(int i=0;i<linearLayouts.size();i++){
                if(i==0){
                   linearLayouts.get(i).setBackgroundResource(R.drawable.shape_txx);
                   xjlist.get(i).setTextColor(Color.parseColor("#EF8E44"));
                   jblist.get(i).setTextColor(Color.parseColor("#EF8E44"));
                }
                else{
                    linearLayouts.get(i).setBackgroundResource(R.drawable.shape_txz);
                    xjlist.get(i).setTextColor(Color.parseColor("#2F3236"));
                    jblist.get(i).setTextColor(Color.parseColor("#828486"));
                }
            }

        }
        else if(R.id.tx15==view.getId()){
            showPopWindow(img,15,150000);
            for(int i=0;i<linearLayouts.size();i++){
                if(i==1){
                    linearLayouts.get(i).setBackgroundResource(R.drawable.shape_txx);
                    xjlist.get(i).setTextColor(Color.parseColor("#EF8E44"));
                    jblist.get(i).setTextColor(Color.parseColor("#EF8E44"));
                }
                else{
                    linearLayouts.get(i).setBackgroundResource(R.drawable.shape_txz);
                    xjlist.get(i).setTextColor(Color.parseColor("#2F3236"));
                    jblist.get(i).setTextColor(Color.parseColor("#828486"));
                }
            }
        }
        else if(R.id.tx20==view.getId()){
            showPopWindow(img,20,200000);
            for(int i=0;i<linearLayouts.size();i++){
                if(i==2){
                    linearLayouts.get(i).setBackgroundResource(R.drawable.shape_txx);
                    xjlist.get(i).setTextColor(Color.parseColor("#EF8E44"));
                    jblist.get(i).setTextColor(Color.parseColor("#EF8E44"));
                }
                else{
                    linearLayouts.get(i).setBackgroundResource(R.drawable.shape_txz);
                    xjlist.get(i).setTextColor(Color.parseColor("#2F3236"));
                    jblist.get(i).setTextColor(Color.parseColor("#828486"));
                }
            }
        }
        else if(R.id.tx25==view.getId()){
            showPopWindow(img,25,250000);
            for(int i=0;i<linearLayouts.size();i++){
                if(i==3){
                    linearLayouts.get(i).setBackgroundResource(R.drawable.shape_txx);
                    xjlist.get(i).setTextColor(Color.parseColor("#EF8E44"));
                    jblist.get(i).setTextColor(Color.parseColor("#EF8E44"));
                }
                else{
                    linearLayouts.get(i).setBackgroundResource(R.drawable.shape_txz);
                    xjlist.get(i).setTextColor(Color.parseColor("#2F3236"));
                    jblist.get(i).setTextColor(Color.parseColor("#828486"));
                }
            }
        }
        else if(R.id.tx30==view.getId()){
            showPopWindow(img,30,300000);
            for(int i=0;i<linearLayouts.size();i++){
                if(i==4){
                    linearLayouts.get(i).setBackgroundResource(R.drawable.shape_txx);
                    xjlist.get(i).setTextColor(Color.parseColor("#EF8E44"));
                    jblist.get(i).setTextColor(Color.parseColor("#EF8E44"));
                }
                else{
                    linearLayouts.get(i).setBackgroundResource(R.drawable.shape_txz);
                    xjlist.get(i).setTextColor(Color.parseColor("#2F3236"));
                    jblist.get(i).setTextColor(Color.parseColor("#828486"));
                }
            }
        }
        else if(R.id.tx50==view.getId()){
            showPopWindow(img,50,500000);
            for(int i=0;i<linearLayouts.size();i++){
                if(i==5){
                    linearLayouts.get(i).setBackgroundResource(R.drawable.shape_txx);
                    xjlist.get(i).setTextColor(Color.parseColor("#EF8E44"));
                    jblist.get(i).setTextColor(Color.parseColor("#EF8E44"));
                }
                else{
                    linearLayouts.get(i).setBackgroundResource(R.drawable.shape_txz);
                    xjlist.get(i).setTextColor(Color.parseColor("#2F3236"));
                    jblist.get(i).setTextColor(Color.parseColor("#828486"));
                }
            }
        }

    }
    private View.OnClickListener itemclick=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.e("asd","1111");
            LinearLayout ks=view.findViewById(R.id.pop_ks);
            LinearLayout pt=view.findViewById(R.id.pop_pt);
            TextView ksdz=view.findViewById(R.id.ksdz);
            TextView kxsp=view.findViewById(R.id.kxsp);
            TextView ptdz=view.findViewById(R.id.ptdz);
            TextView gzdz=view.findViewById(R.id.gzdz);
           if(R.id.pop_ks==view.getId()){
               //showPopWindow(view,);
           }
           else if(R.id.pop_pt==view.getId()){
               //showPopWindow(view);
               ks.setBackgroundResource(R.drawable.shape_pt);
               ksdz.setTextColor(Color.parseColor("#E8564E"));
               kxsp.setTextColor(Color.parseColor("#E8564E"));
               pt.setBackgroundResource(R.drawable.shape_ks);
               ptdz.setTextColor(Color.parseColor("#FFFFFF"));
               gzdz.setTextColor(Color.parseColor("#FFFFFF"));
           }
        }
    };
    private void showPopWindow(View v,int num,int gold){
        final CustomPopWindow customPopWindow=new CustomPopWindow(TxActivity.this,itemclick,wxname,num,gold,totle,time);
        customPopWindow.showAtLocation(v,
                Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
        customPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                customPopWindow.backgroundAlpha(TxActivity.this, 1f);
            }
        });

    }



}
