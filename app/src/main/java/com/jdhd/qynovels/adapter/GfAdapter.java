package com.jdhd.qynovels.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.jdhd.qynovels.module.bookcase.BookInfoBean;
import com.jdhd.qynovels.module.bookshop.ShopBean;
import com.jdhd.qynovels.ui.activity.XqActivity;

import java.util.ArrayList;
import java.util.List;

public class GfAdapter extends RecyclerView.Adapter<GfAdapter.GfViewHolder>{
    private Context context;
    private int type,usetype;
    private onItemClick onItemClick;
    private List<ShopBean.DataBean.ListBeanX.ListBean> list=new ArrayList<>();
    private List<BookInfoBean.DataBean.ListBean> listBeanList=new ArrayList<>();
    public void refresh(List<ShopBean.DataBean.ListBeanX.ListBean> list){
        this.list=list;
        notifyDataSetChanged();
    }
    public void refreshbooklist(List<BookInfoBean.DataBean.ListBean> listBeanList){
        this.listBeanList=listBeanList;
        notifyDataSetChanged();
    }

    public void setOnItemClick(GfAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public GfAdapter(Context context, int type, int usetype) {
        this.context = context;
        this.type = type;
        this.usetype = usetype;
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
        if(usetype==1){
            if(listBeanList.size()==0){
                return;
            }
            Glide.with(context).load(listBeanList.get(position).getImage()).into(holder.book);
            holder.name.setText(listBeanList.get(position).getName());
            //holder.grade.setText(listBeanList.get(position).getGrade()+"");
            holder.des.setText(listBeanList.get(position).getIntro());
            holder.type.setText(listBeanList.get(position).getClassX());
            //holder.num.setText(listBeanList.get(position).+"万字");
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
        else if(usetype==0){
            if(list.size()==0){
                return;
            }
            Glide.with(context).load(list.get(position).getImage()).into(holder.book);
            holder.name.setText(list.get(position).getName());
            holder.grade.setText(list.get(position).getGrade()+"");
            holder.des.setText(list.get(position).getIntro());
            holder.type.setText(list.get(position).getFinishStatus()+"");
            holder.num.setText(list.get(position).getNumber()+"字");
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


    }

    @Override
    public int getItemCount() {
        if(usetype==1){
           return 3;
        }
        else if(usetype==0){
            return list.size();
        }
        return 0;
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
