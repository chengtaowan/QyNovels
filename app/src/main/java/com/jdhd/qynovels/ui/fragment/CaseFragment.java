package com.jdhd.qynovels.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.CaseAdapter;
import com.jdhd.qynovels.adapter.ListAdapter;
import com.jdhd.qynovels.module.bookcase.AddBookBean;
import com.jdhd.qynovels.module.bookcase.CaseBean;
import com.jdhd.qynovels.module.bookcase.ConfigBean;
import com.jdhd.qynovels.module.personal.UserBean;
import com.jdhd.qynovels.persenter.impl.bookcase.ICasePresenterImpl;
import com.jdhd.qynovels.persenter.impl.bookcase.IConfigPresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.IPersonalPresenterImpl;
import com.jdhd.qynovels.ui.activity.LsActivity;
import com.jdhd.qynovels.ui.activity.SsActivity;
import com.jdhd.qynovels.ui.activity.XqActivity;
import com.jdhd.qynovels.utils.AppSigning;
import com.jdhd.qynovels.utils.DbUtils;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.bookcase.ICaseView;
import com.jdhd.qynovels.view.bookcase.IConfigView;
import com.jdhd.qynovels.view.personal.IPersonalView;
import com.jdhd.qynovels.widget.MRefreshHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class CaseFragment extends Fragment implements View.OnClickListener, ICaseView,CaseAdapter.onItemClick, IPersonalView, IConfigView {
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
    private SmartRefreshLayout sr;
    private boolean hasNetWork;
    private String token;
    private TTAdNative mTTAdNative;
    private static final int LIST_ITEM_COUNT = 30;
    private List<TTFeedAd> mData=new ArrayList<>();
    protected boolean isCreated = false;
    private ICaseView iCaseView;
    private IPersonalPresenterImpl personalPresenter;
    private IConfigPresenterImpl configPresenter;
    private TextView time;
    private int studia=0;

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
        Log.e("statud","oncreate");
        adapter=new CaseAdapter(getContext(),getActivity());

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void change(AddBookBean addBookBean){
        Log.e("addcode",addBookBean.getCode()+"[[");
        if(addBookBean.getCode()==9005){
            List<CaseBean.DataBean.ListBean> list =new ArrayList<>();
            List<CaseBean.DataBean.ListBean> newlist =new ArrayList<>();
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
            Log.e("caselistsizerl",list.size()+"[[");
            for(int i=0;i<list.size();i++){
                if(!list.get(i).getReadContent().equals("")){
                    newlist.add(list.get(i));
                }

            }
            if(newlist.size()!=0){
                adapter.refreshlist(newlist);
                sr.finishRefresh();
            }
//            else{
//                casePresenter.loadData();
//            }
        }
        else{
            casePresenter=new ICasePresenterImpl(this,getContext());
            casePresenter.loadData();
        }

        Log.e("change","收到");

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("statud","onstart");
        personalPresenter.loadData();
//        // 标记
//        isCreated = true;
//        casePresenter=new ICasePresenterImpl(new ICaseView() {
//            @Override
//            public void onSuccess(CaseBean caseBean) {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        adapter.refreshlist(caseBean.getData().getList());
//                    }
//                });
//            }
//
//            @Override
//            public void onError(String error) {
//
//            }
//        }, getContext());
//        loadListAd();
//        casePresenter.loadData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("statud","onattach");
//        // 标记
        isCreated = true;
//        configPresenter=new IConfigPresenterImpl(new IConfigView() {
//            @Override
//            public void onConfigSuccess(ConfigBean configBean) {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if(configBean.getData().getList().get(1).getStatus()==20){
//                            new Thread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    loadListAd();
//                                }
//                            }).start();
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public void onConfigError(String error) {
//               Log.e("configerror",error);
//            }
//        },getContext());
//        configPresenter.loadData();
//
//        casePresenter=new ICasePresenterImpl(new ICaseView() {
//            @Override
//            public void onSuccess(CaseBean caseBean) {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if(sr!=null){
//                            sr.finishRefresh();
//                        }
//                        adapter.refreshlist(caseBean.getData().getList());
//                        adapter.refreshhot(caseBean.getData().getHot());
//                    }
//                });
//            }
//
//            @Override
//            public void onError(String error) {
//
//            }
//        }, getContext());
//        casePresenter.loadData();

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("statud","onresume");
//        configPresenter=new IConfigPresenterImpl(new IConfigView() {
//            @Override
//            public void onConfigSuccess(ConfigBean configBean) {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if(configBean.getData().getList().get(1).getStatus()==20){
//                            new Thread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    loadListAd();
//                                }
//                            }).start();
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public void onConfigError(String error) {
//                Log.e("configerror",error);
//            }
//        },getContext());
//        configPresenter.loadData();
//        casePresenter=new ICasePresenterImpl(new ICaseView() {
//            @Override
//            public void onSuccess(CaseBean caseBean) {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if(sr!=null){
//                            sr.finishRefresh();
//                        }
//                        adapter.refreshlist(caseBean.getData().getList());
//                        adapter.refreshhot(caseBean.getData().getHot());
//                    }
//                });
//            }
//
//            @Override
//            public void onError(String error) {
//
//            }
//        }, getContext());
//        casePresenter.loadData();
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.e("statud","onpause");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e("statud","oncreateview");
        View view= inflater.inflate(R.layout.fragment_case, container, false);
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        SharedPreferences preferences=getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
        token = preferences.getString("token", "");
        iCaseView=this;
        personalPresenter=new IPersonalPresenterImpl(this,getContext());
        personalPresenter.loadData();
        configPresenter=new IConfigPresenterImpl(this,getContext());
        configPresenter.loadData();
        String sha1 = AppSigning.getSha1(getContext());
        Log.e("sha1",sha1);
        casePresenter=new ICasePresenterImpl(this,getContext());
        if(!token.equals("")){
            casePresenter.loadData();
        }
        dbUtils=new DbUtils(getContext());
        init(view);
        hasNetWork = DeviceInfoUtils.hasNetWork(getContext());
        Log.e("hasnetwork",hasNetWork+"---");
        if(!hasNetWork){
            jz.setVisibility(View.GONE);
           database=dbUtils.getReadableDatabase();
           String hotsql="select * from hot";
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
           Log.e("casetoken",token+"---");
           if(!token.equals("")){
               List<CaseBean.DataBean.ListBean> list =new ArrayList<>();
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
               adapter.refreshlist(list);
           }
           else{
               List<CaseBean.DataBean.ListBean> list =new ArrayList<>();
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
               adapter.refreshlist(list);
           }
        }
        else{
            Log.e("casetoken",token+"---");
            if(!token.equals("")){

            }
            else{
                List<CaseBean.DataBean.ListBean> list =new ArrayList<>();
                List<CaseBean.DataBean.ListBean> newlist =new ArrayList<>();
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
                    casePresenter.loadData();
                }

            }


            }

        return  view;
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
      Glide.with(getContext()).load(R.mipmap.re).into(gif);
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
                  if(token.equals("")){
                      List<CaseBean.DataBean.ListBean> list =new ArrayList<>();
                      List<CaseBean.DataBean.ListBean> newlist =new ArrayList<>();
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
                      Log.e("caselistsizerl",list.size()+"[[");
                      for(int i=0;i<list.size();i++){
                          if(!list.get(i).getReadContent().equals("")){
                              newlist.add(list.get(i));
                          }

                      }
                      if(newlist.size()!=0){
                          adapter.refreshlist(newlist);
                          sr.finishRefresh();
                      }
                      else{
                          casePresenter.loadData();
                      }

                  }
                  else{
                      casePresenter.loadData();
                      //mData.clear();
                      if(studia==20){
                          new Thread(new Runnable() {
                              @Override
                              public void run() {
                                  loadListAd();
                              }
                          }).start();
                      }
                      adapter.refreshfeed(mData);
                  }

              }

          }
      });
    }


    @Override
    public void onClick(View view) {
        if(R.id.sj_ss==view.getId()){
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
    }


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
                database=dbUtils.getWritableDatabase();
                database.execSQL("delete from hot");
                database.execSQL("delete from usercase");
                String sql="insert into hot(bookid,name,image,intro,author) values('"+caseBean.getData().getHot().getBookId()+"','"+caseBean.getData().getHot().getName()+"','"+caseBean.getData().getHot().getImage()+"','"+caseBean.getData().getHot().getIntro()+"','"+caseBean.getData().getHot().getAuthor()+"')";
                if(!token.equals("")){
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
                else{
                    for(int i=0;i<caseBean.getData().getList().size();i++){
                        Log.e("notokencontent",caseBean.getData().getList().get(i).getReadContent()+"---");
                        database.execSQL("insert into usercase(user,name,image,author,readContent,readStatus,bookStatus,bookid,backlistPercent,lastTime,backlistId)" +
                                " values('visitor'," +
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

                database.execSQL(sql);
                jz.setVisibility(View.GONE);
                list=caseBean.getData().getList();
                adapter.refreshhot(caseBean.getData().getHot());
                adapter.refreshlist(caseBean.getData().getList());
                hotid=caseBean.getData().getHot().getBookId();
            }
        });

    }

    @Override
    public void onSuccess(UserBean userBean) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
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

    private void loadListAd() {
        //step4:创建feed广告请求类型参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                //.setCodeId("901121737")
                .setCodeId("926447877")
                .setSupportDeepLink(true)
                .setImageAcceptedSize(640, 320)
                .setAdCount(10) //请求广告数量为1到3条
                .build();
        //step5:请求广告，调用feed广告异步请求接口，加载到广告后，拿到广告素材自定义渲染
        mTTAdNative.loadFeedAd(adSlot, new TTAdNative.FeedAdListener() {
            @Override
            public void onError(int code, String message) {
             // Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFeedAdLoad(List<TTFeedAd> ads) {
                mData=ads;
                Log.e("data",mData.size()+"--"+mData.get(0).getTitle());
                adapter.refreshfeed(mData);
            }
        });
    }

//    /**
////     * 此方法目前仅适用于标示ViewPager中的Fragment是否真实可见
////     */
////    @Override
////    public void setUserVisibleHint(boolean isVisibleToUser) {
////        super.setUserVisibleHint(isVisibleToUser);
////
////        if (!isCreated) {
////            return;
////        }
////
////        if (isVisibleToUser) {
////           loadListAd();
////
////        }
////    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    loadListAd();
                }
            }).start();
            casePresenter=new ICasePresenterImpl(new ICaseView() {
                @Override
                public void onSuccess(CaseBean caseBean) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(sr!=null){
                                sr.finishRefresh();
                            }
                            adapter.refreshlist(caseBean.getData().getList());
                            adapter.refreshhot(caseBean.getData().getHot());
                        }
                    });
                }

                @Override
                public void onError(String error) {

                }
            }, getContext());
            //casePresenter.loadData();
        }
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
}
