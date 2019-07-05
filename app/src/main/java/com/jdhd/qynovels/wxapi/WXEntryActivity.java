package com.jdhd.qynovels.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.TokenBean;
import com.jdhd.qynovels.module.UserBean;
import com.jdhd.qynovels.persenter.impl.IPersonalPresenterImpl;
import com.jdhd.qynovels.ui.activity.MainActivity;
import com.jdhd.qynovels.ui.fragment.WodeFragment;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.IPersonalView;
import com.rxjava.rxlife.RxLife;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import rxhttp.wrapper.annotation.DefaultDomain;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler , IPersonalView {
    private IWXAPI api;
    private static final String APP_ID = "wxf2f9d368f73b6719";
    private String brand,model,sv,imei;
    private int os,root,sim,network,time;
    public static String token="";
    private IPersonalPresenterImpl personalPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = MyApp.getApi();
        api.handleIntent(getIntent(), this);
        personalPresenter=new IPersonalPresenterImpl(this,WXEntryActivity.this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode){
            case BaseResp.ErrCode.ERR_OK://同意授权
                 if(baseResp.getType()==1){
                     String code=((SendAuth.Resp) baseResp).code;
                     getInfo();
                     Map<String,String> map=new HashMap<>();
                     map.put("code",code);
                     map.put("brand",brand);
                     map.put("model",model);
                     map.put("sv",sv);
                     map.put("os",os+"");
                     map.put("imei",imei);
                     map.put("root",root+"");
                     map.put("sim",sim+"");
                     map.put("network",network+"");
                     map.put("time",time+"");
                     String s = DeviceInfoUtils.md5(DeviceInfoUtils.getCompareTo(map));
                     Map<String,String> map1=new HashMap<>();
                     map1=map;
                     map1.put("sign",s);
                     String baseUrl = MyApp.Url.baseUrl+"token";
                     RxHttp.postForm(baseUrl)
                             .add(map1)
                             .asParser(new SimpleParser<TokenBean>(){})
                             .subscribe(tokenBean->{
                                 if(tokenBean.getCode()==200&&tokenBean.getMsg().equals("success")){
                                     token=tokenBean.getData().getToken();
                                     SharedPreferences preferences=getSharedPreferences("token",MODE_PRIVATE);
                                     SharedPreferences.Editor editor=preferences.edit();
                                     editor.putString("token",token);
                                     editor.putString("login","success");
                                     editor.commit();
                                     personalPresenter.loadData();
                                 }
                             },throwable->{
                                Log.e("asd","th"+throwable.getMessage());
                             });

                     finish();
                 }
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED://拒绝授权

                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL://取消

                break;
        }
        finish();
    }

    private void getInfo(){
        brand=DeviceInfoUtils.getDeviceBrand();
        model=DeviceInfoUtils.getDeviceModel();
        sv=DeviceInfoUtils.getDeviceAndroidVersion();
        os=DeviceInfoUtils.getOs();
        imei=DeviceInfoUtils.getIMEI(this);
        root=DeviceInfoUtils.getRootAhth();
        sim=DeviceInfoUtils.getSim(this);
        network=DeviceInfoUtils.getNetWork(this);
        time=DeviceInfoUtils.getTime();
    }

    @Override
    public void onSuccess(UserBean userBean) {
        WodeFragment.user = userBean;
        EventBus.getDefault().post(userBean);
    }

    @Override
    public void onError(String error) {

    }
}
