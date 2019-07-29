package com.jdhd.qynovels.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.module.bookcase.CaseBean;
import com.jdhd.qynovels.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class CaseContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_LIST=0;
    private static final int TYPE_FOOT=1;
    private Context context;
    private FragmentActivity activity;
    private List<CaseBean.DataBean.ListBean> list=new ArrayList<>();
    public void refresh(List<CaseBean.DataBean.ListBean> list){
        this.list=list;
        notifyDataSetChanged();
    }
    private List<TTFeedAd> feedlist=new ArrayList<>();
    public void refreshfeed(List<TTFeedAd> feedlist){
        this.feedlist=feedlist;
        notifyDataSetChanged();
    }

    public CaseContentAdapter(Context context, FragmentActivity activity) {
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder=null;
        if(viewType==TYPE_LIST){
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null, false);
            viewHolder=new ListViewHolder(view);
        }
        else if(viewType==TYPE_FOOT){
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foot, parent, false);
            viewHolder=new FootViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ListViewHolder){
            LinearLayoutManager manager=new LinearLayoutManager(context);
            ((ListViewHolder) holder).rv.setLayoutManager(manager);
            ListAdapter adapter=new ListAdapter(context,activity);
            adapter.refresh(list);
            adapter.refreshfeed(feedlist);
            ((ListViewHolder) holder).rv.setAdapter(adapter);
        }
        else if(holder instanceof FootViewHolder){
           ((FootViewHolder) holder).xh.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   toFragment(1);
                   MainActivity.rb_shop.setChecked(true);
               }
           });
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return TYPE_LIST;
        }
        else if(position==1){
            return TYPE_FOOT;
        }
        return super.getItemViewType(position);
    }
    class ListViewHolder extends RecyclerView.ViewHolder{
        private RecyclerView rv;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            rv=itemView.findViewById(R.id.rv);
        }
    }
    class FootViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout xh;
        public FootViewHolder(@NonNull View itemView) {
            super(itemView);
            xh=itemView.findViewById(R.id.xh);
        }
    }
    private void toFragment(final int i) {
        final MainActivity mainActivity = (MainActivity) activity;
        mainActivity.setFragment2Fragment(new MainActivity.Fragment2Fragment() {
            @Override
            public void gotoFragment(ViewPager viewPager) {
                viewPager.setCurrentItem(i);
            }
        });
        mainActivity.forSkip();
    }
}
