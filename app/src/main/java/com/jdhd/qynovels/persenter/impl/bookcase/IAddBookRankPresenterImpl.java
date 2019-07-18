package com.jdhd.qynovels.persenter.impl.bookcase;

import android.util.Log;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.bookcase.AddBookBean;
import com.jdhd.qynovels.persenter.inter.bookcase.IBookInfoPresenter;
import com.jdhd.qynovels.view.bookcase.IAddBookRankView;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IAddBookRankPresenterImpl implements IBookInfoPresenter {
    private IAddBookRankView iAddBookRankView;

    public IAddBookRankPresenterImpl(IAddBookRankView iAddBookRankView) {
        this.iAddBookRankView = iAddBookRankView;
    }

    private int id;

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void loadData() {
        RxHttp.postForm(MyApp.Url.baseUrl+"addBookrack")
                .add("bookId",id)
                .add("backlistId",0)
                .add("backlistPercent",0)
                .add("readStatus",10)
                .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                .asParser(new SimpleParser<AddBookBean>(){})
                .subscribe(addBookBean->{
                    Log.e("qqq",addBookBean.getMsg());
                    if(addBookBean.getCode()==200&&addBookBean.getMsg().equals("请求成功")){

                        iAddBookRankView.onSuccess(addBookBean);
                    }
                },throwable->{
                    iAddBookRankView.onAddError(throwable.getMessage());
                });
    }

    @Override
    public void destoryView() {
      if(iAddBookRankView!=null){
          iAddBookRankView=null;
      }
    }
}
