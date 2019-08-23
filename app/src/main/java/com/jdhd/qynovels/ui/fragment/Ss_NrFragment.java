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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.Ss_NrAdapter;
import com.jdhd.qynovels.adapter.Ss_WsjAdapter;
import com.jdhd.qynovels.module.bookcase.SearchContentBean;
import com.jdhd.qynovels.persenter.impl.bookcase.ISearchContentPresenterImpl;
import com.jdhd.qynovels.ui.activity.XqActivity;
import com.jdhd.qynovels.view.bookshop.ISearchContentView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Ss_NrFragment extends Fragment implements Ss_NrAdapter.onItemClick, ISearchContentView ,View.OnClickListener{

    private RecyclerView rv,tjrv;
    private TextView hyh;
    private String content;
    private RelativeLayout wsj;
    private ISearchContentPresenterImpl searchContentPresenter;
    private Ss_NrAdapter adapter;
    private List<SearchContentBean.DataBean.ListBean> listBean=new ArrayList<>();
    private Ss_WsjAdapter wsjAdapter;
    private SearchContentBean searchContent;

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
        hyh=view.findViewById(R.id.hyh);
        hyh.setOnClickListener(this);
        wsj=view.findViewById(R.id.tx_wsj);
        rv=view.findViewById(R.id.ss_nr_rv);
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        rv.setLayoutManager(manager);
        adapter=new Ss_NrAdapter(getContext());
        rv.setAdapter(adapter);
        adapter.setOnItemClick(this);

        wsjAdapter=new Ss_WsjAdapter(getContext());
        tjrv=view.findViewById(R.id.rv);
        LinearLayoutManager manager1=new LinearLayoutManager(getContext());
        tjrv.setLayoutManager(manager1);
        tjrv.setAdapter(wsjAdapter);

    }

    @Override
    public void onNrClick(int index) {

    }

    @Override
    public void onSuccess(SearchContentBean searchContentBean) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                searchContent=searchContentBean;
               // listBean=searchContentBean.getData().getList();
                if(searchContentBean.getData().getList()==null){
                    wsj.setVisibility(View.VISIBLE);
                    int[] ints = randomCommon(0, searchContentBean.getData().getRecommend().size() - 1, 3);
                    for(int i=0;i<ints.length;i++){
                        listBean.add(searchContentBean.getData().getRecommend().get(ints[i]));
                    }
                    Log.e("recommendsize",searchContentBean.getData().getRecommend().size()+"--");

                    wsjAdapter.refresh(listBean);
                }
                else if(searchContentBean.getData().getList()!=null){
                    Log.e("recommendsize",searchContentBean.getData().getList().toString()+"--");
                    adapter.refresh(searchContentBean.getData().getList());
                }

            }
        });
    }
    @Override
    public void onClick(View view) {
        List<SearchContentBean.DataBean.ListBean> list=new ArrayList<>();
        int[] ints = randomCommon(0, searchContent.getData().getRecommend().size() - 1, 3);
        for(int i=0;i<ints.length;i++){
            list.add(searchContent.getData().getRecommend().get(ints[i]));
        }
        Log.e("recommendsize",searchContent.getData().getRecommend().size()+"--");

        wsjAdapter.refresh(list);
    }

    @Override
    public void onError(String error) {
        Log.e("ssnrerror",error);
    }

    /**
     * 随机指定范围内N个不重复的数
     * 最简单最基本的方法
     * @param min 指定范围最小值
     * @param max 指定范围最大值
     * @param n 随机数个数
     */
    public static int[] randomCommon(int min, int max, int n){
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while(count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if(num == result[j]){
                    flag = false;
                    break;
                }
            }
            if(flag){
                result[count] = num;
                count++;
            }
        }
        return result;
    }


}
