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

public class KdAdapter extends RecyclerView.Adapter<KdAdapter.KdViewHolder>{
    private Context context;
    private onItemClick onItemClick;

    public void setOnItemClick(KdAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public KdAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public KdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kd, parent, false);
        KdViewHolder viewHolder=new KdViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull KdViewHolder holder, final int position) {
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               onItemClick.onKdclick(position);
           }
       });
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class KdViewHolder extends RecyclerView.ViewHolder{
        private ImageView book,top;
        private TextView name,num;
        public KdViewHolder(@NonNull View itemView) {
            super(itemView);
            book=itemView.findViewById(R.id.kd_book);
            top=itemView.findViewById(R.id.kd_top);
            name=itemView.findViewById(R.id.kd_name);
            num=itemView.findViewById(R.id.kd_num);
        }
    }
    public interface onItemClick{
        void onKdclick(int index);
    }
}
