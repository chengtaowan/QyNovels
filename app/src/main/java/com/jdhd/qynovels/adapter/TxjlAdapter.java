package com.jdhd.qynovels.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.module.personal.DrawListBean;

import java.util.ArrayList;
import java.util.List;

public class TxjlAdapter extends RecyclerView.Adapter<TxjlAdapter.TxjlViewHolder>{
    private Context context;
    private List<DrawListBean.DataBean.ListBean> list=new ArrayList<>();
    public void refresh(List<DrawListBean.DataBean.ListBean> list){
        this.list=list;
        notifyDataSetChanged();
    }

    public TxjlAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public TxjlViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_txjl,parent,false);
        TxjlViewHolder viewHolder=new TxjlViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TxjlViewHolder holder, int position) {
        if(list.size()==0){
            return;
        }
        holder.num.setText(list.get(position).getTitle());
        holder.time.setText(list.get(position).getTime());
        holder.stadus.setText(list.get(position).getStatus());

    }

    @Override
    public int getItemCount() {
        if(list.size()==0){
          return 0;
        }
        else{
            return list.size();
        }

    }

    class TxjlViewHolder extends RecyclerView.ViewHolder{
        private TextView num,time,stadus;
        public TxjlViewHolder(@NonNull View itemView) {
            super(itemView);
            num=itemView.findViewById(R.id.tx_num);
            time=itemView.findViewById(R.id.tx_time);
            stadus=itemView.findViewById(R.id.tx_cg);
        }
    }
}
