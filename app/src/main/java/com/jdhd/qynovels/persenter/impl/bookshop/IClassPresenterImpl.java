package com.jdhd.qynovels.persenter.impl.bookshop;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.bookshop.ClassBean;
import com.jdhd.qynovels.persenter.inter.bookshop.IClassPresenter;
import com.jdhd.qynovels.view.bookshop.IClassView;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IClassPresenterImpl implements IClassPresenter {
    private IClassView iClassView;

    public IClassPresenterImpl(IClassView iClassView) {
        this.iClassView = iClassView;
    }

    @Override
    public void loadData() {
        RxHttp.postForm(MyApp.Url.baseUrl+"class")
                .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                .asParser(new SimpleParser<ClassBean>(){})
                .subscribe(classBean->{
                    if(classBean.getCode()==200&&classBean.getMsg().equals("请求成功")){
                        iClassView.onSuccess(classBean);
                    }
                },throwable->{
                    iClassView.onError(throwable.getMessage());
                });
    }

    @Override
    public void destoryView() {
       if(iClassView!=null){
           iClassView=null;
       }
    }
}
