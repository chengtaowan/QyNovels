package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.ShopAdapter;
import com.jdhd.qynovels.ui.fragment.MphbFragment;
import com.jdhd.qynovels.ui.fragment.WphbFragment;

import java.util.ArrayList;
import java.util.List;

public class PhbActivity extends AppCompatActivity implements View.OnClickListener,TabLayout.OnTabSelectedListener {
    private ImageView back;
    private TabLayout tab;
    private List<Fragment> fragmentList=new ArrayList<>();
    private ViewPager vp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phb);
        init();
    }

    private void init() {
        back=findViewById(R.id.phb_back);
        back.setOnClickListener(this);
        tab=findViewById(R.id.phb_tab);
        tab.addTab(tab.newTab().setText("男生排行榜"));
        tab.addTab(tab.newTab().setText("女生排行榜"));
        tab.setSelectedTabIndicatorHeight(0);
        tab.getTabAt(0).select();
        tab.addOnTabSelectedListener(this);
        vp=findViewById(R.id.phb_vp);
        vp.setOffscreenPageLimit(0);
        fragmentList.add(new MphbFragment());
        fragmentList.add(new WphbFragment());
        ShopAdapter adapter=new ShopAdapter(getSupportFragmentManager(),fragmentList);
        vp.setAdapter(adapter);
        vp.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));

    }

    @Override
    public void onClick(View view) {
        finish();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        vp.setCurrentItem(tab.getPosition());
        View view= LayoutInflater.from(this).inflate(R.layout.item_tabtex,null);
        TextView textView = view.findViewById(R.id.tabtex);
        float selectedSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 18, getResources().getDisplayMetrics());
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


}
