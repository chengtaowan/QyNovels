package com.jdhd.qynovels.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.Map;
import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class RxHttpUtils {
    private static Context context;
    private static String token;
    private static Object object;
    private static Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            object=msg.obj;
        }
    };

    public RxHttpUtils(Context context) {
        this.context = context;
    }

    public static void RxHttp(String url, Map<String,String> map){
        final Object[] o = {new Object()};
        SharedPreferences preferences=context.getSharedPreferences("token", Context.MODE_PRIVATE);
        token = preferences.getString("token", "");
        int time=DeviceInfoUtils.getTime();
        map.put("time",time+"");
        map.put("token",token);
        String compare=DeviceInfoUtils.getCompareTo(map);
        String sing=DeviceInfoUtils.md5(compare);
        map.put("sign",sing);
        RxHttp.postForm(url)
                .addHeader("token",token)
                .add(map)
                .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                .asParser(new SimpleParser<Object>(){})
                .subscribe(object->{
                   Message message=handler.obtainMessage();
                   message.obj=object;
                   handler.sendMessage(message);
                },throwable -> {
                    o[1]=throwable.getMessage();
                });
    }

}
