package com.jdhd.qynovels.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.ui.activity.XqActivity;

public class XqAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements GfAdapter.onItemClick{
    public static final int TYPE_IMG=0;
    public static final int TYPE_GFJX=1;
    private Context context;

    public XqAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder=null;
        if(viewType==TYPE_IMG){
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xqimg, parent, false);
            viewHolder=new ImgViewHolder(view);
        }
        else if(viewType==TYPE_GFJX){
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xqgfjx, parent, false);
            viewHolder=new GfjxViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ImgViewHolder){
            ImgViewHolder viewHolder= (ImgViewHolder) holder;
//            Glide.with(context).load(R.mipmap.a)
//                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
//                    .into(viewHolder.img);
        }
        else if(holder instanceof GfjxViewHolder){
            GfjxViewHolder viewHolder= (GfjxViewHolder) holder;
            LinearLayoutManager manager=new LinearLayoutManager(context);
            viewHolder.rv.setLayoutManager(manager);
            GfAdapter adapter=new GfAdapter(context,1);
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
            return TYPE_IMG;
        }
        else if(position==1){
            return TYPE_GFJX;
        }
        return -1;
    }

    @Override
    public void onGfclick(int index) {
        Intent intent=new Intent(context, XqActivity.class);
        context.startActivity(intent);
    }

    class ImgViewHolder extends RecyclerView.ViewHolder{
        private ImageView img,close;
        public ImgViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.xq_img);
            close=itemView.findViewById(R.id.xq_close);
        }
    }

    class GfjxViewHolder extends RecyclerView.ViewHolder{
        private TextView hyh;
        private RecyclerView rv;
        public GfjxViewHolder(@NonNull View itemView) {
            super(itemView);
            hyh=itemView.findViewById(R.id.xq_hyh);
            rv=itemView.findViewById(R.id.xq_jxrv);
        }
    }
}
