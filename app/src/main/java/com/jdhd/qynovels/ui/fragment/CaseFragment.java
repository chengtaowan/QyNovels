package com.jdhd.qynovels.ui.fragment;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.FilterWord;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdDislike;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.google.gson.Gson;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.CaseAdapter;
import com.jdhd.qynovels.adapter.ListAdapter;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.bookcase.AddBookBean;
import com.jdhd.qynovels.module.bookcase.CaseBean;
import com.jdhd.qynovels.module.bookcase.ConfigBean;
import com.jdhd.qynovels.module.personal.EventBean;
import com.jdhd.qynovels.module.personal.TokenBean;
import com.jdhd.qynovels.module.personal.UserBean;
import com.jdhd.qynovels.module.personal.UserEventBean;
import com.jdhd.qynovels.module.personal.VisitorBean;
import com.jdhd.qynovels.persenter.impl.bookcase.ICasePresenterImpl;
import com.jdhd.qynovels.persenter.impl.bookcase.IConfigPresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.IPersonalPresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.IUserEventPresenterImpl;
import com.jdhd.qynovels.ui.activity.LsActivity;
import com.jdhd.qynovels.ui.activity.SsActivity;
import com.jdhd.qynovels.ui.activity.XqActivity;
import com.jdhd.qynovels.utils.DbUtils;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.utils.EventDbUtils;
import com.jdhd.qynovels.view.bookcase.ICaseView;
import com.jdhd.qynovels.view.bookcase.IConfigView;
import com.jdhd.qynovels.view.personal.IPersonalView;
import com.jdhd.qynovels.view.personal.IUserEventView;
import com.jdhd.qynovels.widget.GetMoneyPopWindow;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.List;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class CaseFragment extends BaseFragment implements View.OnClickListener, ICaseView,CaseAdapter.onItemClick, IPersonalView, IConfigView, IUserEventView {
    private ImageView ss,ls,qd;
    private RecyclerView rv;
    public static ICasePresenterImpl casePresenter;
    private CaseAdapter adapter;
    public static List<CaseBean.DataBean.ListBean> list=new ArrayList<>();
    private RelativeLayout jz;
    private ImageView gif;
    private int hotid;
    private DbUtils dbUtils;
    private SQLiteDatabase database;
    private Cursor hotcursor;
    private Cursor listcursor;
    public static SmartRefreshLayout sr;
    private boolean hasNetWork;
    private String token;
    private RelativeLayout rl;
    private IUserEventPresenterImpl iUserEventPresenter;
    private int sratrTime,endTime;


    private static final int LIST_ITEM_COUNT = 30;
    private List<TTNativeExpressAd> mData=new ArrayList<>();
    protected boolean isCreated = false;
    private ICaseView iCaseView;
    private IPersonalPresenterImpl personalPresenter;
    private IConfigPresenterImpl configPresenter;
    private TextView time;
    private int studia=0;
    private TTNativeExpressAd mTTAd;
    private TTAdNative mTTAdNative;
    public static ImageView lhb;

    private String TAG="CaseFragment";
    private String islogin="";

    private View rootView;
    /** 标志位，标志已经初始化完成 /
     private boolean isPrepared;
     /* 是否已被加载过一次，第二次就不再去请求数据了 */
    private boolean mHasLoadedOnce;
    private boolean isPrepared;


    /**
     * 是否显示ｃｈｅｃｋｂｏｘ
     */
    private boolean isShowCheck;
    /**
     * 记录选中的ｃｈｅｃｋｂｏｘ
     */
    private List<String> checkList;
    public CaseFragment() {
        mTTAdNative = TTAdSdk.getAdManager().createAdNative(getContext());
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"oncreate");
        adapter=new CaseAdapter(getActivity(),getActivity());
        mTTAdNative = TTAdSdk.getAdManager().createAdNative(getContext());
        casePresenter=new ICasePresenterImpl(this,getContext());


    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getToken(TokenBean tokenBean){
        casePresenter=new ICasePresenterImpl(this,getContext());
        casePresenter.setToken(tokenBean.getData().getToken());
        casePresenter.loadData();
        lhb.setVisibility(View.GONE);
        closePopWindow();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void change(AddBookBean addBookBean){
        Log.e("addcode",addBookBean.getCode()+"[[");
        //未登录查找数据库中游客书架
        if(addBookBean.getCode()==9005){
            List<CaseBean.DataBean.ListBean> list =new ArrayList<>();
            List<CaseBean.DataBean.ListBean> newlist =new ArrayList<>();
            database=dbUtils.getReadableDatabase();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    listcursor=database.rawQuery("select * from usercase where user='visitor'",new String[]{});
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
                }
            }).start();
            Log.e("caselistsizerl",list.size()+"[[");
            //将推荐的书不展示
            for(int i=0;i<list.size();i++){
                if(!list.get(i).getReadContent().equals("")){
                    newlist.add(list.get(i));
                }

            }
            //刷新适配器
            if(newlist.size()!=0){
                adapter.refreshlist(newlist);
                if(sr!=null){
                    sr.finishRefresh();
                }

            }
        }
        //如果登录直接请求书架接口
        else{
            SharedPreferences sharedPreferences=getContext().getSharedPreferences("token",MODE_PRIVATE);
            token=sharedPreferences.getString("token","");
            casePresenter.setToken(token);
            casePresenter.loadData();
        }

        Log.e("change","收到");

    }

    @Override
    public void onStart() {
        super.onStart();
        sratrTime=DeviceInfoUtils.getTime();
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("token",Context.MODE_PRIVATE);
        token=sharedPreferences.getString("token","");
        islogin=sharedPreferences.getString("islogin","");
        if((!token.equals("")&&islogin.equals("0"))||token.equals("")){
            lhb.setVisibility(View.VISIBLE);
        }
        else{
            lhb.setVisibility(View.GONE);
        }
        Log.e(TAG,"onstart");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG,"onattach");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG,"onstartresume");
        lazyLoad();
    }


    @Override
    public void onPause() {
        super.onPause();
        endTime=DeviceInfoUtils.getTime();
        EventDbUtils eventDbUtils=new EventDbUtils(getContext());
        List<EventBean.DataBean> updata = eventDbUtils.updata(MyApp.kQYDataAnalysisEventType.kQYDataAnalysisPageEvent, sratrTime, endTime, MyApp.kQYoperationType.kQYSoperationTypeOpen, MyApp.kQYPageDataAnalysis.kQYPageDataAnalysisBookShelf);
        if(updata.size()==20){
            Gson gson=new Gson();
            String s=gson.toJson(updata);
            iUserEventPresenter.setJson(s);
            iUserEventPresenter.loadData();
        }
        Log.e(TAG,"onpause");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e(TAG,"oncreateview");
        if(rootView==null){
            rootView= inflater.inflate(R.layout.fragment_case, container, false);
            isPrepared = true;
            SharedPreferences preferences=getActivity().getSharedPreferences("token", MODE_PRIVATE);
            token = preferences.getString("token", "");

            init(rootView);
        }
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        iUserEventPresenter=new IUserEventPresenterImpl(this,getContext());
        dbUtils=new DbUtils(getContext());

        init(rootView);
        hasNetWork = DeviceInfoUtils.hasNetWork(getContext());
        Log.e("hasnetwork",hasNetWork+"---");
        //如果没有网络
        if(!hasNetWork){
            jz.setVisibility(View.GONE);
           database=dbUtils.getReadableDatabase();
           String hotsql="select * from hot";
           new Thread(new Runnable() {
               @Override
               public void run() {
                   hotcursor=database.rawQuery(hotsql,new String[]{});
                   while(hotcursor.moveToNext()){
                       CaseBean.DataBean.HotBean hotBean=new CaseBean.DataBean.HotBean();
                       hotBean.setBookId(hotcursor.getInt(hotcursor.getColumnIndex("bookid")));
                       hotBean.setName(hotcursor.getString(hotcursor.getColumnIndex("name")));
                       hotBean.setImage(hotcursor.getString(hotcursor.getColumnIndex("image")));
                       hotBean.setAuthor(hotcursor.getString(hotcursor.getColumnIndex("author")));
                       hotBean.setIntro(hotcursor.getString(hotcursor.getColumnIndex("intro")));
                       Log.e("hotbean",hotBean.toString());
                       adapter.refreshhot(hotBean);
                   }
               }
           }).start();
           Log.e("casetoken",token+"---");
           //如果为用户
           if((!token.equals("")&&islogin.equals("1"))){
               List<CaseBean.DataBean.ListBean> list =new ArrayList<>();
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       database=dbUtils.getReadableDatabase();
                       listcursor=database.rawQuery("select * from usercase where user='user'",new String[]{});
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
                   }
               }).start();
               adapter.refreshlist(list);
           }
           //如果为游客
           else{
               List<CaseBean.DataBean.ListBean> list =new ArrayList<>();
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       database=dbUtils.getReadableDatabase();
                       listcursor=database.rawQuery("select * from usercase where user='visitor'",new String[]{});
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
                   }
               }).start();
               adapter.refreshlist(list);
           }
        }
        //有网络
        else{
            Log.e("casetoken",token+"---");
            if(!token.equals("")&&islogin.equals("1")){
                casePresenter.setToken(token);
                casePresenter.loadData();
            }
            else{
                List<CaseBean.DataBean.ListBean> list =new ArrayList<>();
                List<CaseBean.DataBean.ListBean> newlist =new ArrayList<>();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        database=dbUtils.getReadableDatabase();
                        listcursor=database.rawQuery("select * from usercase where user='visitor'",new String[]{});
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
                    }
                }).start();
                Log.e("caselistsize",list.size()+"]]]");
                for(int i=0;i<list.size();i++){
                    if(!list.get(i).getReadContent().equals("")){
                        newlist.add(list.get(i));
                    }

                }
                if(newlist.size()!=0){
                    adapter.refreshlist(newlist);
                    jz.setVisibility(View.GONE);
                }
                else{
                    SharedPreferences sharedPreferences=getContext().getSharedPreferences("token",MODE_PRIVATE);
                    token=sharedPreferences.getString("token","");
                    casePresenter.setToken(token);
                    casePresenter.loadData();
                }

            }


            }

        return  rootView;
    }

    private void init(View view) {
      time=view.findViewById(R.id.time);
      sr=view.findViewById(R.id.sr);
      ss=view.findViewById(R.id.sj_ss);
      ls=view.findViewById(R.id.sj_ls);
      qd=view.findViewById(R.id.sj_qd);
      rv=view.findViewById(R.id.sj_rv);
      jz=view.findViewById(R.id.jz);
      gif=view.findViewById(R.id.case_gif);
      ss.setOnClickListener(this);
      ls.setOnClickListener(this);
      qd.setOnClickListener(this);
      rl=view.findViewById(R.id.rl);
        lhb=view.findViewById(R.id.lhb);
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("token",Context.MODE_PRIVATE);
        token=sharedPreferences.getString("token","");
        islogin=sharedPreferences.getString("islogin","");
        if((!token.equals("")&&islogin.equals("0"))||token.equals("")){
            lhb.setVisibility(View.VISIBLE);
        }
        else{
            lhb.setVisibility(View.GONE);
        }
        lhb.setOnClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
      rv.setLayoutManager(layoutManager);
      time.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Oswald-Bold.otf"));

      rv.setAdapter(adapter);
        //loadListAd();
      Glide.with(getContext()).load(R.mipmap.re).into(gif);
        rv.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (sr.isRefreshing()) {
                            return true;
                        }
                        else {
                            return false;
                        }
                    }
                });

      sr.setOnRefreshListener(new OnRefreshListener() {
          @Override
          public void onRefresh(RefreshLayout refreshLayout) {
              hasNetWork = DeviceInfoUtils.hasNetWork(getContext());
              if(!hasNetWork){
                  Toast.makeText(getContext(),"网络连接不可用",Toast.LENGTH_SHORT).show();
                  if(sr!=null){
                      sr.finishRefresh();
                  }

              }
              else{
                  new Thread(new Runnable() {
                      @Override
                      public void run() {
                          try {
                              Thread.sleep(1000);
                          } catch (InterruptedException e) {
                              e.printStackTrace();
                          }
                          sr.finishRefresh();
                      }
                  }).start();
//                  if((!token.equals("")&&islogin.equals("0"))||token.equals("")){
//                      List<CaseBean.DataBean.ListBean> list =new ArrayList<>();
//                      List<CaseBean.DataBean.ListBean> newlist =new ArrayList<>();
//                      database=dbUtils.getReadableDatabase();
//                      listcursor=database.rawQuery("select * from usercase where user='visitor'",new String[]{});
//                      while(listcursor.moveToNext()){
//                          CaseBean.DataBean.ListBean listBean=new CaseBean.DataBean.ListBean();
//                          listBean.setName(listcursor.getString(listcursor.getColumnIndex("name")));
//                          listBean.setImage(listcursor.getString(listcursor.getColumnIndex("image")));
//                          listBean.setAuthor(listcursor.getString(listcursor.getColumnIndex("author")));
//                          listBean.setReadContent(listcursor.getString(listcursor.getColumnIndex("readContent")));
//                          listBean.setReadStatus(listcursor.getInt(listcursor.getColumnIndex("readStatus")));
//                          listBean.setBookStatus(listcursor.getInt(listcursor.getColumnIndex("bookStatus")));
//                          listBean.setBookId(listcursor.getInt(listcursor.getColumnIndex("bookid")));
//                          listBean.setBacklistPercent(listcursor.getInt(listcursor.getColumnIndex("backlistPercent")));
//                          listBean.setLastTime(listcursor.getInt(listcursor.getColumnIndex("lastTime")));
//                          listBean.setBacklistId(listcursor.getInt(listcursor.getColumnIndex("backlistId")));
//                          list.add(listBean);
//                      }
//                      Log.e("caselistsizerl",list.size()+"[[");
//                      for(int i=0;i<list.size();i++){
//                          if(!list.get(i).getReadContent().equals("")){
//                              newlist.add(list.get(i));
//                          }
//
//                      }
//                      if(newlist.size()!=0){
//                          adapter.refreshlist(newlist);
//                          sr.finishRefresh();
//                      }
//                      else{
//                          SharedPreferences sharedPreferences=getContext().getSharedPreferences("token",MODE_PRIVATE);
//                          token=sharedPreferences.getString("token","");
//                          casePresenter.setToken(token);
//                          casePresenter.loadData();
//                      }
//
//                  }
//                  else{
//                      SharedPreferences sharedPreferences=getContext().getSharedPreferences("token",MODE_PRIVATE);
//                      token=sharedPreferences.getString("token","");
//                      casePresenter.setToken(token);
//                      casePresenter.loadData();
//                      mData.clear();
//                      if(studia==20){
//                          new Thread(new Runnable() {
//                              @Override
//                              public void run() {
//                                  loadListAd();
//                                 // loadExpressAd("901121253", ListAdapter.FeedViewHolder.ll);
//                              }
//                          }).start();
//                      }
//                      adapter.refreshfeed(mData);
//                  }

              }

          }
      });
    }


    @Override
    public void onClick(View view) {
        if(R.id.sj_ss==view.getId()){
            int time=DeviceInfoUtils.getTime();
            EventDbUtils eventDbUtils=new EventDbUtils(getContext());
            List<EventBean.DataBean> updata = eventDbUtils.updata(MyApp.kQYDataAnalysisEventType.kQYDataAnalysisTargetEvent, time, 0, MyApp.kQYoperationType.kQYSoperationTypeOpen, MyApp.kQYTargetDataAnalysis.kQYTargetDataAnalysisBookshelf_search);
            if(updata.size()==20){
                Gson gson=new Gson();
                String s=gson.toJson(updata);
                iUserEventPresenter.setJson(s);
                iUserEventPresenter.loadData();
            }
            Intent intent=new Intent(getActivity(), SsActivity.class);
            intent.putExtra("ss",1);
            startActivity(intent);
        }
        else if(R.id.sj_ls==view.getId()){
            Intent intent=new Intent(getActivity(), LsActivity.class);
            intent.putExtra("ls",1);
            startActivity(intent);
        }
        else if(R.id.sj_qd==view.getId()){
            if(!token.equals("")){
//                Intent intent=new Intent(getActivity(), QdActivity.class);
//                startActivity(intent);
            }
            else{
                Toast.makeText(getContext(),"请先登录",Toast.LENGTH_SHORT).show();
            }

        }
        else if(R.id.lhb==view.getId()){
            showPopWindow(rl);
            lhb.setVisibility(View.GONE);
        }
    }

    /**
     * 书架接口请求完成
     * @param caseBean
     */
    @Override
    public void onSuccess(CaseBean caseBean) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("caselist","casebean");
                if(sr!=null){
                    sr.finishRefresh();
                }
                jz.setVisibility(View.GONE);
                //如果请求成功使用请求的数据刷新适配器
                if(caseBean.getCode()==200){
                    database=dbUtils.getWritableDatabase();
                    //先清空用户书架的数据
                    database.execSQL("delete from hot");
                    database.execSQL("delete from usercase where user='user'");
                    String sql="insert into hot(bookid,name,image,intro,author) values('"+caseBean.getData().getHot().getBookId()+"','"+caseBean.getData().getHot().getName()+"','"+caseBean.getData().getHot().getImage()+"','"+caseBean.getData().getHot().getIntro()+"','"+caseBean.getData().getHot().getAuthor()+"')";
                    //将数据插入到用户书架的数据库中
                    if(!token.equals("")&&islogin.equals("1")){
                        for(int i=0;i<caseBean.getData().getList().size();i++){
                            database.execSQL("insert into usercase(user,name,image,author,readContent,readStatus,bookStatus,bookid,backlistPercent,lastTime,backlistId)" +
                                    " values('user'," +
                                    "'"+caseBean.getData().getList().get(i).getName()+"'," +
                                    "'"+caseBean.getData().getList().get(i).getImage()+"'," +
                                    "'"+caseBean.getData().getList().get(i).getAuthor()+"'," +
                                    "'"+caseBean.getData().getList().get(i).getReadContent()+"'," +
                                    "'"+caseBean.getData().getList().get(i).getReadStatus()+"'," +
                                    "'"+caseBean.getData().getList().get(i).getBookStatus()+"'," +
                                    "'"+caseBean.getData().getList().get(i).getBookId()+"'," +
                                    "'"+caseBean.getData().getList().get(i).getBacklistPercent()+"'," +
                                    "'"+caseBean.getData().getList().get(i).getLastTime()+"'," +
                                    "'"+caseBean.getData().getList().get(i).getBacklistId()+"')");
                        }
                    }
//                else{
//                    for(int i=0;i<caseBean.getData().getList().size();i++){
//                        Log.e("notokencontent",caseBean.getData().getList().get(i).getReadContent()+"---");
//                        database.execSQL("insert into usercase(user,name,image,author,readContent,readStatus,bookStatus,bookid,backlistPercent,lastTime,backlistId)" +
//                                " values('visitor'," +
//                                "'"+caseBean.getData().getList().get(i).getName()+"'," +
//                                "'"+caseBean.getData().getList().get(i).getImage()+"'," +
//                                "'"+caseBean.getData().getList().get(i).getAuthor()+"'," +
//                                "'"+caseBean.getData().getList().get(i).getReadContent()+"'," +
//                                "'"+caseBean.getData().getList().get(i).getReadStatus()+"'," +
//                                "'"+caseBean.getData().getList().get(i).getBookStatus()+"'," +
//                                "'"+caseBean.getData().getList().get(i).getBookId()+"'," +
//                                "'"+caseBean.getData().getList().get(i).getBacklistPercent()+"'," +
//                                "'"+caseBean.getData().getList().get(i).getLastTime()+"'," +
//                                "'"+caseBean.getData().getList().get(i).getBacklistId()+"')");
//                    }
//                }
                    //刷新适配器
                    database.execSQL(sql);
                    jz.setVisibility(View.GONE);
                    list=caseBean.getData().getList();
                    adapter.refreshhot(caseBean.getData().getHot());
                    adapter.refreshlist(caseBean.getData().getList());
                    hotid=caseBean.getData().getHot().getBookId();
                }
                //没有请求成功使用用户书架数据刷新适配器
                else {
                    List<CaseBean.DataBean.ListBean> list = new ArrayList<>();
                    List<CaseBean.DataBean.ListBean> newlist = new ArrayList<>();
                    database = dbUtils.getReadableDatabase();
                    listcursor = database.rawQuery("select * from usercase where user='user'", new String[]{});
                    while (listcursor.moveToNext()) {
                        CaseBean.DataBean.ListBean listBean = new CaseBean.DataBean.ListBean();
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
                    Log.e("caselistsize", list.size() + "]]]");
                    for (int i = 0; i < list.size(); i++) {
                        if (!list.get(i).getReadContent().equals("")) {
                            newlist.add(list.get(i));
                        }

                    }
                    if (newlist.size() != 0) {
                        adapter.refreshlist(newlist);
                        jz.setVisibility(View.GONE);
                    }
                }
            }
        });

    }

    @Override
    public void onSuccess(UserBean userBean) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("userbean","1");
                time.setText(userBean.getData().getRead_time()/60+"");
            }
        });
    }

    @Override
    public void onError(String error) {
        if(sr!=null){
            sr.finishRefresh();
        }
        Log.e("caseerror",error);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if(casePresenter!=null){
            casePresenter.destoryView();
        }
        if(hotcursor!=null){
            hotcursor.close();
        }
        if(listcursor!=null){
            listcursor.close();
        }
    }

    @Override
    public void onClick(int index) {
        Intent intent=new Intent(getContext(),XqActivity.class);
        intent.putExtra("xq",1);
        intent.putExtra("id",list.get(index).getId());
        startActivity(intent);
    }


    @Override
    public void onConfigSuccess(ConfigBean configBean) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                studia=configBean.getData().getList().get(1).getStatus();
                Log.e("studia",studia+"");
                if(configBean.getData().getList().get(1).getStatus()==20){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            loadListAd();
                        }
                    }).start();
                }

            }
        });
    }

    @Override
    public void onConfigError(String error) {
        Log.e("configerror",error);
    }


    /**
     * 加载feed广告
     */
    private void loadListAd() {
        //step4:创建feed广告请求类型参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId("926447523")
               // .setCodeId("901121253")
                .setSupportDeepLink(true)
                .setImageAcceptedSize(150, 200)
                .setExpressViewAcceptedSize(150, 200) //期望模板广告view的size,单位dp
                .setAdCount(3) //请求广告数量为1到3条
                .build();
        //step5:请求广告，调用feed广告异步请求接口，加载到广告后，拿到广告素材自定义渲染
        mTTAdNative.loadNativeExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int code, String message) {
                Log.e("message",message+"---===");
               // Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
                //TToast.show(NativeExpressListActivity.this, message);
            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
                Log.e("message","1111---");
                bindAdListener(ads);
            }
        });
    }

    private void bindAdListener(final List<TTNativeExpressAd> ads) {
        for (TTNativeExpressAd ad : ads) {
            final TTNativeExpressAd adTmp = ad;
            mData.add(ad);
            adapter.refreshfeed(mData);

            adTmp.setExpressInteractionListener(new TTNativeExpressAd.ExpressAdInteractionListener() {
                @Override
                public void onAdClicked(View view, int type) {
                    //TToast.show(NativeExpressListActivity.this, "广告被点击");
                    Log.e("message","广告被点击");
                }

                @Override
                public void onAdShow(View view, int type) {
                    Log.e("message","广告展示");
                    //TToast.show(NativeExpressListActivity.this, "广告展示");
                }

                @Override
                public void onRenderFail(View view, String msg, int code) {
                    Log.e("message",msg + " code:" + code);
                    //TToast.show(NativeExpressListActivity.this, msg + " code:" + code);
                }

                @Override
                public void onRenderSuccess(View view, float width, float height) {
                    //返回view的宽高 单位 dp
                    Log.e("message","渲染成功"+width+"--"+height);
                   // TToast.show(NativeExpressListActivity.this, "渲染成功");
                   // adapter.notifyDataSetChanged();
                }
            });
            ad.render();

        }

    }





    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce){
            return;
        }
        personalPresenter=new IPersonalPresenterImpl(this,getContext());
        SharedPreferences sharedPreferences1=getContext().getSharedPreferences("token",MODE_PRIVATE);
        token=sharedPreferences1.getString("token","");
        personalPresenter.setToken(token);
        personalPresenter.loadData();
        configPresenter=new IConfigPresenterImpl(this,getContext());
        configPresenter.loadData();
        casePresenter=new ICasePresenterImpl(this,getContext());
        if(!token.equals("")&&islogin.equals("1")){
            SharedPreferences sharedPreferences=getContext().getSharedPreferences("token",MODE_PRIVATE);
            token=sharedPreferences.getString("token","");
            casePresenter.setToken(token);
            casePresenter.loadData();
        }
        Log.e(TAG,TAG+"加载数据");
        //mHasLoadedOnce = true;
    }

    public  static GetMoneyPopWindow customPopWindow;
    private void showPopWindow(View v){
        customPopWindow=new GetMoneyPopWindow(getActivity());
        customPopWindow.showAtLocation(v,
                Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
        customPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                customPopWindow.backgroundAlpha(getActivity(), 1f);
            }
        });

    }

    public static void closePopWindow(){
        if(customPopWindow!=null){
            customPopWindow.dismiss();
        }

    }

    @Override
    public void onUserEventSuccess(UserEventBean userEventBean) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(userEventBean.getCode()==200){
                    database=dbUtils.getWritableDatabase();
                    database.execSQL("delete from userevent");
                }
            }
        });
    }

    @Override
    public void onUserEventError(String error) {

    }
}
