package com.jdhd.qynovels.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jdhd.qynovels.R;

public class PhbAdapter extends RecyclerView.Adapter<PhbAdapter.PhbViewHolder>{
    private Context context;
    private onItemClick onItemClick;

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
        if(position==0){
            holder.num.setTextColor(Color.parseColor("#E8564E"));
        }
        else if(position==1){
            holder.num.setTextColor(Color.parseColor("#EF8E44"));
        }
        else if(position==2){
            holder.num.setTextColor(Color.parseColor("#F2C63F"));
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
