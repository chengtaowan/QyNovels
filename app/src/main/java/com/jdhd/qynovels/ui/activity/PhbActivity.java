package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.ShopAdapter;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.bookshop.RankBean;
import com.jdhd.qynovels.persenter.impl.bookshop.IRankPresenterImpl;
import com.jdhd.qynovels.ui.fragment.MphbFragment;
import com.jdhd.qynovels.ui.fragment.WphbFragment;
import com.jdhd.qynovels.utils.AndroidBug54971Workaround;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.jdhd.qynovels.view.bookshop.IRankView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class PhbActivity extends AppCompatActivity implements View.OnClickListener,TabLayout.OnTabSelectedListener, IRankView {
    private ImageView back;
    private TabLayout tab;
    private List<Fragment> fragmentList=new ArrayList<>();
    private IRankPresenterImpl rankPresenter;
    private ViewPager vp;
    private RelativeLayout jz;
    private ImageView gif;
    private MphbFragment mphbFragment=new MphbFragment();
    private WphbFragment wphbFragment=new WphbFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phb);
        AndroidBug54971Workaround.assistActivity(findViewById(android.R.id.content),this);


        MyApp.addActivity(this);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        rankPresenter=new IRankPresenterImpl(this,this);
        rankPresenter.loadData();
        init();
    }

    private void init() {
        jz=findViewById(R.id.jz);
        gif=findViewById(R.id.case_gif);
        Glide.with(this).load(R.mipmap.re).into(gif);
        back=findViewById(R.id.phb_back);
        back.setOnClickListener(this);
        tab=findViewById(R.id.phb_tab);
        tab.setSelectedTabIndicatorHeight(0);
        tab.addOnTabSelectedListener(this);
        vp=findViewById(R.id.phb_vp);


    }
    @Override
    public void onSuccess(RankBean rankBean) {
       runOnUiThread(new Runnable() {
           @Override
           public void run() {
               jz.setVisibility(View.GONE);
             for(int i=0;i<rankBean.getData().getList().size();i++){
                tab.addTab(tab.newTab().setText(rankBean.getData().getList().get(i).getName()));
             }
               tab.getTabAt(0).select();
               Bundle bundle = new Bundle();
               bundle.putParcelableArrayList("data", (ArrayList<RankBean.DataBean.ListBean.ChildBean>) rankBean.getData().getList().get(0).getChild());
               mphbFragment.setArguments(bundle);
               Bundle bundlew = new Bundle();
               bundlew.putParcelableArrayList("data", (ArrayList<RankBean.DataBean.ListBean.ChildBean>) rankBean.getData().getList().get(1).getChild());
               wphbFragment.setArguments(bundlew);
               vp.setOffscreenPageLimit(0);
               fragmentList.add(mphbFragment);
               fragmentList.add(wphbFragment);
               ShopAdapter adapter=new ShopAdapter(getSupportFragmentManager());
               adapter.refresh(fragmentList);
               vp.setAdapter(adapter);
               vp.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
           }
       });
    }

    @Override
    public void onError(String error) {
        Log.e("phberror",error);
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(PhbActivity.this,MainActivity.class);
        intent.putExtra("page", 1);
        startActivity(intent);
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

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this); // 不能遗漏
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
        MobclickAgent.onPause(this); // 不能遗漏
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(PhbActivity.this,MainActivity.class);
        intent.putExtra("fragment_flag", 2);
        startActivity(intent);
    }


}
