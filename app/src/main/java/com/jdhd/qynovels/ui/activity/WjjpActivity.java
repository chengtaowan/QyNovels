package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
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
import com.jdhd.qynovels.adapter.ShopAdapter;
import com.jdhd.qynovels.adapter.WjjpAdapter;
import com.jdhd.qynovels.module.bookshop.ModuleBean;
import com.jdhd.qynovels.module.bookshop.ShopBean;
import com.jdhd.qynovels.persenter.impl.bookshop.IJxPresenterImpl;
import com.jdhd.qynovels.persenter.impl.bookshop.IModulePresenterImpl;
import com.jdhd.qynovels.ui.fragment.ManWjFragment;
import com.jdhd.qynovels.ui.fragment.WmanWjFragment;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.jdhd.qynovels.view.bookshop.IJxView;
import com.jdhd.qynovels.view.bookshop.IModuleView;

import java.util.ArrayList;
import java.util.List;

public class WjjpActivity extends AppCompatActivity implements View.OnClickListener, IModuleView,TabLayout.OnTabSelectedListener {
    private ImageView back;
    private IModulePresenterImpl modulePresenter;
    private TabLayout tab;
    private List<Fragment> fragmentList=new ArrayList<>();
    private ManWjFragment manWjFragment=new ManWjFragment();
    private WmanWjFragment wmanWjFragment=new WmanWjFragment();
    private ViewPager vp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wjjp);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        modulePresenter=new IModulePresenterImpl(this,this,20);
        modulePresenter.loadData();
        init();
    }

    private void init() {
        back=findViewById(R.id.wj_back);
        back.setOnClickListener(this);
        vp=findViewById(R.id.phb_vp);
        tab=findViewById(R.id.phb_tab);
        tab.setSelectedTabIndicatorHeight(0);
        tab.addOnTabSelectedListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(WjjpActivity.this,MainActivity.class);
        intent.putExtra("page", 1);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(WjjpActivity.this,MainActivity.class);
        intent.putExtra("page", 1);
        startActivity(intent);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(modulePresenter!=null){
            modulePresenter.destoryView();
        }
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
               fragmentList.add(manWjFragment);
               fragmentList.add(wmanWjFragment);
               ShopAdapter adapter=new ShopAdapter(getSupportFragmentManager(),fragmentList);
               vp.setAdapter(adapter);
               vp.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
           }
       });
    }

    @Override
    public void onError(String error) {
       Log.e("wj",error);
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
