package com.jdhd.qynovels.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.ui.activity.XqActivity;

public class WjjpAdapter extends RecyclerView.Adapter<WjjpAdapter.WjjpViewHolder> implements XsAdapter.onItemClick{
    private Context context;
    private onItemClick onItemClick;

    public void setOnItemClick(WjjpAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public WjjpAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public WjjpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_wj,parent,false);
        WjjpViewHolder viewHolder=new WjjpViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WjjpViewHolder holder, final int position) {
       if(position==0){
           holder.more.setVisibility(View.GONE);
       }
       GridLayoutManager manager=new GridLayoutManager(context,4);
       holder.rv.setLayoutManager(manager);
       XsAdapter adapter=new XsAdapter(context);
       holder.rv.setAdapter(adapter);
       adapter.setOnItemClick(this);
       holder.more.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               onItemClick.onMoreClick(position);
           }
       });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public void onXsclick(int index) {
        Intent intent=new Intent(context, XqActivity.class);
        intent.putExtra("xq",4);
        context.startActivity(intent);
    }

    class WjjpViewHolder extends RecyclerView.ViewHolder{
        private TextView type,more;
        private RecyclerView rv;
        public WjjpViewHolder(@NonNull View itemView) {
            super(itemView);
            type=itemView.findViewById(R.id.wj_type);
            more=itemView.findViewById(R.id.wj_more);
            rv=itemView.findViewById(R.id.wj_rv);
        }
    }
    public interface onItemClick{
        void onMoreClick(int index);
    }
}
