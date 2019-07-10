package com.jdhd.qynovels.persenter.impl.bookshop;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.bookshop.RankBean;
import com.jdhd.qynovels.persenter.inter.bookshop.IRankPresenter;
import com.jdhd.qynovels.view.bookshop.IRankView;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IRankPresenterImpl implements IRankPresenter {
    private IRankView iRankView;

    public IRankPresenterImpl(IRankView iRankView) {
        this.iRankView = iRankView;
    }

    @Override
    public void loadData() {
        RxHttp.postForm(MyApp.Url.baseUrl+"rank")
                .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                .asParser(new SimpleParser<RankBean>(){})
                .subscribe(rankBean->{
                    if(rankBean.getCode()==200&&rankBean.getMsg().equals("请求成功")){
                        iRankView.onSuccess(rankBean);
                    }
                },throwable->{
                    iRankView.onError(throwable.getMessage());
                });
    }

    @Override
    public void destoryView() {
       if(iRankView!=null){
          iRankView=null;
       }
    }
}
