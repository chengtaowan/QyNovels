package com.glong.reader.localtest;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import com.glong.reader.activities.MyApp;
import com.glong.reader.entry.BookContentBean;
import com.glong.reader.entry.BookListBean;
import com.glong.reader.entry.ChapterContentBean;
import com.glong.reader.entry.ChapterItemBean;
import com.glong.reader.presenter.IBookContentPresenterImpl;
import com.glong.reader.util.DeviceInfoUtils;
import com.glong.reader.view.IBookContentView;

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
public class LocalServer implements IBookContentView {

     public IBookContentPresenterImpl bookContentPresenter=new IBookContentPresenterImpl(this);
     public static BookContentBean.DataBean bookContent=new BookContentBean.DataBean();
     /**
     * 模拟网络请求
     *
     * @param bookId 书ID
     */
    public static void getChapterList(String bookId, final OnResponseCallback onResponseCallback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<ChapterItemBean> chapters = new ArrayList<>();
                while (chapters.size() < 100) {
                    ChapterItemBean chapterItemBean = new ChapterItemBean();
                    chapterItemBean.setChapterId("id" + (chapters.size() + 1));
                    chapterItemBean.setChapterName("第" + (chapters.size() + 1) + "章 名言名句");
                    chapters.add(chapterItemBean);
                }
                onResponseCallback.onSuccess(chapters);
            }
        }).start();
    }

    /**
     * 模拟网络同步下载
     *
     * @return 章节内容
     */
    public static BookContentBean.DataBean syncDownloadContent(String token,BookListBean.DataBean.ListBean listBean) {

        int time= DeviceInfoUtils.getTime();
        Map<String,String> map=new HashMap<>();
        map.put("time",time+"");
        if(token!=null){
            map.put("token",token);
        }
        map.put("id",listBean.getId()+"");
        String compareTo = DeviceInfoUtils.getCompareTo(map);
        String sign=DeviceInfoUtils.md5(compareTo);
        map.put("sign",sign);
        Log.e("zjid",listBean.getId()+"");
        Log.e("time",time+"");
        Log.e("token",token+"");
        Log.e("sign",sign+"");
        if(token!=null){
            RxHttp.postForm(MyApp.Url.baseUrl+"bookContent")
                    .addHeader("token",token)
                    .add(map)
                    .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                    .asParser(new SimpleParser<BookContentBean>(){})
                    .subscribe(bookContentBean->{
                        if(bookContentBean.getCode()==200&&bookContentBean.getMsg().equals("请求成功")){
                            bookContent=bookContentBean.getData();
                        }
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
                        if(bookContentBean.getCode()==200&&bookContentBean.getMsg().equals("请求成功")){
                            bookContent=bookContentBean.getData();
                        }
                    },throwable->{
                        Log.e("zjerror",throwable.getMessage());
                    });
        }
        Log.e("bookcontent",bookContent.toString());
        return bookContent;
    }


    /**
     * 模拟网络同步下载
     *
     * @return 章节内容
     */
    public static BookContentBean.DataBean syncgetContent(int id, String token) {
        int time= DeviceInfoUtils.getTime();
        Map<String,String> map=new HashMap<>();
        map.put("time",time+"");
        if(token!=null){
            map.put("token",token);
        }
        map.put("id",id+"");
        String compareTo = DeviceInfoUtils.getCompareTo(map);
        String sign=DeviceInfoUtils.md5(compareTo);
        map.put("sign",sign);
        Log.e("zjid",id+"");
        Log.e("time",time+"");
        Log.e("token",token+"");
        Log.e("sign",sign+"");
        if(token!=null){
            RxHttp.postForm(MyApp.Url.baseUrl+"bookContent")
                    .addHeader("token",token)
                    .add(map)
                    .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                    .asParser(new SimpleParser<BookContentBean>(){})
                    .subscribe(bookContentBean->{
                        if(bookContentBean.getCode()==200&&bookContentBean.getMsg().equals("请求成功")){
                            bookContent=bookContentBean.getData();
                        }
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
                        if(bookContentBean.getCode()==200&&bookContentBean.getMsg().equals("请求成功")){
                            bookContent=bookContentBean.getData();
                        }
                    },throwable->{
                        Log.e("zjerror",throwable.getMessage());
                    });
        }
        Log.e("bookcontent",bookContent.toString());
        return bookContent;
    }

    @Override
    public void onBookSuccess(BookContentBean bookContentBean) {
        bookContent=bookContentBean.getData();
    }

    @Override
    public void onBookError(String error) {

    }

    public interface OnResponseCallback {
        void onSuccess(List<ChapterItemBean> chapters);

        void onError(Exception e);
    }
}
