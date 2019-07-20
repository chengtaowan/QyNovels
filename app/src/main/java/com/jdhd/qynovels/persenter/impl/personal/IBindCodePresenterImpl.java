package com.jdhd.qynovels.persenter.impl.personal;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.AvatarBean;
import com.jdhd.qynovels.module.personal.BindCodeBean;
import com.jdhd.qynovels.persenter.inter.personal.IAvatarPresenter;
import com.jdhd.qynovels.persenter.inter.personal.IBindCodePresenter;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.personal.IAvatarView;
import com.jdhd.qynovels.view.personal.IBindCodeView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IBindCodePresenterImpl implements IBindCodePresenter {
    private IBindCodeView iBindCodeView;
    private Context context;
    private String token;
    private String red_code;

    public void setRed_code(String red_code) {
        this.red_code = red_code;
    }

    public IBindCodePresenterImpl(IBindCodeView iBindCodeView, Context context) {
        this.iBindCodeView = iBindCodeView;
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
        map.put("red_code",red_code);
        String compareTo = DeviceInfoUtils.getCompareTo(map);
        String sign=DeviceInfoUtils.md5(compareTo);
        map.put("sign",sign);
        Log.e("time",time+"");
        Log.e("sign",sign);
        RxHttp.postForm(MyApp.Url.baseUrl+"bindCode")
                .addHeader("token",token)
                .add(map)
                .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                .asParser(new SimpleParser<BindCodeBean>(){})
                .subscribe(bindCodeBean->{
                    iBindCodeView.onAvatarSuccess(bindCodeBean);
                },throwable->{
                    iBindCodeView.onAvatarError(throwable.getMessage());
                });
    }


    @Override
    public void destoryView() {
        if(iBindCodeView!=null){
            iBindCodeView=null;
        }
    }



}
