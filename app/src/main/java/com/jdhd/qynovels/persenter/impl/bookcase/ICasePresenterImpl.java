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
                    .removeAllHeader("User-Agent")
                    .addHeader("token",token)
                    //.addHeader("User-Agent","Mozilla/5.0 (Linux; Android 4.4.2; MX4 Build/KOT49H) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Mobile Crosswalk/10.39.235.16 Mobile Safari/537.36")
                    .add(map)
                    .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                    .asParser(new SimpleParser<CaseBean>(){})
                    .subscribe(caseBean->{
                        Log.e("case",caseBean.getCode()+"--"+caseBean.getMsg());
                        //if(caseBean.getCode()==200&&caseBean.getMsg().equals("请求成功")){
                            iCaseView.onSuccess(caseBean);
                        //}
                    },throwable->{
                        if(throwable.getMessage()!=null){
                            iCaseView.onError(throwable.getMessage());
                        }
                        else{
                            return;
                        }

                    });
        }
        else{
            RxHttp.get(MyApp.Url.baseUrl+"bookrack")
                    .removeAllHeader("User-Agent")
                    .addHeader("token",token)
                    .addHeader("User-Agent","Mozilla/5.0 (Linux; Android 4.4.2; MX4 Build/KOT49H) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Mobile Crosswalk/10.39.235.16 Mobile Safari/537.36")
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
