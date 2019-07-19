package com.jdhd.qynovels.persenter.impl.bookshop;

import android.content.Context;
import android.content.SharedPreferences;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.bookshop.ModuleBean;
import com.jdhd.qynovels.persenter.inter.bookshop.IModulePresenter;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.bookshop.IModuleView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IModulePresenterImpl implements IModulePresenter {
    private IModuleView iModuleView;
    private Context context;
    private int type;
    private String token;

    public IModulePresenterImpl(IModuleView iModuleView, Context context, int type) {
        this.iModuleView = iModuleView;
        this.context = context;
        this.type = type;
    }

    @Override
    public void loadData() {
        SharedPreferences preferences=context.getSharedPreferences("token", Context.MODE_PRIVATE);
        token = preferences.getString("token", "");
        int time= DeviceInfoUtils.getTime();
        Map<String,String> map=new HashMap<>();
        map.put("time",time+"");
        if(token!=null){
            map.put("token",token);
        }
        map.put("type",type+"");
        String compareTo = DeviceInfoUtils.getCompareTo(map);
        String sign=DeviceInfoUtils.md5(compareTo);
        map.put("sign",sign);
        if(token!=null){
            RxHttp.postForm(MyApp.Url.baseUrl+"module")
                    .addHeader("token",token)
                    .add(map)
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
        else{
            RxHttp.postForm(MyApp.Url.baseUrl+"module")
                    .add(map)
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

    }

    @Override
    public void destoryView() {
       if(iModuleView!=null){
           iModuleView=null;
       }
    }
}
