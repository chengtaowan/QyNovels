package com.jdhd.qynovels.persenter.impl.personal;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.CaptchaBean;
import com.jdhd.qynovels.module.personal.DrawListBean;
import com.jdhd.qynovels.persenter.inter.personal.IPersonalPresenter;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.personal.ICaptchaView;
import com.jdhd.qynovels.view.personal.IDrawListView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class ICaptchaPresenterImpl implements IPersonalPresenter {
    private ICaptchaView iCaptchaView;
    private Context context;
    private String token;
    private String tel;

    public ICaptchaPresenterImpl(ICaptchaView iCaptchaView, Context context, String tel) {
        this.iCaptchaView = iCaptchaView;
        this.context = context;
        this.tel = tel;
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
        map.put("tel",tel);
        String compareTo = DeviceInfoUtils.getCompareTo(map);
        String sign=DeviceInfoUtils.md5(compareTo);
        map.put("sign",sign);
        Log.e("time",time+"");
        Log.e("tel",tel);
        Log.e("sign",sign);
        RxHttp.postForm(MyApp.Url.baseUrl+"captcha")
                .add(map)
                .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                .asParser(new SimpleParser<CaptchaBean>(){})
                .subscribe(captchaBean->{
                    if(captchaBean.getCode()==200&&captchaBean.getMsg().equals("验证码发送成功")){
                        iCaptchaView.onCaptchaSuccess(captchaBean);
                    }
                },throwable->{
                    iCaptchaView.onCaptchaError(throwable.getMessage());
                });
    }


    @Override
    public void destoryView() {
        if(iCaptchaView!=null){
            iCaptchaView=null;
        }
    }



}
