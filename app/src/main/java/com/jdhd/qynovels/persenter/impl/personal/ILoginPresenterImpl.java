package com.jdhd.qynovels.persenter.impl.personal;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.TokenBean;
import com.jdhd.qynovels.module.personal.UserBean;
import com.jdhd.qynovels.persenter.inter.personal.ILoginPresenter;
import com.jdhd.qynovels.persenter.inter.personal.IPersonalPresenter;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.personal.ILoginView;
import com.jdhd.qynovels.view.personal.IPersonalView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

import static android.content.Context.MODE_PRIVATE;

public class ILoginPresenterImpl implements ILoginPresenter {
    private ILoginView iLoginView;
    private Context context;
    private String brand,model,sv,imei,sim;
    private int os,root,network,time;
    private String phone,yzm;
    private String token;

    public ILoginPresenterImpl(ILoginView iLoginView, Context context, String phone, String yzm) {
        this.iLoginView = iLoginView;
        this.context = context;
        this.phone = phone;
        this.yzm = yzm;
    }

    @Override
    public void loadData() {

        getInfo();
        Map<String,String> map=new HashMap<>();
        map.put("tel",phone);
        map.put("captcha",yzm);
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
        map.put("sign",s);
        Log.e("tel",phone);
        Log.e("yzm",yzm);
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
        RxHttp.postForm(MyApp.Url.baseUrl+"token")
                .add(map)
                .asParser(new SimpleParser<TokenBean>(){})
                .subscribe(tokenBean->{
                    Log.e("login",tokenBean.getCode()+"-"+tokenBean.getMsg());
                    //if(tokenBean.getCode()==200&&tokenBean.getMsg().equals("success")){
                        token=tokenBean.getData().getToken();
                        //Log.e("asdtoken",token);
                        iLoginView.onTokenSuccess(tokenBean);
                   // }
                },throwable->{
                    iLoginView.onTokenError(throwable.getMessage());
                });
    }


    @Override
    public void destoryView() {
        if(iLoginView!=null){
            iLoginView=null;
        }
    }

    private void getInfo(){
        brand=DeviceInfoUtils.getDeviceBrand();
        model=DeviceInfoUtils.getDeviceModel();
        sv=DeviceInfoUtils.getDeviceAndroidVersion();
        os=DeviceInfoUtils.getOs();
        imei=DeviceInfoUtils.getIMEI(context);
        //root=DeviceInfoUtils.getRootAhth();
        root=10;
        sim=DeviceInfoUtils.getSIM(context);
        network=DeviceInfoUtils.getNetWork(context);
        time=DeviceInfoUtils.getTime();
    }

}
