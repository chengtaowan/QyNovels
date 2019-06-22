package com.jdhd.qynovels.ui.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.jdhd.qynovels.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CaseFragment extends Fragment implements TabLayout.OnTabSelectedListener{

    private TabLayout tab;
    private ImageView search;
    private ViewPager home_vp;
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
        tab=view.findViewById(R.id.tab);
        tab.addTab(tab.newTab().setText("精选"));
        tab.addTab(tab.newTab().setText("男生"));
        tab.addTab(tab.newTab().setText("女生"));
        tab.addTab(tab.newTab().setText("图书"));
        tab.setSelectedTabIndicatorHeight(0);
        tab.setOnTabSelectedListener(this);
        search=view.findViewById(R.id.search);
        home_vp=view.findViewById(R.id.home_vp);
        home_vp.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
       home_vp.setCurrentItem(tab.getPosition());
       
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
