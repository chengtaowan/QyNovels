package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.QdAdapter;
import com.jdhd.qynovels.module.personal.SignBean;
import com.jdhd.qynovels.module.personal.SignSetingBean;
import com.jdhd.qynovels.persenter.impl.personal.ISignPresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.ISignSetingPresenterImpl;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.jdhd.qynovels.view.personal.ISignSetingView;
import com.jdhd.qynovels.view.personal.ISignView;

import java.util.ArrayList;
import java.util.List;

public class QdActivity extends AppCompatActivity implements View.OnClickListener, ISignSetingView, ISignView {
    private LinearLayout qd;
    private TextView ljqd,cg,sp,tian;
    private ImageView back;
    private RecyclerView rv;
    private QdAdapter adapter;
    private TextView day1,day2,day3,day4,day5,day6,day7;
    private TextView jb1,jb2,jb3,jb4,jb5,jb6,jb7;
    private ImageView qd1,qd2,qd3,qd4,qd5,qd6,qd7;
    private List<TextView> daylist=new ArrayList<>();
    private List<TextView> jblist=new ArrayList<>();
    private List<ImageView> qdlist=new ArrayList<>();
    private ISignSetingPresenterImpl signSetingPresenter;
    private ISignPresenterImpl signPresenter;
    private SignSetingBean signSeting=new SignSetingBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qd);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        signSetingPresenter=new ISignSetingPresenterImpl(this,this);
        signSetingPresenter.loadData();
        signPresenter=new ISignPresenterImpl(this,this);
        init();
    }

    private void init() {
        tian=findViewById(R.id.qd_tian);
        qd=findViewById(R.id.qd_qd);
        qd.setOnClickListener(this);
        ljqd=findViewById(R.id.qd_ljqd);
        cg=findViewById(R.id.qd_cg);
        sp=findViewById(R.id.qd_sp);
        qd.setOnClickListener(this);
        back=findViewById(R.id.qd_back);
        back.setOnClickListener(this);
        rv=findViewById(R.id.qd_rv);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        rv.setLayoutManager(manager);
        adapter=new QdAdapter();
        rv.setAdapter(adapter);
        day1=findViewById(R.id.day1);
        day2=findViewById(R.id.day2);
        day3=findViewById(R.id.day3);
        day4=findViewById(R.id.day4);
        day5=findViewById(R.id.day5);
        day6=findViewById(R.id.day6);
        day7=findViewById(R.id.day7);
        jb1=findViewById(R.id.jb1);
        jb2=findViewById(R.id.jb2);
        jb3=findViewById(R.id.jb3);
        jb4=findViewById(R.id.jb4);
        jb5=findViewById(R.id.jb5);
        jb6=findViewById(R.id.jb6);
        jb7=findViewById(R.id.jb7);
        qd1=findViewById(R.id.qd1);
        qd2=findViewById(R.id.qd2);
        qd3=findViewById(R.id.qd3);
        qd4=findViewById(R.id.qd4);
        qd5=findViewById(R.id.qd5);
        qd6=findViewById(R.id.qd6);
        qd7=findViewById(R.id.qd7);
        daylist.add(day1);
        daylist.add(day2);
        daylist.add(day3);
        daylist.add(day4);
        daylist.add(day5);
        daylist.add(day6);
        daylist.add(day7);
        jblist.add(jb1);
        jblist.add(jb2);
        jblist.add(jb3);
        jblist.add(jb4);
        jblist.add(jb5);
        jblist.add(jb6);
        jblist.add(jb7);
        qdlist.add(qd1);
        qdlist.add(qd2);
        qdlist.add(qd3);
        qdlist.add(qd4);
        qdlist.add(qd5);
        qdlist.add(qd6);
        qdlist.add(qd7);
    }

    @Override
    public void onClick(View view) {
        if(R.id.qd_back==view.getId()){
            finish();
        }
        else{
            signPresenter.loadData();
            qd.setBackgroundResource(R.drawable.shape_qd_on);
            ljqd.setVisibility(View.GONE);
            cg.setVisibility(View.VISIBLE);
            sp.setVisibility(View.VISIBLE);

            for(int i=0;i<signSeting.getData().getSignData().size();i++){
                if(signSeting.getData().getSignData().get(i).getDate().equals("今天")){
                    qdlist.get(i).setImageResource(R.mipmap.qd_jb_wc);
                    jblist.get(i).setVisibility(View.GONE);
                }
            }
            Toast.makeText(QdActivity.this,"签到成功",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onSetingSuccess(SignSetingBean signSetingBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                signSeting=signSetingBean;
                adapter.refresh(signSetingBean.getData().getRule());
                tian.setText(signSetingBean.getData().getSignNum()+"");
                for(int i=0;i<signSetingBean.getData().getSignData().size();i++){
                    daylist.get(i).setText(signSetingBean.getData().getSignData().get(i).getDate());
                    jblist.get(i).setText(signSetingBean.getData().getSignData().get(i).getAward()+"");
                    if(signSetingBean.getData().getSignData().get(i).getDate().equals("今天")&&signSetingBean.getData().getSignData().get(i).getIs_sign()==0){
                        qdlist.get(i).setImageResource(R.mipmap.qd_jb);
                        qd.setBackgroundResource(R.drawable.shap_qd);
                        ljqd.setVisibility(View.VISIBLE);
                        cg.setVisibility(View.GONE);
                        sp.setVisibility(View.GONE);
                    }
                    else if(signSetingBean.getData().getSignData().get(i).getDate().equals("今天")&&signSetingBean.getData().getSignData().get(i).getIs_sign()==1){
                        qdlist.get(i).setImageResource(R.mipmap.qd_jb_wc);
                        qd.setBackgroundResource(R.drawable.shape_qd_on);
                        ljqd.setVisibility(View.GONE);
                        cg.setVisibility(View.VISIBLE);
                        sp.setVisibility(View.VISIBLE);
                        jblist.get(i).setVisibility(View.GONE);
                    }
                    else if(signSetingBean.getData().getSignData().get(i).getIs_sign()==0){
                        qdlist.get(i).setImageResource(R.mipmap.qd_jb_on);
                    }
                    else{
                        qdlist.get(i).setImageResource(R.mipmap.qd_jb_wc);
                        jblist.get(i).setVisibility(View.GONE);
                    }
                }

            }
        });
    }

    @Override
    public void onSetingError(String error) {
        Log.e("setingerror",error);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(signSetingPresenter!=null){
            signSetingPresenter.destoryView();
        }
        if(signPresenter!=null){
           signPresenter.destoryView();
        }
    }

    @Override
    public void onSignSuccess(SignBean signBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
               tian.setText(signBean.getData().getSign_num()+"");
            }
        });
    }

    @Override
    public void onSignError(String error) {
       Log.e("signerror",error);
    }
}
