package com.jdhd.qynovels.persenter.impl.personal;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.AvatarBean;
import com.jdhd.qynovels.module.personal.MessageBean;
import com.jdhd.qynovels.persenter.inter.personal.IAvatarPresenter;
import com.jdhd.qynovels.persenter.inter.personal.IMessagePresenter;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.personal.IAvatarView;
import com.jdhd.qynovels.view.personal.IMessageView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IMessagePresenterImpl implements IMessagePresenter {
    private IMessageView iMessageView;
    private Context context;
    private String token;

    public IMessagePresenterImpl(IMessageView iMessageView, Context context) {
        this.iMessageView = iMessageView;
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
        map.put("page",1+"");
        map.put("limit",10+"");
        String compareTo = DeviceInfoUtils.getCompareTo(map);
        String sign=DeviceInfoUtils.md5(compareTo);
        map.put("sign",sign);
        Log.e("token",token);
        Log.e("time",time+"");
        Log.e("sign",sign);
        RxHttp.postForm(MyApp.Url.baseUrl+"message")
                .addHeader("token",token)
                .add(map)
                .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                .asParser(new SimpleParser<MessageBean>(){})
                .subscribe(messageBean->{
                    if(messageBean.getCode()==200&&messageBean.getMsg().equals("请求成功")){
                        iMessageView.onMessageSuccess(messageBean);
                    }
                },throwable->{
                    iMessageView.onMessageError(throwable.getMessage());
                });
    }


    @Override
    public void destoryView() {
        if(iMessageView!=null){
            iMessageView=null;
        }
    }



}
