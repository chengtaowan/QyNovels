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
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.CaseAdapter;
import com.jdhd.qynovels.module.bookcase.CaseBean;
import com.jdhd.qynovels.persenter.impl.bookcase.ICasePresenterImpl;
import com.jdhd.qynovels.ui.activity.LsActivity;
import com.jdhd.qynovels.ui.activity.QdActivity;
import com.jdhd.qynovels.ui.activity.SsActivity;
import com.jdhd.qynovels.ui.activity.XqActivity;
import com.jdhd.qynovels.view.bookcase.ICaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CaseFragment extends Fragment implements View.OnClickListener, ICaseView,CaseAdapter.onItemClick {
    private ImageView ss,ls,qd;
    private RecyclerView rv;
    private ICasePresenterImpl casePresenter;
    private CaseAdapter adapter;
    private List<CaseBean.DataBean.ListBean> list=new ArrayList<>();
    private RelativeLayout jz;
    private ImageView gif;
    private int hotid;
    public CaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_case, container, false);
        casePresenter=new ICasePresenterImpl(this,getContext());
        casePresenter.loadData();
        init(view);

        return  view;
    }

    private void init(View view) {
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
                jz.setVisibility(View.GONE);
                list=caseBean.getData().getList();
                adapter.refreshdata(caseBean.getData());
                hotid=caseBean.getData().getHot().getBookId();
            }
        });

    }

    @Override
    public void onError(String error) {
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
}
