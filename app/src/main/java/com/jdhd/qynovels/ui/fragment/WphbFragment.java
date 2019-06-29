package com.jdhd.qynovels.ui.fragment;


import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.Fl_Title_Adapter;
import com.jdhd.qynovels.module.Fl_Title_Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WphbFragment extends Fragment implements Fl_Title_Adapter.onTitleClick{

    private RecyclerView rv;
    private List<Fl_Title_Bean> list=new ArrayList<>();
    private List<Fragment> fragmentList=new ArrayList<>();
    public WphbFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view= inflater.inflate(R.layout.fragment_wphb, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        rv=view.findViewById(R.id.phbw_rv);
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(manager);
        list.add(new Fl_Title_Bean("大热搜"));
        list.add(new Fl_Title_Bean("完结榜"));
        list.add(new Fl_Title_Bean("黑马榜"));
        list.add(new Fl_Title_Bean("热搜榜"));
        Fl_Title_Adapter adapter=new Fl_Title_Adapter(getContext(),list);
        rv.setAdapter(adapter);
        adapter.setOnTitleClick(this);
        for(int i=0;i<list.size();i++){
            fragmentList.add(new PhbFragment());
        }
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.phbw_ll,fragmentList.get(0));
        transaction.commit();
    }
    @Override
    public void onclick(int index) {
        FragmentManager manager=getActivity().getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.phbw_ll,fragmentList.get(index));
        transaction.commit();

    }

}
