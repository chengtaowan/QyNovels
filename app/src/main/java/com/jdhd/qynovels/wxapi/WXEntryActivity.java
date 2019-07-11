package com.jdhd.qynovels.wxapi;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.TokenBean;
import com.jdhd.qynovels.module.personal.UserBean;
import com.jdhd.qynovels.persenter.impl.personal.IPersonalPresenterImpl;
import com.jdhd.qynovels.ui.fragment.WodeFragment;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.personal.IPersonalView;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

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
                     String compareTo = DeviceInfoUtils.getCompareTo(map);
                     String s = DeviceInfoUtils.md5(DeviceInfoUtils.getCompareTo(map));
                     map.put("sign",s);
//                     Log.e("brand",brand);
//                     Log.e("model",model);
//                     Log.e("sv",sv);
//                     Log.e("os",os+"");
//                     Log.e("imei",imei);
//                     Log.e("root",root+"");
//                     Log.e("sim",sim+"");
//                     Log.e("network",network+"");
//                     Log.e("time",time+"");
//                     Log.e("sign",s);
//                     Log.e("compare",compareTo);
                     RxHttp.postForm(MyApp.Url.baseUrl+"token")
                             .add(map)
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
