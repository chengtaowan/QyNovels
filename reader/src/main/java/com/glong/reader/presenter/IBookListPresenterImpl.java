package com.glong.reader.presenter;

import com.glong.reader.activities.MyApp;
import com.glong.reader.entry.BookListBean;
import com.glong.reader.view.IBookListView;
import okhttp3.CacheControl;

import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IBookListPresenterImpl implements IBookListPresenter {
    private IBookListView iBookListView;

    public IBookListPresenterImpl(IBookListView iBookListView) {
        this.iBookListView = iBookListView;
    }

    private int id;

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void loadData() {
        RxHttp.postForm(MyApp.Url.baseUrl+"backlist")
                .add("id",id)
                .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                .asParser(new SimpleParser<BookListBean>(){})
                .subscribe(bookListBean->{
                    if(bookListBean.getCode()==200&&bookListBean.getMsg().equals("请求成功")){
                        iBookListView.onSuccess(bookListBean);
                    }
                },throwable->{
                    iBookListView.onError(throwable.getMessage());
                });
    }

    @Override
    public void destoryView() {
      if(iBookListView!=null){
          iBookListView=null;
      }
    }
}
