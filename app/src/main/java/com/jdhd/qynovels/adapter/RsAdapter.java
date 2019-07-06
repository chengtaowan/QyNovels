package com.jdhd.qynovels.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.module.ShopBean;
import com.jdhd.qynovels.ui.activity.PhbActivity;

import java.util.ArrayList;
import java.util.List;

public class RsAdapter extends RecyclerView.Adapter<RsAdapter.RsViewHolder>{
    private Context context;
    private onItemClick onItemClick;
    private List<ShopBean.DataBean.ListBeanX.ListBean> list=new ArrayList<>();
    public void refresh(List<ShopBean.DataBean.ListBeanX.ListBean> list){
        this.list=list;
        notifyDataSetChanged();
    }

    public void setOnItemClick(RsAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public RsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rs, parent, false);
        RsViewHolder viewHolder=new RsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RsViewHolder holder, final int position) {
        Glide.with(context).load(list.get(position).getImage()).into(holder.book);
        holder.name.setText(list.get(position).getName());
        holder.num.setText(list.get(position).getHot()+"");
        if(position==0){
            holder.rs.setImageResource(R.mipmap.home_rs_1);
        }
       else if(position==1){
         holder.rs.setImageResource(R.mipmap.home_rs_2);
       }
       else if(position==2){
           holder.rs.setImageResource(R.mipmap.home_rs_3);
       }
       else if(position==3){
           holder.rs.setImageResource(R.mipmap.home_rs_4);
       }
       else if(position==4){
           holder.rs.setImageResource(R.mipmap.home_rs_5);
       }
       else if(position==list.size()-1){
           holder.rs.setVisibility(View.GONE);
           holder.name.setVisibility(View.GONE);
           holder.book.setVisibility(View.GONE);
           holder.num.setVisibility(View.GONE);
           holder.rd.setVisibility(View.GONE);
           holder.more.setVisibility(View.VISIBLE);
       }
       else{
           holder.rs.setVisibility(View.GONE);
       }
       if(position==list.size()-1){
           holder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent intent=new Intent(context, PhbActivity.class);
                   context.startActivity(intent);
               }
           });
       }
       else{
           holder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   onItemClick.onRsclick(position);
               }
           });
       }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class RsViewHolder extends RecyclerView.ViewHolder{
        private ImageView book,rs;
        private TextView name,rd,num,more;
        private RelativeLayout rl;
        public RsViewHolder(@NonNull View itemView) {
            super(itemView);
            book=itemView.findViewById(R.id.rs_book);
            rs=itemView.findViewById(R.id.rs);
            name=itemView.findViewById(R.id.rs_name);
            rd=itemView.findViewById(R.id.rs_rd);
            num=itemView.findViewById(R.id.rs_num);
            more=itemView.findViewById(R.id.rs_more);
            rl=itemView.findViewById(R.id.rs_rl);
        }
    }
    public interface onItemClick{
        void onRsclick(int index);
    }
}
