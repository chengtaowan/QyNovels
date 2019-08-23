package com.jdhd.qynovels.persenter.impl.personal;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.AvatarBean;
import com.jdhd.qynovels.module.personal.VideoflBean;
import com.jdhd.qynovels.persenter.inter.personal.IAvatarPresenter;
import com.jdhd.qynovels.persenter.inter.personal.IVideoflPresenter;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.personal.IAvatarView;
import com.jdhd.qynovels.view.personal.IVideoflView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IVideoflPresenterImpl implements IVideoflPresenter {
    private IVideoflView iVideoflView;
    private Context context;
    private String token;


    public IVideoflPresenterImpl(IVideoflView iVideoflView, Context context) {
        this.iVideoflView = iVideoflView;
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
        Log.e("time",time+"");
        Log.e("sign",sign);
        RxHttp.postForm(MyApp.Url.baseUrl+"video")
                .addHeader("token",token)
                .add(map)
                .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                .asParser(new SimpleParser<VideoflBean>(){})
                .subscribe(videoflBean->{
                    Log.e("avatar",videoflBean.getCode()+"--"+videoflBean.getMsg());
                    //if(videoflBean.getCode()==200&&videoflBean.getMsg().equals("领取成功")){
                        iVideoflView.onVideoSuccess(videoflBean);
                   // }
                },throwable->{
                    iVideoflView.onVideoError(throwable.getMessage());
                });
    }


    @Override
    public void destoryView() {
        if(iVideoflView!=null){
            iVideoflView=null;
        }
    }



}
