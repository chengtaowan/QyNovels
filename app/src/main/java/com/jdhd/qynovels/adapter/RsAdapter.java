package com.jdhd.qynovels.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jdhd.qynovels.R;

public class RsAdapter extends RecyclerView.Adapter<RsAdapter.RsViewHolder>{
    private Context context;
    private onItemClick onItemClick;

    public void setOnItemClick(RsAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public RsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rs, parent, false);
        RsViewHolder viewHolder=new RsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RsViewHolder holder, final int position) {
       if(position==1){
         holder.rs.setImageResource(R.mipmap.home_rs_2);
       }
       else if(position==2){
           holder.rs.setImageResource(R.mipmap.home_rs_3);
       }
       else if(position==3){
           holder.rs.setImageResource(R.mipmap.home_rs_4);
       }
       else if(position==4){
           holder.rs.setImageResource(R.mipmap.home_rs_5);
       }
       else if(position==5){
           holder.rs.setVisibility(View.GONE);
           holder.name.setVisibility(View.GONE);
           holder.book.setVisibility(View.GONE);
           holder.num.setVisibility(View.GONE);
           holder.rd.setVisibility(View.GONE);
           holder.more.setVisibility(View.VISIBLE);
       }
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               onItemClick.onRsclick(position);
           }
       });
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    class RsViewHolder extends RecyclerView.ViewHolder{
        private ImageView book,rs;
        private TextView name,rd,num,more;
        private RelativeLayout rl;
        public RsViewHolder(@NonNull View itemView) {
            super(itemView);
            book=itemView.findViewById(R.id.rs_book);
            rs=itemView.findViewById(R.id.rs);
            name=itemView.findViewById(R.id.rs_name);
            rd=itemView.findViewById(R.id.rs_rd);
            num=itemView.findViewById(R.id.rs_num);
            more=itemView.findViewById(R.id.rs_more);
            rl=itemView.findViewById(R.id.rs_rl);
        }
    }
    public interface onItemClick{
        void onRsclick(int index);
    }
}
