package com.jdhd.qynovels.persenter.impl.bookcase;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.bookcase.AddBookBean;
import com.jdhd.qynovels.module.bookcase.ConfigBean;
import com.jdhd.qynovels.persenter.inter.bookcase.IBookInfoPresenter;
import com.jdhd.qynovels.persenter.inter.bookcase.IConfigPresenter;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.bookcase.IAddBookRankView;
import com.jdhd.qynovels.view.bookcase.IConfigView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IConfigPresenterImpl implements IConfigPresenter {
    private IConfigView iConfigView;
    private Context context;
    private String token;

    public IConfigPresenterImpl(IConfigView iConfigView, Context context) {
        this.iConfigView = iConfigView;
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
        String compareTo = DeviceInfoUtils.getCompareTo(map);
        String sign=DeviceInfoUtils.md5(compareTo);
        map.put("sign",sign);
        if(token!=null){
            RxHttp.postForm(MyApp.Url.baseUrl+"adConfig")
                    .addHeader("token",token)
                    .add(map)
                    .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                    .asParser(new SimpleParser<ConfigBean>(){})
                    .subscribe(addBookBean->{
                        Log.e("qqq",addBookBean.getMsg());
                        if(addBookBean.getCode()==200&&addBookBean.getMsg().equals("请求成功")){
                            iConfigView.onConfigSuccess(addBookBean);
                        }
                    },throwable->{
                        iConfigView.onConfigError(throwable.getMessage());
                    });
        }
        else{
            RxHttp.postForm(MyApp.Url.baseUrl+"adConfig")
                    .add(map)
                    .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                    .asParser(new SimpleParser<ConfigBean>(){})
                    .subscribe(addBookBean->{
                        Log.e("qqq",addBookBean.getMsg());
                        if(addBookBean.getCode()==200&&addBookBean.getMsg().equals("请求成功")){

                            iConfigView.onConfigSuccess(addBookBean);
                        }
                    },throwable->{
                        iConfigView.onConfigError(throwable.getMessage()+throwable.getCause());
                    });
        }

    }

    @Override
    public void destoryView() {
      if(iConfigView!=null){
          iConfigView=null;
      }
    }
}
