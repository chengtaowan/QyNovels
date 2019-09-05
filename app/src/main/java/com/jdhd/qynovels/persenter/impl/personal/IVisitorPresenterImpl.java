package com.jdhd.qynovels.persenter.impl.personal;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.TokenBean;
import com.jdhd.qynovels.module.personal.VisitorBean;
import com.jdhd.qynovels.persenter.inter.personal.ILoginPresenter;
import com.jdhd.qynovels.persenter.inter.personal.IVisitorPresenter;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.personal.ILoginView;
import com.jdhd.qynovels.view.personal.IVisitorView;

import java.util.HashMap;
import java.util.Map;

import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IVisitorPresenterImpl implements IVisitorPresenter {
    private IVisitorView iVisitorView;
    private Context context;
    private String brand,model,sv,imei,sim,source;
    private int os,root,network,time;

    private String token="";

    public IVisitorPresenterImpl(IVisitorView iVisitorView, Context context) {
        this.iVisitorView = iVisitorView;
        this.context = context;

    }

    @Override
    public void loadData() {
        SharedPreferences sharedPreferences=context.getSharedPreferences("token",Context.MODE_PRIVATE);
        token=sharedPreferences.getString("token",token);
        getInfo();
        Map<String,String> map=new HashMap<>();
        if(token!=null){
            map.put("token",token);
        }
        map.put("brand",brand);
        map.put("model",model);
        map.put("sv",sv);
        map.put("os",os+"");
        map.put("imei",imei);
        map.put("root",root+"");
        map.put("sim",sim+"");
        map.put("network",network+"");
        map.put("time",time+"");
        if(source.equals("null")){
            map.put("source","yingyongbao");
        }
        else{
            map.put("source",source+"");
        }
        String s = DeviceInfoUtils.md5(DeviceInfoUtils.getCompareTo(map));
        map.put("sign",s);
        Log.e("brand",brand);
        Log.e("model",model);
        Log.e("sv",sv);
        Log.e("os",os+"");
        Log.e("imei",imei);
        Log.e("root",root+"");
        Log.e("sim",sim+"");
        Log.e("network",network+"");
        Log.e("time",time+"");
        Log.e("sign",s);
        Log.e("source",source+"---");
        if(token.equals("")){
            RxHttp.postForm(MyApp.Url.baseUrl+"visitor")
                    .add(map)
                    .asParser(new SimpleParser<VisitorBean>(){})
                    .subscribe(tokenBean->{
                        Log.e("vivitor",tokenBean.getCode()+"-"+tokenBean.getMsg());
                        //if(tokenBean.getCode()==200&&tokenBean.getMsg().equals("success")){

                        //Log.e("asdtoken",token);
                        iVisitorView.onVisitorSuccess(tokenBean);
                        // }
                    },throwable->{
                        iVisitorView.onVisitorError(throwable.getMessage());
                    });
        }
        else{
            RxHttp.postForm(MyApp.Url.baseUrl+"visitor")
                    .addHeader("token",token)
                    .add(map)
                    .asParser(new SimpleParser<VisitorBean>(){})
                    .subscribe(tokenBean->{
                        Log.e("vivitor",tokenBean.getCode()+"-"+tokenBean.getMsg());
                        //if(tokenBean.getCode()==200&&tokenBean.getMsg().equals("success")){
                        //Log.e("asdtoken",token);
                        iVisitorView.onVisitorSuccess(tokenBean);
                        // }
                    },throwable->{
                        iVisitorView.onVisitorError(throwable.getMessage());
                    });
        }

    }


    @Override
    public void destoryView() {
        if(iVisitorView!=null){
            iVisitorView=null;
        }
    }

    private void getInfo(){
        brand=DeviceInfoUtils.getDeviceBrand();
        model=DeviceInfoUtils.getDeviceModel();
        sv=DeviceInfoUtils.getDeviceAndroidVersion();
        os=DeviceInfoUtils.getOs();
        imei=DeviceInfoUtils.getIMEI(context);
        //root=DeviceInfoUtils.getRootAhth();
        root=10;
        sim=DeviceInfoUtils.getSIM(context);
        network=DeviceInfoUtils.getNetWork(context);
        time=DeviceInfoUtils.getTime();
        source=DeviceInfoUtils.getChannelName(context);

    }

}
