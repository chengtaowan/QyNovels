package com.jdhd.qynovels.localtest;

import android.util.Log;

import com.jdhd.qynovels.activities.ExtendReaderActivity;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.entry.BookContentBean;
import com.jdhd.qynovels.entry.BookListBean;
import com.jdhd.qynovels.entry.ChapterItemBean;
import com.jdhd.qynovels.readerpresenter.IBookContentPresenterImpl;
import com.jdhd.qynovels.readerutil.DeviceInfoUtils;
import com.jdhd.qynovels.readerview.BookContentCallBack;
import com.jdhd.qynovels.readerview.IBookContentView;
import com.jdhd.qynovels.readerwidget.ReaderView;
import com.rxjava.rxlife.RxLife;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

/**
 * Created by Garrett on 2018/11/23.
 * contact me krouky@outlook.com
 */
public class LocalServer{
    /**
     * 模拟网络同步下载
     *
     * @return 章节内容  BookContentBean.DataBean
     */
    public static void syncDownloadContent(String token, BookListBean.DataBean.ListBean listBean, ReaderView.Adapter adapter
            , BookContentCallBack bookContentCallback) {


        boolean isFirst = true;
        int time= DeviceInfoUtils.getTime();
        Map<String,String> map=new HashMap<>();
        map.put("time",time+"");
        if(token!=null){
            map.put("token",token);
        }
        map.put("id",listBean.getId()+"");
        Log.d("back", "哪个章节内容id值：" + listBean.getId());
        String compareTo = DeviceInfoUtils.getCompareTo(map);
        String sign= DeviceInfoUtils.md5(compareTo);
        map.put("sign",sign);
        Log.e("zjid",listBean.getId()+"");
        Log.e("time",time+"");
        Log.e("token",token+"");
        Log.e("sign",sign+"");
        if(!token.equals("")){
            RxHttp.postForm(MyApp.Url.baseUrl+"bookContent")
                    .addHeader("token",token)
                    .add(map)
                    .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                    .asParser(new SimpleParser<BookContentBean>(){})
                    .subscribe(bookContentBean->{
                        Log.d("back","LocalServer:"+ bookContentBean.getData().getContent()+"--");
                        bookContentCallback.onBookContentSuccess(bookContentBean.getData());


                    },throwable->{
                        Log.e("zjerror",throwable.getMessage());
                    });
        }
        else{
            RxHttp.postForm(MyApp.Url.baseUrl+"bookContent")
                    .add(map)
                    .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                    .asParser(new SimpleParser<BookContentBean>(){})
                    .subscribe(bookContentBean->{
                        Log.d("back","LocalServer:"+ bookContentBean.getData().getContent()+"--");
                        bookContentCallback.onBookContentSuccess(bookContentBean.getData());

                    },throwable->{
                        Log.e("zjerror",throwable.getMessage());
                    });
        }

    }

    public interface OnResponseCallback {
        void onSuccess(List<ChapterItemBean> chapters);

        void onError(Exception e);
    }
}
