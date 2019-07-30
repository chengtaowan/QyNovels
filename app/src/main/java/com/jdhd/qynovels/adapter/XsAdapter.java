package com.jdhd.qynovels.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.bookshop.ShopBean;
import com.jdhd.qynovels.ui.activity.XqActivity;
import com.jdhd.qynovels.utils.DeviceInfoUtils;

import java.util.ArrayList;
import java.util.List;

public class XsAdapter extends RecyclerView.Adapter<XsAdapter.XsViewHolder>{
    private Context context;
    private onItemClick onItemClick;
    private List<ShopBean.DataBean.ListBeanX.ListBean> list=new ArrayList<>();
    public void refresh(List<ShopBean.DataBean.ListBeanX.ListBean> list){
        this.list=list;
        notifyDataSetChanged();
    }

    public void setOnItemClick(XsAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public XsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public XsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.xs, parent, false);
        XsViewHolder viewHolder=new XsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull XsViewHolder holder, final int position) {
        if(list.size()==0){
           return;
        }
        if(list.get(position).getImage()!=null){
            GlideUrl url = DeviceInfoUtils.getUrl(list.get(position).getImage());
            Glide.with(context).load(url).apply(RequestOptions.bitmapTransform(new RoundedCorners(MyApp.raduis))).into(holder.book);

        }
        holder.name.setText(list.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, XqActivity.class);
                intent.putExtra("xq",2);
                intent.putExtra("id",list.get(position).getBookId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class XsViewHolder extends RecyclerView.ViewHolder{
        private ImageView book;
        private TextView name;
        private XsViewHolder(@NonNull View itemView) {
            super(itemView);
            book=itemView.findViewById(R.id.ss_book);
            name=itemView.findViewById(R.id.ss_name);
        }
    }
    public interface onItemClick{
        void onXsclick(int index);
    }
}
