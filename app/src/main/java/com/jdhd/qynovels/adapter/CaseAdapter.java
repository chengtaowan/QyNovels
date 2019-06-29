package com.jdhd.qynovels.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.module.BookBean;
import com.jdhd.qynovels.ui.activity.MainActivity;
import com.jdhd.qynovels.ui.activity.XqActivity;
import com.jdhd.qynovels.ui.fragment.ShopFragment;

import java.util.ArrayList;
import java.util.List;

public class CaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ListAdapter.onItemClick {
    private List<BookBean> list=new ArrayList<>();
    private Context context;
    private FragmentActivity activity;
    public static final int TYPE_TITLE=0;
    public static final int TYPE_LIST=1;
    private onItemClick onItemClick;

    public void setOnItemClick(CaseAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public CaseAdapter(Context context, FragmentActivity activity) {
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder=null;
        if(viewType==TYPE_TITLE){
            View view= LayoutInflater.from(context).inflate(R.layout.item_casetitle,parent,false);
            viewHolder=new TitleViewHolder(view);
        }
        else  if(viewType==TYPE_LIST){
            View view= LayoutInflater.from(context).inflate(R.layout.item_caselist,parent,false);
            viewHolder=new ListViewHolder(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof TitleViewHolder){
            TitleViewHolder viewHolder= (TitleViewHolder) holder;
            viewHolder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.onClick(position);
                }
            });
        }
        else if(holder instanceof ListViewHolder){
            for(int i=0;i<5;i++){
                list.add(new BookBean(R.mipmap.a,"冰与火之歌权力的游戏"+i,"读到：第一章：标题名"));
            }
            ListViewHolder viewHolder= (ListViewHolder) holder;
            LinearLayoutManager manager=new LinearLayoutManager(context);
            viewHolder.rv.setLayoutManager(manager);
            ListAdapter adapter=new ListAdapter(context,list);
            viewHolder.rv.setAdapter(adapter);
            adapter.setOnItemClick(this);
        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
           return TYPE_TITLE;
        }
        else if(position==1){
            return TYPE_LIST;
        }
        return -1;
    }

    @Override
    public void onlistClick(int index) {
        Intent intent=new Intent(context, XqActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onxhClick(int index) {
        FragmentManager manager=activity.getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.ll,new ShopFragment());
        MainActivity.rb_shop.setChecked(true);
        transaction.commit();
    }

    class TitleViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout ll;
        private ImageView book;
        private TextView name,des;
        public TitleViewHolder(@NonNull View itemView) {
            super(itemView);
            ll=itemView.findViewById(R.id.sj_ll);
            book=itemView.findViewById(R.id.sj_book);
            name=itemView.findViewById(R.id.sj_name);
            des=itemView.findViewById(R.id.sj_des);
        }
    }
    class ListViewHolder extends RecyclerView.ViewHolder{
        private RecyclerView rv;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            rv=itemView.findViewById(R.id.sj_rvlist);
        }
    }
    public interface onItemClick{
        void onClick(int index);
    }

}
