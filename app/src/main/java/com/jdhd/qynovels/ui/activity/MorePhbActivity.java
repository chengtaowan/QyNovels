package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.MoreAdapter;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.bookshop.ClassContentBean;
import com.jdhd.qynovels.persenter.impl.bookshop.IClassContentPresenterImpl;
import com.jdhd.qynovels.utils.AndroidBug54971Workaround;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.jdhd.qynovels.view.bookshop.IClassContentView;
import com.umeng.analytics.MobclickAgent;

public class MorePhbActivity extends AppCompatActivity implements View.OnClickListener ,RadioGroup.OnCheckedChangeListener, IClassContentView {
    private ImageView back,search;
    private RadioGroup rg,rg1;
    private RadioButton rd,gx,qb,lz,wj;
    private TextView type;
    private int ptype;
    private String title;
    private RecyclerView rv;
    private int page=1;
    private int searchType=0;
    private int sortType=10;
    private int id;
    private MoreAdapter adapter;
    private IClassContentPresenterImpl classContentPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_phb);
        AndroidBug54971Workaround.assistActivity(findViewById(android.R.id.content),this);


        MyApp.addActivity(this);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        Intent intent=getIntent();
        ptype=intent.getIntExtra("more",1);
        title=intent.getStringExtra("title");
        id=intent.getIntExtra("id",1);
        classContentPresenter=new IClassContentPresenterImpl(this,this);
        classContentPresenter.setPage(page);
        classContentPresenter.setSearchType(searchType);
        classContentPresenter.setSortType(sortType);
        classContentPresenter.setId(id);
        classContentPresenter.loadData();
        init();
    }

    private void init() {
        back=findViewById(R.id.more_back);
        search=findViewById(R.id.more_ss);
        back.setOnClickListener(this);
        search.setOnClickListener(this);
        rg=findViewById(R.id.more_rg);
        rd=findViewById(R.id.more_rd);
        gx=findViewById(R.id.more_gx);
        type=findViewById(R.id.more_type);
        rg1=findViewById(R.id.more_rg1);
        qb=findViewById(R.id.more_qb);
        lz=findViewById(R.id.more_lz);
        wj=findViewById(R.id.more_wj);
        rv=findViewById(R.id.more_rv);
        rd.setChecked(true);
        qb.setChecked(true);
        type.setText(title);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        rv.setLayoutManager(manager);
        adapter=new MoreAdapter(this,1);
        rv.setAdapter(adapter);
        rg.setOnCheckedChangeListener(this);
        rg1.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        if(R.id.more_ss==view.getId()){
            Intent intent=new Intent(MorePhbActivity.this,SsActivity.class);
            startActivity(intent);
        }
        else if(R.id.more_back==view.getId()){
            if(ptype==1){
                Intent intent=new Intent(MorePhbActivity.this,FlActivity.class);
                startActivity(intent);
            }
            else if(ptype==2){
                Intent intent=new Intent(MorePhbActivity.this,WjjpActivity.class);
                startActivity(intent);
            }

        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if(radioGroup.getCheckedRadioButtonId()==R.id.more_rd){
           sortType=10;
            classContentPresenter.setPage(page);
            classContentPresenter.setSearchType(searchType);
            classContentPresenter.setSortType(sortType);
            classContentPresenter.setId(id);
            classContentPresenter.loadData();
        }
        else if(radioGroup.getCheckedRadioButtonId()==R.id.more_gx){
           sortType=20;
            classContentPresenter.setPage(page);
            classContentPresenter.setSearchType(searchType);
            classContentPresenter.setSortType(sortType);
            classContentPresenter.setId(id);
            classContentPresenter.loadData();
        }
        else if(radioGroup.getCheckedRadioButtonId()==R.id.more_qb){
            searchType=0;
            classContentPresenter.setPage(page);
            classContentPresenter.setSearchType(searchType);
            classContentPresenter.setSortType(sortType);
            classContentPresenter.setId(id);
            classContentPresenter.loadData();
        }
        else if(radioGroup.getCheckedRadioButtonId()==R.id.more_lz){
            searchType=10;
            classContentPresenter.setPage(page);
            classContentPresenter.setSearchType(searchType);
            classContentPresenter.setSortType(sortType);
            classContentPresenter.setId(id);
            classContentPresenter.loadData();
        }
        else if(radioGroup.getCheckedRadioButtonId()==R.id.more_wj){
            searchType=20;
            classContentPresenter.setPage(page);
            classContentPresenter.setSearchType(searchType);
            classContentPresenter.setSortType(sortType);
            classContentPresenter.setId(id);
            classContentPresenter.loadData();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(ptype==1){
            Intent intent=new Intent(MorePhbActivity.this,FlActivity.class);
            startActivity(intent);
        }
        else if(ptype==2){
            Intent intent=new Intent(MorePhbActivity.this,WjjpActivity.class);
            startActivity(intent);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(classContentPresenter!=null){
           classContentPresenter.destoryView();
        }
    }

    @Override
    public void onSuccess(ClassContentBean classContentBean) {
       runOnUiThread(new Runnable() {
           @Override
           public void run() {
              adapter.refresh(classContentBean.getData().getList());
           }
       });
    }

    @Override
    public void onError(String error) {
       Log.e("moreerror",error);
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
}
