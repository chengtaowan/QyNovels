package com.jdhd.qynovels.persenter.impl.personal;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.CaptchaBean;
import com.jdhd.qynovels.module.personal.SexBean;
import com.jdhd.qynovels.persenter.inter.personal.IPersonalPresenter;
import com.jdhd.qynovels.persenter.inter.personal.ISexPresenter;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.personal.ICaptchaView;
import com.jdhd.qynovels.view.personal.ISexView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class ISexPresenterImpl implements ISexPresenter {
    private ISexView iSexView;
    private Context context;
    private String token;
    private int sex;

    public ISexPresenterImpl(ISexView iSexView, Context context, int sex) {
        this.iSexView = iSexView;
        this.context = context;
        this.sex = sex;
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
        map.put("sex",sex+"");
        String compareTo = DeviceInfoUtils.getCompareTo(map);
        String sign=DeviceInfoUtils.md5(compareTo);
        map.put("sign",sign);
        Log.e("time",time+"");
        Log.e("sex",sex+"");
        Log.e("sign",sign);
        RxHttp.postForm(MyApp.Url.baseUrl+"sex")
                .addHeader("token",token)
                .add(map)
                .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                .asParser(new SimpleParser<SexBean>(){})
                .subscribe(sexBean->{
                    if(sexBean.getCode()==200&&sexBean.getMsg().equals("修改成功")){
                        iSexView.onSexSuccess(sexBean);
                    }
                },throwable->{
                    iSexView.onSexError(throwable.getMessage());
                });
    }


    @Override
    public void destoryView() {
        if(iSexView!=null){
            iSexView=null;
        }
    }



}
