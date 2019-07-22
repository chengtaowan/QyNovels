package com.jdhd.qynovels.ui.fragment;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.jdhd.qynovels.adapter.BookAdapter;
import com.jdhd.qynovels.module.bookshop.ShopBean;
import com.jdhd.qynovels.persenter.impl.bookshop.IJxPresenterImpl;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.bookshop.IJxView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManFragment extends Fragment implements IJxView {

    private RecyclerView rv;
    private  int lastOffset;//距离

    private  int lastPosition;//第几个item

    private SharedPreferences sharedPreferences;
    private int type;
    private IJxPresenterImpl jxPresenter;
    private BookAdapter adapter;
    private RelativeLayout jz;
    private ImageView gif;
    private SmartRefreshLayout sr;
    private boolean hasNetWork;

    public void setType(int type) {
        this.type = type;
    }

    public ManFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_book, container, false);
        jxPresenter=new IJxPresenterImpl(this,2,getContext());
        jxPresenter.loadData();
        hasNetWork = DeviceInfoUtils.hasNetWork(getContext());
        init(view);
        return view;
    }
    private void init(View view) {
        sr=view.findViewById(R.id.sr);
        jz=view.findViewById(R.id.jz);
        gif=view.findViewById(R.id.case_gif);
        Glide.with(getContext()).load(R.mipmap.re).into(gif);
        rv=view.findViewById(R.id.book_rv);
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        rv.setLayoutManager(manager);
        adapter=new BookAdapter(getContext(),1,0);
        rv.setAdapter(adapter);
//        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if(recyclerView.getLayoutManager() !=null) {
//                    //getPositionAndOffset();
//                }
//            }
//        });
        sr.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if(!hasNetWork){
                    Toast.makeText(getContext(),"网络连接不可用",Toast.LENGTH_SHORT).show();
                    sr.finishRefresh();
                }
                else{
                    jxPresenter.loadData();
                }
            }
        });
    }
    @Override
    public void onSuccess(ShopBean shopBean) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sr.finishRefresh();
              jz.setVisibility(View.GONE);
              adapter.refresh(shopBean.getData().getList());
            }
        });
    }

    @Override
    public void onError(String error) {
        Log.e("mamerror",error);
    }

    @Override
    public void onPause() {
        super.onPause();
        //etPositionAndOffset();
    }

    @Override
    public void onResume() {
        super.onResume();
        //scrollToPosition();
    }

    private void scrollToPosition() {

        sharedPreferences= getContext().getSharedPreferences("man", Activity.MODE_PRIVATE);

        lastOffset=sharedPreferences.getInt("lastOffset",0);

        lastPosition=sharedPreferences.getInt("lastPosition",0);

        if(rv.getLayoutManager() !=null&&lastPosition>=0) {

            ((LinearLayoutManager)rv.getLayoutManager()).scrollToPositionWithOffset(lastPosition,lastOffset);

        }

    }


    private  void getPositionAndOffset() {

        LinearLayoutManager layoutManager = (LinearLayoutManager)rv.getLayoutManager();

//获取可视的第一个view

        View topView = layoutManager.getChildAt(0);

        if(topView !=null) {

//获取与该view的顶部的偏移量

            lastOffset= topView.getTop();

//得到该View的数组位置

            lastPosition= layoutManager.getPosition(topView);

            sharedPreferences= getContext().getSharedPreferences("man", Activity.MODE_PRIVATE);

            SharedPreferences.Editor editor =sharedPreferences.edit();

            editor.putInt("lastOffset",lastOffset);

            editor.putInt("lastPosition",lastPosition);

            editor.commit();

        }

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        jxPresenter.destoryView();
    }
}
