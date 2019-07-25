package com.jdhd.qynovels.persenter.impl.bookcase;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.bookcase.CaseBean;
import com.jdhd.qynovels.persenter.inter.bookcase.ICasePresenter;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.bookcase.ICaseView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class ICasePresenterImpl implements ICasePresenter {
    private ICaseView iCaseView;
    private String token;
    private Context context;

    public ICasePresenterImpl(ICaseView iCaseView, Context context) {
        this.iCaseView = iCaseView;
        this.context = context;
    }

    @Override
    public void loadData() {
        SharedPreferences preferences=context.getSharedPreferences("token", Context.MODE_PRIVATE);
        token = preferences.getString("token", "");
        Log.e("token",token);
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
            RxHttp.get(MyApp.Url.baseUrl+"bookrack")
                    .addHeader("token",token)
                    .add(map)
                    .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                    .asParser(new SimpleParser<CaseBean>(){})
                    .subscribe(caseBean->{
                        Log.e("case",caseBean.getCode()+"--"+caseBean.getMsg());
                        if(caseBean.getCode()==200&&caseBean.getMsg().equals("请求成功")){
                            iCaseView.onSuccess(caseBean);
                        }
                    },throwable->{
                        if(throwable!=null){
                            iCaseView.onError(throwable.getMessage());
                        }
                        else{
                            return;
                        }

                    });
        }
        else{
            RxHttp.get(MyApp.Url.baseUrl+"bookrack")
                    .addHeader("token",token)
                    .add(map)
                    .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                    .asParser(new SimpleParser<CaseBean>(){})
                    .subscribe(caseBean->{
                        if(caseBean.getCode()==200&&caseBean.getMsg().equals("请求成功")){
                            iCaseView.onSuccess(caseBean);
                        }
                    },throwable->{
                        if(throwable!=null){
                            iCaseView.onError(throwable.getMessage());
                        }
                        else{
                            return;
                        }

                    });
        }

    }

    @Override
    public void destoryView() {
       if(iCaseView!=null){
           iCaseView=null;
       }
    }
}
