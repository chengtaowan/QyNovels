package com.jdhd.qynovels.persenter.impl.personal;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.AvatarBean;
import com.jdhd.qynovels.module.personal.FeedBackBean;
import com.jdhd.qynovels.persenter.inter.personal.IAvatarPresenter;
import com.jdhd.qynovels.persenter.inter.personal.IFeedBackPresenter;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.personal.IAvatarView;
import com.jdhd.qynovels.view.personal.IFeedBackView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IFeedBackPresenterImpl implements IFeedBackPresenter {
    private IFeedBackView iFeedBackView;
    private Context context;
    private String token;
    private String content,qq,wx,ali;
    private String imgjson;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx;
    }

    public String getAli() {
        return ali;
    }

    public void setAli(String ali) {
        this.ali = ali;
    }

    public String getImgjson() {
        return imgjson;
    }

    public void setImgjson(String imgjson) {
        this.imgjson = imgjson;
    }

    public IFeedBackPresenterImpl(IFeedBackView iFeedBackView, Context context) {
        this.iFeedBackView = iFeedBackView;
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
        map.put("content",content);
        map.put("qq",qq);
        map.put("wx",wx);
        map.put("ali",ali);
        map.put("images",imgjson);
        String compareTo = DeviceInfoUtils.getCompareTo(map);
        String sign=DeviceInfoUtils.md5(compareTo);
        map.put("sign",sign);
        Log.e("time",time+"");
        Log.e("content",content);
        Log.e("wx",wx);
        Log.e("qq",qq);
        Log.e("ali",ali);
        Log.e("json",imgjson);
        RxHttp.postForm(MyApp.Url.baseUrl+"feedback")
                .addHeader("token",token)
                .add(map)
                .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                .asParser(new SimpleParser<FeedBackBean>(){})
                .subscribe(avatarBean->{
                    Log.e("avatar",avatarBean.getCode()+"--"+avatarBean.getMsg());
                    if(avatarBean.getCode()==200&&avatarBean.getMsg().equals("提交成功")){
                        iFeedBackView.onBackSuccess(avatarBean);
                    }
                },throwable->{
                    iFeedBackView.onBackError(throwable.getMessage());
                });
    }


    @Override
    public void destoryView() {
        if(iFeedBackView!=null){
            iFeedBackView=null;
        }
    }



}
