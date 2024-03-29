package com.jdhd.qynovels.persenter.impl.personal;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.persenter.inter.personal.IDrawPresenter;
import com.jdhd.qynovels.persenter.inter.personal.IPrizePresenter;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.personal.IDrawView;
import com.jdhd.qynovels.view.personal.IPrizesView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;

public class IDrawPresenterImpl implements IDrawPresenter {
    private IDrawView iDrawView;
    private Context context;
    private String token;
    private String game_name;
    private String datapath;
    private String game_num;

    public void setGame_num(String game_num) {
        this.game_num = game_num;
    }

    public void setDatapath(String datapath) {
        this.datapath = datapath;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public IDrawPresenterImpl(IDrawView iDrawView, Context context) {
        this.iDrawView = iDrawView;
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
        map.put("game_num",game_num);
        String compareTo = DeviceInfoUtils.getCompareTo(map);
        String sign=DeviceInfoUtils.md5(compareTo);
        map.put("sign",sign);
        Log.e("token",token);
        Log.e("time",time+"");
        Log.e("sign",sign);
        Log.e("game_name",game_name);
        Log.e("game_num",game_num);
        RxHttp.postForm(MyApp.Url.baseUrl+datapath)
                .addHeader("token",token)
                .add(map)
                .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                .asString()
                .subscribe(avatarBean->{
                    iDrawView.onDrawSuccess(avatarBean);
                },throwable->{
                    iDrawView.onDrawError(throwable.getMessage());
                });
    }


    @Override
    public void destoryView() {
        if(iDrawView!=null){
            iDrawView=null;
        }
    }



}
