package com.glong.reader.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.glong.reader.activities.MyApp;
import com.glong.reader.entry.BookContentBean;
import com.glong.reader.entry.BookListBean;
import com.glong.reader.util.DeviceInfoUtils;
import com.glong.reader.view.IBookContentView;
import com.glong.reader.view.IBookListView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IBookContentPresenterImpl implements IBookContentPresenter {
    private IBookContentView bookContentView;
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    private String token;

    public IBookContentPresenterImpl(IBookContentView bookContentView) {
        this.bookContentView = bookContentView;
    }

    private int id;

    public void setId(int id) {
        this.id = id;
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
        map.put("id",id+"");
        String compareTo = DeviceInfoUtils.getCompareTo(map);
        String sign=DeviceInfoUtils.md5(compareTo);
        map.put("sign",sign);
        if(token!=null){
            RxHttp.postForm(MyApp.Url.baseUrl+"bookContent")
                    .addHeader("token",token)
                    .add(map)
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
        else{
            RxHttp.postForm(MyApp.Url.baseUrl+"bookContent")
                    .add(map)
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

    }

    @Override
    public void destoryView() {
      if(bookContentView!=null){
          bookContentView=null;
      }
    }
}
