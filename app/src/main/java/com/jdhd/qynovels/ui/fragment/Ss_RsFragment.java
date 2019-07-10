package com.jdhd.qynovels.ui.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.Ss_RsAdapter;
import com.jdhd.qynovels.module.bookcase.HotSearchBean;
import com.jdhd.qynovels.persenter.impl.bookcase.IHotSearchPresenterImpl;
import com.jdhd.qynovels.ui.activity.XqActivity;
import com.jdhd.qynovels.view.bookcase.IHotSearchView;
import com.tencent.mm.opensdk.utils.Log;

/**
 * A simple {@link Fragment} subclass.
 */
public class Ss_RsFragment extends Fragment implements Ss_RsAdapter.onItemClick, IHotSearchView {

    private RecyclerView rv;
    private IHotSearchPresenterImpl hotSearchPresenter;
    private Ss_RsAdapter adapter;
    public Ss_RsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_ss__rs, container, false);
        hotSearchPresenter=new IHotSearchPresenterImpl(this);
        hotSearchPresenter.loadData();
        init(view);
        return view;
    }

    private void init(View view) {
        rv=view.findViewById(R.id.ss_rs_rv);
        GridLayoutManager manager=new GridLayoutManager(getContext(), 2);
        rv.setLayoutManager(manager);
        adapter=new Ss_RsAdapter(getContext());
        rv.setAdapter(adapter);
        adapter.setOnItemClick(this);
    }

    @Override
    public void onRsClick(int index) {
        Intent intent=new Intent(getContext(), XqActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSuccess(HotSearchBean hotSearchBean) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.refresh(hotSearchBean.getData().getList());
            }
        });
    }

    @Override
    public void onError(String error) {
        Log.e("ssrserror",error);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hotSearchPresenter.destoryView();
    }
}
