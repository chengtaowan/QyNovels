package com.jdhd.qynovels.persenter.impl.bookshop;

import android.content.Context;
import android.content.SharedPreferences;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.bookshop.ShopBean;
import com.jdhd.qynovels.persenter.inter.bookshop.IJxPresenter;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.bookshop.IJxView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IJxPresenterImpl implements IJxPresenter {
    private IJxView iJxView;
    private int type;
    private Context context;
    private String token;

    public IJxPresenterImpl(IJxView iJxView, int type, Context context) {
        this.iJxView = iJxView;
        this.type = type;
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
        map.put("moduleId",type+"");
        String compareTo = DeviceInfoUtils.getCompareTo(map);
        String sign=DeviceInfoUtils.md5(compareTo);
        map.put("sign",sign);
        if(token!=null){
            RxHttp.postForm(MyApp.Url.baseUrl+"moduleContent")
                    .addHeader("token",token)
                    .add(map)
                    .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                    .asParser(new SimpleParser<ShopBean>(){})
                    .subscribe(shopBean->{
                        if(shopBean.getCode()==200&&shopBean.getMsg().equals("请求成功")){
                            iJxView.onSuccess(shopBean);
                        }
                    },throwable->{
                        iJxView.onError(throwable.getMessage());
                    });
        }
        else{
            RxHttp.postForm(MyApp.Url.baseUrl+"moduleContent")
                    .add(map)
                    .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                    .asParser(new SimpleParser<ShopBean>(){})
                    .subscribe(shopBean->{
                        if(shopBean.getCode()==200&&shopBean.getMsg().equals("请求成功")){
                            iJxView.onSuccess(shopBean);
                        }
                    },throwable->{
                        iJxView.onError(throwable.getMessage());
                    });
        }

    }

    @Override
    public void destoryView() {
       if(iJxView!=null){
          iJxView=null;
       }
    }
}
