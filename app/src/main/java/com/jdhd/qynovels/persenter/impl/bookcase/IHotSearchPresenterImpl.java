package com.jdhd.qynovels.persenter.impl.bookcase;

import android.content.Context;
import android.content.SharedPreferences;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.bookcase.HotSearchBean;
import com.jdhd.qynovels.persenter.inter.bookcase.IHotSearchPresenter;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.bookcase.IHotSearchView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IHotSearchPresenterImpl implements IHotSearchPresenter {
    private IHotSearchView iHotSearchView;
    private Context context;
    private String token;

    public IHotSearchPresenterImpl(IHotSearchView iHotSearchView, Context context) {
        this.iHotSearchView = iHotSearchView;
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
            RxHttp.postForm(MyApp.Url.baseUrl+"searchHot")
                    .addHeader("token",token)
                    .add(map)
                    .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                    .asParser(new SimpleParser<HotSearchBean>(){})
                    .subscribe(hotSearchBean->{
                        //if(hotSearchBean.getCode()==200&&hotSearchBean.getMsg().equals("请求成功")){
                            iHotSearchView.onSuccess(hotSearchBean);
                       // }
                    },throwable->{
                        if(!throwable.getMessage().equals("")){
                            iHotSearchView.onError(throwable.getMessage());
                        }

                    });
        }
        else{
            RxHttp.postForm(MyApp.Url.baseUrl+"searchHot")
                    .add(map)
                    .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                    .asParser(new SimpleParser<HotSearchBean>(){})
                    .subscribe(hotSearchBean->{
                        if(hotSearchBean.getCode()==200&&hotSearchBean.getMsg().equals("请求成功")){
                            iHotSearchView.onSuccess(hotSearchBean);
                        }
                    },throwable->{
                        if(!throwable.getMessage().equals("")){
                            iHotSearchView.onError(throwable.getMessage());
                        }
                    });
        }

    }

    @Override
    public void destoryView() {
       if(iHotSearchView !=null){
           iHotSearchView =null;
       }
    }
}
