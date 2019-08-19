package com.jdhd.qynovels.adapter;

import android.content.Context;
import android.util.Log;
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
import com.jdhd.qynovels.module.bookshop.ClassBean;
import com.jdhd.qynovels.utils.DeviceInfoUtils;

import java.util.ArrayList;
import java.util.List;

public class FlAdapter extends RecyclerView.Adapter<FlAdapter.FlViewHolder>{
    private Context context;
    private onItemClick onItemClick;
    private List<ClassBean.DataBean.ListBean.ChildBean> list=new ArrayList<>();
    public void refresh(List<ClassBean.DataBean.ListBean.ChildBean> list){
        this.list=list;
        notifyDataSetChanged();
    }

    public void setOnItemClick(FlAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public FlAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FlViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_fl,parent,false);
        FlViewHolder viewHolder=new FlViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FlViewHolder holder, final int position) {
        if(list.get(position).getIcon()!=null){
            GlideUrl url = DeviceInfoUtils.getUrl(list.get(position).getIcon());
            Log.e("flimg",list.get(position).getIcon()+"--");
            Glide.with(context)
                    .load(url)
                    .apply(new RequestOptions().error(R.mipmap.book_100))
                    .apply(new RequestOptions().placeholder(R.mipmap.book_100))
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(MyApp.raduis))).into(holder.img);

        }
        holder.name.setText(list.get(position).getName());
        holder.num.setText(list.get(position).getBookNum()+"本");
        holder.type.setText(list.get(position).getDes());
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               onItemClick.onFlClick(position);
           }
       });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class FlViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView name,type,num;
        public FlViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.fl_img);
            name=itemView.findViewById(R.id.fl_name);
            type=itemView.findViewById(R.id.fl_type);
            num=itemView.findViewById(R.id.fl_num);
        }
    }
    public interface onItemClick{
        void onFlClick(int index);
    }
}
