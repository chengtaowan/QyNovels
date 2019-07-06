package com.jdhd.qynovels.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.module.ShopBean;

import java.util.ArrayList;
import java.util.List;

public class KdAdapter extends RecyclerView.Adapter<KdAdapter.KdViewHolder>{
    private Context context;
    private onItemClick onItemClick;
    private List<ShopBean.DataBean.ListBeanX.ListBean> list=new ArrayList<>();
    public void refresh(List<ShopBean.DataBean.ListBeanX.ListBean> list){
        this.list=list;
        notifyDataSetChanged();
    }

    public void setOnItemClick(KdAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public KdAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public KdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kd, parent, false);
        KdViewHolder viewHolder=new KdViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull KdViewHolder holder, final int position) {
        Glide.with(context).load(list.get(position).getImage()).into(holder.book);
        holder.name.setText(list.get(position).getName());
        holder.num.setText(list.get(position).getHot()+"");
        if(position==0){
            holder.top.setImageResource(R.mipmap.home_kd_1);
        }
        else if(position==1){
            holder.top.setImageResource(R.mipmap.home_kd_2);
        }
        else if(position==2){
            holder.top.setImageResource(R.mipmap.home_kd_3);
        }
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               onItemClick.onKdclick(position);
           }
       });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class KdViewHolder extends RecyclerView.ViewHolder{
        private ImageView book,top;
        private TextView name,num;
        public KdViewHolder(@NonNull View itemView) {
            super(itemView);
            book=itemView.findViewById(R.id.kd_book);
            top=itemView.findViewById(R.id.kd_top);
            name=itemView.findViewById(R.id.kd_name);
            num=itemView.findViewById(R.id.kd_num);
        }
    }
    public interface onItemClick{
        void onKdclick(int index);
    }
}
