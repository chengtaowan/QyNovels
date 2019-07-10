package com.jdhd.qynovels.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.module.bookcase.BookListBean;

import java.util.ArrayList;
import java.util.List;

public class MuluAdapter extends RecyclerView.Adapter<MuluAdapter.MuluViewHolder>{
    private List<BookListBean.DataBean.ListBean> list=new ArrayList<>();
    public void refresh(List<BookListBean.DataBean.ListBean> list){
        this.list=list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MuluViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mulu, parent, false);
        MuluViewHolder viewHolder=new MuluViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MuluViewHolder holder, int position) {
       holder.zj.setText(list.get(position).getName());
       if(list.get(position).getReadType()==10){
          holder.type.setText("免费");
       }
       else {
           holder.type.setText("收费");
       }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MuluViewHolder extends RecyclerView.ViewHolder{
        private TextView zj,type;
        public MuluViewHolder(@NonNull View itemView) {
            super(itemView);
            zj=itemView.findViewById(R.id.ml_zj);
            type=itemView.findViewById(R.id.ml_type);
        }
    }
}
