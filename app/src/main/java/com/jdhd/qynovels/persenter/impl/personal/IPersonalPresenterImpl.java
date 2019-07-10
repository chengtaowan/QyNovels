package com.jdhd.qynovels.persenter.impl.personal;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.UserBean;
import com.jdhd.qynovels.persenter.inter.personal.IPersonalPresenter;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.personal.IPersonalView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IPersonalPresenterImpl implements IPersonalPresenter {
    private IPersonalView personalView;
    private Context context;
    private String token;

    public IPersonalPresenterImpl(IPersonalView personalView, Context context) {
        this.personalView = personalView;
        this.context = context;
    }

    @Override
    public void loadData() {
        SharedPreferences preferences=context.getSharedPreferences("token", Context.MODE_PRIVATE);
        token = preferences.getString("token", "");
        int time= DeviceInfoUtils.getTime();
        Map<String,String> map=new HashMap<>();
        map.put("time",time+"");
        map.put("token",token);
        String sign=DeviceInfoUtils.md5(DeviceInfoUtils.getCompareTo(map));
        map.put("sign",sign);
        RxHttp.get(MyApp.Url.baseUrl+"personal")
                .addHeader("token",token)
                .add(map)
                .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                .asParser(new SimpleParser<UserBean>(){})
                .subscribe(userBean->{
                    Log.e("msg",userBean.getMsg());
                    if(userBean.getCode()==200&&userBean.getMsg().equals("请求成功")){
                       personalView.onSuccess(userBean);
                    }
                },throwable->{
                    personalView.onError(throwable.getMessage());
                });
    }


    @Override
    public void destoryView() {
        if(personalView!=null){
            personalView=null;
        }
    }



}
