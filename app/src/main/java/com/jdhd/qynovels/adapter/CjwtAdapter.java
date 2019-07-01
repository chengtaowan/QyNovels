package com.jdhd.qynovels.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jdhd.qynovels.R;

public class CjwtAdapter extends RecyclerView.Adapter<CjwtAdapter.CjwtViewHolder>{

    @NonNull
    @Override
    public CjwtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cjwt, parent, false);
        CjwtViewHolder viewHolder=new CjwtViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CjwtViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class CjwtViewHolder extends RecyclerView.ViewHolder{
        private TextView q,a;
        public CjwtViewHolder(@NonNull View itemView) {
            super(itemView);
            q=itemView.findViewById(R.id.question);
            a=itemView.findViewById(R.id.answer);
        }
    }
}
