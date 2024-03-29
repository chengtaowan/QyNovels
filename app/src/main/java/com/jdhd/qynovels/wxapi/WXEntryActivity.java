package com.jdhd.qynovels.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.BindWxBean;
import com.jdhd.qynovels.module.personal.TokenBean;
import com.jdhd.qynovels.module.personal.UserBean;
import com.jdhd.qynovels.persenter.impl.personal.IBindwxPresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.IPersonalPresenterImpl;
import com.jdhd.qynovels.ui.activity.LoginActivity;
import com.jdhd.qynovels.ui.activity.MainActivity;
import com.jdhd.qynovels.ui.fragment.CaseFragment;
import com.jdhd.qynovels.ui.fragment.ShopFragment;
import com.jdhd.qynovels.ui.fragment.WodeFragment;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.personal.IBindwxView;
import com.jdhd.qynovels.view.personal.IPersonalView;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler , IPersonalView , IBindwxView {
    private IWXAPI api;
    private static final String APP_ID = "wxf2f9d368f73b6719";
    private String brand,model,sv,imei,sim,source="";
    private int os,root,network,time;
    public static String token="";
    private IPersonalPresenterImpl personalPresenter;
    private IBindwxPresenterImpl bindwxPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = MyApp.getApi();
        api.handleIntent(getIntent(), this);
        personalPresenter=new IPersonalPresenterImpl(this,WXEntryActivity.this);

        bindwxPresenter=new IBindwxPresenterImpl(this,WXEntryActivity.this);
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
                     Log.e("state",((SendAuth.Resp) baseResp).state);
                     if(((SendAuth.Resp) baseResp).state.equals("bindwechat")){
                         SharedPreferences preferences=getSharedPreferences("token", Context.MODE_PRIVATE);
                         token = preferences.getString("token", "");
                         int time= DeviceInfoUtils.getTime();
                         Map<String,String> map=new HashMap<>();
                         map.put("time",time+"");
                         if(token!=null){
                             map.put("token",token);
                         }
                         map.put("code",code);
                         String compareTo = DeviceInfoUtils.getCompareTo(map);
                         String sign=DeviceInfoUtils.md5(compareTo);
                         map.put("sign",sign);
                         Log.e("time",time+"");
                         Log.e("sign",sign);
                         RxHttp.postForm(MyApp.Url.baseUrl+"bindWeChat")
                                 .addHeader("token",token)
                                 .add(map)
                                 .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                                 .asParser(new SimpleParser<BindWxBean>(){})
                                 .subscribe(bindWxBean->{
                                     Log.e("avatar",bindWxBean.getCode()+"--"+bindWxBean.getMsg());
                                     EventBus.getDefault().post(bindWxBean);
                                 },throwable->{
                                     Log.e("bindwxerror",throwable.getMessage());
                                 });
                         finish();
                     }
                     else{
                         Log.e("code",code);
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
                         if(source.equals("null")){
                           map.put("source","yingyongbao");
                         }
                         else{
                             map.put("source",source+"");
                         }

                         String compareTo = DeviceInfoUtils.getCompareTo(map);
                         String s = DeviceInfoUtils.md5(DeviceInfoUtils.getCompareTo(map));
                         map.put("sign",s);
                         Log.e("brand",brand);
                         Log.e("model",model);
                         Log.e("sv",sv);
                         Log.e("os",os+"");
                         Log.e("imei",imei);
                         Log.e("root",root+"");
                         Log.e("sim",sim+"");
                         Log.e("network",network+"");
                         Log.e("time",time+"");
                         Log.e("sign",s);
                         Log.e("sourcewx3",source+"---");
                         RxHttp.postForm(MyApp.Url.baseUrl+"token")
                                 .add(map)
                                 .asParser(new SimpleParser<TokenBean>(){})
                                 .subscribe(tokenBean->{
                                     Log.e("tokenbean",tokenBean.getMsg()+"--"+tokenBean.getCode());
                                     if(tokenBean.getCode()==200){
                                         token=tokenBean.getData().getToken();
                                         SharedPreferences preferences=getSharedPreferences("token",MODE_PRIVATE);
                                         SharedPreferences.Editor editor=preferences.edit();
                                         editor.putString("token",token);
                                         editor.putString("login","success");
                                         editor.putString("islogin",tokenBean.getData().getIs_login()+"");
                                         editor.commit();
                                         Log.e("weixintoken",token+"+++");
                                         EventBus.getDefault().post(tokenBean);
                                         SharedPreferences sharedPreferences=getSharedPreferences("token",MODE_PRIVATE);
                                         token=sharedPreferences.getString("token","");
                                         personalPresenter.setToken(token);
                                         personalPresenter.loadData();

                                     }
                                 },throwable->{
                                     Log.e("asd","th"+throwable.getMessage());
                                 });
                     }

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
        source= DeviceInfoUtils.getChannelName(this);
        Log.e("sourcewx1",source+"--");
        if(source.equals("")){
            source="yingyongbao";
        }
        Log.e("sourcewx2",source+"--");
    }

    @Override
    public void onSuccess(UserBean userBean) {
        WodeFragment.user = userBean;
        EventBus.getDefault().post(userBean);
        ShopFragment.lhb.setVisibility(View.GONE);
        ShopFragment.closePopWindow();
        CaseFragment.lhb.setVisibility(View.GONE);
        CaseFragment.closePopWindow();
    }

    @Override
    public void onError(String error) {
       Log.e("usererror",error);
    }

    @Override
    public void onBindwxSuccess(BindWxBean bindWxBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                finish();
            }
        });
    }

    @Override
    public void onBindwxError(String error) {
        Log.e("bindwxerror",error);
    }
}
