package com.jdhd.qynovels.persenter.impl.personal;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.GoldListBean;
import com.jdhd.qynovels.module.personal.UserBean;
import com.jdhd.qynovels.persenter.inter.personal.IPersonalPresenter;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.personal.IGoldListView;
import com.jdhd.qynovels.view.personal.IPersonalView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IGoldListPresenterImpl implements IPersonalPresenter {
    private IGoldListView iGoldListView;
    private Context context;
    private String token;

    public IGoldListPresenterImpl(IGoldListView iGoldListView, Context context) {
        this.iGoldListView = iGoldListView;
        this.context = context;
    }

    @Override
    public void loadData() {
        SharedPreferences preferences=context.getSharedPreferences("token", Context.MODE_PRIVATE);
        token = preferences.getString("token", "");
        Log.e("token",token);
        int time= DeviceInfoUtils.getTime();
        Log.e("time",time+"");
        Map<String,String> map=new HashMap<>();
        map.put("time",time+"");
        map.put("page","1");
        map.put("limit","10");
        map.put("token",token);
        String compareTo = DeviceInfoUtils.getCompareTo(map);
        Log.e("paixu",compareTo);
        String sign=DeviceInfoUtils.md5(DeviceInfoUtils.getCompareTo(map));
        Log.e("sign",sign);
        map.put("sign",sign);
        RxHttp.get(MyApp.Url.baseUrl+"goldList")
                .addHeader("token",token)
                .add("time",time)
                .add("sign",sign)
                .add("page",1)
                .add("limit",10)
                .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                .asParser(new SimpleParser<GoldListBean>(){})
                .subscribe(goldListBean->{
                    Log.e("msg",goldListBean.getMsg());
                    if(goldListBean.getCode()==200&&goldListBean.getMsg().equals("请求成功")){
                       iGoldListView.onSuccess(goldListBean);
                    }
                },throwable->{
                    iGoldListView.onError(throwable.getMessage());
                });
    }


    @Override
    public void destoryView() {
        if(iGoldListView!=null){
            iGoldListView=null;
        }
    }



}
