package com.jdhd.qynovels.persenter.impl.personal;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.SignBean;
import com.jdhd.qynovels.module.personal.SignSetingBean;
import com.jdhd.qynovels.persenter.inter.personal.ISignPresenter;
import com.jdhd.qynovels.persenter.inter.personal.ISignSetingPresenter;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.personal.ISignSetingView;
import com.jdhd.qynovels.view.personal.ISignView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class ISignPresenterImpl implements ISignPresenter {
    private ISignView iSignView;
    private Context context;
    private String token;
    private String file;

    public ISignPresenterImpl(ISignView iSignView, Context context) {
        this.iSignView = iSignView;
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
        String compareTo = DeviceInfoUtils.getCompareTo(map);
        String sign=DeviceInfoUtils.md5(compareTo);
        map.put("sign",sign);
        Log.e("token",token);
        Log.e("time",time+"");
        Log.e("sign",sign);
        RxHttp.postForm(MyApp.Url.baseUrl+"signIn")
                .addHeader("token",token)
                .add(map)
                .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                .asParser(new SimpleParser<SignBean>(){})
                .subscribe(signBean->{
                    if(signBean.getCode()==200&&signBean.getMsg().equals("请求成功")){
                        iSignView.onSignSuccess(signBean);
                    }
                },throwable->{
                    iSignView.onSignError(throwable.getMessage());
                });
    }


    @Override
    public void destoryView() {
        if(iSignView!=null){
            iSignView=null;
        }
    }



}
