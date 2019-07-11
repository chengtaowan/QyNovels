package com.jdhd.qynovels.persenter.impl.personal;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.DrawListBean;
import com.jdhd.qynovels.module.personal.DrawSetBean;
import com.jdhd.qynovels.persenter.inter.personal.IPersonalPresenter;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.personal.IDrawListView;
import com.jdhd.qynovels.view.personal.IDrawSetView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IDrawSetPresenterImpl implements IPersonalPresenter {
    private IDrawSetView iDrawSetView;
    private Context context;
    private String token;

    public IDrawSetPresenterImpl(IDrawSetView iDrawSetView, Context context) {
        this.iDrawSetView = iDrawSetView;
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
        String compareTo = DeviceInfoUtils.getCompareTo(map);
        String sign=DeviceInfoUtils.md5(compareTo);
        map.put("sign",sign);
        RxHttp.postForm(MyApp.Url.baseUrl+"withdrawSet")
                .addHeader("token",token)
                .add(map)
                .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                .asParser(new SimpleParser<DrawSetBean>(){})
                .subscribe(drawSetBean->{
                    if(drawSetBean.getCode()==200&&drawSetBean.getMsg().equals("请求成功")){
                        iDrawSetView.onSuccess(drawSetBean);
                    }
                },throwable->{
                    iDrawSetView.onError(throwable.getMessage());
                });
    }


    @Override
    public void destoryView() {
        if(iDrawSetView!=null){
            iDrawSetView=null;
        }
    }



}
