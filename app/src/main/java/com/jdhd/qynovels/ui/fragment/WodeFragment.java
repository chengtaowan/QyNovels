package com.jdhd.qynovels.ui.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.ui.activity.GrzlActivity;
import com.jdhd.qynovels.ui.activity.LoginActivity;
import com.jdhd.qynovels.ui.activity.LsActivity;
import com.jdhd.qynovels.ui.activity.SzActivity;
import com.jdhd.qynovels.ui.activity.TxActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class WodeFragment extends Fragment implements View.OnClickListener{

    private TextView wo_dl;
    private RelativeLayout wd_ls,wd_tx,wd_fk,wd_sz;
    private ImageView wd_toux;
    public WodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_wode, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        wo_dl=view.findViewById(R.id.wd_dl);
        wd_ls=view.findViewById(R.id.wd_ls);
        wd_tx=view.findViewById(R.id.wd_tx);
        wd_fk=view.findViewById(R.id.wd_fk);
        wd_sz =view.findViewById(R.id.wd_sz);
        wd_toux=view.findViewById(R.id.wd_toux);
        wo_dl.setOnClickListener(this);
        wd_ls.setOnClickListener(this);
        wd_tx.setOnClickListener(this);
        wd_fk.setOnClickListener(this);
        wd_sz.setOnClickListener(this);
        wd_toux.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(R.id.wd_dl==view.getId()){
            Intent intent=new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        }
        else if(R.id.wd_ls==view.getId()){
            Intent intent=new Intent(getContext(), LsActivity.class);
            intent.putExtra("ls",4);
            startActivity(intent);
        }
        else if(R.id.wd_tx==view.getId()){
            Intent intent=new Intent(getContext(), TxActivity.class);
            startActivity(intent);
        }
        else if(R.id.wd_fk==view.getId()){
            Intent intent=new Intent(getContext(), SzActivity.class);
            startActivity(intent);
        }
        else if(R.id.wd_sz==view.getId()){
            Intent intent=new Intent(getContext(), SzActivity.class);
            startActivity(intent);
        }
        else if(R.id.wd_toux==view.getId()){
            Intent intent=new Intent(getContext(), GrzlActivity.class);
            startActivity(intent);
        }
    }
}
