package com.jdhd.qynovels.persenter.impl.personal;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.persenter.inter.personal.IPrizePresenter;
import com.jdhd.qynovels.persenter.inter.personal.IShareImgPresenter;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.personal.IPrizesView;
import com.jdhd.qynovels.view.personal.IShareImgView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;

public class IPrizePresenterImpl implements IPrizePresenter {
    private IPrizesView iPrizesView;
    private Context context;
    private String token;
    private String game_name;

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public IPrizePresenterImpl(IPrizesView iPrizesView, Context context) {
        this.iPrizesView = iPrizesView;
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
        map.put("game_name",game_name);
        String compareTo = DeviceInfoUtils.getCompareTo(map);
        String sign=DeviceInfoUtils.md5(compareTo);
        map.put("sign",sign);
        Log.e("token",token);
        Log.e("time",time+"");
        Log.e("sign",sign);
        Log.e("game_name",game_name);
        RxHttp.postForm(MyApp.Url.baseUrl+"getPrize")
                .addHeader("token",token)
                .add(map)
                .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                .asString()
                .subscribe(avatarBean->{
                    iPrizesView.onPrizeSuccess(avatarBean);
                },throwable->{
                    iPrizesView.onPrizeError(throwable.getMessage());
                });
    }


    @Override
    public void destoryView() {
        if(iPrizesView!=null){
            iPrizesView=null;
        }
    }



}
