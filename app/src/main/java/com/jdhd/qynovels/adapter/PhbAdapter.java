package com.jdhd.qynovels.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.bookshop.RankContentBean;
import com.jdhd.qynovels.ui.activity.XqActivity;

import java.util.ArrayList;
import java.util.List;

public class PhbAdapter extends RecyclerView.Adapter<PhbAdapter.PhbViewHolder>{
    private Context context;
    private onItemClick onItemClick;
    private int count=0;

    public void setCount(int count) {
        this.count = count;
    }

    private List<RankContentBean.DataBean.ListBean> list=new ArrayList<>();
    public void refresh(List<RankContentBean.DataBean.ListBean> list){
        this.list=list;
        notifyDataSetChanged();
    }

    public void setOnItemClick(PhbAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public PhbAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public PhbViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_pb,parent,false);
        PhbViewHolder viewHolder=new PhbViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhbViewHolder holder, final int position) {
        holder.num.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Oswald-Bold.otf"));
        holder.num.setText(position+1+"");
        Glide.with(context).load(list.get(position).getImage()).apply(RequestOptions.bitmapTransform(new RoundedCorners(MyApp.raduis))).into(holder.img);
        holder.name.setText(list.get(position).getName());
        holder.type.setText(list.get(position).getAuthor());
        if(count<5){
            if(position==0){
                holder.num.setTextColor(Color.parseColor("#E8564E"));
            }
            else if(position==1){
                holder.num.setTextColor(Color.parseColor("#EF8E44"));
            }
            else if(position==2){
                holder.num.setTextColor(Color.parseColor("#F2C63F"));
            }
        }
        else {
            holder.num.setTextColor(Color.parseColor("#2F3236"));
        }
        count++;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, XqActivity.class);
                intent.putExtra("page", 2);
                intent.putExtra("xq",3);
                intent.putExtra("id",list.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class PhbViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView name,type,num;
        public PhbViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.fl_img);
            name=itemView.findViewById(R.id.fl_name);
            type=itemView.findViewById(R.id.fl_type);
            num=itemView.findViewById(R.id.fl_num);
        }
    }
    public interface onItemClick{
        void onClick(int index);
    }
}
