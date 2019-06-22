package com.jdhd.qynovels.ui.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.FlAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FlFragment extends Fragment {

    private RecyclerView rv;
    public FlFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fl, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        rv=view.findViewById(R.id.rl_rv);
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(manager);
        FlAdapter adapter=new FlAdapter(getContext());
        rv.setAdapter(adapter);
    }

}
