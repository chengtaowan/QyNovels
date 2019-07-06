package com.jdhd.qynovels.adapter;

import android.content.Context;
import android.graphics.Typeface;
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

public class GfAdapter extends RecyclerView.Adapter<GfAdapter.GfViewHolder>{
    private Context context;
    private int type;
    private onItemClick onItemClick;
    private List<ShopBean.DataBean.ListBeanX.ListBean> list=new ArrayList<>();
    public void refresh(List<ShopBean.DataBean.ListBeanX.ListBean> list){
        this.list=list;
        notifyDataSetChanged();
    }

    public void setOnItemClick(GfAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public GfAdapter(Context context, int type) {
        this.context = context;
        this.type = type;
    }

    @NonNull
    @Override
    public GfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GfViewHolder viewHolder=null;

            View view= LayoutInflater.from(context).inflate(R.layout.item_gf,parent,false);
            viewHolder=new GfViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GfViewHolder holder, final int position) {
        holder.grade.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Oswald-Bold.otf"));
        if(type==1){
            holder.type.setVisibility(View.VISIBLE);
        }
        if(list.size()==0){
            return;
        }
        Glide.with(context).load(list.get(position).getImage()).into(holder.book);
        holder.name.setText(list.get(position).getName());
        holder.grade.setText(list.get(position).getGrade()+"");
        holder.des.setText(list.get(position).getIntro());
        holder.type.setText(list.get(position).getFinishStatus()+"");
        holder.num.setText(list.get(position).getNumber()+"万字");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onGfclick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class GfViewHolder extends RecyclerView.ViewHolder{
        private ImageView book;
        private TextView name,grade,des,num,type;
        public GfViewHolder(@NonNull View itemView) {
            super(itemView);
            book=itemView.findViewById(R.id.gf_book);
            name=itemView.findViewById(R.id.gf_name);
            grade=itemView.findViewById(R.id.gf_grade);
            des=itemView.findViewById(R.id.gf_des);
            num=itemView.findViewById(R.id.gf_num);
            type=itemView.findViewById(R.id.gf_type);
        }
    }
    public interface onItemClick{
        void onGfclick(int index);
    }
}
