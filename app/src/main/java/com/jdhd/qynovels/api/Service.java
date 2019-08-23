package com.jdhd.qynovels.api;


import com.jdhd.qynovels.entry.BookContentBean;
import com.jdhd.qynovels.entry.ChapterContentBean;
import com.jdhd.qynovels.entry.ChapterItemBean;
import com.jdhd.qynovels.entry.Result;
import com.jdhd.qynovels.entry.ResultData;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Garrett on 2018/12/6.
 * contact me krouky@outlook.com
 */
public interface Service {

    /**
     * 获取图书目录
     */
    @GET("/goodbook/catalog")
    Observable<Result<List<ChapterItemBean>>> catalog(@Query("key") String key);

    /**
     * 获取图书内容
     */
    @GET("/goodbook/query")
    Observable<Result<ResultData<List<ChapterContentBean>>>> query(@Query("key") String key,
                                                                   @Query("catalog_id") String catalog,
                                                                   @Query("pn") int start, @Query("rn") int end);
    @POST("")
    Observable<Result<BookContentBean>> getBookContent(@Query("id") int id);

}
