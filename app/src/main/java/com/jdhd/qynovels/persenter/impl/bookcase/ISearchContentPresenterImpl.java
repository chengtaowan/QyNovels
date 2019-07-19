package com.jdhd.qynovels.persenter.impl.bookcase;

import android.content.Context;
import android.content.SharedPreferences;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.bookcase.SearchContentBean;
import com.jdhd.qynovels.persenter.inter.bookcase.ISearchContentPresenter;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.bookshop.ISearchContentView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class ISearchContentPresenterImpl implements ISearchContentPresenter {
    private ISearchContentView iSearchContentView;
    private String content;
    private Context context;
    private String token;

    public ISearchContentPresenterImpl(ISearchContentView iSearchContentView, Context context) {
        this.iSearchContentView = iSearchContentView;
        this.context = context;
    }

    public void setContent(String content) {
        this.content = content;
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
        String compareTo = DeviceInfoUtils.getCompareTo(map);
        String sign=DeviceInfoUtils.md5(compareTo);
        map.put("sign",sign);
        if(token!=null){
            RxHttp.postForm(MyApp.Url.baseUrl+"searchContent")
                    .addHeader("token",token)
                    .add(map)
                    .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                    .asParser(new SimpleParser<SearchContentBean>(){})
                    .subscribe(searchContentBean->{
                        if(searchContentBean.getCode()==200&&searchContentBean.getMsg().equals("请求成功")){
                            iSearchContentView.onSuccess(searchContentBean);
                        }
                    },throwable->{
                        iSearchContentView.onError(throwable.getMessage());
                    });
        }
        else{
            RxHttp.postForm(MyApp.Url.baseUrl+"searchContent")
                    .add(map)
                    .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                    .asParser(new SimpleParser<SearchContentBean>(){})
                    .subscribe(searchContentBean->{
                        if(searchContentBean.getCode()==200&&searchContentBean.getMsg().equals("请求成功")){
                            iSearchContentView.onSuccess(searchContentBean);
                        }
                    },throwable->{
                        iSearchContentView.onError(throwable.getMessage());
                    });
        }

    }

    @Override
    public void destoryView() {
       if(iSearchContentView!=null){
          iSearchContentView=null;
       }
    }
}
