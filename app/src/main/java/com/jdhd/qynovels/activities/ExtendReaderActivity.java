package com.jdhd.qynovels.activities;

import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.FilterWord;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdDislike;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;

import com.google.android.material.navigation.NavigationView;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.module.bookcase.AddBookBean;
import com.jdhd.qynovels.module.bookcase.BookInfoBean;
import com.jdhd.qynovels.module.bookcase.CaseBean;
import com.jdhd.qynovels.persenter.impl.bookcase.IAddBookRankPresenterImpl;
import com.jdhd.qynovels.persenter.impl.bookcase.IBookInfoPresenterImpl;
import com.jdhd.qynovels.persenter.impl.bookcase.ICasePresenterImpl;
import com.jdhd.qynovels.readerview.ScreenUtils;
import com.jdhd.qynovels.readerview.TurnStatus;
import com.jdhd.qynovels.config.ColorsConfig;
import com.jdhd.qynovels.entry.BookContentBean;
import com.jdhd.qynovels.entry.BookListBean;
import com.jdhd.qynovels.entry.ChapterItemBean;
import com.jdhd.qynovels.entry.ReadAwardBean;
import com.jdhd.qynovels.localtest.LocalServer;
import com.jdhd.qynovels.readeradpater.CatalogueAdapter;
import com.jdhd.qynovels.readerpresenter.IBookContentPresenterImpl;
import com.jdhd.qynovels.readerpresenter.IBookListPresenterImpl;
import com.jdhd.qynovels.readerpresenter.IReadAwardPresenterImpl;
import com.jdhd.qynovels.readerutil.StatusBarUtil;
import com.jdhd.qynovels.readerview.IBookContentView;
import com.jdhd.qynovels.readerview.IBookListView;
import com.jdhd.qynovels.readerview.IReadAwardView;
import com.jdhd.qynovels.readerview.MenuView;
import com.jdhd.qynovels.readerview.SettingView;
import com.jdhd.qynovels.readerview.SimpleOnSeekBarChangeListener;
import com.jdhd.qynovels.readerwidget.AddCasePopWindow;
import com.jdhd.qynovels.readerwidget.DislikeDialog;
import com.jdhd.qynovels.readerwidget.EffectOfCover;
import com.jdhd.qynovels.readerwidget.EffectOfRealBothWay;
import com.jdhd.qynovels.readerwidget.EffectOfRealOneWay;
import com.jdhd.qynovels.readerwidget.EffectOfSlide;
import com.jdhd.qynovels.readerwidget.PageChangedCallback;
import com.jdhd.qynovels.readerwidget.ReaderResolve;
import com.jdhd.qynovels.readerwidget.ReaderView;
import com.jdhd.qynovels.ui.activity.XqActivity;
import com.jdhd.qynovels.utils.DbUtils;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.bookcase.IAddBookRankView;
import com.jdhd.qynovels.view.bookcase.IBookInfoView;
import com.jdhd.qynovels.view.bookcase.ICaseView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.disposables.Disposable;

public class ExtendReaderActivity extends AppCompatActivity implements View.OnClickListener, IBookListView, IReadAwardView, IBookContentView , ICaseView , IAddBookRankView , IBookInfoView {

    private static final String TAG = ExtendReaderActivity.class.getSimpleName();
    private UiModeManager uiModeManager;
    private ReaderView mReaderView;
    private ReaderView.ReaderManager mReaderManager;
    private ReaderView.Adapter<BookListBean.DataBean.ListBean, BookContentBean.DataBean> mAdapter;
    private String time;
    private MenuView mMenuView;// 菜单View
    private SettingView mSettingView;// 设置View
    public static SeekBar mChapterSeekBar;//调节章节的SeekBar
    private IBookInfoPresenterImpl bookInfoPresenter;
    private DrawerLayout mDrawerLayout;//操作抽屉
    private NavigationView mNavigationView;//左侧用于展示目录的抽屉
    private RecyclerView mRecyclerView;//展示目录
    private CatalogueAdapter mCatalogueAdapter;

