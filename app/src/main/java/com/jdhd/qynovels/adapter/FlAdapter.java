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

public class FlAdapter extends RecyclerView.Adapter<FlAdapter.FlViewHolder>{
    private Context context;
    private onItemClick onItemClick;

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
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               onItemClick.onFlClick(position);
           }
       });
    }

    @Override
    public int getItemCount() {
        return 10;
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
