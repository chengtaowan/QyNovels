package com.jdhd.qynovels.persenter.impl.bookcase;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.bookcase.CaseBean;
import com.jdhd.qynovels.persenter.inter.bookcase.ICasePresenter;
import com.jdhd.qynovels.view.bookcase.ICaseView;

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
        RxHttp.get(MyApp.Url.baseUrl+"bookrack")
                .addHeader("token",token)
                .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                .asParser(new SimpleParser<CaseBean>(){})
                .subscribe(caseBean->{
                    if(caseBean.getCode()==200&&caseBean.getMsg().equals("请求成功")){
                        iCaseView.onSuccess(caseBean);
                    }
                },throwable->{
                    iCaseView.onError(throwable.getMessage());
                });
    }

    @Override
    public void destoryView() {
       if(iCaseView!=null){
           iCaseView=null;
       }
    }
}
