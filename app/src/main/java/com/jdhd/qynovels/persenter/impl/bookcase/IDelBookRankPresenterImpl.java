package com.jdhd.qynovels.persenter.impl.bookcase;

import android.util.Log;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.bookcase.DelBookRackBean;
import com.jdhd.qynovels.persenter.inter.bookcase.IBookInfoPresenter;
import com.jdhd.qynovels.view.bookcase.IDelBookRankView;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IDelBookRankPresenterImpl implements IBookInfoPresenter {
    private IDelBookRankView iDelBookRankView;

    public IDelBookRankPresenterImpl(IDelBookRankView iDelBookRankView) {
        this.iDelBookRankView = iDelBookRankView;
    }

    private int id;

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void loadData() {
        RxHttp.postForm(MyApp.Url.baseUrl+"delBookrack")
                .add("id",id)
                .add("type",10)
                .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                .asParser(new SimpleParser<DelBookRackBean>(){})
                .subscribe(delBookRackBean->{
                    Log.e("qqq",delBookRackBean.getMsg());
                    if(delBookRackBean.getCode()==200&&delBookRackBean.getMsg().equals("请求成功")){
                        iDelBookRankView.onSuccess(delBookRackBean);
                    }
                },throwable->{
                    iDelBookRankView.onAddError(throwable.getMessage());
                });
    }

    @Override
    public void destoryView() {
      if(iDelBookRankView!=null){
          iDelBookRankView=null;
      }
    }
}
