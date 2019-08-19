package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.MoreAdapter;
import com.jdhd.qynovels.adapter.ShopAdapter;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.bookshop.ModuleBean;
import com.jdhd.qynovels.persenter.impl.bookshop.IModulePresenterImpl;
import com.jdhd.qynovels.ui.fragment.ManxsFragment;
import com.jdhd.qynovels.ui.fragment.WmanxsFragment;
import com.jdhd.qynovels.utils.AndroidBug54971Workaround;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.jdhd.qynovels.view.bookshop.IModuleView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class XssdActivity extends AppCompatActivity implements View.OnClickListener ,TabLayout.OnTabSelectedListener, IModuleView {
    private ImageView back;
    private TabLayout tab;
    private ViewPager vp;
    private List<Fragment> fragmentList=new ArrayList<>();
    private ManxsFragment manxsFragment=new ManxsFragment();
    private WmanxsFragment wmanxsFragment=new WmanxsFragment();
    private IModulePresenterImpl modulePresenter;
    private SharedPreferences sharedPreferences;
    private String sex;
    private int psex=20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xssd);
        AndroidBug54971Workaround.assistActivity(findViewById(android.R.id.content),this);

        MyApp.addActivity(this);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        sharedPreferences=getSharedPreferences("sex", Context.MODE_PRIVATE);
        sex = sharedPreferences.getString("sex", "");
        if(sex.equals("女")){
            psex=30;
        }
        modulePresenter=new IModulePresenterImpl(this,this,30);
        modulePresenter.loadData();
        init();
    }

    private void init() {
        back=findViewById(R.id.xs_back);
        back.setOnClickListener(this);
        tab=findViewById(R.id.xs_tab);
        vp=findViewById(R.id.xs_vp);
        tab.setSelectedTabIndicatorHeight(0);
        tab.addOnTabSelectedListener(this);
    }



    @Override
    public void onClick(View view) {
        Intent intent=new Intent(XssdActivity.this, MainActivity.class);
        intent.putExtra("page",1);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(XssdActivity.this, MainActivity.class);
        intent.putExtra("page",1);
        startActivity(intent);
    }

//    @Override
//    public void onClick(int index) {
//        Intent intent=new Intent(XssdActivity.this, XqActivity.class);
//        intent.putExtra("xq",5);
//        startActivity(intent);
//    }

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
    public void onSuccess(ModuleBean moduleBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<moduleBean.getData().getList().size();i++){
                    tab.addTab(tab.newTab().setText(moduleBean.getData().getList().get(i).getModuleName()));
                }
                tab.getTabAt(0).select();
                vp.setOffscreenPageLimit(0);
                fragmentList.add(wmanxsFragment);
                fragmentList.add(manxsFragment);
                ShopAdapter adapter=new ShopAdapter(getSupportFragmentManager());
                adapter.refresh(fragmentList);
                vp.setAdapter(adapter);
                vp.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
            }
        });
    }

    @Override
    public void onError(String error) {
        Log.e("xs",error);
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
        MobclickAgent.onPause(this); // 不能遗漏
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this); // 不能遗漏
    }
}
