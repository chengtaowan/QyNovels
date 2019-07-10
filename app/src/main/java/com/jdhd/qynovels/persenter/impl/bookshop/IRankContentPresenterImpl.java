package com.jdhd.qynovels.persenter.impl.bookshop;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.bookshop.RankContentBean;
import com.jdhd.qynovels.persenter.inter.bookshop.IRankContentPresenter;
import com.jdhd.qynovels.view.bookshop.IRankContentView;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IRankContentPresenterImpl implements IRankContentPresenter {
    private IRankContentView iRankContentView;
    private int id=0;

    public IRankContentPresenterImpl(IRankContentView iRankContentView) {
        this.iRankContentView = iRankContentView;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void loadData() {

        RxHttp.postForm(MyApp.Url.baseUrl+"rankContent")
                .add("id",id)
                .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                .asParser(new SimpleParser<RankContentBean>(){})
                .subscribe(rankContentBean->{
                    if(rankContentBean.getCode()==200&&rankContentBean.getMsg().equals("请求成功")){
                        iRankContentView.onSuccess(rankContentBean);
                    }
                },throwable->{
                    iRankContentView.onError(throwable.getMessage());
                });
    }

    @Override
    public void destoryView() {
       if(iRankContentView!=null){
           iRankContentView=null;
       }
    }
}
