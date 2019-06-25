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

public class SsAdapter extends RecyclerView.Adapter<SsAdapter.SsViewHolder>{
    private Context context;
    private int type;
    private onItemClick onItemClick;

    public void setOnItemClick(SsAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public SsAdapter(Context context, int type) {
        this.context = context;
        this.type = type;
    }

    @NonNull
    @Override
    public SsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ssl, parent, false);
        SsViewHolder viewHolder=new SsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SsViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onSsclick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(type==1){
            return 8;
        }
        else {
            return 4;
        }

    }

    class SsViewHolder extends RecyclerView.ViewHolder{
        private ImageView book;
        private TextView name,num;
        public SsViewHolder(@NonNull View itemView) {
            super(itemView);
            book=itemView.findViewById(R.id.ss_book);
            name=itemView.findViewById(R.id.ss_name);
            num=itemView.findViewById(R.id.ss_num);
        }
    }
    public interface onItemClick{
        void onSsclick(int index);
    }

}
