package com.jdhd.qynovels.ui.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.BookAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class WmanFragment extends Fragment {
    private RecyclerView rv;

    public WmanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_book, container, false);
        init(view);
        return view;
    }
    private void init(View view) {
        rv=view.findViewById(R.id.book_rv);
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        rv.setLayoutManager(manager);
        BookAdapter adapter=new BookAdapter(getContext(),0,2);
        rv.setAdapter(adapter);
    }

}