package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.CaptchaBean;
import com.jdhd.qynovels.module.personal.TokenBean;
import com.jdhd.qynovels.module.personal.UserBean;
import com.jdhd.qynovels.persenter.impl.personal.ICaptchaPresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.ILoginPresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.IPersonalPresenterImpl;
import com.jdhd.qynovels.ui.fragment.WodeFragment;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.jdhd.qynovels.view.personal.ICaptchaView;
import com.jdhd.qynovels.view.personal.ILoginView;
import com.jdhd.qynovels.view.personal.IPersonalView;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener , ICaptchaView, ILoginView {
    private ImageView gb,wx;
    private EditText phone,yzm;
    private TextView yy;
    private Button but;
    private ICaptchaPresenterImpl captchaPresenter;
    private ILoginPresenterImpl loginPresenter;
    private int recLen = 59;
    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        init();
    }

    private void init() {
        gb=findViewById(R.id.dl_gb);
        wx=findViewById(R.id.dl_wx);
        phone=findViewById(R.id.dl_phone);
        yzm=findViewById(R.id.dl_yzm);
        yy=findViewById(R.id.dl_yy);
        but=findViewById(R.id.dl_but);
        but.setOnClickListener(this);
        gb.setOnClickListener(this);
        wx.setOnClickListener(this);
        yy.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(R.id.dl_gb==view.getId()){
           finish();
        }
        else if(R.id.dl_wx==view.getId()){
            if(!MyApp.getApi().isWXAppInstalled()){
                Toast.makeText(LoginActivity.this,"请先安装微信客户端",Toast.LENGTH_SHORT).show();
                return;
            }
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test";
            MyApp.getApi().sendReq(req);
            finish();
        }
        else if(R.id.dl_yy==view.getId()){
            String tel=phone.getText().toString();
            captchaPresenter=new ICaptchaPresenterImpl(this,LoginActivity.this,tel);
            captchaPresenter.loadData();
        }
        else if(R.id.dl_but==view.getId()){
            String tel=phone.getText().toString();
            String y=yzm.getText().toString();
            loginPresenter=new ILoginPresenterImpl(this,this,tel,y);
            loginPresenter.loadData();

        }
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onCaptchaSuccess(CaptchaBean captchaBean) {
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
                        }
                    }
                });
            }
        };
        timer.schedule(task, 1000, 1000);
        yzm.setText(captchaBean.getData().getCode()+"");
    }

    @Override
    public void onCaptchaError(String error) {
        Log.e("yzmerror",error);
    }


    @Override
    public void onTokenSuccess(TokenBean tokenBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences=getSharedPreferences("token",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("token",tokenBean.getData().getToken());
                editor.putString("login","success");
                editor.commit();
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("fragment_flag", 4);
                intent.putExtra("action",0);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onTokenError(String error) {
        Log.e("tokenerror",error);
    }
}
