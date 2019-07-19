package com.jdhd.qynovels.persenter.impl.bookshop;

import android.content.Context;
import android.content.SharedPreferences;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.bookshop.RankBean;
import com.jdhd.qynovels.persenter.inter.bookshop.IRankPresenter;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.bookshop.IRankView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IRankPresenterImpl implements IRankPresenter {
    private IRankView iRankView;
    private Context context;
    private String token;

    public IRankPresenterImpl(IRankView iRankView, Context context) {
        this.iRankView = iRankView;
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
            RxHttp.postForm(MyApp.Url.baseUrl+"rank")
                    .addHeader("token",token)
                    .add(map)
                    .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                    .asParser(new SimpleParser<RankBean>(){})
                    .subscribe(rankBean->{
                        if(rankBean.getCode()==200&&rankBean.getMsg().equals("请求成功")){
                            iRankView.onSuccess(rankBean);
                        }
                    },throwable->{
                        iRankView.onError(throwable.getMessage());
                    });
        }
        else{
            RxHttp.postForm(MyApp.Url.baseUrl+"rank")
                    .add(map)
                    .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                    .asParser(new SimpleParser<RankBean>(){})
                    .subscribe(rankBean->{
                        if(rankBean.getCode()==200&&rankBean.getMsg().equals("请求成功")){
                            iRankView.onSuccess(rankBean);
                        }
                    },throwable->{
                        iRankView.onError(throwable.getMessage());
                    });
        }

    }

    @Override
    public void destoryView() {
       if(iRankView!=null){
          iRankView=null;
       }
    }
}
