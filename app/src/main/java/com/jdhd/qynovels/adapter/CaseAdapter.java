package com.jdhd.qynovels.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.glong.reader.activities.ExtendReaderActivity;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.module.bookcase.CaseBean;
import com.jdhd.qynovels.ui.activity.MainActivity;
import com.jdhd.qynovels.ui.activity.XqActivity;
import com.jdhd.qynovels.ui.fragment.ShopFragment;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private CaseBean.DataBean.HotBean hotBean=new CaseBean.DataBean.HotBean();
    private List<CaseBean.DataBean.ListBean> listBean=new ArrayList<>();
    private Context context;
    private FragmentActivity activity;
    public static final int TYPE_TITLE=0;
    public static final int TYPE_LIST=1;
    private onItemClick onItemClick;
    public void refreshhot(CaseBean.DataBean.HotBean hotBean){
        this.hotBean=hotBean;
        notifyDataSetChanged();
    }
    public void refreshlist(List<CaseBean.DataBean.ListBean> listBean){
        this.listBean=listBean;
        notifyDataSetChanged();
    }
    public void setOnItemClick(CaseAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public CaseAdapter(Context context,FragmentActivity activity) {
        this.context = context;
        this.activity=activity;
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
            View view= LayoutInflater.from(context).inflate(R.layout.item_caselist,null,false);
            viewHolder=new ListViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof TitleViewHolder){
            TitleViewHolder viewHolder= (TitleViewHolder) holder;
            if(hotBean==null){
                return;
            }
            viewHolder.des.setText(hotBean.getIntro());
            Glide.with(context).load(hotBean.getImage()).into(viewHolder.book);
            viewHolder.name.setText(hotBean.getName());
            viewHolder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, ExtendReaderActivity.class);
                    intent.putExtra("id",hotBean.getBookId());
                    context.startActivity(intent);
                }
            });
        }
        else if(holder instanceof ListViewHolder){
            ListViewHolder viewHolder= (ListViewHolder) holder;
            LinearLayoutManager layoutManager = new LinearLayoutManager(context) {
                @Override
                public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                    return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                }
            };
            viewHolder.rv.setLayoutManager(layoutManager);
            CaseContentAdapter adapter=new CaseContentAdapter(context,activity);
            adapter.refresh(listBean);
            viewHolder.rv.setAdapter(adapter);
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
        private SwipeRecyclerView rv;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            rv=itemView.findViewById(R.id.sj_rvlist);
        }
    }
    public interface onItemClick{
        void onClick(int index);
    }



}
