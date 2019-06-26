package com.jdhd.qynovels.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.module.BookBean;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder>{

    private Context context;
    private List<BookBean> list;
    private onItemClick onItemClick;

    public void setOnItemClick(ListAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public ListAdapter(Context context, List<BookBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_case,parent,false);
        ListViewHolder viewHolder=new ListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, final int position) {
        if(position==list.size()){
            holder.book.setImageResource(R.mipmap.sj_tj);
            holder.name.setVisibility(View.GONE);
            holder.xh.setVisibility(View.VISIBLE);
            holder.zj.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.onxhClick(position);
                }
            });
        }
        else{
            holder.book.setImageResource(list.get(position).url);
            holder.name.setText(list.get(position).name);
            holder.zj.setText(list.get(position).des);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.onlistClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size()+1;
    }

    class ListViewHolder extends RecyclerView.ViewHolder{
        private ImageView book;
        private TextView name,zj,yd,tg,xh;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            book=itemView.findViewById(R.id.case_book);
            name=itemView.findViewById(R.id.case_name);
            zj=itemView.findViewById(R.id.case_zj);
            yd=itemView.findViewById(R.id.case_yd);
            tg=itemView.findViewById(R.id.case_tg);
            xh=itemView.findViewById(R.id.case_xh);
        }
    }
    public interface onItemClick{
        void onlistClick(int index);
        void onxhClick(int index);
    }
}
