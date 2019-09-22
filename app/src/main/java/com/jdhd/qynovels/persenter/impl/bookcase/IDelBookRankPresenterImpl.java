package com.jdhd.qynovels.persenter.impl.bookcase;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.bookcase.DelBookRackBean;
import com.jdhd.qynovels.persenter.inter.bookcase.IBookInfoPresenter;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.bookcase.IDelBookRankView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IDelBookRankPresenterImpl implements IBookInfoPresenter {
    private IDelBookRankView iDelBookRankView;
    private Context context;
    private String token;

    public IDelBookRankPresenterImpl(IDelBookRankView iDelBookRankView, Context context) {
        this.iDelBookRankView = iDelBookRankView;
        this.context = context;
    }

    private String id;

    public void setId(String id) {
        this.id = id;
    }
    private int type;

    public void setType(int type) {
        this.type = type;
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
        map.put("type",type+"");
        String compareTo = DeviceInfoUtils.getCompareTo(map);
        String sign=DeviceInfoUtils.md5(compareTo);
        map.put("sign",sign);
        Log.e("id--",id+"");
        if(token!=null){
            RxHttp.postForm(MyApp.Url.baseUrl+"delBookrack")
                    .addHeader("token",token)
                    .add(map)
                    .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                    .asParser(new SimpleParser<DelBookRackBean>(){})
                    .subscribe(delBookRackBean->{
                        Log.e("delbook",delBookRackBean.getMsg()+"---"+delBookRackBean.getCode());
                        //if(delBookRackBean.getCode()==200&&delBookRackBean.getMsg().equals("请求成功")){
                            iDelBookRankView.onSuccess(delBookRackBean);
                       // }
                    },throwable->{
                        iDelBookRankView.onAddError(throwable.getMessage());
                    });
        }
        else{
            RxHttp.postForm(MyApp.Url.baseUrl+"delBookrack")
                    .add(map)
                    .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                    .asParser(new SimpleParser<DelBookRackBean>(){})
                    .subscribe(delBookRackBean->{
                        Log.e("qqq",delBookRackBean.getMsg());
                        //if(delBookRackBean.getCode()==200&&delBookRackBean.getMsg().equals("请求成功")){
                            iDelBookRankView.onSuccess(delBookRackBean);
                       // }
                    },throwable->{
                        iDelBookRankView.onAddError(throwable.getMessage());
                    });
        }
    }

    @Override
    public void destoryView() {
      if(iDelBookRankView!=null){
          iDelBookRankView=null;
      }
    }
}
