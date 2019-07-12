package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.module.personal.NickNameBean;
import com.jdhd.qynovels.persenter.impl.personal.INickNamePresenterImpl;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.jdhd.qynovels.view.personal.INickNameView;

import org.greenrobot.eventbus.EventBus;

public class XgncActivity extends AppCompatActivity implements View.OnClickListener, INickNameView {
    private ImageView back,qc;
    private TextView bc;
    private EditText nc;
    private INickNamePresenterImpl nickNamePresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xgnc);
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
                Toast.makeText(XgncActivity.this,"昵称不能为空",Toast.LENGTH_SHORT).show();
            }
            else{
                nickNamePresenter=new INickNamePresenterImpl(this,XgncActivity.this,nc.getText().toString());
                nickNamePresenter.loadData();
            }

        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onNickNameSuccess(NickNameBean nickNameBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(nickNameBean.getData().getNickname());
                Intent intent=new Intent(XgncActivity.this,GrzlActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onNickNameError(String error) {
        Log.e("nicknameerror",error);
    }
}
