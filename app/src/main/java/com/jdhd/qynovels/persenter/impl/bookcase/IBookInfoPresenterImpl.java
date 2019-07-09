package com.jdhd.qynovels.persenter.impl.bookcase;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.BookInfoBean;
import com.jdhd.qynovels.persenter.inter.bookcase.IBookInfoPresenter;
import com.jdhd.qynovels.view.bookcase.IBookInfoView;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IBookInfoPresenterImpl implements IBookInfoPresenter {
    private IBookInfoView iBookInfoView;

    public IBookInfoPresenterImpl(IBookInfoView iBookInfoView) {
        this.iBookInfoView = iBookInfoView;
    }
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void loadData() {
        RxHttp.postForm(MyApp.Url.baseUrl+"book")
                .add("id",id)
                .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                .asParser(new SimpleParser<BookInfoBean>(){})
                .subscribe(bookInfoBean->{
                    if(bookInfoBean.getCode()==200&&bookInfoBean.getMsg().equals("请求成功")){
                        iBookInfoView.onSuccess(bookInfoBean);
                    }
                },throwable->{
                    iBookInfoView.onError(throwable.getMessage());
                });
    }

    @Override
    public void destoryView() {
      if(iBookInfoView!=null){
          iBookInfoView=null;
      }
    }
}