    private IBookListPresenterImpl bookListPresenter;
    private int id;
    private IBookContentPresenterImpl bookContentPresenter;
    private String content="";
    private int count=0;
    private BookListBean bookList=new BookListBean();
    private String token;
    private TextView bj1,bj2,bj3,bj4,bj5;
    private TextView big,zh;
    private SeekBar lightSeekBar;
    private IReadAwardPresenterImpl readAwardPresenter;
    private int recLen=30;
    private int clicknum=1;
    private ImageView daynight;
    private LinearLayout gg;
    private TTAdNative mTTAdNative;
    private ImageView book;
    private int p=0;
    private TextView bookname,bookauthor;
    private String img,name,author;
    private ImageView logo;
    private List<TTNativeExpressAd>  mTTAdList=new ArrayList<>();
    private RelativeLayout jz;
    private View view;
    private int type=0;
    private boolean iscase;
    private DbUtils dbUtils;
    private SQLiteDatabase database;
    private int update=0;
    private String mtitle;
    Timer timer = new Timer();
    private ICasePresenterImpl casePresenter;
    private BookInfoBean bookBean;
    private IAddBookRankPresenterImpl addBookRankPresenter;
    private int backlistid,charindex;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                int count=msg.arg1;
                Log.e("readaward",count+"--");
                if(count==0){
                    readAwardPresenter.loadData();
                    recLen=5;

                }
            }
            else{
                content= (String) msg.obj;
            }



        }
    };

    public ExtendReaderActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend_reader);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        uiModeManager=(UiModeManager) getSystemService(Context.UI_MODE_SERVICE);
        mTTAdNative = TTAdSdk.getAdManager().createAdNative(this);
        TTAdSdk.getAdManager().requestPermissionIfNecessary(this);
        dbUtils=new DbUtils(this);
        Intent intent=getIntent();
        id=intent.getIntExtra("id",0);
        token=intent.getStringExtra("token");
        img=intent.getStringExtra("img");
        name=intent.getStringExtra("name");
        author=intent.getStringExtra("author");
        backlistid=intent.getIntExtra("backlistid",0);
        charindex=intent.getIntExtra("charIndex",0);
        Log.e("backlist",backlistid+"--"+charindex);
        type=intent.getIntExtra("type",0);
        Log.e("bookid1",id+"");
        bookListPresenter=new IBookListPresenterImpl(this,this);
        bookListPresenter.setId(id);
        bookListPresenter.loadData();
        bookInfoPresenter=new IBookInfoPresenterImpl(this,this);
        bookInfoPresenter.setId(id);
        bookInfoPresenter.loadData();
        bookContentPresenter=new IBookContentPresenterImpl(this);
        readAwardPresenter=new IReadAwardPresenterImpl(this);
        readAwardPresenter.setToken(token);
        Log.e("readtoken",token);
        readAwardPresenter.setId(id);
        time= DeviceInfoUtils.getTime()+"";
        addBookRankPresenter=new IAddBookRankPresenterImpl(this,this);
        if(token.equals("")){
            List<CaseBean.DataBean.ListBean> list =new ArrayList<>();
            database=dbUtils.getReadableDatabase();
            Cursor listcursor=database.rawQuery("select * from usercase where user='visitor'",new String[]{});
            while(listcursor.moveToNext()){
                CaseBean.DataBean.ListBean listBean=new CaseBean.DataBean.ListBean();
                listBean.setName(listcursor.getString(listcursor.getColumnIndex("name")));
                listBean.setImage(listcursor.getString(listcursor.getColumnIndex("image")));
                listBean.setAuthor(listcursor.getString(listcursor.getColumnIndex("author")));
                listBean.setReadContent(listcursor.getString(listcursor.getColumnIndex("readContent")));
                listBean.setReadStatus(listcursor.getInt(listcursor.getColumnIndex("readStatus")));
                listBean.setBookStatus(listcursor.getInt(listcursor.getColumnIndex("bookStatus")));
                listBean.setBookId(listcursor.getInt(listcursor.getColumnIndex("bookid")));
                listBean.setBacklistPercent(listcursor.getInt(listcursor.getColumnIndex("backlistPercent")));
                listBean.setLastTime(listcursor.getInt(listcursor.getColumnIndex("lastTime")));
                listBean.setBacklistId(listcursor.getInt(listcursor.getColumnIndex("backlistId")));
                list.add(listBean);
            }
            for(int i=0;i<list.size();i++){
                Log.e("bookinfolist",list.get(i).getName()+"--"+list.get(i).getBookId());
                if(list.get(i).getBookId()==id){
                    iscase=true;
                    break;
                }
                else{
                    iscase=false;
                }
            }
        }
        else{
            casePresenter=new ICasePresenterImpl(this,this);
            casePresenter.loadData();
        }
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                Message message = new Message();
//                message.what=1;
//                message.arg1 = recLen;
//                if(recLen!=-1){
//                    recLen--;
//                }else {
//                    return;
//                }
//                handler.sendMessage(message);
//            }
//        }, 1000,1000);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                recLen--;
                Log.e("readaward",recLen+"--");
                if(recLen ==0){
                    readAwardPresenter.loadData();
                    recLen=30;

                }
            }
        };
        timer.schedule(task, 1000, 1000);
       // initData();
        initReader();
        initView();
        initToolbar();

    }

    private void initView() {
        jz=findViewById(R.id.jz);
        logo=findViewById(R.id.img);
        gg=findViewById(R.id.gg);
        loadExpressAd("926447562",gg,logo);
        daynight=findViewById(R.id.daynight);
        bj1=findViewById(R.id.reader_bg_0);
        bj2=findViewById(R.id.reader_bg_1);
        bj3=findViewById(R.id.reader_bg_2);
        bj4=findViewById(R.id.reader_bg_3);
        bj5=findViewById(R.id.reader_bg_4);
        big=findViewById(R.id.big);
        zh=findViewById(R.id.zh);
        zh.setText(mReaderView.getTextSize()+"");
        findViewById(R.id.sm).setOnClickListener(this);
        big.setOnClickListener(this);
        findViewById(R.id.dim).setOnClickListener(this);
        findViewById(R.id.light).setOnClickListener(this);
        mMenuView = findViewById(R.id.menu_view);
        mSettingView = findViewById(R.id.setting_view);
        mChapterSeekBar = findViewById(R.id.chapter_seek_bar);
        // 调节亮度的SeekBar
        lightSeekBar = findViewById(R.id.light_seek_bar);
        //调节字号的SeekBar
        //SeekBar textSizeSeekBar = findViewById(R.id.text_size_seek_bar);
        // 调节文字间距的SeekBar
        //SeekBar textSpaceSeekBar = findViewById(R.id.text_space_seek_bar);

        mDrawerLayout = findViewById(R.id.drawerLayout);
        mNavigationView = findViewById(R.id.navigation);
        mRecyclerView = findViewById(R.id.recyclerView);
        mReaderView.setBackground(getResources().getDrawable(R.color.reader_bg_0));
        //view.setBackground(getResources().getDrawable(R.color.reader_bg_0));
        initRecyclerViewAndDrawerLayout();

        findViewById(R.id.setting).setOnClickListener(this);//设置
        findViewById(R.id.text_prev_chapter).setOnClickListener(this);//上一章
        findViewById(R.id.text_next_chapter).setOnClickListener(this);//下一章
        findViewById(R.id.reader_catalogue).setOnClickListener(this);//目录
        daynight.setOnClickListener(this);//日间夜间
        // 切换背景
        bj1.setOnClickListener(this);
        bj2.setOnClickListener(this);
        bj3.setOnClickListener(this);
        bj4.setOnClickListener(this);
        bj5.setOnClickListener(this);
        // 切换翻页效果
//        findViewById(R.id.effect_real_one_way).setOnClickListener(this);
//        findViewById(R.id.effect_real_both_way).setOnClickListener(this);
//        findViewById(R.id.effect_cover).setOnClickListener(this);
//        findViewById(R.id.effect_slide).setOnClickListener(this);
//        findViewById(R.id.effect_non).setOnClickListener(this);


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

//        textSizeSeekBar.setOnSeekBarChangeListener(new SimpleOnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                mReaderView.setTextSize(progress + 20);//文字大小限制最小20
//            }
//        });

//        textSpaceSeekBar.setOnSeekBarChangeListener(new SimpleOnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                mReaderView.setLineSpace(progress);
//            }
//        });

        lightSeekBar.setMax(255);
//        textSizeSeekBar.setMax(100);
//        textSpaceSeekBar.setMax(200);

        // 初始化SeekBar位置
        mChapterSeekBar.setProgress(0);// 如果需要历史纪录的话，可以在这里实现
        lightSeekBar.setProgress(ScreenUtils.getSystemBrightness(this));
//        textSizeSeekBar.setProgress(mReaderView.getTextSize() - 20);
//        textSpaceSeekBar.setProgress(mReaderView.getLineSpace());
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
                p=position;
                Log.e("position",position+"");
                mChapterSeekBar.setProgress(position);
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
        toolbar.setTitle(name);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iscase){
                    updateCase();
                    finish();
                }
                else{
                    if(type==1){
                        updateCase();
                        finish();
                        //showPopWindow(ExtendReaderActivity.this,1);
                    }
                    else if(type==2){
                        showPopWindow(ExtendReaderActivity.this,2);
                    }
                }
            }
        });
    }
    private void updateCase(){
        update=1;
        int backlistid = 0;
        Log.e("mtitle",ReaderResolve.mTitle);
        for(int i=0;i<bookList.getData().getList().size();i++){
            if(bookList.getData().getList().get(i).getName().equals(ReaderResolve.mTitle)){
                backlistid=bookList.getData().getList().get(i).getId();
                Log.e("back",backlistid+"]]");
            }
        }
        addBookRankPresenter.setId(id);
        addBookRankPresenter.setReadStatus(20);
        addBookRankPresenter.setBacklistPercent(ReaderResolve.percent);
        addBookRankPresenter.setBacklistId(backlistid);
        addBookRankPresenter.setCharIndex(ReaderResolve.mCharIndex);
        addBookRankPresenter.loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.extend_reader_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();

         if (i == R.id.share) {
            //Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initReader() {
        mReaderView = findViewById(R.id.extend_reader);
        mReaderManager = new ReaderView.ReaderManager();

        //mAdapter = new MyReaderAdapter();

        mReaderView.setReaderManager(mReaderManager);

        mReaderView.setLineSpace((int) (mReaderView.getTextSize()*0.5));

        if(mAdapter!=null){
            mReaderView.setAdapter(mAdapter);
            mReaderManager.toPercent(backlistid,charindex);
        }

        //mReaderView.setSretract("111111");
        //mReaderView.setAdapter(mAdapter);
//        view= LayoutInflater.from(this).inflate(R.layout.item_first, null, false);
//        book=view.findViewById(R.id.book_img);
//        bookname=view.findViewById(R.id.book_name);
//        bookauthor=view.findViewById(R.id.book_author);
//        mReaderView.addView(view, FIRST_PAGE);
        //mReaderView.invalidateBothPage();
//        if(img!=null){
//            GlideUrl url = DeviceInfoUtils.getUrl(img,ExtendReaderActivity.this);
//            Glide.with(ExtendReaderActivity.this).load(url)
//                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
//                    .into(new SimpleTarget<Drawable>() {
//                        @Override
//                        public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
//                            book.setImageDrawable(resource);
//                        }
//                    });
//        }
//        bookname.setText(name);
//        bookauthor.setText(author);
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
                        Intent intent = new Intent();
                        intent.setAction("com.jdhd.qynovels");
                        intent.putExtra("id",id);// 通过intent隐式跳转进行跳转
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        startActivity(intent);
                }
                return turnStatus;
            }
        });
    }

    private Disposable mDisposable;

