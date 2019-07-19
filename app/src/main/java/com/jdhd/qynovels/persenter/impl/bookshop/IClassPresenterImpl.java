package com.jdhd.qynovels.persenter.impl.bookshop;

import android.content.Context;
import android.content.SharedPreferences;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.bookshop.ClassBean;
import com.jdhd.qynovels.persenter.inter.bookshop.IClassPresenter;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.bookshop.IClassView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IClassPresenterImpl implements IClassPresenter {
    private IClassView iClassView;
    private Context context;
    private String token;

    public IClassPresenterImpl(IClassView iClassView, Context context) {
        this.iClassView = iClassView;
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
        if(token!=null){
            RxHttp.postForm(MyApp.Url.baseUrl+"class")
                    .addHeader("token",token)
                    .add(map)
                    .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                    .asParser(new SimpleParser<ClassBean>(){})
                    .subscribe(classBean->{
                        if(classBean.getCode()==200&&classBean.getMsg().equals("请求成功")){
                            iClassView.onSuccess(classBean);
                        }
                    },throwable->{
                        iClassView.onError(throwable.getMessage());
                    });
        }
        else{
            RxHttp.postForm(MyApp.Url.baseUrl+"class")
                    .add(map)
                    .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                    .asParser(new SimpleParser<ClassBean>(){})
                    .subscribe(classBean->{
                        if(classBean.getCode()==200&&classBean.getMsg().equals("请求成功")){
                            iClassView.onSuccess(classBean);
                        }
                    },throwable->{
                        iClassView.onError(throwable.getMessage());
                    });
        }

    }

    @Override
    public void destoryView() {
       if(iClassView!=null){
           iClassView=null;
       }
    }
}
