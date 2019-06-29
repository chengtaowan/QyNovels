package com.jdhd.qynovels.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jdhd.qynovels.R;

import java.util.ArrayList;
import java.util.List;

public class Ss_LxAdapter extends RecyclerView.Adapter<Ss_LxAdapter.Ss_LxViewHolder>{
    private Context context;
    private List<String> list;
    private onItemClick onItemClick;

    public void setOnItemClick(Ss_LxAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public Ss_LxAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Ss_LxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ss_lx, parent, false);
        Ss_LxViewHolder viewHolder=new Ss_LxViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Ss_LxViewHolder holder, final int position) {

       holder.tex.setText(list.get(position));
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               onItemClick.onLxClick(position);
           }
       });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Ss_LxViewHolder extends RecyclerView.ViewHolder{
        private TextView tex;
        public Ss_LxViewHolder(@NonNull View itemView) {
            super(itemView);
            tex=itemView.findViewById(R.id.ss_lx_name);
        }
    }
    public interface onItemClick{
        void onLxClick(int index);
    }
}
