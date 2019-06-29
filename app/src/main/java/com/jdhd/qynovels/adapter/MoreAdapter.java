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

import com.jdhd.qynovels.R;

public class MoreAdapter extends RecyclerView.Adapter<MoreAdapter.MoreViewHolder>{
    private Context context;
    private int type;
    private onItemClick onItemClick;

    public void setOnItemClick(MoreAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public MoreAdapter(Context context, int type) {
        this.context = context;
        this.type = type;
    }

    @NonNull
    @Override
    public MoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_more,parent,false);
        MoreViewHolder viewHolder=new MoreViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MoreViewHolder holder, final int position) {
        holder.grade.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Oswald-Bold.otf"));
        if(position==9){
           holder.di.setVisibility(View.VISIBLE);
           holder.book.setVisibility(View.GONE);
           holder.name.setVisibility(View.GONE);
           holder.grade.setVisibility(View.GONE);
           holder.fen.setVisibility(View.GONE);
           holder.des.setVisibility(View.GONE);
           holder.wj.setVisibility(View.GONE);
           holder.num.setVisibility(View.GONE);
           holder.type.setVisibility(View.GONE);
       }
       else{
           holder.type.setVisibility(View.VISIBLE);
       }
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               onItemClick.onClick(position);
           }
       });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class MoreViewHolder extends RecyclerView.ViewHolder{
        private ImageView book;
        private TextView name,grade,fen,des,wj,num,type,di;
        public MoreViewHolder(@NonNull View itemView) {
            super(itemView);
            book=itemView.findViewById(R.id.gf_book);
            name=itemView.findViewById(R.id.gf_name);
            grade=itemView.findViewById(R.id.gf_grade);
            fen=itemView.findViewById(R.id.gf_fen);
            des=itemView.findViewById(R.id.gf_des);
            wj=itemView.findViewById(R.id.gf_wj);
            num=itemView.findViewById(R.id.gf_num);
            type=itemView.findViewById(R.id.gf_type);
            di=itemView.findViewById(R.id.gf_di);
        }
    }
    public interface onItemClick{
        void onClick(int index);
    }
}
