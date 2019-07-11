package com.jdhd.qynovels.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.SyncStateContract;
import android.transition.Fade;
import android.util.Log;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.module.personal.RefreshTokenBean;
import com.jdhd.qynovels.persenter.impl.personal.IRefreshTokenPresenterImpl;
import com.jdhd.qynovels.view.personal.IRefreshTokenView;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class StartActivity extends AppCompatActivity implements IRefreshTokenView {

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.arg1==1){
                Intent intent=new Intent(StartActivity.this,GgActivity.class);
                startActivity(intent);
            }
        }
    };
    private IRefreshTokenPresenterImpl refreshTokenPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        refreshTokenPresenter=new IRefreshTokenPresenterImpl(this,StartActivity.this);
        //refreshTokenPresenter.loadData();
        final Message message=handler.obtainMessage();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                message.arg1=1;
                handler.sendMessage(message);
            }
        },1000);


    }


    @Override
    public void onSuccess(RefreshTokenBean refreshTokenBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(refreshTokenBean==null){
                    return;
                }
                SharedPreferences preferences=getSharedPreferences("token",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("token",refreshTokenBean.getData().getToken());
                editor.commit();
            }
        });
    }

    @Override
    public void onError(String error) {
        Log.e("refresherror",error);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        refreshTokenPresenter.destoryView();
    }
}
