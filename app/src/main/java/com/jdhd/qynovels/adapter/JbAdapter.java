package com.jdhd.qynovels.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.module.personal.GoldListBean;

import java.util.ArrayList;
import java.util.List;

public class JbAdapter extends RecyclerView.Adapter<JbAdapter.JbViewHolder> {
    private List<GoldListBean.DataBean.ListBean> list=new ArrayList<>();
    public void refresh(List<GoldListBean.DataBean.ListBean> list){
        this.list=list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public JbViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jb, parent, false);
        JbViewHolder viewHolder=new JbViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull JbViewHolder holder, int position) {
       holder.des.setText(list.get(position).getTitle());
       holder.time.setText(list.get(position).getTime());
       holder.num.setText(list.get(position).getAward());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class JbViewHolder extends RecyclerView.ViewHolder{
        private TextView des,time,num;
        public JbViewHolder(@NonNull View itemView) {
            super(itemView);
            des=itemView.findViewById(R.id.des);
            time=itemView.findViewById(R.id.time);
            num=itemView.findViewById(R.id.num);
        }
    }
}
