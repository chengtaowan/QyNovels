package com.glong.reader.presenter;

import com.glong.reader.activities.MyApp;
import com.glong.reader.entry.BookContentBean;
import com.glong.reader.entry.BookListBean;
import com.glong.reader.view.IBookContentView;
import com.glong.reader.view.IBookListView;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IBookContentPresenterImpl implements IBookContentPresenter {
    private IBookContentView bookContentView;

    public IBookContentPresenterImpl(IBookContentView bookContentView) {
        this.bookContentView = bookContentView;
    }

    private int id;

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void loadData() {
        RxHttp.postForm(MyApp.Url.baseUrl+"bookContent")
                .add("id",id)
                .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                .asParser(new SimpleParser<BookContentBean>(){})
                .subscribe(bookListBean->{
                    if(bookListBean.getCode()==200&&bookListBean.getMsg().equals("请求成功")){
                        bookContentView.onBookSuccess(bookListBean);
                    }
                },throwable->{
                    bookContentView.onBookError(throwable.getMessage());
                });
    }

    @Override
    public void destoryView() {
      if(bookContentView!=null){
          bookContentView=null;
      }
    }
}
