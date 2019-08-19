package com.jdhd.qynovels.persenter.impl.personal;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.NickNameBean;
import com.jdhd.qynovels.module.personal.SexBean;
import com.jdhd.qynovels.persenter.inter.personal.ISexPresenter;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.personal.INickNameView;
import com.jdhd.qynovels.view.personal.ISexView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class INickNamePresenterImpl implements ISexPresenter {
    private INickNameView iNickNameView;
    private Context context;
    private String token;
    private String nickname;

    public INickNamePresenterImpl(INickNameView iNickNameView, Context context, String nickname) {
        this.iNickNameView = iNickNameView;
        this.context = context;
        this.nickname = nickname;
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
        map.put("nickname",nickname);
        String compareTo = DeviceInfoUtils.getCompareTo(map);
        String sign=DeviceInfoUtils.md5(compareTo);
        map.put("sign",sign);
        Log.e("time",time+"");
        Log.e("nickname",nickname+"");
        Log.e("sign",sign);
        RxHttp.postForm(MyApp.Url.baseUrl+"nickname")
                .addHeader("token",token)
                .add(map)
                .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                .asParser(new SimpleParser<NickNameBean>(){})
                .subscribe(nickNameBean->{
                    //if(nickNameBean.getCode()==200&&nickNameBean.getMsg().equals("修改成功")){
                        iNickNameView.onNickNameSuccess(nickNameBean);
                   // }
                },throwable->{
                    iNickNameView.onNickNameError(throwable.getMessage());
                });
    }


    @Override
    public void destoryView() {
        if(iNickNameView!=null){
            iNickNameView=null;
        }
    }



}
