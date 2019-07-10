package com.jdhd.qynovels.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.module.bookcase.BookInfoBean;
import com.jdhd.qynovels.ui.activity.MuluActivity;
import com.jdhd.qynovels.ui.activity.XqActivity;
import com.jdhd.qynovels.utils.FastBlurUtil;
import com.jdhd.qynovels.widget.ExpandTextView;
import com.jdhd.qynovels.widget.RatingBar;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class XqymAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements GfAdapter.onItemClick{
    private static final int TYEP_BOOK=0;
    private static final int TYEP_TOP=1;
    private static final int TYEP_CENTER=2;
    private static final int TYEP_BOTTOM=3;
    private Context context;
    private Activity activity;

    public XqymAdapter(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    private BookInfoBean.DataBean dataBean=new BookInfoBean.DataBean();
    private List<BookInfoBean.DataBean.ListBean> xlist=new ArrayList<>();
    public void refresh(BookInfoBean.DataBean dataBean){
        this.dataBean=dataBean;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder=null;
        if(viewType==TYEP_BOOK){
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xq, parent, false);
            viewHolder=new BookViewHolder(view);
        }
        else if(viewType==TYEP_TOP){
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xqtop, parent, false);
            viewHolder=new TopViewHolder(view);
        }
        else if(viewType==TYEP_CENTER){
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xqcenter, parent, false);
            viewHolder=new CenterViewHolder(view);
        }
        else if(viewType==TYEP_BOTTOM){
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xqbottom, parent, false);
            viewHolder=new BottomViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
       if(holder instanceof BookViewHolder){
           BookViewHolder viewHolder= (BookViewHolder) holder;
           if(dataBean.getBook()==null){
               return;
           }
           new MyThread(dataBean.getBook().getImage(),viewHolder.bj).start();
           Glide.with(context).load(dataBean.getBook().getImage()).into(viewHolder.book);
           //Glide.with(XqActivity.this).load(bookInfoBean.getData().getBook().getImage()).into(bj);
           //       scaledBitmap为目标图像，10是缩放的倍数（越大模糊效果越高）
           if(dataBean.getBook().getFinishStatus()==10){
               viewHolder.finishtype.setText("连载");
           }
           else{
               viewHolder.finishtype.setText("完结");
           }

           viewHolder.name.setText(dataBean.getBook().getName());
           viewHolder.num.setText(dataBean.getBook().getGrade()+"");
           viewHolder.author.setText(dataBean.getBook().getAuthor());
           viewHolder.zs.setText(dataBean.getBook().getNumber()+"字");
           BigDecimal bigDecimal=new BigDecimal(viewHolder.num.getText().toString());
           BigDecimal pf = bigDecimal.setScale(0, BigDecimal.ROUND_UP);
           int zs= (Integer.parseInt(pf.toString())/2);
           int ys=(Integer.parseInt(pf.toString())%2);
           viewHolder.start.setSelected(false);

           viewHolder.start.setStartTotalNumber(zs+ys);
           if(ys==0){
               viewHolder.start.setSelectedNumber(Float.parseFloat(zs+0.8+""));
           }
           else if(ys==1){
               viewHolder.start.setSelectedNumber(Float.parseFloat(zs+0.5+""));
           }
       }
       else if(holder instanceof TopViewHolder){
           TopViewHolder viewHolder= (TopViewHolder) holder;
           if(dataBean.getBook()==null){
               return;
           }
           viewHolder.rq.setText(dataBean.getBook().getAttention()+"");
           viewHolder.zd.setText(dataBean.getBook().getReading()+"");
           viewHolder.bookdes.setText(dataBean.getBook().getIntro());
           viewHolder.classname.setText(dataBean.getBook().getClassName());
           viewHolder.close.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   viewHolder.gg.setVisibility(View.GONE);
               }
           });
           viewHolder.ml.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent intent=new Intent(context, MuluActivity.class);
                   intent.putExtra("id",dataBean.getBook().getId());
                   intent.putExtra("name",dataBean.getBook().getName());
                   context.startActivity(intent);
               }
           });
       }
       else if(holder instanceof CenterViewHolder){
           if(dataBean.getList()==null){
               return;
           }
           CenterViewHolder viewHolder= (CenterViewHolder) holder;
           LinearLayoutManager manager=new LinearLayoutManager(context);
           viewHolder.rv.setLayoutManager(manager);
           GfAdapter adapter=new GfAdapter(context,1,1);
           int[] ints = randomCommon(0, dataBean.getList().size() - 1, 3);
           for(int i=0;i<ints.length;i++){
               xlist.add(dataBean.getList().get(ints[i]));
           }
           adapter.refreshbooklist(xlist);
           viewHolder.rv.setAdapter(adapter);
           adapter.setOnItemClick(this);
           viewHolder.hyh.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   int[] ints = randomCommon(0, dataBean.getList().size() - 1, 3);
                   xlist.clear();
                   for(int i=0;i<ints.length;i++){
                       xlist.add(dataBean.getList().get(ints[i]));
                   }
                   adapter.refreshbooklist(xlist);
                   adapter.setOnItemClick(new GfAdapter.onItemClick() {
                       @Override
                       public void onGfclick(int index) {
                           Intent intent=new Intent(context, XqActivity.class);
                           intent.putExtra("id",xlist.get(index).getId());
                           context.startActivity(intent);
                       }
                   });
               }
           });
       }
       else if(holder instanceof BottomViewHolder){
           BottomViewHolder viewHolder= (BottomViewHolder) holder;
       }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
           return TYEP_BOOK;
        }
        else if(position==1){
            return TYEP_TOP;
        }
        else if(position==2){
            return TYEP_CENTER;
        }
        else if(position==3){
            return TYEP_BOTTOM;
        }
        return -1;
    }

    @Override
    public void onGfclick(int index) {
        Intent intent=new Intent(context, XqActivity.class);
        intent.putExtra("id",xlist.get(index).getId());
        context.startActivity(intent);
    }

    class BookViewHolder extends RecyclerView.ViewHolder{
        private ImageView bj,back,book;
        private RatingBar start;
        private TextView num,name,author,finishtype,zs;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bj=itemView.findViewById(R.id.xq_bj);
            back=itemView.findViewById(R.id.xq_back);
            book=itemView.findViewById(R.id.xq_book);
            num=itemView.findViewById(R.id.xq_num);
            name=itemView.findViewById(R.id.xq_name);
            author=itemView.findViewById(R.id.xq_author);
            finishtype=itemView.findViewById(R.id.xq_finishtype);
            start=itemView.findViewById(R.id.xq_start);
            zs=itemView.findViewById(R.id.xq_zs);

        }
    }

    class TopViewHolder extends RecyclerView.ViewHolder{
        private TextView rq,zd,classname;
        private ExpandTextView bookdes;
        private RelativeLayout ml;
        private ImageView img,close;
        private RelativeLayout gg;
        public TopViewHolder(@NonNull View itemView) {
            super(itemView);
            rq=itemView.findViewById(R.id.xq_rq);
            zd=itemView.findViewById(R.id.xq_zd);
            bookdes=itemView.findViewById(R.id.xq_bookdes);
            classname=itemView.findViewById(R.id.xq_type);
            ml=itemView.findViewById(R.id.ml);
            img=itemView.findViewById(R.id.xq_img);
            close=itemView.findViewById(R.id.xq_close);
            gg=itemView.findViewById(R.id.xq_gg);
        }
    }

    class CenterViewHolder extends RecyclerView.ViewHolder{
        private TextView hyh;
        private RecyclerView rv;
        public CenterViewHolder(@NonNull View itemView) {
            super(itemView);
            hyh=itemView.findViewById(R.id.xq_hyh);
            rv=itemView.findViewById(R.id.xq_jxrv);
        }
    }

    class BottomViewHolder extends RecyclerView.ViewHolder{

        public BottomViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    public class MyThread extends Thread{
        private String url;
        private ImageView bj;

        public MyThread(String url, ImageView bj) {
            this.url = url;
            this.bj = bj;
        }

        @Override
        public void run() {
            super.run();
            final Bitmap blurBitmap2 = FastBlurUtil.GetUrlBitmap(url, 2);
            // 刷新ui必须在主线程中执行
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    bj.setImageBitmap(blurBitmap2);
                }
            });
        }
    }

    /**
     * 随机指定范围内N个不重复的数
     * 最简单最基本的方法
     * @param min 指定范围最小值
     * @param max 指定范围最大值
     * @param n 随机数个数
     */
    public static int[] randomCommon(int min, int max, int n){
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while(count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if(num == result[j]){
                    flag = false;
                    break;
                }
            }
            if(flag){
                result[count] = num;
                count++;
            }
        }
        return result;
    }

}
