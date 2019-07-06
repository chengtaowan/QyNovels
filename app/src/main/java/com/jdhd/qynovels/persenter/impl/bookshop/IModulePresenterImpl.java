package com.jdhd.qynovels.persenter.impl.bookshop;

import android.content.Context;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.ModuleBean;
import com.jdhd.qynovels.persenter.inter.bookshop.IModulePresenter;
import com.jdhd.qynovels.view.bookshop.IModuleView;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IModulePresenterImpl implements IModulePresenter {
    private IModuleView iModuleView;
    private Context context;

    public IModulePresenterImpl(IModuleView iModuleView, Context context) {
        this.iModuleView = iModuleView;
        this.context = context;
    }

    @Override
    public void loadData() {
        RxHttp.postForm(MyApp.Url.baseUrl+"module")
                .add("type","10")
                .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                .asParser(new SimpleParser<ModuleBean>(){})
                .subscribe(moduleBean->{
                    if(moduleBean.getCode()==200&&moduleBean.getMsg().equals("请求成功")){
                        iModuleView.onSuccess(moduleBean);
                    }
                },throwable->{
                    iModuleView.onError(throwable.getMessage());
                });
    }

    @Override
    public void destoryView() {
       if(iModuleView!=null){
           iModuleView=null;
       }
    }
}
