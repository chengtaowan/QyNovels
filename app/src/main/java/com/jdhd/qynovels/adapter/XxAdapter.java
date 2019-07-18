package com.jdhd.qynovels.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.module.personal.MessageBean;

import java.util.ArrayList;
import java.util.List;

public class XxAdapter extends RecyclerView.Adapter<XxAdapter.XxViewHolder>{
    private List<MessageBean.DataBean.ListBean> list=new ArrayList<>();
    public void refresh(List<MessageBean.DataBean.ListBean> list){
        this.list=list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public XxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xx, parent, false);
        XxViewHolder viewHolder=new XxViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull XxViewHolder holder, int position) {
         holder.title.setText(list.get(position).getTitle());
         holder.content.setText(list.get(position).getContent());
         if(list.get(position).getIs_read()==10){
           holder.img.setVisibility(View.GONE);
         }
         else{
             holder.img.setVisibility(View.VISIBLE);
         }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class XxViewHolder extends RecyclerView.ViewHolder{
        private TextView title,content;
        private ImageView img;
        public XxViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.xx_title);
            content=itemView.findViewById(R.id.xx_content);
            img=itemView.findViewById(R.id.xx_dian);
        }
    }
}
