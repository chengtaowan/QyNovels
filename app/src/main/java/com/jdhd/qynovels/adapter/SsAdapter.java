package com.jdhd.qynovels.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.module.bookshop.ShopBean;
import com.jdhd.qynovels.ui.activity.XqActivity;

import java.util.ArrayList;
import java.util.List;

public class SsAdapter extends RecyclerView.Adapter<SsAdapter.SsViewHolder>{
    private Context context;
    private int type;
    private onItemClick onItemClick;
    private List<ShopBean.DataBean.ListBeanX.ListBean> list=new ArrayList<>();
    public void refresh(List<ShopBean.DataBean.ListBeanX.ListBean> list){
        this.list=list;
        notifyDataSetChanged();
    }

    public void setOnItemClick(SsAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public SsAdapter(Context context, int type) {
        this.context = context;
        this.type = type;
    }

    @NonNull
    @Override
    public SsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ssl, parent, false);
        SsViewHolder viewHolder=new SsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SsViewHolder holder, final int position) {
        if(list.size()==0){
            return;
        }
        if(type==1){
            Glide.with(context).load(list.get(position).getImage()).into(holder.book);
            holder.name.setText(list.get(position).getName());
            holder.num.setText(list.get(position).getSearch()+"次搜索");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, XqActivity.class);
                    intent.putExtra("xq",2);
                    intent.putExtra("id",list.get(position).getBookId());
                    context.startActivity(intent);
                }
            });
        }
        else{
            Glide.with(context).load(list.get(position+1).getImage()).into(holder.book);
            holder.name.setText(list.get(position+1).getName());
            holder.num.setText(list.get(position+1).getSearch()+"次搜索");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, XqActivity.class);
                    intent.putExtra("xq",2);
                    intent.putExtra("id",list.get(position+1).getBookId());
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if(type==1){
            return list.size();
        }
        else {
            return list.size()-1;
        }

    }

    class SsViewHolder extends RecyclerView.ViewHolder{
        private ImageView book;
        private TextView name,num;
        public SsViewHolder(@NonNull View itemView) {
            super(itemView);
            book=itemView.findViewById(R.id.ss_book);
            name=itemView.findViewById(R.id.ss_name);
            num=itemView.findViewById(R.id.ss_num);
        }
    }
    public interface onItemClick{
        void onSsclick(int index);
    }

}
