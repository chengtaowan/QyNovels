package com.glong.reader.activities;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;


import com.glong.reader.entry.ChapterContentBean;
import com.glong.reader.entry.ChapterItemBean;
import com.glong.reader.localtest.LocalServer;
import com.glong.reader.widget.ReaderView;
import com.glong.sample.R;


import java.util.List;

public class SimpleReaderActivity extends AppCompatActivity {

    private ReaderView.Adapter<ChapterItemBean, ChapterContentBean> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_reader);

        initReader();
        initData();
    }

    private void initReader() {
        final String userId = "123";
        ReaderView readerView = findViewById(R.id.simple_reader_view);

        ReaderView.ReaderManager readerManager = new ReaderView.ReaderManager();
        readerView.setReaderManager(readerManager);

        mAdapter = new ReaderView.Adapter<ChapterItemBean, ChapterContentBean>() {

            @Override
            public String obtainCacheKey(ChapterItemBean chapterItemBean) {
                return chapterItemBean.getChapterId() + userId;
            }

            @Override
            public String obtainChapterName(ChapterItemBean chapterItemBean) {
                return chapterItemBean.getChapterName();
            }

            @Override
            public String obtainChapterContent(ChapterContentBean contentBean) {
                return contentBean.getChapterContent();
            }

            /**
             * 这个方法运行在子线程中，同步返回章节内容
             */
            @Override
            public ChapterContentBean downLoad(ChapterItemBean chapterItemBean) {
                return LocalServer.syncDownloadContent(chapterItemBean);
            }
        };

        readerView.setAdapter(mAdapter);
    }

    private void initData() {
        /*
         * 获取章节列表
         */
        LocalServer.getChapterList("123", new LocalServer.OnResponseCallback() {
            @Override
            public void onSuccess(final List<ChapterItemBean> chapters) {
                mAdapter.setChapterList(chapters);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
}

