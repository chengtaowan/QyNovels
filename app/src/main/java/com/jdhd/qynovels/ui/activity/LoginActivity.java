package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView gb,wx;
    private EditText phone,yzm;
    private TextView yy;
    private Button but;
    // APP_ID 替换为你的应用从官方网站申请到的合法appID
    private static final String APP_ID = "wxf2f9d368f73b6719";

    // IWXAPI 是第三方app和微信通信的openApi接口
    private IWXAPI api;
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
    private void regToWx() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, APP_ID, true);

        // 将应用的appId注册到微信
        api.registerApp(APP_ID);

//        //建议动态监听微信启动广播进行注册到微信
//        registerReceiver(new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                // 将该app注册到微信
//                api.registerApp(SyncStateContract.Constants._ID);
//            }
//        }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));

    }

    @Override
    public void onClick(View view) {
        if(R.id.dl_gb==view.getId()){
           finish();
        }
        else if(R.id.dl_wx==view.getId()){
            regToWx();
            if(!MyApp.getApi().isWXAppInstalled()){
                Toast.makeText(LoginActivity.this,"请先安装微信客户端",Toast.LENGTH_SHORT).show();
                return;
            }
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test";
            api.sendReq(req);

        }
        else if(R.id.dl_yy==view.getId()){

        }
        else if(R.id.dl_but==view.getId()){
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            intent.putExtra("fragment_flag", 4);
            intent.putExtra("action",0);
            startActivity(intent);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

}
