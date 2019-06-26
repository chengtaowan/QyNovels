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

public class Ss_NrAdapter extends RecyclerView.Adapter<Ss_NrAdapter.Ss_NrViewHolder>{
    private Context context;

    public Ss_NrAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Ss_NrViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gf, parent, false);
        Ss_NrViewHolder viewHolder=new Ss_NrViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Ss_NrViewHolder holder, int position) {
        holder.grade.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Oswald-Bold.otf"));
        if(position==9){
            holder.book.setVisibility(View.GONE);
            holder.name.setVisibility(View.GONE);
            holder.des.setVisibility(View.GONE);
            holder.di.setVisibility(View.VISIBLE);
            holder.num.setVisibility(View.GONE);
            holder.grade.setVisibility(View.GONE);
            holder.fen.setVisibility(View.GONE);
            holder.wj.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class Ss_NrViewHolder extends RecyclerView.ViewHolder{
        private ImageView book;
        private TextView name,grade,des,num,di,fen,wj;
        public Ss_NrViewHolder(@NonNull View itemView) {
            super(itemView);
            book=itemView.findViewById(R.id.gf_book);
            name=itemView.findViewById(R.id.gf_name);
            grade=itemView.findViewById(R.id.gf_grade);
            des=itemView.findViewById(R.id.gf_des);
            num=itemView.findViewById(R.id.gf_num);
            di=itemView.findViewById(R.id.gf_di);
            fen=itemView.findViewById(R.id.gf_fen);
            wj=itemView.findViewById(R.id.gf_wj);
        }
    }
}
