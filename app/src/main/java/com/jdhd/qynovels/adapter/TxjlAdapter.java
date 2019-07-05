package com.jdhd.qynovels.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jdhd.qynovels.R;

public class TxjlAdapter extends RecyclerView.Adapter<TxjlAdapter.TxjlViewHolder>{
    private Context context;

    public TxjlAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public TxjlViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_txjl,parent,false);
        TxjlViewHolder viewHolder=new TxjlViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TxjlViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class TxjlViewHolder extends RecyclerView.ViewHolder{

        public TxjlViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
