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

public class XsAdapter extends RecyclerView.Adapter<XsAdapter.XsViewHolder>{
    private Context context;
    private onItemClick onItemClick;

    public void setOnItemClick(XsAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public XsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public XsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.xs, parent, false);
        XsViewHolder viewHolder=new XsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull XsViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onXsclick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    class XsViewHolder extends RecyclerView.ViewHolder{
        private ImageView book;
        private TextView name,num;
        private XsViewHolder(@NonNull View itemView) {
            super(itemView);
            book=itemView.findViewById(R.id.ss_book);
            name=itemView.findViewById(R.id.ss_name);
        }
    }
    public interface onItemClick{
        void onXsclick(int index);
    }
}
