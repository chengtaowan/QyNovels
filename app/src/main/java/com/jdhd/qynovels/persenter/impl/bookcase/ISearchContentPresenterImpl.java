package com.jdhd.qynovels.persenter.impl.bookcase;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.SearchContentBean;
import com.jdhd.qynovels.persenter.inter.bookcase.ISearchContentPresenter;
import com.jdhd.qynovels.view.bookshop.ISearchContentView;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class ISearchContentPresenterImpl implements ISearchContentPresenter {
    private ISearchContentView iSearchContentView;
    private String content;

    public ISearchContentPresenterImpl(ISearchContentView iSearchContentView) {
        this.iSearchContentView = iSearchContentView;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void loadData() {
        RxHttp.postForm(MyApp.Url.baseUrl+"searchContent")
                .add("content",content)
                .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                .asParser(new SimpleParser<SearchContentBean>(){})
                .subscribe(searchContentBean->{
                    if(searchContentBean.getCode()==200&&searchContentBean.getMsg().equals("请求成功")){
                        iSearchContentView.onSuccess(searchContentBean);
                    }
                },throwable->{
                    iSearchContentView.onError(throwable.getMessage());
                });
    }

    @Override
    public void destoryView() {
       if(iSearchContentView!=null){
          iSearchContentView=null;
       }
    }
}
