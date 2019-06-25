package com.jdhd.qynovels.ui.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.ShopAdapter;
import com.jdhd.qynovels.ui.activity.SsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends Fragment implements TabLayout.OnTabSelectedListener,View.OnClickListener{

    private TabLayout tab;
    private ImageView search;
    private ViewPager home_vp;
    private List<Fragment> list=new ArrayList<>();
    public ShopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_shop, container, false);
        init(view);
        return view;
    }
    private void init(View view) {
        tab=view.findViewById(R.id.tab);
        tab.addTab(tab.newTab().setText("精选"));
        tab.addTab(tab.newTab().setText("男生"));
        tab.addTab(tab.newTab().setText("女生"));
        tab.addTab(tab.newTab().setText("图书"));
        tab.setSelectedTabIndicatorHeight(0);
        tab.getTabAt(0).select();
        tab.addOnTabSelectedListener(this);
        search=view.findViewById(R.id.search);
        search.setOnClickListener(this);
        home_vp=view.findViewById(R.id.home_vp);
        home_vp.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
        list.add(new JxFragment());
        list.add(new ManFragment());
        list.add(new WmanFragment());
        list.add(new BookFragment());
        ShopAdapter adapter=new ShopAdapter(getChildFragmentManager(),list);
        home_vp.setAdapter(adapter);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        home_vp.setCurrentItem(tab.getPosition());
        View view=LayoutInflater.from(getContext()).inflate(R.layout.item_tabtex,null);
        TextView textView = view.findViewById(R.id.tabtex);
        float selectedSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 19, getResources().getDisplayMetrics());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,selectedSize);
        textView.setTextColor(Color.parseColor("#E8564E"));
        textView.setText(tab.getText());
        tab.setCustomView(textView);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        tab.setCustomView(null);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(getContext(), SsActivity.class);
        startActivity(intent);
    }
}
