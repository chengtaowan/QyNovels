package com.jdhd.qynovels.persenter.impl.personal;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.GoldListBean;
import com.jdhd.qynovels.module.personal.RefreshTokenBean;
import com.jdhd.qynovels.persenter.inter.personal.IPersonalPresenter;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.personal.IGoldListView;
import com.jdhd.qynovels.view.personal.IRefreshTokenView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IRefreshTokenPresenterImpl implements IPersonalPresenter {
    private IRefreshTokenView iRefreshTokenView;
    private Context context;
    private String token;

    public IRefreshTokenPresenterImpl(IRefreshTokenView iRefreshTokenView, Context context) {
        this.iRefreshTokenView = iRefreshTokenView;
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
        RxHttp.postForm(MyApp.Url.baseUrl+"refreshToken")
                .addHeader("token",token)
                .add(map)
                .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                .asParser(new SimpleParser<RefreshTokenBean>(){})
                .subscribe(refreshTokenBean->{
                    Log.e("msg",refreshTokenBean.getMsg());
                    if(refreshTokenBean.getCode()==200&&refreshTokenBean.getMsg().equals("请求成功")){
                       iRefreshTokenView.onSuccess(refreshTokenBean);
                    }
                },throwable->{
                    iRefreshTokenView.onError(throwable.getMessage());
                });
    }


    @Override
    public void destoryView() {
        if(iRefreshTokenView!=null){
            iRefreshTokenView=null;
        }
    }



}
