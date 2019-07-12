package com.jdhd.qynovels.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.module.bookcase.CaseBean;
import com.jdhd.qynovels.module.bookcase.DelBookRackBean;
import com.jdhd.qynovels.persenter.impl.bookcase.IDelBookRankPresenterImpl;
import com.jdhd.qynovels.ui.activity.XqActivity;
import com.jdhd.qynovels.view.bookcase.IDelBookRankView;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> implements IDelBookRankView {

    private Context context;
    private List<CaseBean.DataBean.ListBean> list=new ArrayList<>();
    private onItemClick onItemClick;
    private IDelBookRankPresenterImpl delBookRankPresenter;

    public void setOnItemClick(ListAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }
    public void refresh(List<CaseBean.DataBean.ListBean> list){
        this.list=list;
        notifyDataSetChanged();
    }
    public ListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        delBookRankPresenter=new IDelBookRankPresenterImpl(this);
        View view= LayoutInflater.from(context).inflate(R.layout.item_case,parent,false);
        ListViewHolder viewHolder=new ListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, final int position) {
        if(list.size()==0){
            return;
        }
        if(position==list.size()){
            holder.book.setImageResource(R.mipmap.sj_tj);
            holder.name.setVisibility(View.GONE);
            holder.xh.setVisibility(View.VISIBLE);
            holder.zj.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.onxhClick(position);
                }
            });
        }
        else{
            Glide.with(context).load(list.get(position).getImage()).into(holder.book);
            holder.name.setText(list.get(position).getName());
            holder.zj.setText("读到："+list.get(position).getReadContent());
            if(list.get(position).getReadStatus()==20){
               holder.yd.setVisibility(View.VISIBLE);
            }
            holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, XqActivity.class);
                    intent.putExtra("xq",2);
                    intent.putExtra("id",list.get(position).getBookId());
                    context.startActivity(intent);
                }
            });
            holder.del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  delBookRankPresenter.setId(list.get(position).getId());
                  delBookRankPresenter.loadData();
                  list.remove(position);
                  notifyDataSetChanged();
                  holder.sml.quickClose();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(list==null){
            return 0;
        }
        else{
            if(list.size()==0){
                return 1;
            }
            else{
                return list.size()+1;
            }
        }


    }

    @Override
    public void onSuccess(DelBookRackBean delBookRackBean) {
        Log.e("del","删除成功");


    }

    @Override
    public void onAddError(String error) {
       Log.e("delbookerror",error);
    }

    class ListViewHolder extends RecyclerView.ViewHolder{
        private ImageView book;
        private TextView name,zj,yd,tg,xh;
        private Button del;
        private SwipeMenuLayout sml;
        private LinearLayout ll;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            book=itemView.findViewById(R.id.case_book);
            name=itemView.findViewById(R.id.case_name);
            zj=itemView.findViewById(R.id.case_zj);
            yd=itemView.findViewById(R.id.case_yd);
            tg=itemView.findViewById(R.id.case_tg);
            xh=itemView.findViewById(R.id.case_xh);
            del=itemView.findViewById(R.id.case_del);
            sml=itemView.findViewById(R.id.case_sml);
            ll=itemView.findViewById(R.id.case_ll);
        }
    }
    public interface onItemClick{
        void onlistClick(int index);
        void onxhClick(int index);
    }
}
