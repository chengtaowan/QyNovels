package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.bifan.txtreaderlib.main.TxtReaderView;

import com.glong.reader.entry.ChapterContentBean;
import com.glong.reader.entry.ChapterItemBean;
import com.glong.reader.localtest.LocalServer;
import com.glong.reader.util.Request;
import com.glong.reader.widget.ReaderView;
import com.jdhd.qynovels.R;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.utils.AndroidBug54971Workaround;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

public class ReadActivity extends AppCompatActivity {
    private ReaderView readview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        AndroidBug54971Workaround.assistActivity(findViewById(android.R.id.content),this);

        MyApp.addActivity(this);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();
    }

    private void init() {
        readview=findViewById(R.id.readview);
        ReaderView.ReaderManager manager=new ReaderView.ReaderManager();
        readview.setReaderManager(manager);
        readview.setAdapter(adapter);
        /*
         * 获取章节列表
         */
        LocalServer.getChapterList("123", new LocalServer.OnResponseCallback() {
            @Override
            public void onSuccess(List<ChapterItemBean> chapters) {
               adapter .setChapterList(chapters);
               adapter. notifyDataSetChanged();
            }
               @Override
            public void onError(Exception e) {
            }
        });
    }
    public ReaderView.Adapter adapter=new ReaderView.Adapter<ChapterItemBean, ChapterContentBean>(){
        @Override
        public String obtainCacheKey(ChapterItemBean chapterItemBean) {
            return chapterItemBean.getChapterId();
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
            return null;
        }
        @Override
        public Request requestParams(ChapterItemBean chapterItemBean) {
            return new Request.Builder()
                    .baseUrl("111")
                    .addHeader("token", "userToken")
                    .addUrlParams("bookId", "123")
                    .addUrlParams("bookName", "123")
                    .addBody("chapterId", chapterItemBean.getChapterId())
                    .get()
                    .build();
        }

    };
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this); // 不能遗漏
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this); // 不能遗漏
    }
}
