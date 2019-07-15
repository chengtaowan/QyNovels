package com.glong.reader.activities;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.glong.reader.ScreenUtils;
import com.glong.reader.TurnStatus;
import com.glong.reader.adpater.CatalogueAdapter;
import com.glong.reader.adpater.MyReaderAdapter;
import com.glong.reader.api.Api;
import com.glong.reader.api.Service;
import com.glong.reader.entry.BookListBean;
import com.glong.reader.entry.ChapterContent2Bean;
import com.glong.reader.entry.ChapterItemBean;
import com.glong.reader.entry.Result;
import com.glong.reader.presenter.IBookListPresenterImpl;
import com.glong.reader.util.StatusBarUtil;
import com.glong.reader.view.IBookListView;
import com.glong.reader.view.MenuView;
import com.glong.reader.view.SettingView;
import com.glong.reader.view.SimpleOnSeekBarChangeListener;
import com.glong.reader.widget.EffectOfCover;
import com.glong.reader.widget.EffectOfNon;
import com.glong.reader.widget.EffectOfRealBothWay;
import com.glong.reader.widget.EffectOfRealOneWay;
import com.glong.reader.widget.EffectOfSlide;
import com.glong.reader.widget.PageChangedCallback;
import com.glong.reader.widget.ReaderView;
import com.glong.sample.R;
import com.google.android.material.navigation.NavigationView;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ExtendReaderActivity extends AppCompatActivity implements View.OnClickListener, IBookListView {

    private static final String TAG = ExtendReaderActivity.class.getSimpleName();

    private ReaderView mReaderView;
    private ReaderView.ReaderManager mReaderManager;
    private ReaderView.Adapter<ChapterItemBean, ChapterContent2Bean> mAdapter;

    private MenuView mMenuView;// 菜单View
    private SettingView mSettingView;// 设置View
    private SeekBar mChapterSeekBar;//调节章节的SeekBar

    private DrawerLayout mDrawerLayout;//操作抽屉
    private NavigationView mNavigationView;//左侧用于展示目录的抽屉
    private RecyclerView mRecyclerView;//展示目录
    private CatalogueAdapter mCatalogueAdapter;

    private IBookListPresenterImpl bookListPresenter;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend_reader);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        Intent intent=getIntent();
        id=intent.getIntExtra("id",0);
        bookListPresenter=new IBookListPresenterImpl(this);
        bookListPresenter.setId(id);
        bookListPresenter.loadData();
        initReader();
        initView();
        initToolbar();
        initData();
    }

    private void initView() {
        mMenuView = findViewById(R.id.menu_view);
        mSettingView = findViewById(R.id.setting_view);
        mChapterSeekBar = findViewById(R.id.chapter_seek_bar);
        // 调节亮度的SeekBar
        SeekBar lightSeekBar = findViewById(R.id.light_seek_bar);
        //调节字号的SeekBar
        SeekBar textSizeSeekBar = findViewById(R.id.text_size_seek_bar);
        // 调节文字间距的SeekBar
        SeekBar textSpaceSeekBar = findViewById(R.id.text_space_seek_bar);

        mDrawerLayout = findViewById(R.id.drawerLayout);
        mNavigationView = findViewById(R.id.navigation);
        mRecyclerView = findViewById(R.id.recyclerView);
        initRecyclerViewAndDrawerLayout();

        findViewById(R.id.setting).setOnClickListener(this);//设置
        findViewById(R.id.text_prev_chapter).setOnClickListener(this);//上一章
        findViewById(R.id.text_next_chapter).setOnClickListener(this);//下一章
        findViewById(R.id.reader_catalogue).setOnClickListener(this);//目录
        // 切换背景
        findViewById(R.id.reader_bg_0).setOnClickListener(this);
        findViewById(R.id.reader_bg_1).setOnClickListener(this);
        findViewById(R.id.reader_bg_2).setOnClickListener(this);
        findViewById(R.id.reader_bg_3).setOnClickListener(this);
        // 切换翻页效果
        findViewById(R.id.effect_real_one_way).setOnClickListener(this);
        findViewById(R.id.effect_real_both_way).setOnClickListener(this);
        findViewById(R.id.effect_cover).setOnClickListener(this);
        findViewById(R.id.effect_slide).setOnClickListener(this);
        findViewById(R.id.effect_non).setOnClickListener(this);

        mChapterSeekBar.setOnSeekBarChangeListener(new SimpleOnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                TurnStatus turnStatus = mReaderManager.toSpecifiedChapter(seekBar.getProgress(), 0);
                if (turnStatus == TurnStatus.LOAD_SUCCESS)
                    mReaderView.invalidateBothPage();
            }
        });

        lightSeekBar.setOnSeekBarChangeListener(new SimpleOnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ScreenUtils.changeAppBrightness(ExtendReaderActivity.this, progress);
            }
        });

        textSizeSeekBar.setOnSeekBarChangeListener(new SimpleOnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mReaderView.setTextSize(progress + 20);//文字大小限制最小20
            }
        });

        textSpaceSeekBar.setOnSeekBarChangeListener(new SimpleOnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mReaderView.setLineSpace(progress);
            }
        });

        lightSeekBar.setMax(255);
        textSizeSeekBar.setMax(100);
        textSpaceSeekBar.setMax(200);

        // 初始化SeekBar位置
        mChapterSeekBar.setProgress(0);// 如果需要历史纪录的话，可以在这里实现
        lightSeekBar.setProgress(ScreenUtils.getSystemBrightness(this));
        textSizeSeekBar.setProgress(mReaderView.getTextSize() - 20);
        textSpaceSeekBar.setProgress(mReaderView.getLineSpace());
    }

    private void initRecyclerViewAndDrawerLayout() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCatalogueAdapter = new CatalogueAdapter(new CatalogueAdapter.OnItemClickListener() {
            @Override
            public void onClicked(int position) {
                TurnStatus turnStatus = mReaderManager.toSpecifiedChapter(position, 0);
                if (turnStatus == TurnStatus.LOAD_SUCCESS) {
                    mReaderView.invalidateBothPage();
                }
                mDrawerLayout.closeDrawer(mNavigationView);
            }
        });
        mRecyclerView.setAdapter(mCatalogueAdapter);

        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);// 不响应滑动打开
        // 这个订阅目的:当抽屉打开时可以滑动关闭,未打开时不能滑动打开
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }

            @Override
            public void onDrawerClosed(@NonNull View view) {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }

            @Override
            public void onDrawerStateChanged(int i) {
            }
        });
    }

    private void initToolbar() {
        final Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.extend_reader_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.delete_cache) {
            Toast.makeText(this, "删除缓存" + (mReaderManager.getCache().removeAll() ? "成功" : "失败"), Toast.LENGTH_SHORT).show();
        } else if (i == R.id.share) {
            Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initReader() {
        mReaderView = findViewById(R.id.extend_reader);
        mReaderManager = new ReaderView.ReaderManager();
        mAdapter = new MyReaderAdapter();

        mReaderView.setReaderManager(mReaderManager);
        mReaderView.setAdapter(mAdapter);

        mReaderView.setPageChangedCallback(new PageChangedCallback() {
            @Override
            public TurnStatus toPrevPage() {
                TurnStatus turnStatus = mReaderManager.toPrevPage();
                if (turnStatus == TurnStatus.NO_PREV_CHAPTER) {
                    Toast.makeText(ExtendReaderActivity.this, "没有上一页啦", Toast.LENGTH_SHORT).show();
                }
                return turnStatus;
            }

            @Override
            public TurnStatus toNextPage() {
                TurnStatus turnStatus = mReaderManager.toNextPage();
                if (turnStatus == TurnStatus.NO_NEXT_CHAPTER) {
                    Toast.makeText(ExtendReaderActivity.this, "没有下一页啦", Toast.LENGTH_SHORT).show();
                }
                return turnStatus;
            }
        });
    }

    private Disposable mDisposable;

    private void initData() {
        Api.getInstance().getService(Service.class).catalog(Api.KEY)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Result<List<ChapterItemBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Result<List<ChapterItemBean>> listResult) {
                        List<ChapterItemBean> chapters = listResult.getResult();
                        if (chapters != null) {
                            mAdapter.setChapterList(chapters);
                            mAdapter.notifyDataSetChanged();
                            mChapterSeekBar.setMax(chapters.size() - 1);
                            //mCatalogueAdapter.refresh(chapters);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    private long mDownTime;
    private float mDownX;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int longClickTime = ViewConfiguration.get(this).getLongPressTimeout();
        int touchSlop = ViewConfiguration.get(this).getScaledPagingTouchSlop();
        int screenWidth = ScreenUtils.getScreenWidth(this);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (ev.getX() > screenWidth / 3 && ev.getX() < screenWidth * 2 / 3) {
                    mDownTime = System.currentTimeMillis();
                } else {
                    mDownTime = 0;
                }
                mDownX = ev.getX();
                break;
            case MotionEvent.ACTION_UP:
                if (System.currentTimeMillis() - mDownTime < longClickTime && (Math.abs(ev.getX() - mDownX) < touchSlop)) {

                    if (!mMenuView.isShowing() && !mSettingView.isShowing() && !mDrawerLayout.isDrawerOpen(mNavigationView)) {
                        Log.d(TAG, "show menuView!");
                        mMenuView.show();
                        return true;
                    }
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();//上一章
//下一章
//目录
        if (i == R.id.setting) {
            mMenuView.dismiss();
            mSettingView.show();
        } else if (i == R.id.text_prev_chapter) {
            TurnStatus turnStatus = mReaderManager.toPrevChapter();
            if (turnStatus == TurnStatus.LOAD_SUCCESS) {
                mReaderView.invalidateBothPage();
                mChapterSeekBar.setProgress(mChapterSeekBar.getProgress() - 1);
            } else if (turnStatus == TurnStatus.DOWNLOADING) {
                mChapterSeekBar.setProgress(mChapterSeekBar.getProgress() - 1);
            } else if (turnStatus == TurnStatus.NO_PREV_CHAPTER) {
                Toast.makeText(this, "没有上一章啦", Toast.LENGTH_SHORT).show();
            }
        } else if (i == R.id.text_next_chapter) {
            TurnStatus turnStatus2 = mReaderManager.toNextChapter();
            if (turnStatus2 == TurnStatus.LOAD_SUCCESS) {
                mReaderView.invalidateBothPage();
                mChapterSeekBar.setProgress(mChapterSeekBar.getProgress() + 1);
            } else if (turnStatus2 == TurnStatus.DOWNLOADING) {
                mChapterSeekBar.setProgress(mChapterSeekBar.getProgress() + 1);
            } else if (turnStatus2 == TurnStatus.NO_NEXT_CHAPTER) {
                Toast.makeText(this, "没有下一章啦", Toast.LENGTH_SHORT).show();
            }
        } else if (i == R.id.reader_catalogue) {
            mDrawerLayout.openDrawer(mNavigationView);
            mMenuView.dismiss();
            // 切换背景
        } else if (i == R.id.reader_bg_0) {
            mReaderView.setBackgroundColor(getResources().getColor(R.color.reader_bg_0));
        } else if (i == R.id.reader_bg_1) {
            mReaderView.setBackgroundColor(getResources().getColor(R.color.reader_bg_1));
        } else if (i == R.id.reader_bg_2) {
            mReaderView.setBackgroundColor(getResources().getColor(R.color.reader_bg_2));
        } else if (i == R.id.reader_bg_3) {
            mReaderView.setBackgroundColor(getResources().getColor(R.color.reader_bg_3));
            //切换翻页效果
        } else if (i == R.id.effect_real_one_way) {
            mReaderView.setEffect(new EffectOfRealOneWay(this));
        } else if (i == R.id.effect_real_both_way) {
            mReaderView.setEffect(new EffectOfRealBothWay(this));
        } else if (i == R.id.effect_cover) {
            mReaderView.setEffect(new EffectOfCover(this));
        } else if (i == R.id.effect_slide) {
            mReaderView.setEffect(new EffectOfSlide(this));
        } else if (i == R.id.effect_non) {
            mReaderView.setEffect(new EffectOfNon(this));

            // 日间/夜间模式的切换可参考：https://www.jianshu.com/p/ef3d05809dce
        }
    }

    @Override
    public void onBackPressed() {
        /*
         * 返回时,依次关闭目录,设置,菜单
         */
        if (mDrawerLayout.isDrawerOpen(mNavigationView)) {
            mDrawerLayout.closeDrawer(mNavigationView);
        } else if (mSettingView.isShowing()) {
            mSettingView.dismiss();
        } else if (mMenuView.isShowing()) {
            mMenuView.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onSuccess(BookListBean bookListBean) {
       runOnUiThread(new Runnable() {
           @Override
           public void run() {
               List<ChapterItemBean> list=new ArrayList<>();
               for(int i=0;i<bookListBean.getData().getList().size();i++){
                   ChapterItemBean itemBean=new ChapterItemBean();
                   itemBean.setChapterId(bookListBean.getData().getList().get(i).getId()+"");
                   itemBean.setChapterName(bookListBean.getData().getList().get(i).getName());
                   list.add(itemBean);
               }
               mCatalogueAdapter.refresh(list);
           }
       });
    }

    @Override
    public void onError(String error) {
       Log.e("zjerror",error);
    }
}
