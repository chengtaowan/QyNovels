package com.jdhd.qynovels.ui.fragment;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.Ss_LxAdapter;
import com.jdhd.qynovels.ui.activity.XqActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Ss_LxFragment extends Fragment implements Ss_LxAdapter.onItemClick{

    private RecyclerView rv;
    private List<String> list=new ArrayList<>();
    public Ss_LxFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_ss__lx, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        for(int i=0;i<10;i++){
            list.add("万历十五年"+i);
        }
        rv=view.findViewById(R.id.ss_rs_lx_rv);
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        rv.setLayoutManager(manager);
        DividerItemDecoration did=new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(getContext(),R.drawable.shape_decor);
        did.setDrawable(drawable);
        rv.addItemDecoration(did);
        Ss_LxAdapter adapter=new Ss_LxAdapter(getContext(),list);
        rv.setAdapter(adapter);
        adapter.setOnItemClick(this);
    }

    @Override
    public void onLxClick(int index) {
        Intent intent=new Intent(getContext(), XqActivity.class);
        startActivity(intent);
    }
}
