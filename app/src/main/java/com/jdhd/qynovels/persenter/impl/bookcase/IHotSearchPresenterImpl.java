package com.jdhd.qynovels.persenter.impl.bookcase;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.HotSearchBean;
import com.jdhd.qynovels.persenter.inter.bookcase.IHotSearchPresenter;
import com.jdhd.qynovels.view.bookcase.IHotSearchView;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IHotSearchPresenterImpl implements IHotSearchPresenter {
    private IHotSearchView iHotSearchView;

    public IHotSearchPresenterImpl(IHotSearchView iHotSearchView) {
        this.iHotSearchView = iHotSearchView;
    }

    @Override
    public void loadData() {
        RxHttp.get(MyApp.Url.baseUrl+"searchHot")
                .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                .asParser(new SimpleParser<HotSearchBean>(){})
                .subscribe(hotSearchBean->{
                    if(hotSearchBean.getCode()==200&&hotSearchBean.getMsg().equals("请求成功")){
                        iHotSearchView.onSuccess(hotSearchBean);
                    }
                },throwable->{
                    iHotSearchView.onError(throwable.getMessage());
                });
    }

    @Override
    public void destoryView() {
       if(iHotSearchView !=null){
           iHotSearchView =null;
       }
    }
}
