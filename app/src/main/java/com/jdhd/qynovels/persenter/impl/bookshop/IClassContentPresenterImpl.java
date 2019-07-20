package com.jdhd.qynovels.persenter.impl.bookshop;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.bookshop.ClassBean;
import com.jdhd.qynovels.module.bookshop.ClassContentBean;
import com.jdhd.qynovels.persenter.inter.bookshop.IClassContentPresenter;
import com.jdhd.qynovels.persenter.inter.bookshop.IClassPresenter;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.bookshop.IClassContentView;
import com.jdhd.qynovels.view.bookshop.IClassView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IClassContentPresenterImpl implements IClassContentPresenter {
    private IClassContentView iClassContentView;
    private Context context;
    private String token;
    private int searchType;
    private int sortType;
    private int page;
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public void setSearchType(int searchType) {
        this.searchType = searchType;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public IClassContentPresenterImpl(IClassContentView iClassContentView, Context context) {
        this.iClassContentView = iClassContentView;
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
        map.put("searchType",searchType+"");
        map.put("sortType",sortType+"");
        map.put("page",page+"");
        map.put("id",id+"");
        String compareTo = DeviceInfoUtils.getCompareTo(map);
        String sign=DeviceInfoUtils.md5(compareTo);
        map.put("sign",sign);

        Log.e("token",token);
        Log.e("time",time+"");
        Log.e("id",id+"");
        Log.e("searchType",searchType+"");
        Log.e("sortType",sortType+"");
        Log.e("page",page+"");
        Log.e("sign",sign);
        if(token!=null){
            RxHttp.postForm(MyApp.Url.baseUrl+"classContent")
                    .addHeader("token",token)
                    .add(map)
                    .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                    .asParser(new SimpleParser<ClassContentBean>(){})
                    .subscribe(classContentBean->{
                        Log.e("code",classContentBean.getCode()+""+classContentBean.getMsg());
                        if(classContentBean.getCode()==200&&classContentBean.getMsg().equals("请求成功")){
                            iClassContentView.onSuccess(classContentBean);
                        }
                    },throwable->{
                        iClassContentView.onError(throwable.getMessage());
                    });
        }
        else{
            RxHttp.postForm(MyApp.Url.baseUrl+"classContent")
                    .add(map)
                    .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                    .asParser(new SimpleParser<ClassContentBean>(){})
                    .subscribe(classContentBean->{
                        if(classContentBean.getCode()==200&&classContentBean.getMsg().equals("请求成功")){
                            iClassContentView.onSuccess(classContentBean);
                        }
                    },throwable->{
                        iClassContentView.onError(throwable.getMessage());
                    });
        }

    }

    @Override
    public void destoryView() {
       if(iClassContentView!=null){
           iClassContentView=null;
       }
    }
}
