package com.jdhd.qynovels.persenter.impl.personal;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.SignSetingBean;
import com.jdhd.qynovels.persenter.inter.personal.IShareImgPresenter;
import com.jdhd.qynovels.persenter.inter.personal.ISignSetingPresenter;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.personal.IShareImgView;
import com.jdhd.qynovels.view.personal.ISignSetingView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class ISignSetingPresenterImpl implements ISignSetingPresenter {
    private ISignSetingView  iSignSetingView;
    private Context context;
    private String token;
    private String file;
    private String islogin;
    public void setToken(String token) {
        this.token = token;
    }

    public void setIslogin(String islogin) {
        this.islogin = islogin;
    }

    public ISignSetingPresenterImpl(ISignSetingView  iSignSetingView, Context context) {
        this.iSignSetingView = iSignSetingView;
        this.context = context;
    }

    @Override
    public void loadData() {
        if(token.equals("")){
            SharedPreferences preferences=context.getSharedPreferences("token", Context.MODE_PRIVATE);
            token = preferences.getString("token", "");
        }
        int time= DeviceInfoUtils.getTime();
        Map<String,String> map=new HashMap<>();
        map.put("time",time+"");
        if(token!=null){
            map.put("token",token);
        }
        String compareTo = DeviceInfoUtils.getCompareTo(map);
        String sign=DeviceInfoUtils.md5(compareTo);
        map.put("sign",sign);
        Log.e("signtoken",token+"##");
        Log.e("time",time+"");
        Log.e("sign",sign);
        Log.e("signsetingislogin",islogin);
        if(islogin.equals("1")){
            RxHttp.postForm(MyApp.Url.baseUrl+"signSetting")
                    .addHeader("token",token)
                    .add(map)
                    .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                    .asString()
                    .subscribe(avatarBean->{
                        iSignSetingView.onSetingSuccess(avatarBean);
                    },throwable->{
                        iSignSetingView.onSetingError(throwable.getMessage());
                    });
        }

    }


    @Override
    public void destoryView() {
        if(iSignSetingView!=null){
            iSignSetingView=null;
        }
    }



}
