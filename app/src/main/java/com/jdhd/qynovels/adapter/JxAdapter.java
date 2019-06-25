package com.jdhd.qynovels.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.ui.activity.FlActivity;
import com.jdhd.qynovels.ui.activity.JrgxActivity;
import com.jdhd.qynovels.ui.activity.PhbActivity;
import com.jdhd.qynovels.ui.activity.WjjpActivity;
import com.jdhd.qynovels.ui.activity.XqActivity;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class JxAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener,RsAdapter.onItemClick,KdAdapter.onItemClick,SsAdapter.onItemClick,XsAdapter.onItemClick,GfAdapter.onItemClick{
    public static final int TYPE_BANNER=0;
    public static final int TYPE_TYPE=1;
    public static final int TYPE_LINE=2;
    public static final int TYPE_RS=3;
    public static final int TYPE_KD=4;
    public static final int TYPE_SS=5;
    public static final int TYPE_XS=6;
    public static final int TYPE_GF=7;
    private Context context;

    public JxAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder=null;
        if(viewType==TYPE_BANNER){
           View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner, null, false);
           viewHolder=new BannerViewHolder(view);
        }
        else if(viewType==TYPE_TYPE){
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type, parent, false);
            viewHolder=new TypeViewHolder(view);
        }
        else if(viewType==TYPE_LINE){
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_line, parent, false);
            viewHolder=new LineViewHolder(view);
        }
        else if(viewType==TYPE_RS){
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_base, parent, false);
            viewHolder=new RsViewHolder(view);
        }
        else if(viewType==TYPE_KD){
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_base, parent, false);
            viewHolder=new KdViewHolder(view);
        }
        else if(viewType==TYPE_SS){
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_base, parent, false);
            viewHolder=new SsViewHolder(view);
        }
        else if(viewType==TYPE_XS){
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xs, parent, false);
            viewHolder=new XsViewHolder(view);
        }
        else if(viewType==TYPE_GF){
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xs, parent, false);
            viewHolder=new GfViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
       if(holder instanceof BannerViewHolder){
           BannerViewHolder viewHolder= (BannerViewHolder) holder;
           List<Integer> imglist=new ArrayList<>();
           imglist.add(R.mipmap.ggw_sy);
           imglist.add(R.mipmap.ggw_sy);
           imglist.add(R.mipmap.ggw_sy);
           viewHolder.banner.setImageLoader(new GlideImageLoader());   //设置图片加载器
           viewHolder.banner.setImages(imglist);  //设置banner中显示图片
           viewHolder.banner.start();  //设置完毕后调用
       }
       else if(holder instanceof TypeViewHolder){
           TypeViewHolder viewHolder= (TypeViewHolder) holder;
           viewHolder.fl.setOnClickListener(this);
           viewHolder.phb.setOnClickListener(this);
           viewHolder.wjjp.setOnClickListener(this);
           viewHolder.jrgx.setOnClickListener(this);
       }
       else if(holder instanceof LineViewHolder){
           LineViewHolder viewHolder= (LineViewHolder) holder;
       }
       else if(holder instanceof RsViewHolder){
           RsViewHolder viewHolder= (RsViewHolder) holder;
           viewHolder.tex.setText("今日大热搜");
           GridLayoutManager manager=new GridLayoutManager(context, 3);
           viewHolder.rv.addItemDecoration(new SpaceItemDecoration(20));
           viewHolder.rv.setLayoutManager(manager);
           RsAdapter adapter=new RsAdapter(context);
           viewHolder.rv.setAdapter(adapter);
           adapter.setOnItemClick(this);
       }
       else if(holder instanceof KdViewHolder){
           KdViewHolder viewHolder= (KdViewHolder) holder;
           viewHolder.tex.setText("本周看点");
           LinearLayoutManager manager=new LinearLayoutManager(context);
           manager.setOrientation(RecyclerView.HORIZONTAL);
           viewHolder.rv.setLayoutManager(manager);
           KdAdapter adapter=new KdAdapter(context);
           viewHolder.rv.setAdapter(adapter);
           adapter.setOnItemClick(this);
       }
       else if(holder instanceof SsViewHolder){
           SsViewHolder viewHolder= (SsViewHolder) holder;
           viewHolder.tex.setText("实时热搜");
           GridLayoutManager manager=new GridLayoutManager(context, 4);
           viewHolder.rv.setLayoutManager(manager);
           SsAdapter adapter= new SsAdapter(context,1);
           viewHolder.rv.setAdapter(adapter);
           adapter.setOnItemClick(this);
       }
       else if(holder instanceof XsViewHolder){
           XsViewHolder viewHolder= (XsViewHolder) holder;
           viewHolder.tex.setText("新书抢先");
           GridLayoutManager manager=new GridLayoutManager(context, 4);
           viewHolder.rv.setLayoutManager(manager);
           XsAdapter adapter=new XsAdapter(context);
           viewHolder.rv.setAdapter(adapter);
           adapter.setOnItemClick(this);
       }
       else if(holder instanceof GfViewHolder){
           GfViewHolder viewHolder= (GfViewHolder) holder;
           viewHolder.tex.setText("高分精选");
           LinearLayoutManager manager=new LinearLayoutManager(context);
           viewHolder.rv.setLayoutManager(manager);
           GfAdapter adapter=new GfAdapter(context,0);
           viewHolder.rv.setAdapter(adapter);
           adapter.setOnItemClick(this);
       }
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return TYPE_BANNER;
        }
        else if(position==1){
            return TYPE_TYPE;
        }
        else if(position==2){
            return TYPE_LINE;
        }
        else if(position==3){
            return TYPE_RS;
        }
        else if(position==4){
            return TYPE_KD;
        }
        else if(position==5){
            return TYPE_SS;
        }
        else if(position==6){
            return TYPE_XS;
        }
        else if(position==7){
            return TYPE_GF;
        }
        return -1;
    }

    @Override
    public void onClick(View view) {
       if(R.id.home_fl==view.getId()){
           Intent intent=new Intent(context, FlActivity.class);
           context.startActivity(intent);
       }
       else if(R.id.home_phb==view.getId()){
           Intent intent=new Intent(context, PhbActivity.class);
           context.startActivity(intent);
       }
       else if(R.id.home_wjjp==view.getId()){
           Intent intent=new Intent(context, WjjpActivity.class);
           context.startActivity(intent);
       }
       else if(R.id.home_jrgx==view.getId()){
           Intent intent=new Intent(context, JrgxActivity.class);
           context.startActivity(intent);
       }
    }

    @Override
    public void onRsclick(int index) {
        if(index==5){
            Intent intent=new Intent(context, PhbActivity.class);
            context.startActivity(intent);
        }
        else{
            Intent intent=new Intent(context, XqActivity.class);
            context.startActivity(intent);
        }
    }

    @Override
    public void onGfclick(int index) {
        Intent intent=new Intent(context, XqActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onKdclick(int index) {
        Intent intent=new Intent(context, XqActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onSsclick(int index) {
        Intent intent=new Intent(context, XqActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onXsclick(int index) {
        Intent intent=new Intent(context, XqActivity.class);
        context.startActivity(intent);
    }

    /**
     * 轮播图
     */
    class BannerViewHolder extends RecyclerView.ViewHolder{
        private Banner banner;
        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            banner=itemView.findViewById(R.id.jx_banner);
        }
    }

    class TypeViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout fl,phb,wjjp,jrgx;
        public TypeViewHolder(@NonNull View itemView) {
            super(itemView);
            fl=itemView.findViewById(R.id.home_fl);
            phb=itemView.findViewById(R.id.home_phb);
            wjjp=itemView.findViewById(R.id.home_wjjp);
            jrgx=itemView.findViewById(R.id.home_jrgx);
        }
    }

    class LineViewHolder extends RecyclerView.ViewHolder{

        public LineViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class RsViewHolder extends RecyclerView.ViewHolder{
        private TextView tex;
        private RecyclerView rv;
        public RsViewHolder(@NonNull View itemView) {
            super(itemView);
            tex=itemView.findViewById(R.id.home_tex);
            rv=itemView.findViewById(R.id.home_rv);
        }
    }
    class KdViewHolder extends RecyclerView.ViewHolder{
        private TextView tex;
        private RecyclerView rv;
        public KdViewHolder(@NonNull View itemView) {
            super(itemView);
            tex=itemView.findViewById(R.id.home_tex);
            rv=itemView.findViewById(R.id.home_rv);
        }
    }

    class SsViewHolder extends RecyclerView.ViewHolder{
        private TextView tex;
        private RecyclerView rv;
        public SsViewHolder(@NonNull View itemView) {
            super(itemView);
            tex=itemView.findViewById(R.id.home_tex);
            rv=itemView.findViewById(R.id.home_rv);
        }
    }

    class XsViewHolder extends RecyclerView.ViewHolder{
        private TextView tex;
        private RecyclerView rv;
        public XsViewHolder(@NonNull View itemView) {
            super(itemView);
            tex=itemView.findViewById(R.id.home_tex);
            rv=itemView.findViewById(R.id.home_rv);
        }
    }

    class GfViewHolder extends RecyclerView.ViewHolder{
        private TextView tex;
        private RecyclerView rv;
        public GfViewHolder(@NonNull View itemView) {
            super(itemView);
            tex=itemView.findViewById(R.id.home_tex);
            rv=itemView.findViewById(R.id.home_rv);
        }
    }
    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    }
    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        private int space;
        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            //不是第一个的格子都设一个左边和底部的间距
            outRect.left = space;
            outRect.bottom = space;
            //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
            if (parent.getChildLayoutPosition(view) %3==0) {
                outRect.left = 0;
            }
        }
    }


}
