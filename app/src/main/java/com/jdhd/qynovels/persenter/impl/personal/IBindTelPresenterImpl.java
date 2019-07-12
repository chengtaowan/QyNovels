package com.jdhd.qynovels.persenter.impl.personal;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.AvatarBean;
import com.jdhd.qynovels.module.personal.BindTelBean;
import com.jdhd.qynovels.persenter.inter.personal.IAvatarPresenter;
import com.jdhd.qynovels.persenter.inter.personal.IBindTelPresenter;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.personal.IAvatarView;
import com.jdhd.qynovels.view.personal.IBindTelView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IBindTelPresenterImpl implements IBindTelPresenter {
    private IBindTelView iBindTelView;
    private Context context;
    private String token;
    private String tel;
    private String captcha;

    public IBindTelPresenterImpl(IBindTelView iBindTelView, Context context, String tel, String captcha) {
        this.iBindTelView = iBindTelView;
        this.context = context;
        this.tel = tel;
        this.captcha = captcha;
    }

    @Override
    public void loadData() {
        SharedPreferences preferences=context.getSharedPreferences("token", Context.MODE_PRIVATE);
        token = preferences.getString("token", "");
        int time= DeviceInfoUtils.getTime();
        Map<String,String> map=new HashMap<>();
        map.put("time",time+"");
        map.put("token",token);
        map.put("captcha",captcha);
        map.put("tel",tel);
        String compareTo = DeviceInfoUtils.getCompareTo(map);
        String sign=DeviceInfoUtils.md5(compareTo);
        map.put("sign",sign);
        Log.e("time",time+"");
        Log.e("captcha",captcha+"");
        Log.e("sign",sign);
        Log.e("tel",tel+"");
        RxHttp.postForm(MyApp.Url.baseUrl+"bindTel")
                .addHeader("token",token)
                .add(map)
                .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                .asParser(new SimpleParser<BindTelBean>(){})
                .subscribe(bindTelBean->{
                    if(bindTelBean.getCode()==200&&bindTelBean.getMsg().equals("绑定成功")){
                        iBindTelView.onBindtelSuccess(bindTelBean);
                    }
                },throwable->{
                    iBindTelView.onBindtelError(throwable.getMessage());
                });
    }


    @Override
    public void destoryView() {
        if(iBindTelView!=null){
            iBindTelView=null;
        }
    }



}
