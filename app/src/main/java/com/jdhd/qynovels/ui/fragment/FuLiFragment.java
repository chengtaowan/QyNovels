package com.jdhd.qynovels.ui.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jdhd.qynovels.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FuLiFragment extends Fragment {


    public FuLiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.item_ls, container, false);
    }

}
