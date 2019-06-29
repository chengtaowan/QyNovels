package com.jdhd.qynovels.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jdhd.qynovels.R;

public class Ss_RsAdapter extends RecyclerView.Adapter<Ss_RsAdapter.Ss_RsViewHolder>{
    private Context context;
    private onItemClick onItemClick;

    public void setOnItemClick(Ss_RsAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public Ss_RsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Ss_RsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_ss_rs_lay,parent,false);
        Ss_RsViewHolder viewHolder=new Ss_RsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Ss_RsViewHolder holder, final int position) {
        if(position==1){
            holder.num.setText("6");
            holder.num.setTextColor(Color.parseColor("#828486"));
            holder.num.setBackgroundColor(Color.parseColor("#FAFAFA"));
        }
        else if(position==2){
            holder.num.setText("2");
            holder.num.setTextColor(Color.parseColor("#FFFFFF"));
            holder.num.setBackgroundColor(Color.parseColor("#EF8E44"));
        }
        else if(position==3){
            holder.num.setText("7");
            holder.num.setTextColor(Color.parseColor("#828486"));
            holder.num.setBackgroundColor(Color.parseColor("#FAFAFA"));
        }
        else if(position==4){
            holder.num.setText("3");
            holder.num.setTextColor(Color.parseColor("#FFFFFF"));
            holder.num.setBackgroundColor(Color.parseColor("#F2C63F"));
        }
        else if(position==5){
            holder.num.setText("8");
            holder.num.setTextColor(Color.parseColor("#828486"));
            holder.num.setBackgroundColor(Color.parseColor("#FAFAFA"));
        }
        else if(position==6){
            holder.num.setText("4");
            holder.num.setTextColor(Color.parseColor("#828486"));
            holder.num.setBackgroundColor(Color.parseColor("#FAFAFA"));
        }
        else if(position==7){
            holder.num.setText("9");
            holder.num.setTextColor(Color.parseColor("#828486"));
            holder.num.setBackgroundColor(Color.parseColor("#FAFAFA"));
        }
        else if(position==8){
            holder.num.setText("5");
            holder.num.setTextColor(Color.parseColor("#828486"));
            holder.num.setBackgroundColor(Color.parseColor("#FAFAFA"));
        }
        else if(position==9){
            holder.num.setText("10");
            holder.num.setTextColor(Color.parseColor("#828486"));
            holder.num.setBackgroundColor(Color.parseColor("#FAFAFA"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onRsClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class Ss_RsViewHolder extends RecyclerView.ViewHolder{
        private TextView num,name;
        public Ss_RsViewHolder(@NonNull View itemView) {
            super(itemView);
            num=itemView.findViewById(R.id.ss_rs_num);
            name=itemView.findViewById(R.id.ss_rs_name);
        }
    }
    public interface onItemClick{
        void onRsClick(int index);
    }
}
