package com.jdhd.qynovels.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.ui.activity.XqActivity;

public class BookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SsAdapter.onItemClick,XsAdapter.onItemClick,GfAdapter.onItemClick{
    public static final int TYPE_IMG=0;
    public static final int TYPE_TEX=1;
    public static final int TYPE_TJ=2;
    public static final int TYPE_HTJ=3;
    public static final int TYPE_HP=4;
    public int TYPE_AK=5;
    private Context context;
    private int tp;//推荐类型
    private int type;//男生、女生、图书区分


    public BookAdapter(Context context, int tp, int type) {
        this.context = context;
        this.tp = tp;
        this.type = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder=null;
        if(type==3){
            TYPE_AK=4;
            if(viewType==TYPE_IMG){
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_img, parent, false);
                viewHolder=new ImgViewHolder(view);
            }
            else if(viewType==TYPE_TEX){
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tex, parent, false);
                viewHolder=new TexViewHolder(view);
            }
            else if(viewType==TYPE_TJ){
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tj, parent, false);
                viewHolder=new TjViewHolder(view);
            }
            else if(viewType==TYPE_HTJ){
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
                viewHolder=new HtjViewHolder(view);
            }
            else if(viewType==TYPE_AK){
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xs, parent, false);
                viewHolder=new AkViewHolder(view);
            }
        }
        else{
            if(viewType==TYPE_IMG){
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_img, parent, false);
                viewHolder=new ImgViewHolder(view);
            }
            else if(viewType==TYPE_TEX){
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tex, parent, false);
                viewHolder=new TexViewHolder(view);
            }
            else if(viewType==TYPE_TJ){
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tj, parent, false);
                viewHolder=new TjViewHolder(view);
            }
            else if(viewType==TYPE_HTJ){
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
                viewHolder=new HtjViewHolder(view);
            }
            else if(viewType==TYPE_HP){
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xs, parent, false);
                viewHolder=new HpViewHolder(view);
            }
            else if(viewType==TYPE_AK){
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xs, parent, false);
                viewHolder=new AkViewHolder(view);
            }
        }


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ImgViewHolder){
            ImgViewHolder viewHolder= (ImgViewHolder) holder;
            Glide.with(context).load(R.mipmap.book_100)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                    .into(((ImgViewHolder) holder).img);

        }
        else if(holder instanceof TexViewHolder){
            TexViewHolder viewHolder= (TexViewHolder) holder;
        }
        else if(holder instanceof TjViewHolder){
            TjViewHolder viewHolder= (TjViewHolder) holder;
            viewHolder.grade.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Oswald-Bold.otf"));
            if(tp==0){
               viewHolder.type.setVisibility(View.VISIBLE);
            }
            viewHolder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, XqActivity.class);
                    intent.putExtra("xq",2);
                    if(type==1){
                        intent.putExtra("lx",2);
                    }
                    else if(type==2){
                        intent.putExtra("lx",3);
                    }
                    else if(type==3){
                        intent.putExtra("lx",4);
                    }
                    context.startActivity(intent);
                }
            });
        }
        else if(holder instanceof HtjViewHolder){
            HtjViewHolder viewHolder= (HtjViewHolder) holder;
            GridLayoutManager manager=new GridLayoutManager(context,4);
            viewHolder.rv.setLayoutManager(manager);
            SsAdapter adapter=new SsAdapter(context,2);
            viewHolder.rv.setAdapter(adapter);
            adapter.setOnItemClick(this);
        }
        else if(holder instanceof HpViewHolder){
            HpViewHolder viewHolder= (HpViewHolder) holder;
            viewHolder.tex.setText("好评佳作");
            GridLayoutManager manager=new GridLayoutManager(context,4);
            viewHolder.rv.setLayoutManager(manager);
            XsAdapter adapter=new XsAdapter(context);
            viewHolder.rv.setAdapter(adapter);
            adapter.setOnItemClick(this);
        }
        else if(holder instanceof AkViewHolder){
            AkViewHolder viewHolder= (AkViewHolder) holder;
            if(type==1){
               viewHolder.tex.setText("男生都在看");
            }
            else if(type==2){
                viewHolder.tex.setText("女生都在看");
            }
            else{
                viewHolder.tex.setText("好评佳作");
            }
            LinearLayoutManager manager=new LinearLayoutManager(context);
            viewHolder.rv.setLayoutManager(manager);
            GfAdapter adapter=new GfAdapter(context,0);
            viewHolder.rv.setAdapter(adapter);
            adapter.setOnItemClick(this);
        }
    }

    @Override
    public int getItemCount() {
        if(type==3){
            return 5;
        }
        else {
            return 6;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(type==3){
            TYPE_AK=4;
            if(position==0){
                return TYPE_IMG;
            }
            else if(position==1){
                return TYPE_TEX;
            }
            else if(position==2){
                return TYPE_TJ;
            }
            else if(position==3){
                return TYPE_HTJ;
            }
            else if(position==4){
                return TYPE_AK;
            }
        }
        else{
            if(position==0){
                return TYPE_IMG;
            }
            else if(position==1){
                return TYPE_TEX;
            }
            else if(position==2){
                return TYPE_TJ;
            }
            else if(position==3){
                return TYPE_HTJ;
            }
            else if(position==4){
                return TYPE_HP;
            }
            else if(position==5){
                return TYPE_AK;
            }
        }
        return -1;
    }

    @Override
    public void onSsclick(int index) {
        Intent intent=new Intent(context, XqActivity.class);
        intent.putExtra("xq",2);
        if(type==1){
            intent.putExtra("lx",2);
        }
        else if(type==2){
            intent.putExtra("lx",3);
        }
        else if(type==3){
            intent.putExtra("lx",4);
        }

        context.startActivity(intent);
    }

    @Override
    public void onGfclick(int index) {
        Intent intent=new Intent(context, XqActivity.class);
        intent.putExtra("xq",2);
        if(type==1){
            intent.putExtra("lx",2);
        }
        else if(type==2){
            intent.putExtra("lx",3);
        }
        else if(type==3){
            intent.putExtra("lx",4);
        }
        context.startActivity(intent);
    }

    @Override
    public void onXsclick(int index) {
        Intent intent=new Intent(context, XqActivity.class);
        intent.putExtra("xq",2);
        if(type==1){
            intent.putExtra("lx",2);
        }
        else if(type==2){
            intent.putExtra("lx",3);
        }
        else if(type==3){
            intent.putExtra("lx",4);
        }
        context.startActivity(intent);
    }

    class ImgViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        public ImgViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.book_img);
        }
    }

    class TexViewHolder extends RecyclerView.ViewHolder{

        public TexViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class TjViewHolder extends RecyclerView.ViewHolder{
        private ImageView book;
        private TextView name,grade,des,num,type;
        private LinearLayout ll;
        public TjViewHolder(@NonNull View itemView) {
            super(itemView);
            book=itemView.findViewById(R.id.gf_book);
            name=itemView.findViewById(R.id.gf_name);
            grade=itemView.findViewById(R.id.gf_grade);
            des=itemView.findViewById(R.id.gf_des);
            num=itemView.findViewById(R.id.gf_num);
            type=itemView.findViewById(R.id.gf_type);
            ll=itemView.findViewById(R.id.tj_ll);
        }
    }

    class HtjViewHolder extends RecyclerView.ViewHolder{
        private RecyclerView rv;
        public HtjViewHolder(@NonNull View itemView) {
            super(itemView);
            rv=itemView.findViewById(R.id.home_rv);
        }
    }

    class HpViewHolder extends RecyclerView.ViewHolder{
        private TextView tex;
        private RecyclerView rv;
        public HpViewHolder(@NonNull View itemView) {
            super(itemView);
            tex=itemView.findViewById(R.id.home_tex);
            rv=itemView.findViewById(R.id.home_rv);
        }
    }

    class AkViewHolder extends RecyclerView.ViewHolder{
        private TextView tex;
        private RecyclerView rv;
        public AkViewHolder(@NonNull View itemView) {
            super(itemView);
            tex=itemView.findViewById(R.id.home_tex);
            rv=itemView.findViewById(R.id.home_rv);
        }
    }

}
