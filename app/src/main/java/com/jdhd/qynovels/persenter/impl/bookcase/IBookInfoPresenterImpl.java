package com.jdhd.qynovels.persenter.impl.bookcase;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.bookcase.BookInfoBean;
import com.jdhd.qynovels.persenter.inter.bookcase.IBookInfoPresenter;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.bookcase.IBookInfoView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IBookInfoPresenterImpl implements IBookInfoPresenter {
    private IBookInfoView iBookInfoView;
    private Context context;
    private String token;

    public IBookInfoPresenterImpl(IBookInfoView iBookInfoView, Context context) {
        this.iBookInfoView = iBookInfoView;
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
        Log.e("token",token);
        Log.e("id",id+"");
        Log.e("time",time+"");
        Log.e("sign",sign);
        if(token!=null){
            RxHttp.postForm(MyApp.Url.baseUrl+"book")
                    .addHeader("token",token)
                    .add(map)
                    .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                    .asParser(new SimpleParser<BookInfoBean>(){})
                    .subscribe(bookInfoBean->{
                        Log.e("bookinfo",bookInfoBean.getCode()+"--"+bookInfoBean.getMsg());
                        if(bookInfoBean.getCode()==200&&bookInfoBean.getMsg().equals("请求成功")){
                            iBookInfoView.onBookinfoSuccess(bookInfoBean);
                        }
                    },throwable->{
                        iBookInfoView.onBookinfoError(throwable.getMessage());
                    });
        }
        else{
            RxHttp.postForm(MyApp.Url.baseUrl+"book")
                    .add(map)
                    .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                    .asParser(new SimpleParser<BookInfoBean>(){})
                    .subscribe(bookInfoBean->{
                        if(bookInfoBean.getCode()==200&&bookInfoBean.getMsg().equals("请求成功")){
                            iBookInfoView.onBookinfoSuccess(bookInfoBean);
                        }
                    },throwable->{
                        iBookInfoView.onBookinfoError(throwable.getMessage());
                    });
        }

    }

    @Override
    public void destoryView() {
      if(iBookInfoView!=null){
          iBookInfoView=null;
      }
    }
}
