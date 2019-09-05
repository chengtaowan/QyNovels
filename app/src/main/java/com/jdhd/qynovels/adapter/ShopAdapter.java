package com.jdhd.qynovels.adapter;

import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.jdhd.qynovels.ui.fragment.JxFragment;
import com.jdhd.qynovels.ui.fragment.ManFragment;
import com.jdhd.qynovels.ui.fragment.WmanFragment;

import java.util.ArrayList;
import java.util.List;

public class ShopAdapter extends FragmentPagerAdapter {
    private List<Fragment> list=new ArrayList<>();

    public void refresh(List<Fragment> list){
        this.list.clear();
        this.list=list;
        notifyDataSetChanged();
    }

    public ShopAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

}
