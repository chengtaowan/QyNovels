package com.jdhd.qynovels.persenter.impl.bookcase;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.bookcase.AddBookBean;
import com.jdhd.qynovels.module.bookcase.ReadEndBean;
import com.jdhd.qynovels.persenter.inter.bookcase.IBookInfoPresenter;
import com.jdhd.qynovels.persenter.inter.bookcase.IReadEndPresenter;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.bookcase.IAddBookRankView;
import com.jdhd.qynovels.view.bookcase.IReadEndView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IReadEndPresenterImpl implements IReadEndPresenter {
    private IReadEndView iReadEndView;
    private Context context;
    private String token;

    public IReadEndPresenterImpl(IReadEndView iReadEndView, Context context) {
        this.iReadEndView = iReadEndView;
        this.context = context;
    }

    private int id;

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
            RxHttp.postForm(MyApp.Url.baseUrl+"readEnd")
                    .addHeader("token",token)
                    .add(map)
                    .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                    .asParser(new SimpleParser<ReadEndBean>(){})
                    .subscribe(readEndBean->{
                        Log.e("qqq",readEndBean.getMsg());
                        if(readEndBean.getCode()==200&&readEndBean.getMsg().equals("请求成功")){
                            iReadEndView.onEndSuccess(readEndBean);
                        }
                    },throwable->{
                        iReadEndView.onEndError(throwable.getMessage());
                    });
        }
        else{
            RxHttp.postForm(MyApp.Url.baseUrl+"readEnd")
                    .add(map)
                    .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                    .asParser(new SimpleParser<ReadEndBean>(){})
                    .subscribe(readEndBean->{
                        Log.e("qqq",readEndBean.getMsg());
                        if(readEndBean.getCode()==200&&readEndBean.getMsg().equals("请求成功")){

                            iReadEndView.onEndSuccess(readEndBean);
                        }
                    },throwable->{
                        iReadEndView.onEndError(throwable.getMessage()+throwable.getCause());
                    });
        }

    }

    @Override
    public void destoryView() {
      if(iReadEndView!=null){
          iReadEndView=null;
      }
    }
}
