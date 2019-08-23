package com.jdhd.qynovels.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
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
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.bookcase.BookListBean;
import com.jdhd.qynovels.module.bookcase.CaseBean;
import com.jdhd.qynovels.persenter.impl.bookcase.IBookListPresenterImpl;
import com.jdhd.qynovels.ui.activity.MainActivity;
import com.jdhd.qynovels.ui.activity.XqActivity;
import com.jdhd.qynovels.ui.fragment.ShopFragment;
import com.jdhd.qynovels.utils.DbUtils;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.bookcase.IBookListView;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private CaseBean.DataBean.HotBean hotBean=new CaseBean.DataBean.HotBean();
    private List<CaseBean.DataBean.ListBean> listBean=new ArrayList<>();
    private Context context;
    private FragmentActivity activity;
    public static final int TYPE_LIST=1;
    private onItemClick onItemClick;
    private String token="";
    private DbUtils dbUtils;
    private SQLiteDatabase database;
    private String time;
    private IBookListPresenterImpl bookListPresenter;
    private BookListBean bookBean=new BookListBean();
    private List<TTFeedAd> feedlist=new ArrayList<>();
    public void refreshfeed(List<TTFeedAd> feedlist){
        this.feedlist=feedlist;
        notifyDataSetChanged();
    }
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
        dbUtils=new DbUtils(context);
        time= DeviceInfoUtils.getTime()+"";
        SharedPreferences preferences=context.getSharedPreferences("token", Context.MODE_PRIVATE);
        token = preferences.getString("token", "");
        RecyclerView.ViewHolder viewHolder=null;
        if(viewType==TYPE_LIST){
            View view= LayoutInflater.from(context).inflate(R.layout.item_caselist,null,false);
            viewHolder=new ListViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof ListViewHolder){
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
            adapter.refreshfeed(feedlist);
            viewHolder.rv.setAdapter(adapter);
        }

    }



    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return TYPE_LIST;
        }
        return -1;
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
