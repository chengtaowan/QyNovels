package com.jdhd.qynovels.readeradpater;


import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.entry.ChapterContent2Bean;
import com.jdhd.qynovels.entry.ChapterItemBean;
import com.jdhd.qynovels.readerutil.Request;
import com.jdhd.qynovels.readerwidget.ReaderView;


/**
 * Created by Garrett on 2018/11/28.
 * contact me krouky@outlook.com
 */
public class MyReaderAdapter extends ReaderView.Adapter<ChapterItemBean, ChapterContent2Bean> {
    @Override
    public String obtainCacheKey(ChapterItemBean chapterItemBean) {
        return chapterItemBean.getChapterId() + "userId";
    }

    @Override
    public String obtainChapterName(ChapterItemBean chapterItemBean) {
        return chapterItemBean.getChapterName();
    }

    @Override
    public String obtainChapterContent(ChapterContent2Bean ChapterContent2Bean) {
        return ChapterContent2Bean.getResult().getData().get(0).getSub2();
    }

    @Override
    public ChapterContent2Bean downLoad(ChapterItemBean chapterItemBean) {
        return null;//LocalServer.syncDownloadContent(chapterItemBean);
    }

    @Override
    public Request requestParams(ChapterItemBean chapterItemBean) {
        return new Request.Builder().baseUrl(MyApp.Url.baseUrl)
                .addUrlParams("id", chapterItemBean.getChapterId())
                .post().build();
    }
}
