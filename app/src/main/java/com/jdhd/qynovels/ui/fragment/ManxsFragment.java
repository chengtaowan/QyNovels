package com.jdhd.qynovels.ui.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.JrgxAdapter;
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
public class ManxsFragment extends Fragment implements IJxView {

    private RecyclerView rv;
    private IJxPresenterImpl jxPresenter;
    private JrgxAdapter adapter;
    private RelativeLayout jz;
    private ImageView gif;
    private SmartRefreshLayout sr;
    private boolean hasNetWork;
    public ManxsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_wmanxs, container, false);
        jxPresenter=new IJxPresenterImpl(this,6,getContext());
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
        rv=view.findViewById(R.id.xs_rv);
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        rv.setLayoutManager(manager);
        adapter=new JrgxAdapter(getContext());
        rv.setAdapter(adapter);
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

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(jxPresenter!=null){
            jxPresenter.destoryView();
        }
    }

}
