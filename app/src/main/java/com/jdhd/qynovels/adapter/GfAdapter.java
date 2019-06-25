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

public class GfAdapter extends RecyclerView.Adapter<GfAdapter.GfViewHolder>{
    private Context context;
    private int type;
    private onItemClick onItemClick;

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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onGfclick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
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
