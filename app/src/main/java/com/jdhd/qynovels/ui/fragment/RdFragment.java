package com.jdhd.qynovels.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.MoreAdapter;
import com.jdhd.qynovels.ui.activity.XqActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class RdFragment extends Fragment implements MoreAdapter.onItemClick{

   private RecyclerView rv;


    public RdFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_rd, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        rv=view.findViewById(R.id.rd_rv);
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        rv.setLayoutManager(manager);
        MoreAdapter adapter=new MoreAdapter(getContext(),0);
        rv.setAdapter(adapter);
        adapter.setOnItemClick(this);
    }

    @Override
    public void onClick(int index) {

        Intent intent=new Intent(getContext(), XqActivity.class);
        intent.putExtra("xq",6);
        startActivity(intent);
    }
}
