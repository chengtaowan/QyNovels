package com.jdhd.qynovels.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.jdhd.qynovels.ui.activity.PhbActivity;
import com.jdhd.qynovels.ui.activity.XqActivity;
import com.jdhd.qynovels.utils.DeviceInfoUtils;

import java.util.ArrayList;
import java.util.List;

public class RsAdapter extends RecyclerView.Adapter<RsAdapter.RsViewHolder>{
    private Context context;
    private onItemClick onItemClick;
    private List<ShopBean.DataBean.ListBeanX.ListBean> list=new ArrayList<>();
    public void refresh(List<ShopBean.DataBean.ListBeanX.ListBean> list){
        this.list=list;
        notifyDataSetChanged();
    }

    public void setOnItemClick(RsAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public RsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rs, parent, false);
        RsViewHolder viewHolder=new RsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RsViewHolder holder, final int position) {
        if(list.get(position+1).getImage()!=null){
            GlideUrl url = DeviceInfoUtils.getUrl(list.get(position+1).getImage());
            Glide.with(context)
                    .load(url)
                    .apply(new RequestOptions().error(R.mipmap.book_100))
                    .apply(new RequestOptions().placeholder(R.mipmap.book_100))
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(MyApp.raduis))).into(holder.book);

        }
        holder.name.setText(list.get(position+1).getName());
        holder.num.setText(list.get(position+1).getHot()+"");
           holder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                  Intent intent=new Intent(context, XqActivity.class);
                  intent.putExtra("xq",2);
                  intent.putExtra("id",list.get(position+1).getBookId());
                  context.startActivity(intent);
               }
           });
    }

    @Override
    public int getItemCount() {
        return list.size()-1;
    }

    class RsViewHolder extends RecyclerView.ViewHolder{
        private ImageView book;
        private TextView name,num;
        public RsViewHolder(@NonNull View itemView) {
            super(itemView);
            book=itemView.findViewById(R.id.kd_book);
            name=itemView.findViewById(R.id.kd_name);
            num=itemView.findViewById(R.id.kd_num);
        }
    }
    public interface onItemClick{
        void onRsclick(int index);
    }
}