//    private void initData() {
//        LocalServer.getChapterList(id+"", new LocalServer.OnResponseCallback() {
//            @Override
//            public void onSuccess(List<ChapterItemBean> chapters) {
//                List<ChapterItemBean> list=new ArrayList<>();
//                for(int i=0;i<bookList.getData().getList().size();i++){
//                   ChapterItemBean chapterItemBean=new ChapterItemBean();
//                   chapterItemBean.setChapterId(bookList.getData().getList().get(i).getId()+"");
//                   chapterItemBean.setChapterName(bookList.getData().getList().get(i).getName());
//                    list.add(chapterItemBean);
//                }
//                mAdapter.setChapterList(bookList.getData().getList());
//                mAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onError(Exception e) {
//
//            }
//        });
//    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
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
            mDrawerLayout.setBackgroundColor(getResources().getColor(R.color.reader_bg_0));
            bj1.setBackground(getResources().getDrawable(R.drawable.item_readbj1_on));
            bj2.setBackground(getResources().getDrawable(R.drawable.item_readbj2));
            bj3.setBackground(getResources().getDrawable(R.drawable.item_readbj3));
            bj4.setBackground(getResources().getDrawable(R.drawable.item_readbj4));
            bj5.setBackground(getResources().getDrawable(R.drawable.item_readbj5));
            mReaderView.setBackgroundColor(getResources().getColor(R.color.reader_bg_0));
           // view.setBackground(getResources().getDrawable(R.color.reader_bg_0));
            ColorsConfig colorsConfig=new ColorsConfig();
            colorsConfig.setTextColor(Color.parseColor("#5B5956"));
            mReaderView.setColorsConfig(colorsConfig);
        } else if (i == R.id.reader_bg_1) {
            mDrawerLayout.setBackgroundColor(getResources().getColor(R.color.reader_bg_1));
            bj2.setBackground(getResources().getDrawable(R.drawable.item_readbj2_on));
            bj1.setBackground(getResources().getDrawable(R.drawable.item_readbj1));
            bj3.setBackground(getResources().getDrawable(R.drawable.item_readbj3));
            bj4.setBackground(getResources().getDrawable(R.drawable.item_readbj4));
            bj5.setBackground(getResources().getDrawable(R.drawable.item_readbj5));
            mReaderView.setBackgroundColor(getResources().getColor(R.color.reader_bg_1));
           // view.setBackground(getResources().getDrawable(R.color.reader_bg_1));
            ColorsConfig colorsConfig=new ColorsConfig();
            colorsConfig.setTextColor(Color.parseColor("#4E402A"));
            mReaderView.setColorsConfig(colorsConfig);
        } else if (i == R.id.reader_bg_2) {
            mDrawerLayout.setBackgroundColor(getResources().getColor(R.color.reader_bg_2));
            bj3.setBackground(getResources().getDrawable(R.drawable.item_readbj3_on));
            bj1.setBackground(getResources().getDrawable(R.drawable.item_readbj1));
            bj2.setBackground(getResources().getDrawable(R.drawable.item_readbj2));
            bj4.setBackground(getResources().getDrawable(R.drawable.item_readbj4));
            bj5.setBackground(getResources().getDrawable(R.drawable.item_readbj5));
            mReaderView.setBackgroundColor(getResources().getColor(R.color.reader_bg_2));
            //view.setBackground(getResources().getDrawable(R.color.reader_bg_2));

            ColorsConfig colorsConfig=new ColorsConfig();
            colorsConfig.setTextColor(Color.parseColor("#5F665F"));
            mReaderView.setColorsConfig(colorsConfig);
        } else if (i == R.id.reader_bg_3) {
            mDrawerLayout.setBackgroundColor(getResources().getColor(R.color.reader_bg_3));
            bj4.setBackground(getResources().getDrawable(R.drawable.item_readbj4_on));
            bj1.setBackground(getResources().getDrawable(R.drawable.item_readbj1));
            bj2.setBackground(getResources().getDrawable(R.drawable.item_readbj2));
            bj3.setBackground(getResources().getDrawable(R.drawable.item_readbj3));
            bj5.setBackground(getResources().getDrawable(R.drawable.item_readbj5));
            mReaderView.setBackgroundColor(getResources().getColor(R.color.reader_bg_3));
            //view.setBackground(getResources().getDrawable(R.color.reader_bg_3));

            ColorsConfig colorsConfig=new ColorsConfig();
            colorsConfig.setTextColor(Color.parseColor("#928E8C"));
            mReaderView.setColorsConfig(colorsConfig);
        } else if (i == R.id.reader_bg_4) {
            mDrawerLayout.setBackgroundColor(getResources().getColor(R.color.reader_bg_4));
            bj5.setBackground(getResources().getDrawable(R.drawable.item_readbj5_on));
            bj1.setBackground(getResources().getDrawable(R.drawable.item_readbj1));
            bj2.setBackground(getResources().getDrawable(R.drawable.item_readbj2));
            bj3.setBackground(getResources().getDrawable(R.drawable.item_readbj3));
            bj4.setBackground(getResources().getDrawable(R.drawable.item_readbj4));
            mReaderView.setBackgroundColor(getResources().getColor(R.color.reader_bg_4));
            //view.setBackground(getResources().getDrawable(R.color.reader_bg_4));

            ColorsConfig colorsConfig=new ColorsConfig();
            colorsConfig.setTextColor(Color.parseColor("#8A8D90"));
            mReaderView.setColorsConfig(colorsConfig);
            //切换翻页效果
        }
        else if(i==R.id.sm){
            zh.setText(mReaderView.getTextSize()-1+"");
            mReaderView.setTextSize(Integer.parseInt(zh.getText().toString()));
            mReaderView.setLineSpace((int) (mReaderView.getTextSize()*0.5));
            Log.e("textsize",mReaderView.getTextSize()+"");
            Log.e("line",mReaderView.getLineSpace()+"");
        }
        else if(i==R.id.big){
            zh.setText(mReaderView.getTextSize()+1+"");
            mReaderView.setTextSize(Integer.parseInt(zh.getText().toString()));
            mReaderView.setLineSpace((int) (mReaderView.getTextSize()*0.5));
            Log.e("textsize",mReaderView.getTextSize()+"");
            Log.e("line",mReaderView.getLineSpace()+"");
        }
        else if(i==R.id.dim){
            ScreenUtils.changeAppBrightness(ExtendReaderActivity.this, lightSeekBar.getProgress()-1);
            lightSeekBar.setProgress(lightSeekBar.getProgress()-1);

        }
        else if(i==R.id.light){
            ScreenUtils.changeAppBrightness(ExtendReaderActivity.this, lightSeekBar.getProgress()+1);
            lightSeekBar.setProgress(lightSeekBar.getProgress()+1);


        }
        else if (i == R.id.effect_real_one_way) {
            mReaderView.setEffect(new EffectOfRealOneWay(this));
        } else if (i == R.id.effect_real_both_way) {
            mReaderView.setEffect(new EffectOfRealBothWay(this));
        } else if (i == R.id.effect_cover) {
            mReaderView.setEffect(new EffectOfCover(this));
        } else if (i == R.id.effect_slide) {
            mReaderView.setEffect(new EffectOfSlide(this));
        } else if (i == R.id.daynight) {
            if(clicknum%2==0){
                mDrawerLayout.setBackgroundColor(getResources().getColor(R.color.reader_bg_0));
                mReaderView.setBackground(getResources().getDrawable(R.color.reader_bg_0));
                daynight.setImageResource(R.mipmap.yuedu_yj);
                ColorsConfig colorsConfig=new ColorsConfig();
                colorsConfig.setTextColor(Color.parseColor("#5B5956"));
                mReaderView.setColorsConfig(colorsConfig);
            }
            else{
                mDrawerLayout.setBackgroundColor(getResources().getColor(R.color.reader_bg_4));
                mReaderView.setBackground(getResources().getDrawable(R.color.reader_bg_4));
                daynight.setImageResource(R.mipmap.yuedu_yj_on);
                ColorsConfig colorsConfig=new ColorsConfig();
                colorsConfig.setTextColor(Color.parseColor("#8A8D90"));
                mReaderView.setColorsConfig(colorsConfig);
            }
            clicknum++;
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
            if(iscase){
                updateCase();
                finish();
            }
            else{
                if(type==1){
                    Log.e("addcode","调用");
                    updateCase();
                    finish();
                    //showPopWindow(ExtendReaderActivity.this,1);
                }
                else if(type==2){
                    showPopWindow(ExtendReaderActivity.this,2);
                }
            }


        }

    }
    private View.OnClickListener itemclick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView add=v.findViewById(R.id.add);
            TextView notadd=findViewById(R.id.notadd);
            if(R.id.add==v.getId()){
            }
            else if(R.id.notadd==v.getId()){

            }
        }
    };
    private void showPopWindow(ExtendReaderActivity activity, int type){
        AddCasePopWindow addCasePopWindow=new AddCasePopWindow(ExtendReaderActivity.this,itemclick,type,id);
        addCasePopWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
        addCasePopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                addCasePopWindow.backgroundAlpha(ExtendReaderActivity.this,1f);
            }
        });
    }

    @Override
    public void onSuccess(BookListBean bookListBean) {
        List<ChapterItemBean> list=new ArrayList<>();
        bookList=bookListBean;
        mChapterSeekBar.setMax(bookList.getData().getList().size()-1);
       runOnUiThread(new Runnable() {
           @Override
           public void run() {
               mAdapter = new ReaderView.Adapter<BookListBean.DataBean.ListBean, BookContentBean.DataBean>() {
                   @Override
                   public String obtainCacheKey(BookListBean.DataBean.ListBean listBean) {
                       Log.e("listbeanid",listBean.getId()+"[[");
                       return listBean.getId()+"";
                   }

                   @Override
                   public String obtainChapterName(BookListBean.DataBean.ListBean listBean) {
                       return listBean.getName();//chapterItemBean.getChapterName();
                   }


                   @Override
                   public String obtainChapterContent(BookContentBean.DataBean dataBean) {
                       if(dataBean.getContent()!=null){
                           Log.e("nr",dataBean.getContent());
                           runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                   jz.setVisibility(View.GONE);
                               }
                           });
                           return dataBean.getContent();
                       }
                       else{
                           return "暂无数据";
                       }

                   }

                   @Override
                   public BookContentBean.DataBean downLoad(BookListBean.DataBean.ListBean listBean) {
                       Log.e("listbeanid",listBean.getId()+"]]");
                       return LocalServer.syncgetContent(listBean.getId(),token);

                   }
               };

               bookContentPresenter.setContext(ExtendReaderActivity.this);
               bookContentPresenter.setId(backlistid);
               bookContentPresenter.loadData();
               for(int i=0;i<bookListBean.getData().getList().size();i++){
                   if(bookListBean.getData().getList().get(i).getId()==backlistid){
                       mtitle=bookListBean.getData().getList().get(i).getName();
                       Log.e("back",mtitle+"...");
                   }
                   ChapterItemBean itemBean=new ChapterItemBean();
                   itemBean.setChapterId(bookListBean.getData().getList().get(i).getId()+"");
                   itemBean.setChapterName(bookListBean.getData().getList().get(i).getName());
                   list.add(itemBean);
               }
               if(list!=null){
                   mReaderView.setAdapter(mAdapter);
                   mReaderManager.setmTitle(mtitle);
                   mReaderView.setmTitle(mtitle);
                   mReaderManager.toPercent(backlistid,charindex);
                   mReaderView.invalidateBothPage();
                   mCatalogueAdapter.refresh(list);
                   if(mAdapter!=null){
                       mAdapter.setChapterList(bookListBean.getData().getList());
                      // mAdapter.notifyDataSetChanged();
                   }

               }

           }
       });
    }

    @Override
    public void onSuccess(CaseBean caseBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<caseBean.getData().getList().size();i++){
                    if((caseBean.getData().getList().get(i).getBookId()+"").equals(id+"")){
                        iscase=true;
                        break;
                    }
                    else{
                        iscase=false;
                    }
                }
            }
        });
    }

    @Override
    public void onError(String error) {
       Log.e("zjerror",error);
    }


    @Override
    public void onReadSuccess(ReadAwardBean readAwardBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(readAwardBean.getCode()!=200){
                    Toast.makeText(ExtendReaderActivity.this,readAwardBean.getMsg(),Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(ExtendReaderActivity.this,"您已阅读30s,奖励金币"+readAwardBean.getData().getAward(),Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    public void onReadError(String error) {
        Log.e("readawarderror",error);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //timer.cancel();
        //timer=new Timer();
    }


    private void loadExpressAd(String codeId,LinearLayout mExpressContainer,ImageView img) {
        mExpressContainer.removeAllViews();
        float expressViewWidth = 350;
        float expressViewHeight = 350;
        try{
            expressViewWidth = 350;
            expressViewHeight = 53;
        }catch (Exception e){
            expressViewHeight = 0; //高度设置为0,则高度会自适应
        }
        //step4:创建广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeId) //广告位id
                .setSupportDeepLink(true)
                .setAdCount(1) //请求广告数量为1到3条
                .setExpressViewAcceptedSize(expressViewWidth,expressViewHeight) //期望模板广告view的size,单位dp
                .setImageAcceptedSize(150,53 )
                .build();
        //step5:请求广告，对请求回调的广告作渲染处理
        mTTAdNative.loadBannerExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int code, String message) {
                Toast.makeText(ExtendReaderActivity.this,message,Toast.LENGTH_SHORT).show();
                //TToast.show(BannerExpressActivity.this, "load error : " + code + ", " + message);
                mExpressContainer.removeAllViews();
                mExpressContainer.setVisibility(View.GONE);
                img.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
                if (ads == null || ads.size() == 0){
                    return;
                }
                TTNativeExpressAd mTTAd = ads.get(0);
                mTTAdList.add(mTTAd);
                mTTAd.setSlideIntervalTime(30*1000);
                bindAdListener(mTTAd,mExpressContainer,img);
                startTime = System.currentTimeMillis();
                mTTAd.render();
            }
        });
    }
    private long startTime = 0;

    private boolean mHasShowDownloadActive = false;

    private void bindAdListener(TTNativeExpressAd ad,LinearLayout mExpressContainer,ImageView img) {
        ad.setExpressInteractionListener(new TTNativeExpressAd.ExpressAdInteractionListener() {
            @Override
            public void onAdClicked(View view, int type) {
                // TToast.show(mContext, "广告被点击");
            }

            @Override
            public void onAdShow(View view, int type) {
                //TToast.show(mContext, "广告展示");
                img.setVisibility(View.GONE);
            }

            @Override
            public void onRenderFail(View view, String msg, int code) {
                Log.e("ExpressView","render fail:"+(System.currentTimeMillis() - startTime));
                //TToast.show(mContext, msg+" code:"+code);
            }

            @Override
            public void onRenderSuccess(View view, float width, float height) {
                Log.e("ExpressView", "render suc:" + (System.currentTimeMillis() - startTime));
                //返回view的宽高 单位 dp
                // TToast.show(mContext, "渲染成功");
                mExpressContainer.removeAllViews();
                mExpressContainer.addView(view);
            }
        });
        //dislike设置
        bindDislike(ad, false,mExpressContainer,img);
        if (ad.getInteractionType() != TTAdConstant.INTERACTION_TYPE_DOWNLOAD){
            return;
        }
        ad.setDownloadListener(new TTAppDownloadListener() {
            @Override
            public void onIdle() {
                // TToast.show(BannerExpressActivity.this, "点击开始下载", Toast.LENGTH_LONG);
            }

            @Override
            public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                if (!mHasShowDownloadActive) {
                    mHasShowDownloadActive = true;
                    // TToast.show(BannerExpressActivity.this, "下载中，点击暂停", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                //TToast.show(BannerExpressActivity.this, "下载暂停，点击继续", Toast.LENGTH_LONG);
            }

            @Override
            public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                // TToast.show(BannerExpressActivity.this, "下载失败，点击重新下载", Toast.LENGTH_LONG);
            }

            @Override
            public void onInstalled(String fileName, String appName) {
                //TToast.show(BannerExpressActivity.this, "安装完成，点击图片打开", Toast.LENGTH_LONG);
            }

            @Override
            public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                // TToast.show(BannerExpressActivity.this, "点击安装", Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 设置广告的不喜欢, 注意：强烈建议设置该逻辑，如果不设置dislike处理逻辑，则模板广告中的 dislike区域不响应dislike事件。
     * @param ad
     * @param customStyle 是否自定义样式，true:样式自定义
     */
    private void bindDislike(TTNativeExpressAd ad, boolean customStyle,LinearLayout mExpressContainer,ImageView img) {
        if (customStyle) {
            //使用自定义样式
            List<FilterWord> words = ad.getFilterWords();
            if (words == null || words.isEmpty()) {
                return;
            }

            final DislikeDialog dislikeDialog = new DislikeDialog(ExtendReaderActivity.this, words);
            dislikeDialog.setOnDislikeItemClick(new DislikeDialog.OnDislikeItemClick() {
                @Override
                public void onItemClick(FilterWord filterWord) {
                    //屏蔽广告
                    // TToast.show(mContext, "点击 " + filterWord.getName());
                    //用户选择不喜欢原因后，移除广告展示
                    mExpressContainer.removeAllViews();
                    mExpressContainer.setVisibility(View.GONE);
                    img.setVisibility(View.VISIBLE);
                }
            });
            ad.setDislikeDialog(dislikeDialog);
            return;
        }
        //使用默认模板中默认dislike弹出样式
        ad.setDislikeCallback(ExtendReaderActivity.this, new TTAdDislike.DislikeInteractionCallback() {
            @Override
            public void onSelected(int position, String value) {
                // TToast.show(mContext, "点击 " + value);
                //用户选择不喜欢原因后，移除广告展示
                mExpressContainer.removeAllViews();
                mExpressContainer.setVisibility(View.GONE);
                img.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancel() {
                // TToast.show(mContext, "点击取消 ");
            }
        });
    }

    @Override
    public void onBookSuccess(BookContentBean bookContentBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("back",bookContentBean.getData().getContent());
                mReaderManager.setUpReaderResolve(backlistid,charindex,mtitle,bookContentBean.getData().getContent());
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onBookError(String error) {
       Log.e("bookerror",error);
    }

    @Override
    public void onSuccess(AddBookBean addBookBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("addbookbean",addBookBean.getMsg()+"--"+addBookBean.getCode());
                if(addBookBean.getCode()==9005){
                    if(update==0){
                        Toast.makeText(ExtendReaderActivity.this,"加入书架",Toast.LENGTH_SHORT).show();
                        XqActivity.jr.setText("已加入书架");
                        XqActivity.jrsj.setClickable(false);
                        XqActivity.jr.setTextColor(Color.parseColor("#999999"));
                        XqActivity.icon.setImageResource(R.mipmap.xqy_jrsjon);
                    }

                    database=dbUtils.getWritableDatabase();
                    if(bookBean.getData().getBook()!=null){
                        Log.e("addbookid",bookBean.getData().getBook().getBookId()+"==="+bookBean.getData().getBook().getName());
                        database.execSQL("delete from usercase where user='visitor' and name='"+bookBean.getData().getBook().getName()+"'");
                        database.execSQL("insert into usercase(user,name,image,author,readContent,readStatus,bookStatus,bookid,backlistPercent,lastTime,backlistId) " +
                                "values('visitor'," +
                                "'"+bookBean.getData().getBook().getName()+"'," +
                                "'"+bookBean.getData().getBook().getImage()+"'," +
                                "'"+bookBean.getData().getBook().getAuthor()+"'," +
                                "'"+ReaderResolve.mTitle+"'," +
                                "20,+'"+bookBean.getData().getBook().getFinishStatus()+"',+'"+bookBean.getData().getBook().getBookId()+"'," +
                                "'"+ReaderResolve.percent+"','"+DeviceInfoUtils.changeData(time)+"日"+"',+'"+bookBean.getData().getBook().getBacklistNum()+"')");
                        Cursor cursor = database.rawQuery("select * from usercase where user='visitor'", new String[]{});

                    }
                    EventBus.getDefault().post(addBookBean);
                }
                else if(addBookBean.getCode()!=200){
                    Toast.makeText(ExtendReaderActivity.this,addBookBean.getMsg(),Toast.LENGTH_SHORT).show();
                }
                else{
                    if(update==0){
                        Toast.makeText(ExtendReaderActivity.this,"加入书架",Toast.LENGTH_SHORT).show();
                        XqActivity.jr.setText("已加入书架");
                        XqActivity.jrsj.setClickable(false);
                        XqActivity.jr.setTextColor(Color.parseColor("#999999"));
                        XqActivity.icon.setImageResource(R.mipmap.xqy_jrsjon);
                    }
                    database=dbUtils.getWritableDatabase();

                    if(bookBean.getData().getBook()!=null){
                        database.execSQL("delete from usercase where user='user' and name='"+bookBean.getData().getBook().getName()+"'");
                        database.execSQL("insert into usercase(user,name,image,author,readContent,readStatus,bookStatus,bookid,backlistPercent,lastTime,backlistId) " +
                                "values('visitor'," +
                                "'"+bookBean.getData().getBook().getName()+"'," +
                                "'"+bookBean.getData().getBook().getImage()+"'," +
                                "'"+bookBean.getData().getBook().getAuthor()+"'," +
                                "'"+ReaderResolve.mTitle+"'," +
                                "20,+'"+bookBean.getData().getBook().getFinishStatus()+"',+'"+bookBean.getData().getBook().getBookId()+"'," +
                                "'"+ReaderResolve.percent+"','"+DeviceInfoUtils.changeData(time)+"日"+"',+'"+bookBean.getData().getBook().getBacklistNum()+"')");
                    }
                    EventBus.getDefault().post(addBookBean);


                }


            }
        });
    }

    @Override
    public void onAddError(String error) {

    }

    @Override
    public void onBookinfoSuccess(BookInfoBean bookInfoBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bookBean=bookInfoBean;
            }
        });
    }

    @Override
    public void onBookinfoError(String error) {

    }
}
