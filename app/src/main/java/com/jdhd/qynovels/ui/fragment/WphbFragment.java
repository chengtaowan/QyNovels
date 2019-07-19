package com.jdhd.qynovels.ui.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.Fl_Title_Adapter;
import com.jdhd.qynovels.adapter.PhbAdapter;
import com.jdhd.qynovels.module.bookshop.RankBean;
import com.jdhd.qynovels.module.bookshop.RankContentBean;
import com.jdhd.qynovels.persenter.impl.bookshop.IRankContentPresenterImpl;
import com.jdhd.qynovels.ui.activity.XqActivity;
import com.jdhd.qynovels.view.bookshop.IRankContentView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WphbFragment extends Fragment implements Fl_Title_Adapter.onTitleClick,PhbAdapter.onItemClick,IRankContentView {

    private RecyclerView rv,datarv;
    private TextView des,time;
    private IRankContentPresenterImpl rankContentPresenter;
    private PhbAdapter adapter;
    private RelativeLayout jz;
    private ImageView gif;
    private List<RankBean.DataBean.ListBean.ChildBean> list=new ArrayList<>();
    public WphbFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view= inflater.inflate(R.layout.fragment_wphb, container, false);
        Bundle bundle=this.getArguments();
        if(bundle!=null){
            list = bundle.getParcelableArrayList("data");
        }
        rankContentPresenter=new IRankContentPresenterImpl(this,getContext());
        rankContentPresenter.setId(list.get(0).getId());
        rankContentPresenter.loadData();
        init(view);
        return view;
    }

    private void init(View view) {
        jz=view.findViewById(R.id.jz);
        gif=view.findViewById(R.id.case_gif);
        Glide.with(getContext()).load(R.mipmap.re).into(gif);
        des=view.findViewById(R.id.phb_des);
        time=view.findViewById(R.id.phb_time);
        des.setText(list.get(0).getName()+"");
        time.setText(MphbFragment.changeData(list.get(0).getUpdateTime()+"")+"日更新");
        datarv=view.findViewById(R.id.phb_rv);
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        datarv.setLayoutManager(manager);
        adapter=new PhbAdapter(getContext());
        datarv.setAdapter(adapter);
        adapter.setOnItemClick(this);
        rv=view.findViewById(R.id.phbw_rv);
        LinearLayoutManager manager1=new LinearLayoutManager(getContext());
        manager1.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(manager1);
        Fl_Title_Adapter adapter1=new Fl_Title_Adapter(getContext());
        adapter1.refresh(list);
        rv.setAdapter(adapter1);
        adapter1.setOnTitleClick(this);
    }
    @Override
    public void onclick(int index) {
        des.setText(list.get(index).getName());
        time.setText(MphbFragment.changeData(list.get(index).getUpdateTime()+"")+"日更新");
        rankContentPresenter.setId(list.get(index).getId());
        rankContentPresenter.loadData();
    }

    @Override
    public void onClick(int index) {
        Intent intent=new Intent(getContext(), XqActivity.class);
        intent.putExtra("fragment_flag", 2);
        intent.putExtra("xq",3);
        startActivity(intent);
    }

    @Override
    public void onSuccess(RankContentBean rankContentBean) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                jz.setVisibility(View.GONE);
                adapter.refresh(rankContentBean.getData().getList());
            }
        });
    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        rankContentPresenter.destoryView();
    }
}
