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

public class XsAdapter extends RecyclerView.Adapter<XsAdapter.XsViewHolder>{
    private Context context;
    private onItemClick onItemClick;
    private List<ShopBean.DataBean.ListBeanX.ListBean> list=new ArrayList<>();
    public void refresh(List<ShopBean.DataBean.ListBeanX.ListBean> list){
        this.list=list;
        notifyDataSetChanged();
    }

    public void setOnItemClick(XsAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public XsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public XsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.xs, parent, false);
        XsViewHolder viewHolder=new XsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull XsViewHolder holder, final int position) {
        if(list.size()==0){
           return;
        }
        Glide.with(context).load(list.get(position).getImage()).into(holder.book);
        holder.name.setText(list.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onXsclick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class XsViewHolder extends RecyclerView.ViewHolder{
        private ImageView book;
        private TextView name;
        private XsViewHolder(@NonNull View itemView) {
            super(itemView);
            book=itemView.findViewById(R.id.ss_book);
            name=itemView.findViewById(R.id.ss_name);
        }
    }
    public interface onItemClick{
        void onXsclick(int index);
    }
}
