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
import com.jdhd.qynovels.module.ClassBean;
import com.jdhd.qynovels.module.RankBean;

import java.util.ArrayList;
import java.util.List;

public class Fl_biaoti_Adapter extends RecyclerView.Adapter<Fl_biaoti_Adapter.Fl_Title_ViewHolder>{
    private Context context;
    private List<ClassBean.DataBean.ListBean> list=new ArrayList<>();
    private List<Boolean> isClick;
    private onTitleClick onTitleClick;

    public void refresh(List<ClassBean.DataBean.ListBean> list){
        this.list=list;
        notifyDataSetChanged();
    }

    public void setOnTitleClick(Fl_biaoti_Adapter.onTitleClick onTitleClick) {
        this.onTitleClick = onTitleClick;
    }

    public Fl_biaoti_Adapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Fl_Title_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_fl_title,parent,false);
        Fl_Title_ViewHolder viewHolder=new Fl_Title_ViewHolder(view);
        isClick=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            if(i==0){
                isClick.add(i,true);
            }
            else{
                isClick.add(i,false);
            }
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Fl_Title_ViewHolder holder, final int position) {
      holder.title.setText(list.get(position).getName());
      if(isClick.get(position)){
          holder.title.setTextColor(Color.parseColor("#2F3236"));
          holder.title.setTextSize(16);
          holder.title.setBackgroundColor(Color.parseColor("#FFFFFF"));
      }
      else{
          holder.title.setTextColor(Color.parseColor("#828486"));
          holder.title.setTextSize(14);
          holder.title.setBackgroundColor(Color.parseColor("#FAFAFA"));
      }
      holder.title.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              onTitleClick.onclick(position);
              for(int i=0;i<list.size();i++){
                 isClick.add(i,false);
              }
              isClick.set(position,true);
              notifyDataSetChanged();
          }
      });
    }

    @Override
    public int getItemCount() {
        if(list.size()==0){
            return 0;
        }
        else{
            return list.size();
        }

    }

    class Fl_Title_ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        public Fl_Title_ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.rl_title);
        }
    }
    public interface onTitleClick{
        void onclick(int index);
    }
}
