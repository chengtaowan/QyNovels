package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.BindCodeBean;
import com.jdhd.qynovels.persenter.impl.personal.IBindCodePresenterImpl;
import com.jdhd.qynovels.utils.AndroidBug54971Workaround;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.jdhd.qynovels.view.personal.IBindCodeView;
import com.umeng.analytics.MobclickAgent;

public class BindCodeActivity extends AppCompatActivity implements View.OnClickListener, IBindCodeView {
    private ImageView back,qc;
    private TextView bc;
    private EditText nc;
    private IBindCodePresenterImpl bindCodePresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_code);
        AndroidBug54971Workaround.assistActivity(findViewById(android.R.id.content),this);

        MyApp.addActivity(this);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        init();
    }

    private void init() {
        back=findViewById(R.id.zl_back);
        qc=findViewById(R.id.qc);
        bc=findViewById(R.id.zl_bc);
        nc=findViewById(R.id.nc);
        back.setOnClickListener(this);
        qc.setOnClickListener(this);
        bc.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(R.id.zl_back==view.getId()){
            finish();
        }
        else if(R.id.qc==view.getId()){
            nc.clearComposingText();
        }
        else if(R.id.zl_bc==view.getId()){
            if(nc.getText().length()==0){
                Toast.makeText(BindCodeActivity.this,"红包码不能为空",Toast.LENGTH_SHORT).show();
            }
            else{
               bindCodePresenter=new IBindCodePresenterImpl(this,this);
               bindCodePresenter.setRed_code(nc.getText().toString().toUpperCase());
               bindCodePresenter.loadData();
            }

        }
    }

    @Override
    public void onAvatarSuccess(BindCodeBean bindCodeBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(bindCodeBean.getCode()!=200){
                    Toast.makeText(BindCodeActivity.this,bindCodeBean.getMsg(),Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(BindCodeActivity.this,bindCodeBean.getMsg(),Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });
    }

    @Override
    public void onAvatarError(String error) {
        Log.e("bindcodeerror",error);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(bindCodePresenter!=null){
            bindCodePresenter.destoryView();
        }
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
