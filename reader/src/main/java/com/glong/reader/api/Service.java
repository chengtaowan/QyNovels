package com.glong.reader.api;


import com.glong.reader.entry.BookContentBean;
import com.glong.reader.entry.ChapterContentBean;
import com.glong.reader.entry.ChapterItemBean;
import com.glong.reader.entry.Result;
import com.glong.reader.entry.ResultData;

import java.util.List;
import java.util.Map;

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
