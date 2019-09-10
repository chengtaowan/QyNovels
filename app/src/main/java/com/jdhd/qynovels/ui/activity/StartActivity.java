package com.jdhd.qynovels.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.SyncStateContract;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.RefreshTokenBean;
import com.jdhd.qynovels.module.personal.VisitorBean;
import com.jdhd.qynovels.persenter.impl.personal.IRefreshTokenPresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.IVisitorPresenterImpl;
import com.jdhd.qynovels.utils.AndroidBug54971Workaround;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.jdhd.qynovels.view.personal.IRefreshTokenView;
import com.jdhd.qynovels.view.personal.IVisitorView;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;


public class StartActivity extends AppCompatActivity implements IRefreshTokenView,EasyPermissions.PermissionCallbacks {
    private String[] permissions = {Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};

    private IRefreshTokenPresenterImpl refreshTokenPresenter;
    public static int startTime=0;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            SharedPreferences sharedPreferences=getSharedPreferences("sex",MODE_PRIVATE);
            String sex=sharedPreferences.getString("sex","");
            if(!sex.equals("")){
                Intent intent=new Intent(StartActivity.this,MainActivity.class);
                intent.putExtra("page",1);
                startActivity(intent);
                finish();
            }
            else{
                Intent intent=new Intent(StartActivity.this,ChoseSexActivity.class);
                startActivity(intent);
                finish();
            }
            Log.e("sex",sex+"---");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        AndroidBug54971Workaround.assistActivity(findViewById(android.R.id.content),this);
        MyApp.addActivity(this);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        refreshTokenPresenter=new IRefreshTokenPresenterImpl(this,StartActivity.this);
        refreshTokenPresenter.loadData();
        startTime= DeviceInfoUtils.getTime();
        getPermission();

    }

    //获取权限
    private void getPermission() {
        if (EasyPermissions.hasPermissions(this, permissions)) {
            Message message=new Message();
            handler.sendMessageDelayed(message,1000);
            //已经打开权限
            // Toast.makeText(this, "已经申请相关权限", Toast.LENGTH_SHORT).show();
        } else {
            //没有打开相关权限、申请权限
            EasyPermissions.requestPermissions(this, "需要获取您的设备使用权限", 1, permissions);
        }
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        SharedPreferences sharedPreferences=getSharedPreferences("sex",MODE_PRIVATE);
        String sex=sharedPreferences.getString("sex","");
        if(!sex.equals("")){
            Intent intent=new Intent(StartActivity.this,MainActivity.class);
            intent.putExtra("page",1);
            startActivity(intent);
            finish();
        }
        else{
            Intent intent=new Intent(StartActivity.this,ChoseSexActivity.class);
            startActivity(intent);
            finish();
        }
        Log.e("sex",sex+"---");
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        SharedPreferences sharedPreferences=getSharedPreferences("sex",MODE_PRIVATE);
        String sex=sharedPreferences.getString("sex","");
        if(!sex.equals("")){
            Intent intent=new Intent(StartActivity.this,MainActivity.class);
            intent.putExtra("page",1);
            startActivity(intent);
            finish();
        }
        else{
            Intent intent=new Intent(StartActivity.this,ChoseSexActivity.class);
            startActivity(intent);
            finish();
        }
        Log.e("sex",sex+"---");
    }
}
