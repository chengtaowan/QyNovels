package com.jdhd.qynovels.persenter.impl.personal;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.DrawListBean;
import com.jdhd.qynovels.module.personal.RefreshTokenBean;
import com.jdhd.qynovels.persenter.inter.personal.IPersonalPresenter;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.personal.IDrawListView;
import com.jdhd.qynovels.view.personal.IRefreshTokenView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IDrawListPresenterImpl implements IPersonalPresenter {
    private IDrawListView iDrawListView;
    private Context context;
    private String token;

    public IDrawListPresenterImpl(IDrawListView iDrawListView, Context context) {
        this.iDrawListView = iDrawListView;
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
        map.put("page","1");
        map.put("limit","10");
        String compareTo = DeviceInfoUtils.getCompareTo(map);
        String sign=DeviceInfoUtils.md5(compareTo);
        map.put("sign",sign);
        RxHttp.postForm(MyApp.Url.baseUrl+"withdrawList")
                .addHeader("token",token)
                .add(map)
                .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                .asParser(new SimpleParser<DrawListBean>(){})
                .subscribe(drawListBean->{
                    //if(drawListBean.getCode()==200&&drawListBean.getMsg().equals("请求成功")){
                        iDrawListView.onSuccess(drawListBean);
                    //}
                },throwable->{
                    iDrawListView.onError(throwable.getMessage());
                });
    }


    @Override
    public void destoryView() {
        if(iDrawListView!=null){
            iDrawListView=null;
        }
    }



}
