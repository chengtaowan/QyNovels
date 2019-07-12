package com.jdhd.qynovels.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.bookshop.ShopBean;

import java.util.ArrayList;
import java.util.List;

public class JrgxAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_BookUpdate= MyApp.ModuleType.kSectionTypeHotBookUpdate;
    private static final int TYPE_BookUpdateList= MyApp.ModuleType.kSectionTypeHotBookUpdateList;
    private List<ShopBean.DataBean.ListBeanX> list=new ArrayList<>();
    private Context context;

    public JrgxAdapter(Context context) {
        this.context = context;
    }

    public void refresh(List<ShopBean.DataBean.ListBeanX> list){
        this.list=list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder=null;
        if(viewType==TYPE_BookUpdate){
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xstop, parent, false);
            viewHolder=new BookUpdateViewHolder(view);
        }
        else if(viewType==TYPE_BookUpdateList){
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
            viewHolder=new BookUpdateListViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(list.size()==0){
            return;
        }
       if(holder instanceof BookUpdateViewHolder){
           BookUpdateViewHolder viewHolder= (BookUpdateViewHolder) holder;
           if(list.get(position).getType()==TYPE_BookUpdate){
               viewHolder.tex.setText(list.get(position).getName());
               GridLayoutManager manager=new GridLayoutManager(context, 4);
               viewHolder.rv.setLayoutManager(manager);
               XsAdapter adapter=new XsAdapter(context);
               adapter.refresh(list.get(position).getList());
               viewHolder.rv.setAdapter(adapter);
           }
       }
       else if(holder instanceof BookUpdateListViewHolder){
           BookUpdateListViewHolder viewHolder= (BookUpdateListViewHolder) holder;
           if(list.get(position).getType()==TYPE_BookUpdateList){
               LinearLayoutManager manager=new LinearLayoutManager(context);
               viewHolder.rv.setLayoutManager(manager);
               GfAdapter adapter=new GfAdapter(context,0,0);
               adapter.refresh(list.get(position).getList());
               viewHolder.rv.setAdapter(adapter);
           }
       }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return TYPE_BookUpdate;
        }
        else if(position==1){
            return TYPE_BookUpdateList;
        }
        return -1;
    }
    class BookUpdateViewHolder extends RecyclerView.ViewHolder{
        private TextView tex;
        private RecyclerView rv;
        public BookUpdateViewHolder(@NonNull View itemView) {
            super(itemView);
            tex=itemView.findViewById(R.id.home_tex);
            rv=itemView.findViewById(R.id.home_rv);
        }
    }

    class BookUpdateListViewHolder extends RecyclerView.ViewHolder{
        private RecyclerView rv;
        public BookUpdateListViewHolder(@NonNull View itemView) {
            super(itemView);
            rv=itemView.findViewById(R.id.home_rv);
        }
    }
}
