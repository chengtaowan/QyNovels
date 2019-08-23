package com.jdhd.qynovels.persenter.impl.bookcase;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.bookcase.AddBookBean;
import com.jdhd.qynovels.persenter.inter.bookcase.IBookInfoPresenter;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.bookcase.IAddBookRankView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CacheControl;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

public class IAddBookRankPresenterImpl implements IBookInfoPresenter {
    private IAddBookRankView iAddBookRankView;
    private Context context;
    private String token;

    public IAddBookRankPresenterImpl(IAddBookRankView iAddBookRankView, Context context) {
        this.iAddBookRankView = iAddBookRankView;
        this.context = context;
    }

    private int id;
    private int backlistId;
    private float backlistPercent;
    private int readStatus;
    private int charIndex;

    public void setCharIndex(int charIndex) {
        this.charIndex = charIndex;
    }

    public void setBacklistId(int backlistId) {
        this.backlistId = backlistId;
    }

    public void setBacklistPercent(float backlistPercent) {
        this.backlistPercent = backlistPercent;
    }

    public void setReadStatus(int readStatus) {
        this.readStatus = readStatus;
    }

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
        map.put("bookId",id+"");
        map.put("backlistId",backlistId+"");
        map.put("backlistPercent",backlistPercent+"");
        map.put("readStatus",readStatus+"");
        map.put("charIndex",charIndex+"");
        String compareTo = DeviceInfoUtils.getCompareTo(map);
        String sign=DeviceInfoUtils.md5(compareTo);
        map.put("sign",sign);
        if(!token.equals("")){
            RxHttp.postForm(MyApp.Url.baseUrl+"addBookrack")
                    .addHeader("token",token)
                    .add(map)
                    .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                    .asParser(new SimpleParser<AddBookBean>(){})
                    .subscribe(addBookBean->{
                        Log.e("qqq",addBookBean.getMsg());
                        if(addBookBean.getCode()==200&&addBookBean.getMsg().equals("请求成功")){
                            iAddBookRankView.onSuccess(addBookBean);
                        }
                    },throwable->{
                        iAddBookRankView.onAddError(throwable.getMessage());
                    });
        }
        else{
            RxHttp.postForm(MyApp.Url.baseUrl+"addBookrack")
                    .add(map)
                    .cacheControl(CacheControl.FORCE_NETWORK)  //缓存控制
                    .asParser(new SimpleParser<AddBookBean>(){})
                    .subscribe(addBookBean->{
                        Log.e("addbook",addBookBean.getMsg());
                        //if(addBookBean.getCode()==200&&addBookBean.getMsg().equals("请求成功")){
                            iAddBookRankView.onSuccess(addBookBean);
                       // }
                    },throwable->{
                        iAddBookRankView.onAddError(throwable.getMessage()+throwable.getCause());
                    });
        }

    }

    @Override
    public void destoryView() {
      if(iAddBookRankView!=null){
          iAddBookRankView=null;
      }
    }
}
