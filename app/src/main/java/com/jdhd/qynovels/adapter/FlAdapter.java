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
import com.jdhd.qynovels.module.ClassBean;

import java.util.ArrayList;
import java.util.List;

public class FlAdapter extends RecyclerView.Adapter<FlAdapter.FlViewHolder>{
    private Context context;
    private onItemClick onItemClick;
    private List<ClassBean.DataBean.ListBean.ChildBean> list=new ArrayList<>();
    public void refresh(List<ClassBean.DataBean.ListBean.ChildBean> list){
        this.list=list;
        notifyDataSetChanged();
    }

    public void setOnItemClick(FlAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public FlAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FlViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_fl,parent,false);
        FlViewHolder viewHolder=new FlViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FlViewHolder holder, final int position) {
        Glide.with(context).load(list.get(position).getIcon()).into(holder.img);
        holder.name.setText(list.get(position).getName());
        holder.num.setText(list.get(position).getBookNum()+"本");
        holder.type.setText(list.get(position).getDes());
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               onItemClick.onFlClick(position);
           }
       });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class FlViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView name,type,num;
        public FlViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.fl_img);
            name=itemView.findViewById(R.id.fl_name);
            type=itemView.findViewById(R.id.fl_type);
            num=itemView.findViewById(R.id.fl_num);
        }
    }
    public interface onItemClick{
        void onFlClick(int index);
    }
}
