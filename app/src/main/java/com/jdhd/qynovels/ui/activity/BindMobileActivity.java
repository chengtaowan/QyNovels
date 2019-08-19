package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.BindTelBean;
import com.jdhd.qynovels.module.personal.CaptchaBean;
import com.jdhd.qynovels.persenter.impl.personal.IBindTelPresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.ICaptchaPresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.ILoginPresenterImpl;
import com.jdhd.qynovels.utils.AndroidBug54971Workaround;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.jdhd.qynovels.view.personal.IBindTelView;
import com.jdhd.qynovels.view.personal.ICaptchaView;
import com.umeng.analytics.MobclickAgent;

import java.util.Timer;
import java.util.TimerTask;

public class BindMobileActivity extends AppCompatActivity implements View.OnClickListener, ICaptchaView, IBindTelView {
    private ImageView close;
    private EditText phone,yzm;
    private TextView yy;
    private Button bd;
    private int recLen = 59;
    Timer timer = new Timer();
    private ICaptchaPresenterImpl captchaPresenter;
    private IBindTelPresenterImpl bindTelPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_mobile);
        AndroidBug54971Workaround.assistActivity(findViewById(android.R.id.content),this);

        MyApp.addActivity(this);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        init();
    }

    private void init() {
        close=findViewById(R.id.bd_gb);
        phone=findViewById(R.id.dl_phone);
        yzm=findViewById(R.id.dl_yzm);
        yy=findViewById(R.id.dl_yy);
        bd=findViewById(R.id.dl_but);
        yy.setOnClickListener(this);
        bd.setOnClickListener(this);
        close.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(R.id.bd_gb==view.getId()){
            finish();
        }
        else if(R.id.dl_yy==view.getId()){
            String tel=phone.getText().toString();
            if(tel.equals("")){
                Toast.makeText(BindMobileActivity.this,"请输入手机号",Toast.LENGTH_SHORT).show();
            }
            else{
                captchaPresenter=new ICaptchaPresenterImpl(this,BindMobileActivity.this);
                captchaPresenter.setTel(tel);
                captchaPresenter.loadData();
            }
        }
        else if(R.id.dl_but==view.getId()){
            String tel=phone.getText().toString();
            String y=yzm.getText().toString();
            if(tel.equals("")||y.equals("")){
                Toast.makeText(BindMobileActivity.this,"手机号或验证码为空",Toast.LENGTH_SHORT).show();
            }
            else{
                bindTelPresenter=new IBindTelPresenterImpl(this,BindMobileActivity.this,tel,y);
                bindTelPresenter.loadData();
            }
        }
    }

    @Override
    public void onCaptchaSuccess(CaptchaBean captchaBean) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("yzm",captchaBean.getData().getCode()+"");
                //yy.setClickable(false);
                if(captchaBean.getCode()==9008){
                    Toast toast = Toast.makeText(BindMobileActivity.this, captchaBean.getMsg(), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    yy.setText("获取语音验证码");
                }
                else if(captchaBean.getCode()==9007){
                    Toast toast = Toast.makeText(BindMobileActivity.this, captchaBean.getMsg(), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    yy.setText("获取语音验证码");
                }
                else if(captchaBean.getCode()==9002){
                    Toast toast = Toast.makeText(BindMobileActivity.this, captchaBean.getMsg(), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    yy.setText("获取语音验证码");
                }
                else{
                    yy.setClickable(false);
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                // UI thread
                                @Override
                                public void run() {
                                    recLen--;
                                    yy.setText("已发送  "+recLen+"s");
                                    if(recLen <=0){
                                        yy.setText("获取语音验证码");
                                        yy.setClickable(true);
                                        recLen=60;
                                        timer.cancel();
                                        timer=new Timer();
                                    }
                                }
                            });
                        }
                    };
                    timer.schedule(task, 1000, 1000);
                }

                //yzm.setText(captchaBean.getData().getCode()+"");
            }
        });
    }

    @Override
    public void onCaptchaError(String error) {
        Log.e("bindyzmerror",error);

    }

    @Override
    public void onBindtelSuccess(BindTelBean bindTelBean) {
       runOnUiThread(new Runnable() {
           @Override
           public void run() {
               if(bindTelBean.getCode()!=200){
                   Toast toast = Toast.makeText(BindMobileActivity.this, bindTelBean.getMsg(), Toast.LENGTH_LONG);
                   toast.setGravity(Gravity.CENTER, 0, 0);
                   toast.show();
                   timer.cancel();
                   timer=new Timer();
                   yy.setText("获取语音验证码");
                   yy.setClickable(true);
               }
               else{
                   Intent intent=new Intent(BindMobileActivity.this,ZhglActivity.class);
                   intent.putExtra("mobile",bindTelBean.getData().getMobile());
                   intent.setAction("bindtel");
                   startActivity(intent);
                   Toast.makeText(BindMobileActivity.this,"绑定成功",Toast.LENGTH_SHORT).show();
               }

           }
       });
    }

    @Override
    public void onBindtelError(String error) {
      Log.e("bindtelerror",error);
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
