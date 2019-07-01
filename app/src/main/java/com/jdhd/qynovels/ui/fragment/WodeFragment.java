package com.jdhd.qynovels.ui.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.ui.activity.CjwtActivity;
import com.jdhd.qynovels.ui.activity.GrzlActivity;
import com.jdhd.qynovels.ui.activity.JbActivity;
import com.jdhd.qynovels.ui.activity.LoginActivity;
import com.jdhd.qynovels.ui.activity.LsActivity;
import com.jdhd.qynovels.ui.activity.SzActivity;
import com.jdhd.qynovels.ui.activity.TxActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class WodeFragment extends Fragment implements View.OnClickListener{

    private TextView wo_dl,wd_name,wd_hbm;
    private RelativeLayout wd_ls,wd_tx,wd_fk,wd_sz,wd_lb,wd_yq,wd_xj;
    private ImageView wd_toux;
    private int action;
    private LinearLayout wdjb;
    public WodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_wode, container, false);
        init(view);
        Intent intent=getActivity().getIntent();
        action=intent.getIntExtra("action",1);
        if(action==0){
            wd_lb.setVisibility(View.GONE);
            wd_yq.setVisibility(View.GONE);
            wd_xj.setVisibility(View.GONE);
            wo_dl.setVisibility(View.GONE);
            wd_name.setVisibility(View.VISIBLE);
            wd_hbm.setVisibility(View.VISIBLE);
        }
        return view;
    }

    private void init(View view) {
        wo_dl=view.findViewById(R.id.wd_dl);
        wd_ls=view.findViewById(R.id.wd_ls);
        wd_tx=view.findViewById(R.id.wd_tx);
        wd_fk=view.findViewById(R.id.wd_fk);
        wd_sz =view.findViewById(R.id.wd_sz);
        wd_lb=view.findViewById(R.id.wd_lb);
        wd_yq=view.findViewById(R.id.wd_yq);
        wd_xj=view.findViewById(R.id.wd_xj);
        wd_toux=view.findViewById(R.id.wd_toux);
        wd_name=view.findViewById(R.id.wd_name);
        wd_hbm=view.findViewById(R.id.wd_hbm);
        wdjb=view.findViewById(R.id.wd_wdjb);
        wdjb.setOnClickListener(this);
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
            Intent intent=new Intent(getContext(), CjwtActivity.class);
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
        else if(R.id.wd_wdjb==view.getId()){
            Intent intent=new Intent(getContext(), JbActivity.class);
            startActivity(intent);
        }
    }
}
