package com.jdhd.qynovels.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.WjjpAdapter;
import com.jdhd.qynovels.module.bookshop.ShopBean;
import com.jdhd.qynovels.persenter.impl.bookshop.IJxPresenterImpl;
import com.jdhd.qynovels.ui.activity.MorePhbActivity;
import com.jdhd.qynovels.ui.activity.WjjpActivity;
import com.jdhd.qynovels.view.bookshop.IJxView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManWjFragment extends Fragment implements IJxView,WjjpAdapter.onItemClick {

    private RecyclerView rv;
    private IJxPresenterImpl jxPresenter;
    private WjjpAdapter adapter;
    public ManWjFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_man_wj, container, false);
        jxPresenter=new IJxPresenterImpl(this,4,getContext());
        jxPresenter.loadData();
        init(view);
        return view;
    }

    private void init(View view) {
        rv=view.findViewById(R.id.wj_rv);
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        rv.setLayoutManager(manager);
        adapter=new WjjpAdapter(getContext());
        adapter.setOnItemClick(this);
        rv.setAdapter(adapter);
    }

    @Override
    public void onSuccess(ShopBean shopBean) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
               adapter.refresh(shopBean.getData().getList());
            }
        });
    }

    @Override
    public void onError(String error) {
        Log.e("manwjerror",error);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(jxPresenter!=null){
            jxPresenter.destoryView();
        }
    }

    @Override
    public void onMoreClick(int index) {
        Intent intent=new Intent(getContext(), MorePhbActivity.class);
        intent.putExtra("more",2);
        startActivity(intent);
    }
}
