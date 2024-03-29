package com.jdhd.qynovels.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.module.personal.DrawSetBean;
import com.jdhd.qynovels.module.personal.SignSetingBean;

import java.util.ArrayList;
import java.util.List;

public class QdAdapter extends RecyclerView.Adapter<QdAdapter.TxViewHolder>{
    private List<SignSetingBean.DataBean.RuleBean> list=new ArrayList<>();
    public void refresh(List<SignSetingBean.DataBean.RuleBean> list){
        this.list=list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public TxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tx,parent,false);
        TxViewHolder viewHolder=new TxViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TxViewHolder holder, int position) {
        holder.tex.setText(list.get(position).getSort()+":"+list.get(position).getRule());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TxViewHolder extends RecyclerView.ViewHolder{
        private TextView tex;
        public TxViewHolder(@NonNull View itemView) {
            super(itemView);
            tex=itemView.findViewById(R.id.tex);
        }
    }
}
