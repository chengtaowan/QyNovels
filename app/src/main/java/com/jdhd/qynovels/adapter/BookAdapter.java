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

public class BookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SsAdapter.onItemClick,XsAdapter.onItemClick,GfAdapter.onItemClick{
//    public static final int TYPE_IMG=0;
//    public static final int TYPE_TEX=1;
    public static final int TYPE_ZBTJ= MyApp.ModuleType.kSectionTypeBlockbusterRecommended;
    public static final int TYPE_HPJZ=MyApp.ModuleType.kSectionTypeNewBookFresh;
    public static final int TYPE_AK=MyApp.ModuleType.kSectionTypeHighMarks;
    private Context context;
    private int type;
    private int tp;
    private List<ShopBean.DataBean.ListBeanX> list=new ArrayList();
    public void refresh(List<ShopBean.DataBean.ListBeanX> list){
        this.list=list;
        notifyDataSetChanged();
    }


    public BookAdapter(Context context, int type,int tp) {
        this.context = context;
        this.type = type;
        this.tp=tp;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder=null;
            if(viewType==TYPE_ZBTJ){
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tj, parent, false);
                viewHolder=new TjViewHolder(view);
            }
            else if(viewType==TYPE_HPJZ){
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xs, parent, false);
                viewHolder=new HpViewHolder(view);
            }
            else if(viewType==TYPE_AK){
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xs, parent, false);
                viewHolder=new AkViewHolder(view);
            }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

         if(holder instanceof TjViewHolder){
            TjViewHolder viewHolder= (TjViewHolder) holder;
            viewHolder.grade.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Oswald-Bold.otf"));
            if(tp==0){
               viewHolder.type.setVisibility(View.VISIBLE);
            }
            if(list.size()==0){
                return;
            }
             if(list.get(position).getType()==MyApp.ModuleType.kSectionTypeBlockbusterRecommended){
                 viewHolder.tex.setText(list.get(position).getName());
                 if(list.get(position).getList().get(0).getImage()!=null){
                     GlideUrl url = DeviceInfoUtils.getUrl(list.get(position).getList().get(0).getImage());
                     Glide.with(context).load(url).apply(RequestOptions.bitmapTransform(new RoundedCorners(MyApp.raduis))).into(viewHolder.book);

                 }
                 viewHolder.name.setText(list.get(position).getList().get(0).getName());
                 viewHolder.grade.setText(list.get(position).getList().get(0).getGrade());
                 viewHolder.des.setText(list.get(position).getList().get(0).getIntro());
                 viewHolder.type.setText(list.get(position).getList().get(0).getClassName());
                 viewHolder.num.setText(list.get(position).getList().get(0).getNumber()+"字");
                 GridLayoutManager manager=new GridLayoutManager(context, 4);
                 viewHolder.rv.setLayoutManager(manager);
                 SsAdapter adapter=new SsAdapter(context,2);
                 adapter.refresh(list.get(position).getList());
                 viewHolder.rv.setAdapter(adapter);
                 adapter.setOnItemClick(this);

             }
            viewHolder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, XqActivity.class);
                    intent.putExtra("xq",2);
                    intent.putExtra("id",list.get(position).getList().get(0).getBookId());
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
        else if(holder instanceof HpViewHolder){
            HpViewHolder viewHolder= (HpViewHolder) holder;
             if(list.size()==0){
                 return;
             }
             if(list.get(position).getType()==MyApp.ModuleType.kSectionTypeNewBookFresh){
                 viewHolder.tex.setText(list.get(position).getName());
                 GridLayoutManager manager=new GridLayoutManager(context,4);
                 viewHolder.rv.setLayoutManager(manager);
                 XsAdapter adapter=new XsAdapter(context);
                 adapter.refresh(list.get(position).getList());
                 viewHolder.rv.setAdapter(adapter);
                 adapter.setOnItemClick(this);
             }

        }
        else if(holder instanceof AkViewHolder){
            AkViewHolder viewHolder= (AkViewHolder) holder;
             if(list.size()==0){
                 return;
             }
             if(list.get(position).getType()==MyApp.ModuleType.kSectionTypeHighMarks){
                 viewHolder.tex.setText(list.get(position).getName());
                 LinearLayoutManager manager=new LinearLayoutManager(context);
                 viewHolder.rv.setLayoutManager(manager);
                 GfAdapter adapter=new GfAdapter(context,0,0);
                 adapter.refresh(list.get(position).getList());
                 viewHolder.rv.setAdapter(adapter);
                 adapter.setOnItemClick(this);
             }

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
            if(position==0){
                return TYPE_ZBTJ;
            }
            else if(position==1){
                return TYPE_HPJZ;
            }
            else if(position==2){
                return TYPE_AK;
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


    class TjViewHolder extends RecyclerView.ViewHolder{
        private ImageView book;
        private TextView name,grade,des,num,type,tex;
        private LinearLayout ll;
        private RecyclerView rv;
        public TjViewHolder(@NonNull View itemView) {
            super(itemView);
            book=itemView.findViewById(R.id.gf_book);
            name=itemView.findViewById(R.id.gf_name);
            grade=itemView.findViewById(R.id.gf_grade);
            des=itemView.findViewById(R.id.gf_des);
            num=itemView.findViewById(R.id.gf_num);
            type=itemView.findViewById(R.id.gf_type);
            ll=itemView.findViewById(R.id.tj_ll);
            rv=itemView.findViewById(R.id.tj_rv);
            tex=itemView.findViewById(R.id.tj_tex);
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
