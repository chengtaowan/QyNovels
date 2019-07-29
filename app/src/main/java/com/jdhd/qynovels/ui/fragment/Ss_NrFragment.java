package com.jdhd.qynovels.ui.fragment;


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
import com.jdhd.qynovels.adapter.Ss_NrAdapter;
import com.jdhd.qynovels.module.bookcase.SearchContentBean;
import com.jdhd.qynovels.persenter.impl.bookcase.ISearchContentPresenterImpl;
import com.jdhd.qynovels.ui.activity.XqActivity;
import com.jdhd.qynovels.view.bookshop.ISearchContentView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Ss_NrFragment extends Fragment implements Ss_NrAdapter.onItemClick, ISearchContentView {

    private RecyclerView rv;
    private String content;
    private ISearchContentPresenterImpl searchContentPresenter;
    private Ss_NrAdapter adapter;
    private List<SearchContentBean.DataBean.ListBean> listBean=new ArrayList<>();

    public void setContent(String content) {
        this.content = content;
    }

    public Ss_NrFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_ss__nr, container, false);
        searchContentPresenter=new ISearchContentPresenterImpl(this,getContext());
        searchContentPresenter.setContent(content);
        searchContentPresenter.loadData();
        init(view);
        return view;
    }

    private void init(View view) {
        rv=view.findViewById(R.id.ss_nr_rv);
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        rv.setLayoutManager(manager);
        adapter=new Ss_NrAdapter(getContext());
        rv.setAdapter(adapter);
        adapter.setOnItemClick(this);
    }

    @Override
    public void onNrClick(int index) {

    }

    @Override
    public void onSuccess(SearchContentBean searchContentBean) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listBean=searchContentBean.getData().getList();
               adapter.refresh(searchContentBean.getData().getList());
            }
        });
    }

    @Override
    public void onError(String error) {
        Log.e("ssnrerror",error);
    }
}
