package com.jdhd.qynovels.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.CaseAdapter;
import com.jdhd.qynovels.module.BookBean;
import com.jdhd.qynovels.ui.activity.LsActivity;
import com.jdhd.qynovels.ui.activity.SsActivity;
import com.jdhd.qynovels.ui.activity.XqActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CaseFragment extends Fragment implements View.OnClickListener,CaseAdapter.onItemClick{
    private ImageView ss,ls,qd;
    private RecyclerView rv;
    private List<BookBean> list=new ArrayList<>();
    public CaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_case, container, false);
        init(view);
        return  view;
    }

    private void init(View view) {
      ss=view.findViewById(R.id.sj_ss);
      ls=view.findViewById(R.id.sj_ls);
      qd=view.findViewById(R.id.sj_qd);
      rv=view.findViewById(R.id.sj_rv);
      ss.setOnClickListener(this);
      ls.setOnClickListener(this);
      qd.setOnClickListener(this);
      LinearLayoutManager manager=new LinearLayoutManager(getContext());
      rv.setLayoutManager(manager);
      CaseAdapter adapter=new CaseAdapter(getContext(),getActivity());
      rv.setAdapter(adapter);
      adapter.setOnItemClick(this);
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
            Intent intent=new Intent(getActivity(), SsActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(int index) {
        Intent intent=new Intent(getActivity(),XqActivity.class);
        intent.putExtra("xq",1);
        startActivity(intent);
    }
}
