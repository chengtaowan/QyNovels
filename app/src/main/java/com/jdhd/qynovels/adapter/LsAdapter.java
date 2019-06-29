package com.jdhd.qynovels.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.module.BookBean;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.List;

public class LsAdapter extends RecyclerView.Adapter<LsAdapter.LsViewHolder>{
    private Context context;
    private List<BookBean> list;

    public LsAdapter(Context context, List<BookBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public LsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ls, parent, false);
        LsViewHolder viewHolder=new LsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final LsViewHolder holder, final int position) {
        holder.name.setText(list.get(position).name);
        holder.zj.setText(list.get(position).des);
       holder.del.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               list.remove(position);
               notifyDataSetChanged();
               holder.sml.quickClose();
           }
       });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class LsViewHolder extends RecyclerView.ViewHolder{
        private ImageView book;
        private TextView name,zj,day;
        private Button del;
        private SwipeMenuLayout sml;
        public LsViewHolder(@NonNull View itemView) {
            super(itemView);
            book=itemView.findViewById(R.id.ls_book);
            name=itemView.findViewById(R.id.ls_name);
            zj=itemView.findViewById(R.id.ls_zj);
            day=itemView.findViewById(R.id.ls_day);
            del=itemView.findViewById(R.id.ls_del);
            sml=itemView.findViewById(R.id.sml);
        }
    }
}