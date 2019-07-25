package com.glong.reader.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.glong.reader.activities.MyApp;
import com.glong.reader.entry.BookContentBean;
import com.glong.reader.entry.ReadAwardBean;
import com.glong.reader.util.DeviceInfoUtils;
import com.glong.reader.view.IBookContentView;
import com.glong.reader.view.IReadAwardView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CacheControl;

import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IReadAwardPresenterImpl implements IReadAwardPresenter {
    private IReadAwardView iReadAwardView;
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    private String token="";

    public void setToken(String token) {
        this.token = token;
    }

    public IReadAwardPresenterImpl(IReadAwardView iReadAwardView) {
        this.iReadAwardView = iReadAwardView;
    }


    @Override
    public void loadData() {
        int time= DeviceInfoUtils.getTime();
        Map<String,String> map=new HashMap<>();
        map.put("time",time+"");
        if(!token.equals("")){
            map.put("token",token);
        }
        String compareTo = DeviceInfoUtils.getCompareTo(map);
        String sign=DeviceInfoUtils.md5(compareTo);
        map.put("sign",sign);
        if(!token.equals("")){
            RxHttp.postForm(MyApp.Url.baseUrl+"readAward")
                    .addHeader("token",token)
                    .add(map)
                    .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                    .asParser(new SimpleParser<ReadAwardBean>(){})
                    .subscribe(readAwardBean->{
                        iReadAwardView.onReadSuccess(readAwardBean);
                    },throwable->{
                        iReadAwardView.onReadError(throwable.getMessage());
                    });
        }
        else{
            RxHttp.postForm(MyApp.Url.baseUrl+"readAward")
                    .add(map)
                    .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                    .asParser(new SimpleParser<ReadAwardBean>(){})
                    .subscribe(readAwardBean->{
                       iReadAwardView.onReadSuccess(readAwardBean);
                    },throwable->{
                        iReadAwardView.onReadError(throwable.getMessage());
                    });
        }

    }

    @Override
    public void destoryView() {
      if(iReadAwardView!=null){
          iReadAwardView=null;
      }
    }
}
