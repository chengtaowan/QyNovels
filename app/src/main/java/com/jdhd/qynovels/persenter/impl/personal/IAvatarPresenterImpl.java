package com.jdhd.qynovels.persenter.impl.personal;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.AvatarBean;
import com.jdhd.qynovels.persenter.inter.personal.IAvatarPresenter;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.personal.IAvatarView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IAvatarPresenterImpl implements IAvatarPresenter {
    private IAvatarView iAvatarView;
    private Context context;
    private String token;
    private String file;

    public IAvatarPresenterImpl(IAvatarView iAvatarView, Context context, String file) {
        this.iAvatarView = iAvatarView;
        this.context = context;
        this.file = file;
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
        map.put("file",file);
        String compareTo = DeviceInfoUtils.getCompareTo(map);
        String sign=DeviceInfoUtils.md5(compareTo);
        map.put("sign",sign);
        Log.e("time",time+"");
        Log.e("sign",sign);
        RxHttp.postForm(MyApp.Url.baseUrl+"avatar")
                .addHeader("token",token)
                .add(map)
                .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                .asParser(new SimpleParser<AvatarBean>(){})
                .subscribe(avatarBean->{
                    if(avatarBean.getCode()==200&&avatarBean.getMsg().equals("success")){
                        iAvatarView.onAvatarSuccess(avatarBean);
                    }
                },throwable->{
                    iAvatarView.onAvatarError(throwable.getMessage());
                });
    }


    @Override
    public void destoryView() {
        if(iAvatarView!=null){
            iAvatarView=null;
        }
    }



}
