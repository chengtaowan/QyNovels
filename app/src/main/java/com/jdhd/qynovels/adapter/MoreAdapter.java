package com.jdhd.qynovels.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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
import com.jdhd.qynovels.module.bookshop.ClassContentBean;
import com.jdhd.qynovels.ui.activity.XqActivity;
import com.jdhd.qynovels.utils.DeviceInfoUtils;

import java.util.ArrayList;
import java.util.List;

public class MoreAdapter extends RecyclerView.Adapter<MoreAdapter.MoreViewHolder>{
    private Context context;
    private int type;
    private onItemClick onItemClick;
    private List<ClassContentBean.DataBean.ListBean> list=new ArrayList<>();
    public void refresh(List<ClassContentBean.DataBean.ListBean> list){
        this.list=list;
        notifyDataSetChanged();
    }

    public void setOnItemClick(MoreAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public MoreAdapter(Context context, int type) {
        this.context = context;
        this.type = type;
    }

    @NonNull
    @Override
    public MoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_more,parent,false);
        MoreViewHolder viewHolder=new MoreViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MoreViewHolder holder, final int position) {
        holder.grade.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Oswald-Bold.otf"));
        holder.type.setVisibility(View.VISIBLE);
        if(list.get(position).getImage()!=null){
            GlideUrl url = DeviceInfoUtils.getUrl(list.get(position).getImage());
            Glide.with(context).load(list.get(position).getImage()).apply(RequestOptions.bitmapTransform(new RoundedCorners(MyApp.raduis))).into(holder.book);

        }
        holder.name.setText(list.get(position).getName());
        holder.grade.setText(list.get(position).getGrade()+"");
        holder.des.setText(list.get(position).getIntro());
        if(list.get(position).getFinishStatus()==10){
            holder.wj.setText("连载");
        }
        else{
            holder.wj.setText("完结");
        }
        holder.num.setText(list.get(position).getNumber()+"字");
        holder.type.setText(list.get(position).getClassName());
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent(context, XqActivity.class);
               intent.putExtra("id",list.get(position).getBookId());
               intent.putExtra("xq",8);
               context.startActivity(intent);
           }
       });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MoreViewHolder extends RecyclerView.ViewHolder{
        private ImageView book;
        private TextView name,grade,fen,des,wj,num,type;
        public MoreViewHolder(@NonNull View itemView) {
            super(itemView);
            book=itemView.findViewById(R.id.gf_book);
            name=itemView.findViewById(R.id.gf_name);
            grade=itemView.findViewById(R.id.gf_grade);
            fen=itemView.findViewById(R.id.gf_fen);
            des=itemView.findViewById(R.id.gf_des);
            wj=itemView.findViewById(R.id.gf_wj);
            num=itemView.findViewById(R.id.gf_num);
            type=itemView.findViewById(R.id.gf_type);
        }
    }
    public interface onItemClick{
        void onClick(int index);
    }
}
