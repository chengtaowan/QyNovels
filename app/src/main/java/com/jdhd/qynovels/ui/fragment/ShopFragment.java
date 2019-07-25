package com.jdhd.qynovels.ui.fragment;


import android.content.Context;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.ShopAdapter;
import com.jdhd.qynovels.module.bookshop.ModuleBean;
import com.jdhd.qynovels.persenter.impl.bookshop.IModulePresenterImpl;
import com.jdhd.qynovels.ui.activity.SsActivity;
import com.jdhd.qynovels.view.bookshop.IModuleView;
import com.tencent.mm.opensdk.utils.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends Fragment implements TabLayout.OnTabSelectedListener,View.OnClickListener, IModuleView {

    private ImageView search;
    private ViewPager home_vp;
    private List<Fragment> list=new ArrayList<>();
    private Context context;
    private int type;
    private RelativeLayout jz;
    private ImageView gif;
    private IModulePresenterImpl modulePresenter;
    private JxFragment jxFragment=new JxFragment();
    private ManFragment manFragment=new ManFragment();
    private WmanFragment wmanFragment=new WmanFragment();

    private TabLayout tab;
    private List<RadioButton> rblist=new ArrayList<>();
    public ShopFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_shop, container, false);
        modulePresenter=new IModulePresenterImpl(this,getContext(),10);
        modulePresenter.loadData();
        init(view);
        Intent intent=getActivity().getIntent();
        type=intent.getIntExtra("lx",1);
        return view;
    }
    private void init(View view) {
        if(jxFragment!=null){
            jxFragment=new JxFragment();
        }
        if(manFragment!=null){
            manFragment=new ManFragment();

        }
        if(wmanFragment!=null){
            wmanFragment=new WmanFragment();
        }
        jz=view.findViewById(R.id.jz);
        gif=view.findViewById(R.id.case_gif);
        search=view.findViewById(R.id.search);
        search.setOnClickListener(this);
        tab=view.findViewById(R.id.tab);
        tab.setSelectedTabIndicatorHeight(0);
        tab.addOnTabSelectedListener(this);
        home_vp=view.findViewById(R.id.home_vp);
        list.clear();
        list.add(jxFragment);
        list.add(manFragment);
        list.add(wmanFragment);
        ShopAdapter adapter=new ShopAdapter(getChildFragmentManager());
        adapter.refresh(list);
        home_vp.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        changeFragment();

    }
    private void changeFragment(){
        if(type==2){
           home_vp.setCurrentItem(1);
        }
        else if(type==3){
            home_vp.setCurrentItem(2);
        }
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
        intent.putExtra("ss",2);
        startActivity(intent);
    }


    @Override
    public void onSuccess(ModuleBean moduleBean) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                jxFragment.setType(moduleBean.getData().getList().get(0).getId());
                manFragment.setType(moduleBean.getData().getList().get(1).getId());
                wmanFragment.setType(moduleBean.getData().getList().get(2).getId());
                jz.setVisibility(View.GONE);
                for(int i=0;i<moduleBean.getData().getList().size();i++){
                    tab.addTab(tab.newTab().setText(moduleBean.getData().getList().get(i).getModuleName()));
                }
                tab.getTabAt(0).select();
                home_vp.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
            }
        });
    }

    @Override
    public void onError(String error) {
        Log.e("moduleerror",error);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        modulePresenter.destoryView();
    }
}
