package com.jdhd.qynovels.persenter.impl.bookshop;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.bookshop.ShopBean;
import com.jdhd.qynovels.persenter.inter.bookshop.IJxPresenter;
import com.jdhd.qynovels.view.bookshop.IJxView;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IJxPresenterImpl implements IJxPresenter {
    private IJxView iJxView;
    private int type;

    public IJxPresenterImpl(IJxView iJxView, int type) {
        this.iJxView = iJxView;
        this.type = type;
    }

    @Override
    public void loadData() {
        RxHttp.postForm(MyApp.Url.baseUrl+"moduleContent")
                .add("moduleId",type)
                .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                .asParser(new SimpleParser<ShopBean>(){})
                .subscribe(shopBean->{
                    if(shopBean.getCode()==200&&shopBean.getMsg().equals("请求成功")){
                        iJxView.onSuccess(shopBean);
                    }
                },throwable->{
                    iJxView.onError(throwable.getMessage());
                });
    }

    @Override
    public void destoryView() {
       if(iJxView!=null){
          iJxView=null;
       }
    }
}
