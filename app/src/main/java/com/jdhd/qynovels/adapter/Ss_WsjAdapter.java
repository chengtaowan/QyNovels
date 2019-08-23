package com.jdhd.qynovels.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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
import com.jdhd.qynovels.module.bookcase.SearchContentBean;
import com.jdhd.qynovels.ui.activity.XqActivity;
import com.jdhd.qynovels.utils.DeviceInfoUtils;

import java.util.ArrayList;
import java.util.List;

public class Ss_WsjAdapter extends RecyclerView.Adapter<Ss_WsjAdapter.Ss_WsjViewHolder>{
    private List<SearchContentBean.DataBean.ListBean> listBeans=new ArrayList<>();
    private Context context;

    public Ss_WsjAdapter(Context context) {
        this.context = context;
    }

    public void refresh(List<SearchContentBean.DataBean.ListBean> listBeans){
        this.listBeans=listBeans;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public Ss_WsjViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gf, parent, false);
        return new Ss_WsjViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Ss_WsjViewHolder holder, int position) {
        holder.grade.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Oswald-Bold.otf"));
        Log.e("recommendsize",listBeans.size()+"]]]");
        if(listBeans==null){
            return;
        }
        if(listBeans.get(position).getImage()!=null){
            GlideUrl url = DeviceInfoUtils.getUrl(listBeans.get(position).getImage());
            Glide.with(context).load(url)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(MyApp.raduis)))
                    .apply(new RequestOptions().error(R.mipmap.book_100))
                    .apply(new RequestOptions().placeholder(R.mipmap.book_100))
                    .into(holder.book);

        }
        Log.e("wsj",listBeans.get(position).toString());
        holder.name.setText(listBeans.get(position).getName());
        holder.grade.setText(listBeans.get(position).getGrade()+"");
        holder.des.setText(listBeans.get(position).getIntro());
        holder.num.setText(listBeans.get(position).getNumber()+"万字");
        holder.type.setVisibility(View.GONE);
        if(listBeans.get(position).getFinishStatus()==0){
            holder.wj.setText("完结");
        }
        else{
            holder.wj.setText("连载");
        }
        //holder.num.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, XqActivity.class);
                intent.putExtra("xq",2);
                intent.putExtra("id",listBeans.get(position).getBookId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBeans.size();
    }

    class Ss_WsjViewHolder extends RecyclerView.ViewHolder{
        private ImageView book;
        private TextView name,grade,des,num,type,wj,fen;
        public Ss_WsjViewHolder(@NonNull View itemView) {
            super(itemView);
            book=itemView.findViewById(R.id.gf_book);
            name=itemView.findViewById(R.id.gf_name);
            grade=itemView.findViewById(R.id.gf_grade);
            des=itemView.findViewById(R.id.gf_des);
            num=itemView.findViewById(R.id.gf_num);
            type=itemView.findViewById(R.id.gf_type);
            wj=itemView.findViewById(R.id.gf_wj);
            fen=itemView.findViewById(R.id.gf_fen);
        }
    }
}
