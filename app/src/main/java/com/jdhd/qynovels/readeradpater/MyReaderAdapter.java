package com.jdhd.qynovels.readeradpater;


import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.jdhd.qynovels.api.Api;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.entry.BookContentBean;
import com.jdhd.qynovels.entry.BookListBean;
import com.jdhd.qynovels.entry.ChapterContent2Bean;
import com.jdhd.qynovels.entry.ChapterItemBean;
import com.jdhd.qynovels.localtest.LocalServer;
import com.jdhd.qynovels.readerutil.Request;
import com.jdhd.qynovels.readerview.BookContentCallBack;
import com.jdhd.qynovels.readerwidget.ReaderView;


/**
 * Created by Garrett on 2018/11/28.
 * contact me krouky@outlook.com
 */
public class MyReaderAdapter extends ReaderView.Adapter<BookListBean.DataBean.ListBean, BookContentBean.DataBean> {
   private String token;
   private Activity context;


    public MyReaderAdapter(String token, Activity context) {
        this.token = token;
        this.context = context;
    }

    @Override
    public String obtainCacheKey(BookListBean.DataBean.ListBean listBean) {
        return listBean.getId()+"";
    }

    @Override
    public String obtainChapterName(BookListBean.DataBean.ListBean listBean) {
        return listBean.getName();
    }

    @Override
    public String obtainChapterContent(BookContentBean.DataBean dataBean) {
        Log.e("nr",dataBean.getContent()+"---");
        return dataBean.getContent();
    }

    @Override
    public void downLoad(BookListBean.DataBean.ListBean listBean, BookContentCallBack bookContentCallBack){
        LocalServer.syncDownloadContent(token,listBean,this,bookContentCallBack);
    }

}
