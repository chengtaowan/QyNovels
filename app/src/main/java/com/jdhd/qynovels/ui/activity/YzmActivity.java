package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.jdhd.qynovels.module.personal.CaptchaBean;
import com.jdhd.qynovels.module.personal.TokenBean;
import com.jdhd.qynovels.persenter.impl.personal.ICaptchaPresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.ILoginPresenterImpl;
import com.jdhd.qynovels.ui.fragment.CaseFragment;
import com.jdhd.qynovels.ui.fragment.ShopFragment;
import com.jdhd.qynovels.utils.AndroidBug54971Workaround;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.jdhd.qynovels.view.personal.ICaptchaView;
import com.jdhd.qynovels.view.personal.ILoginView;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

public class YzmActivity extends AppCompatActivity implements View.OnClickListener , ICaptchaView , ILoginView {
    private ImageView gb;
    private TextView phone;
    private EditText yzm;
    private TextView again;
    private Button login;
    private ImageView wxlogin;
    private TextView xy,ys;
    private String tel;
    private int recLen=60;
    private int type;
    private Timer timer=new Timer();
    private ICaptchaPresenterImpl captchaPresenter;
    private ILoginPresenterImpl loginPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yzm);
        AndroidBug54971Workaround.assistActivity(findViewById(android.R.id.content),this);
        MyApp.addActivity(this);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        Intent intent=getIntent();
        tel=intent.getStringExtra("phone");
        type=intent.getIntExtra("type",0);
        init();
    }

    private void init() {
        gb=findViewById(R.id.dl_gb);
        phone=findViewById(R.id.phone);
        yzm=findViewById(R.id.yzm);
        again=findViewById(R.id.again);
        login=findViewById(R.id.login);
        wxlogin=findViewById(R.id.dl_wx);
        xy=findViewById(R.id.xy);
        ys=findViewById(R.id.ys);
        phone.setText(tel);
        again.setOnClickListener(this);
        login.setOnClickListener(this);
        wxlogin.setOnClickListener(this);
        xy.setOnClickListener(this);
        ys.setOnClickListener(this);
        gb.setOnClickListener(this);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    // UI thread
                    @Override
                    public void run() {
                        recLen--;
                        again.setClickable(false);
                        again.setText(recLen+"s");
                        if(recLen <=0){
                            again.setText("重新发送");
                            recLen=60;
                            again.setClickable(true);
                            timer.cancel();
                            timer=new Timer();
                        }
                    }
                });
            }
        };
        timer.schedule(task, 1000, 1000);

    }

    @Override
    public void onClick(View view) {
        if(R.id.again==view.getId()){
            captchaPresenter=new ICaptchaPresenterImpl(this,YzmActivity.this);
            captchaPresenter.setTel(tel);
            captchaPresenter.loadData();
        }
        else if(R.id.login==view.getId()){
            String y=yzm.getText().toString();
            if(y.equals("")){
              Toast.makeText(YzmActivity.this,"验证码不能为空",Toast.LENGTH_SHORT).show();
            }
            else{
                loginPresenter=new ILoginPresenterImpl(this,YzmActivity.this,tel,y);
                loginPresenter.loadData();
            }
        }
        else if(R.id.dl_wx==view.getId()){
            if(!MyApp.getApi().isWXAppInstalled()){
                Toast.makeText(YzmActivity.this,"请先安装微信客户端",Toast.LENGTH_SHORT).show();
                return;
            }
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test";
            MyApp.getApi().sendReq(req);
            finish();
        }
        else if(R.id.xy==view.getId()){
            Intent intent=new Intent(YzmActivity.this,XyActivity.class);
            intent.putExtra("title","用户协议");
            intent.putExtra("type",1);
            startActivity(intent);
        }
        else if(R.id.ys==view.getId()){
            Intent intent=new Intent(YzmActivity.this,XyActivity.class);
            intent.putExtra("title","隐私协议");
            intent.putExtra("type",2);
            startActivity(intent);
        }
        else if(R.id.dl_gb==view.getId()){
//            Intent intent=new Intent(YzmActivity.this,LoginActivity.class);
//            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onCaptchaSuccess(CaptchaBean captchaBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("yzm",captchaBean.getData().getCode()+"");

                if(captchaBean.getCode()==9008){
                    Toast toast = Toast.makeText(YzmActivity.this, captchaBean.getMsg(), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                }
                else if(captchaBean.getCode()==9007){
                    Toast toast = Toast.makeText(YzmActivity.this, captchaBean.getMsg(), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                }
                else if(captchaBean.getCode()==9002){
                    Toast toast = Toast.makeText(YzmActivity.this, captchaBean.getMsg(), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                }
                else if(captchaBean.getCode()==200){
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                // UI thread
                                @Override
                                public void run() {
                                    recLen--;
                                    again.setClickable(false);
                                    again.setText(recLen+"s");
                                    if(recLen <=0){
                                        again.setText("重新发送");
                                        recLen=60;
                                        again.setClickable(true);
                                        timer.cancel();
                                        timer=new Timer();
                                    }
                                }
                            });
                        }
                    };
                    timer.schedule(task, 1000, 1000);
                    Toast toast = Toast.makeText(YzmActivity.this, captchaBean.getMsg(), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else{
                    Toast toast = Toast.makeText(YzmActivity.this, captchaBean.getMsg(), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

                //yzm.setText(captchaBean.getData().getCode()+"");
            }
        });

    }

    @Override
    public void onCaptchaError(String error) {
        Log.e("captchaerror",error);
    }

    @Override
    public void onTokenSuccess(TokenBean tokenBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("isloginiii",tokenBean.getCode()+"--"+tokenBean.getMsg());
                if(tokenBean.getCode()==9007){
                    Toast toast = Toast.makeText(YzmActivity.this, tokenBean.getMsg(), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else if(tokenBean.getCode()==200){
                    SharedPreferences preferences=getSharedPreferences("token",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("token",tokenBean.getData().getToken());
                    editor.putString("login","success");
                    editor.putString("islogin",tokenBean.getData().getIs_login()+"");
                    editor.commit();
                    Log.e("islogin",tokenBean.getData().getIs_login()+"+++");
                    if(type==1){
                        Intent intent=new Intent(YzmActivity.this,MainActivity.class);
                        intent.putExtra("page", 2);
                        startActivity(intent);
                    }
                    else{
                        Intent intent=new Intent(YzmActivity.this,MainActivity.class);
                        intent.putExtra("page", 3);
                        intent.putExtra("action",0);
                        startActivity(intent);
                        EventBus.getDefault().post(tokenBean);
                    }

                    ShopFragment.lhb.setVisibility(View.GONE);
                    ShopFragment.closePopWindow();
                    CaseFragment.lhb.setVisibility(View.GONE);
                    CaseFragment.closePopWindow();
                }
                else{
                    Toast toast = Toast.makeText(YzmActivity.this, tokenBean.getMsg(), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
    }

    @Override
    public void onTokenError(String error) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
}
