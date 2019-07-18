package com.jdhd.qynovels.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.CaseAdapter;
import com.jdhd.qynovels.module.bookcase.CaseBean;
import com.jdhd.qynovels.persenter.impl.bookcase.ICasePresenterImpl;
import com.jdhd.qynovels.ui.activity.LsActivity;
import com.jdhd.qynovels.ui.activity.QdActivity;
import com.jdhd.qynovels.ui.activity.SsActivity;
import com.jdhd.qynovels.ui.activity.XqActivity;
import com.jdhd.qynovels.utils.DbUtils;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.bookcase.ICaseView;
import com.jdhd.qynovels.widget.MRefreshHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CaseFragment extends Fragment implements View.OnClickListener, ICaseView,CaseAdapter.onItemClick {
    private ImageView ss,ls,qd;
    private RecyclerView rv;
    public static ICasePresenterImpl casePresenter;
    private CaseAdapter adapter;
    private List<CaseBean.DataBean.ListBean> list=new ArrayList<>();
    private RelativeLayout jz;
    private ImageView gif;
    private int hotid;
    private DbUtils dbUtils;
    private SQLiteDatabase database;
    private Cursor hotcursor,listcursor;
    private SmartRefreshLayout sr;
    private boolean hasNetWork;
    private String token;
    public CaseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_case, container, false);
        SharedPreferences preferences=getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
        token = preferences.getString("token", "");
        casePresenter=new ICasePresenterImpl(this,getContext());
        casePresenter.loadData();
        dbUtils=new DbUtils(getContext());
        init(view);
        hasNetWork = DeviceInfoUtils.hasNetWork(getContext());
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
               adapter.refreshhot(hotBean);
           }
            List<CaseBean.DataBean.ListBean> list =new ArrayList<>();
           listcursor=database.rawQuery("select * from usercase",new String[]{});
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
        return  view;
    }

    private void init(View view) {
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
      adapter=new CaseAdapter(getContext(),getActivity());
      rv.setAdapter(adapter);
      Glide.with(getContext()).load(R.mipmap.re).into(gif);
      sr.setOnRefreshListener(new OnRefreshListener() {
          @Override
          public void onRefresh(RefreshLayout refreshLayout) {
              hasNetWork = DeviceInfoUtils.hasNetWork(getContext());
              if(!hasNetWork){
                  Toast.makeText(getContext(),"网络连接不可用",Toast.LENGTH_SHORT).show();
                 sr.finishRefresh();
              }
              else{
                  casePresenter.loadData();
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
            Intent intent=new Intent(getActivity(), QdActivity.class);
            startActivity(intent);
        }
    }


    @Override
    public void onSuccess(CaseBean caseBean) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sr.finishRefresh();
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
    public void onError(String error) {
        sr.finishRefresh();
        Log.e("caseerror",error);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        casePresenter.destoryView();
    }

    @Override
    public void onClick(int index) {
        Intent intent=new Intent(getContext(),XqActivity.class);
        intent.putExtra("xq",1);
        intent.putExtra("id",list.get(index).getId());
        startActivity(intent);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(casePresenter!=null){
            casePresenter.loadData();
        }

    }
}
