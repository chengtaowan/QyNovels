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
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.glong.reader.activities.ExtendReaderActivity;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.module.bookcase.CaseBean;
import com.jdhd.qynovels.module.bookcase.DelBookRackBean;
import com.jdhd.qynovels.persenter.impl.bookcase.IDelBookRankPresenterImpl;
import com.jdhd.qynovels.ui.activity.MainActivity;
import com.jdhd.qynovels.ui.activity.XqActivity;
import com.jdhd.qynovels.view.bookcase.IDelBookRankView;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> implements IDelBookRankView {

    private Context context;
    private FragmentActivity activity;
    private List<CaseBean.DataBean.ListBean> list=new ArrayList<>();

    private IDelBookRankPresenterImpl delBookRankPresenter;
    public void refresh(List<CaseBean.DataBean.ListBean> list){
        this.list=list;
        notifyDataSetChanged();
    }

    public ListAdapter(Context context, FragmentActivity activity) {
        this.context = context;
        this.activity = activity;
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
            else{
                Glide.with(context).load(list.get(position).getImage()).into(holder.book);
                holder.name.setText(list.get(position).getName());
                holder.zj.setText("读到："+list.get(position).getReadContent());
                holder.ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(context, ExtendReaderActivity.class);
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
        return list.size();
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
            tg=itemView.findViewById(R.id.case_tg);
            xh=itemView.findViewById(R.id.case_xh);
            del=itemView.findViewById(R.id.case_del);
            sml=itemView.findViewById(R.id.case_sml);
            ll=itemView.findViewById(R.id.case_ll);
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
