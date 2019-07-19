package com.glong.reader.activities;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;


import com.glong.reader.entry.BookContentBean;
import com.glong.reader.entry.BookListBean;
import com.glong.reader.entry.ChapterContentBean;
import com.glong.reader.entry.ChapterItemBean;
import com.glong.reader.localtest.LocalServer;
import com.glong.reader.presenter.IBookListPresenterImpl;
import com.glong.reader.view.IBookListView;
import com.glong.reader.widget.ReaderView;
import com.glong.sample.R;


import java.util.List;

public class SimpleReaderActivity extends AppCompatActivity implements IBookListView {

    private ReaderView.Adapter<BookListBean.DataBean.ListBean, BookContentBean> mAdapter;
    private IBookListPresenterImpl bookListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_reader);
        Intent intent=getIntent();
        int id=intent.getIntExtra("id",0);
        bookListPresenter=new IBookListPresenterImpl(this,this) ;
        bookListPresenter.setId(id);
        bookListPresenter.loadData();
        initReader();
        initData();
    }

    private void initReader() {
        final String userId = "123";
        ReaderView readerView = findViewById(R.id.simple_reader_view);

        ReaderView.ReaderManager readerManager = new ReaderView.ReaderManager();
        readerView.setReaderManager(readerManager);

        mAdapter = new ReaderView.Adapter<BookListBean.DataBean.ListBean,BookContentBean>(){


            @Override
            public BookContentBean downLoad(BookListBean.DataBean.ListBean listBean) {
                LocalServer localServer=new LocalServer();
                return localServer.syncgetContent(listBean.getId(),SimpleReaderActivity.this);
            }

            @Override
            public String obtainCacheKey(BookListBean.DataBean.ListBean listBean) {
                return listBean.getId()+userId;
            }

            @Override
            public String obtainChapterName(BookListBean.DataBean.ListBean listBean) {
                return listBean.getName();
            }

            @Override
            public String obtainChapterContent(BookContentBean bookContentBean) {
                return bookContentBean.getData().getContent();
            }
        };

        readerView.setAdapter(mAdapter);
    }

    private void initData() {
//        /*
//         * 获取章节列表
//         */
//        LocalServer.getChapterList("123", new LocalServer.OnResponseCallback() {
//            @Override
//            public void onSuccess(final List<ChapterItemBean> chapters) {
//                mAdapter.setChapterList(chapters);
//                mAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onError(Exception e) {
//
//            }
//        });
    }

    @Override
    public void onSuccess(BookListBean bookListBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
              mAdapter.setChapterList(bookListBean.getData().getList());
              mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onError(String error) {

    }
}

