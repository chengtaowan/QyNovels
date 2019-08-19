package com.jdhd.qynovels.persenter.impl.personal;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.AvatarBean;
import com.jdhd.qynovels.module.personal.BindWxBean;
import com.jdhd.qynovels.persenter.inter.personal.IAvatarPresenter;
import com.jdhd.qynovels.persenter.inter.personal.IBindwxPresenter;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.personal.IAvatarView;
import com.jdhd.qynovels.view.personal.IBindwxView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IBindwxPresenterImpl implements IBindwxPresenter {
    private IBindwxView iBindwxView;
    private Context context;
    private String token;
    private String code;

    public void setCode(String code) {
        this.code = code;
    }

    public IBindwxPresenterImpl(IBindwxView iBindwxView, Context context) {
        this.iBindwxView = iBindwxView;
        this.context = context;
    }

    @Override
    public void loadData() {
        SharedPreferences preferences=context.getSharedPreferences("token", Context.MODE_PRIVATE);
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
                .subscribe(avatarBean->{
                    Log.e("avatar",avatarBean.getCode()+"--"+avatarBean.getMsg());
                    //if(avatarBean.getCode()==200&&avatarBean.getMsg().equals("绑定成功")){
                        iBindwxView.onBindwxSuccess(avatarBean);
                    //}
                },throwable->{
                    iBindwxView.onBindwxError(throwable.getMessage());
                });
    }


    @Override
    public void destoryView() {
        if(iBindwxView!=null){
            iBindwxView=null;
        }
    }



}
