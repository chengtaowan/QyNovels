package com.jdhd.qynovels.persenter.impl.bookshop;

import android.content.Context;
import android.content.SharedPreferences;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.bookshop.RankContentBean;
import com.jdhd.qynovels.persenter.inter.bookshop.IRankContentPresenter;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.bookshop.IRankContentView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IRankContentPresenterImpl implements IRankContentPresenter {
    private IRankContentView iRankContentView;
    private int id=0;
    private Context context;
    private String token;

    public IRankContentPresenterImpl(IRankContentView iRankContentView, Context context) {
        this.iRankContentView = iRankContentView;
        this.context = context;
    }


    public void setId(int id) {
        this.id = id;
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
        map.put("id",id+"");
        String compareTo = DeviceInfoUtils.getCompareTo(map);
        String sign=DeviceInfoUtils.md5(compareTo);
        map.put("sign",sign);
        if(token!=null){
            RxHttp.postForm(MyApp.Url.baseUrl+"rankContent")
                    .addHeader("token",token)
                    .add(map)
                    .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                    .asParser(new SimpleParser<RankContentBean>(){})
                    .subscribe(rankContentBean->{
                        if(rankContentBean.getCode()==200&&rankContentBean.getMsg().equals("请求成功")){
                            iRankContentView.onSuccess(rankContentBean);
                        }
                    },throwable->{
                        iRankContentView.onError(throwable.getMessage());
                    });
        }
        else{
            RxHttp.postForm(MyApp.Url.baseUrl+"rankContent")
                    .add(map)
                    .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                    .asParser(new SimpleParser<RankContentBean>(){})
                    .subscribe(rankContentBean->{
                        if(rankContentBean.getCode()==200&&rankContentBean.getMsg().equals("请求成功")){
                            iRankContentView.onSuccess(rankContentBean);
                        }
                    },throwable->{
                        iRankContentView.onError(throwable.getMessage());
                    });
        }

    }

    @Override
    public void destoryView() {
       if(iRankContentView!=null){
           iRankContentView=null;
       }
    }
}
